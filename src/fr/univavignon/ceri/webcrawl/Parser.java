
package fr.univavignon.ceri.webcrawl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class is used to parse a web page.
 * 
 * linksOnpage function return an array list of String contains all URLs
 * 
 * @author Abdelhakim RASFI
 * 
 */


public class Parser {


	private String url;
	private String body;
	public String bodyRobotsTxt;
	
	private ArrayList<String> sitemapUrls;
	
	public static boolean RESPECT_ROBOT_TXT = true;
	public static boolean NORESPECT_ROBOT_TXT = false;
	public static boolean RESPECT_SITEMAP = true;
	public static boolean NORESPECT_SITEMAP = false;

	public Parser(String _url, boolean robottxt, boolean sitemap) {
		this.url = _url;
		String content = null;
		String contentRobotsTxt = null;
		try {
			HttpClient client = HttpClient.newBuilder()
					.connectTimeout(Duration.ofSeconds(20))
					.followRedirects(Redirect.ALWAYS)
					.build();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(this.url))
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			content = response.body();
			
			String urlRobotsTxt = url.split("/")[0] + "//" + url.split("/")[1] + url.split("/")[2];
			urlRobotsTxt += "/robots.txt";
			System.out.println(urlRobotsTxt);
			HttpRequest request1 = HttpRequest.newBuilder()
					.uri(URI.create(urlRobotsTxt))
					.GET().build();
			HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
			contentRobotsTxt = response1.body();
		} catch (Exception ex) {
		}
		this.body = content;
		this.bodyRobotsTxt = contentRobotsTxt;
		
	}

	static public String getTitle(String url)
	{
		String content = null;
		String title = null;
		try {
			HttpClient client = HttpClient.newBuilder()
					.connectTimeout(Duration.ofSeconds(20))
					.followRedirects(Redirect.ALWAYS)
					.build();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET().build();
			HttpResponse<String> response;
				response = client.send(request, HttpResponse.BodyHandlers.ofString());
				content = response.body();
		String contentToLower = content.toLowerCase();
		int begin = contentToLower.indexOf("<title") + 7;
		int end = contentToLower.indexOf("</title>");
		//System.out.println(begin + " " + end);
		title = content.substring(begin, end).trim();
		title = title.substring(title.indexOf('>') + 1, title.length());
		System.out.println(title);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return title;
	}

	public ArrayList<String> linksOnRobotsTxt(){
		ArrayList<String> urls = new ArrayList<String>();
		String[] lines =  this.bodyRobotsTxt.split("\n");
		int isInBlock = 0;
		for (String line : lines)
		{
			if (line.toLowerCase().indexOf("user-agent:") != -1 && isInBlock == 1)
				break;
			if (line.toLowerCase().indexOf("user-agent: *") == -1 && isInBlock == 0)
			{
				continue;
			}
			if (isInBlock == 0)
			{
				isInBlock = 1;
				continue;
			}
			if (line.toLowerCase().indexOf("disallow") != -1)
			{
				line = line.replace("Disallow: ", "");
				line = url.split("/")[0] + "//" + url.split("/")[1] + url.split("/")[2] + line;
				urls.add(line);
				System.out.println(line);
			}
		}
		return urls;

	}
	public ArrayList<String> linksOnPage() {
		ArrayList<String> result = new ArrayList<String>();
		String globalRegex = "<a (.*?)</a>";
		Pattern globalPattern = Pattern.compile(globalRegex, Pattern.CASE_INSENSITIVE);
		Matcher globalMatcher = globalPattern.matcher(this.body);
		while (globalMatcher.find()) {
			String regex = "href=\"(.*?)\"";
			Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(globalMatcher.group());
			while (m.find()) {
				if (m.group(1).length() > 0 && m.group(1).charAt(0) != '#') 
				{
					String currentHref = m.group(1);
					if (currentHref.length() >= 2 && currentHref.substring(0, 2).equals("//")) 
					{
						currentHref = this.url.split("//")[0] + m.group(1);
						result.add(currentHref);
					} else 
					{
						try 
						{
							new URL(currentHref);
							result.add(m.group(1));
						} 
						catch (MalformedURLException malformedURLException) 
						{
							URL url = null;
							try {
								url = new URL(new URL(this.url), currentHref);
								result.add(url.toString());
							} 
							catch (MalformedURLException e1) 
							{

							}
						}
					}

				}
			}
		}

		return result;
	}
	

}

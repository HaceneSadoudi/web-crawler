
package fr.univavignon.ceri.webcrawl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
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

	// TODO
	// Variables declaration
	private String url;
	private String body;
	public String bodyRobotsTxt;
	
	private ArrayList<String> sitemapUrls;
	
	public static boolean RESPECT_ROBOT_TXT = true;
	public static boolean NORESPECT_ROBOT_TXT = false;
	public static boolean RESPECT_SITEMAP = true;
	public static boolean NORESPECT_SITEMAP = false;

	public String getUrl()
	{
		return this.url;
	}
	
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
		title = content.substring(begin, end).trim();
		title = title.substring(title.indexOf('>') + 1, title.length());
		System.out.println(title);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return title;
	}

	public ArrayList<String> getSitemapsFromRobotDotTxt(){
		ArrayList<String> urls = new ArrayList<String>();
		String[] lines =  this.bodyRobotsTxt.split("\n");
		for (String line : lines)
		{
			if (line.indexOf("Sitemap:") != -1)
			{
				line = line.split(" ")[1];
				urls.add(line);
			}
		}
		return urls;

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
				//System.out.println(line);
			}
		}
		return urls;

	}
	
	public ArrayList<String> linksOnSiteMap(String url, boolean isSiteMapLink){
		ArrayList<String> urls = new ArrayList<String>();
		ArrayList<String> urlSiteMap = new ArrayList<String>();
		boolean recursive = false;
		try {			
			HttpClient client = HttpClient.newBuilder()
					.connectTimeout(Duration.ofSeconds(20))
					.followRedirects(Redirect.ALWAYS)
					.build();
			if (!isSiteMapLink)
			{
				urlSiteMap.add(url.split("/")[0] + "//" + url.split("/")[1] + url.split("/")[2]);
				urlSiteMap.set(0, urlSiteMap.get(0) + "/sitemap.xml");
				urlSiteMap.addAll(this.getSitemapsFromRobotDotTxt());
				urlSiteMap.addAll(this.getSitemapsFromRobotDotTxt());
			}
			else
				urlSiteMap.add(url);
			Set<String> set = new HashSet<>(urlSiteMap);
			urlSiteMap.clear();
			urlSiteMap.addAll(set);
			System.out.println(urlSiteMap);
			for (String url1 : urlSiteMap)
			{
				HttpRequest request1 = HttpRequest.newBuilder()
						.setHeader("User-Agent", "Googlebot")
						.uri(URI.create(url1))
						.GET().build();
				HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
				String globalRegex = "<loc>(.*?)</loc>";
				Pattern globalPattern = Pattern.compile(globalRegex, Pattern.CASE_INSENSITIVE);
				Matcher globalMatcher = globalPattern.matcher(response1.body());
				int counter = 0;
				if (response1.body().indexOf("</sitemapindex>") != -1)
				{
					System.out.println("if " + urlSiteMap);
					recursive = true;
					while (globalMatcher.find()) 
					{
						urls.addAll(this.linksOnSiteMap(globalMatcher.group(1), true));
					}
				}
				else
				{
					while (globalMatcher.find() && !recursive) 
					{	
						counter++;
						urls.add(globalMatcher.group(1));
						if (counter >= 3)
							break;
					}
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
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
								// TODO
							}
						}
					}

				}
			}
		}

		return result;
	}
	

}

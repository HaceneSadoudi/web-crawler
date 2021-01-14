
package fr.univavignon.ceri.webcrawl;

import java.io.IOException;
import java.io.InputStream;
<<<<<<< HEAD
import java.net.HttpURLConnection;
=======
>>>>>>> refs/remotes/origin/feature/graph
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
=======
import java.util.Scanner;
>>>>>>> refs/remotes/origin/feature/graph
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
<<<<<<< HEAD
	private boolean respectSitemap;
	private boolean respectRobot;
=======
>>>>>>> refs/remotes/origin/feature/graph
	public String bodyRobotsTxt;
	
	private ArrayList<String> sitemapUrls;
	
	public static boolean RESPECT_ROBOT_TXT = true;
	public static boolean NORESPECT_ROBOT_TXT = false;
	public static boolean RESPECT_SITEMAP = true;
	public static boolean NORESPECT_SITEMAP = false;
<<<<<<< HEAD
	public int nbrLinks = 0;

	public String getUrl()
	{
		return this.url;
	}
	
	public Parser(String _url, boolean robottxt, boolean sitemap) {
		this.respectRobot = robottxt;
		this.respectSitemap = sitemap;
=======

	public Parser(String _url, boolean robottxt, boolean sitemap) {
>>>>>>> refs/remotes/origin/feature/graph
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
<<<<<<< HEAD
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
=======
			//urlRobotsTxt += "/robots.txt";
			//System.out.println(urlRobotsTxt);
			HttpRequest request1 = HttpRequest.newBuilder()
					.uri(URI.create(urlRobotsTxt))
					.GET().build();
			// Recuperation de la reponse pour la stocker dans une variable locale
			HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
			// Stockage du contenu de la page web dans la variable content
			contentRobotsTxt = response1.body();
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		this.body = content;
		this.bodyRobotsTxt = contentRobotsTxt;
		
		// remplacement des retours a la ligne par des espaces pour
		// utiliser les expressions regulieres qui vont reconnaitre les liens
		// content = content.replace("\n", " ").replace("\n\r", " ");
>>>>>>> refs/remotes/origin/feature/graph
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
<<<<<<< HEAD
		title = content.substring(begin, end).trim();
		title = title.substring(title.indexOf('>') + 1, title.length());
		System.out.println(title);
=======
		//System.out.println(begin + " " + end);
		title = content.substring(begin, end).trim();
		title = title.substring(title.indexOf('>') + 1, title.length());
		//System.out.println(title);
>>>>>>> refs/remotes/origin/feature/graph
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return title;
	}

<<<<<<< HEAD
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
	
=======
>>>>>>> refs/remotes/origin/feature/graph
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
<<<<<<< HEAD
=======
				//System.out.println(line);
>>>>>>> refs/remotes/origin/feature/graph
			}
		}
		return urls;

	}
<<<<<<< HEAD
	
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
			}
			else
				urlSiteMap.add(url);
			Set<String> set = new HashSet<>(urlSiteMap);
			urlSiteMap.clear();
			urlSiteMap.addAll(set);
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
				if (response1.body().indexOf("</sitemapindex>") != -1)
				{
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
						urls.add(globalMatcher.group(1));
						this.nbrLinks++;
					
					}
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return urls;

	}
	
=======
>>>>>>> refs/remotes/origin/feature/graph
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
						this.nbrLinks++;
					} else 
					{
						try 
						{
							new URL(currentHref);
							result.add(m.group(1));
							this.nbrLinks++;
						} 
						catch (MalformedURLException malformedURLException) 
						{
							URL url = null;
							try {
								url = new URL(new URL(this.url), currentHref);
								result.add(url.toString());
								this.nbrLinks++;
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

		if (this.respectSitemap) 
		{
			result.addAll(this.linksOnSiteMap(this.url, false));
		}
		Set<String> set = new HashSet<>(result);
		result.clear();
		result.addAll(set);
		this.nbrLinks = result.size();
		if (this.respectRobot) {
			ArrayList<String> linksInRobotsTxt = new ArrayList<String>();
			linksInRobotsTxt = this.linksOnRobotsTxt();
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext(); ) {
			    String value = iterator.next();
				for (String linkInRobotsTxt : linksInRobotsTxt)
				{
					String link1 = linkInRobotsTxt.replace("*", "(.*)");
					if (value.matches(link1))
					{
				        iterator.remove();
						this.nbrLinks--;
					}
				}
			}
		}
		return result;
	}
	

}

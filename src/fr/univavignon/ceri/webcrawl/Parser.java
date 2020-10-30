package fr.univavignon.ceri.webcrawl;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
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
 * @author Abdelhakim RASFI
 * 
 */

public class Parser {

	// TODO
	private String url;
	private String body;

	public Parser(String _url) {
		this.url = _url;
		String content = null;

		URLConnection connection = null;
		try {
			HttpClient client = HttpClient.newBuilder()
			        .version(Version.HTTP_1_1)
			        .connectTimeout(Duration.ofSeconds(20))
			        .followRedirects(Redirect.ALWAYS)
			        .build();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url)).GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			content = response.body();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		content = content.replace("\n", " ").replace("\r", " ");
		this.body = content;
	}

	public ArrayList<String> linksOnPage() {
		ArrayList<String> result = new ArrayList<String>();
		String globalRegex = "<a(.*?)</a>";
		Pattern globalPattern = Pattern.compile(globalRegex, Pattern.CASE_INSENSITIVE);
		Matcher globalMatcher = globalPattern.matcher(this.body);
		while (globalMatcher.find()) {
			String regex = "href=\"(.*?)\"";
			Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(globalMatcher.group());
			while (m.find()) {
				if (m.group(1).length() > 0 && m.group(1).charAt(0) != '#') {

					if (m.group(1).length() >= 2 && m.group(1).substring(0, 2).equals("//")) {
						result.add(this.url.split("//")[0] + m.group(1));
					} else if (m.group(1).charAt(0) == '/') {
						String[] urlParts = this.url.split("/");
						result.add(urlParts[0] + "//" + urlParts[2] + m.group(1));
					} else {
						if (m.group(1).substring(0, 4).equals("http")) {
							result.add(m.group(1));
						}
					}
				}
			}
		}

		return result;
	}

}

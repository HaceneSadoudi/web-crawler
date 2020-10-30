package fr.univavignon.ceri.webcrawl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
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
		try {
			HttpClient client = HttpClient.newBuilder()
					.version(Version.HTTP_1_1)
					.connectTimeout(Duration.ofSeconds(20))
					.followRedirects(Redirect.ALWAYS)
					.build();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(this.url))
					.GET().build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			content = response.body();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		content = content.replace("\n", " ").replace("\r", " ");
		this.body = content;
	}

	

}

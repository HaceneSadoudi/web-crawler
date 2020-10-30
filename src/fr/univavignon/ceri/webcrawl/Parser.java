package fr.univavignon.ceri.webcrawl;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
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
			connection = new URL(this.url).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
			scanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.body = content;
	}

	public ArrayList<String> linksOnPage() {
		ArrayList<String> result = new ArrayList<String>();
		String globalRegex = "<a(.*?)</a>";
		Pattern globalPattern = Pattern.compile(globalRegex);
		Matcher globalMatcher = globalPattern.matcher(this.body);
		while (globalMatcher.find()) {

			String regex = "href=\"(.*?)\"";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(globalMatcher.group());

			while (m.find()) {
				if (m.group(1).charAt(0) != '#') {

					if (m.group(1).substring(0, 2).equals("//")) {
						result.add(this.url.split("//")[0] + m.group(1));
						System.out.println(this.url.split("//")[0] + m.group(1));
					}
					else if (m.group(1).charAt(0) == '/') {
						String[] urlParts = this.url.split("/");
						result.add(urlParts[0] + "//" + urlParts[2] + m.group(1));
						System.out.println(urlParts[0] + "//"  + urlParts[2] + m.group(1));
					} else {
						if (m.group(1).substring(0, 4).equals("http")) {
							result.add(m.group(1));
							System.out.println(m.group(1));
						}
					}
				}
			}
		}

		return result;
	}

}

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

 //TODO
 private String url;
 private String body;
 
 public Parser(String _url) {
  this.url = _url;
  String content = null;
  
  URLConnection connection = null;
  try {
    connection =  new URL(this.url).openConnection();
    //System.out.println(this.url);
    Scanner scanner = new Scanner(connection.getInputStream());
    scanner.useDelimiter("\\Z");
    content = scanner.next();
    scanner.close();
  }catch ( Exception ex ) {
      ex.printStackTrace();
  }
  this.body = content;
 }
 
 public String getHostName() {
     URI uri = null;
  try {
   uri = new URI(this.url);
  } catch (URISyntaxException e) {
   e.printStackTrace();
  }
     String hostname = uri.getHost();
     if (hostname != null) {
         return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
     }
     return hostname;
 }
 
 public ArrayList<String> linksOnPage()
 {
     ArrayList<String> result = new ArrayList<String>();

     //String regex = "<a+(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
     String regex = "<a[^>]+href=\\\"(.*?)\\\"[^>]*>(.*)?</a>";
     //(?i)<a([^>]+)>(.+?)</a>
     Pattern p = Pattern.compile(regex);
     Matcher m = p.matcher(this.body);
     while (m.find())
     {
      String[] parts = m.group().split("href=\"");
      String part1 = parts[1];
      if (part1.charAt(0) != '#')
      {

    	 if (part1.charAt(0) != '/')
         {
    	  //part1 = "http://" + this.getHostName() + part1;
         
       String[] partss = part1.split("\"");
       String partss1 = partss[0];
       result.add(partss1);
       //System.out.println(partss1);
         }
      }
     }

     return result;
 }
 

}
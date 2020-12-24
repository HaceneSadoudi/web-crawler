package fr.univavignon.ceri.webcrawl;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * This class is used to launch the game.
 * 
 * @author Abdelhakim RASFI
 * @author Youssef ABIDAR
 * @author Imane HACEN 
 * @author Mohamed KHARCHOUF 
 * 
 */
public class Launcher
{	
	
	/**
	 * Launches the game.
	 * 
	 * @param args
	 * 		Not used here.
	 * @throws MalformedURLException 
	 */
	
	public static void main(String[] args) throws MalformedURLException
	{	//TODO to be completed

		//main test graphe.
		ArrayList<String> tabUrl=new ArrayList<String>();
		Graph graph=new Graph("https://youtube.com");
		//graph.graphSite(tabUrl);
		graph.afficherListDomain(tabUrl);
		//createxml xml = new createxml();
		//xml.creaat(graph);
	}
}

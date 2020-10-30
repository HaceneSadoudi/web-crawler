package fr.univavignon.ceri.webcrawl;

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
	 */
	
	public static void main(String[] args)
	{	//TODO to be completed
		
		//main test graphe.
		ArrayList<String> tabUrl=new ArrayList<String>();
		Graphh graph=new Graphh("https://www.nytimes.com");
		graph.graphSite(tabUrl);
	}
}

package fr.univavignon.ceri.webcrawl;

import java.util.LinkedList;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

// robot
// cas 1 : utilsiateur a choisi de respecter robot.txt : passer variable Parser.RESPECT_Robot_TXT (toujours)
// cas 2 : utilsiateur a choisi de ne pas respecter robot.txt : passer variable Parser.NON_RESPECT_Robot_TXT (toujours)

// sitemap
// cas 3 : utilsiateur a choisi de respecter sitemap.txt : passer variable Parser.RESPECTsitemap_TXT
// cas 4 : utilsiateur a choisi de ne pas respecter sitemap.txt : passer variable Parser.NON_RESPECTsitemap_TXT (toujours)


// cas 3 special :
// on passe Parser.RESPECTsitemap_TXT, si c'est une page on ne respecte pas (que dans domaine)

public class Graph extends Thread{
	
	ArrayList<String> tableauUrl = new ArrayList<String>();
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	//element Vertex
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
		
	LinkedList<LinkedList<Vertex>> listEnsembleVertex=new LinkedList<LinkedList<Vertex>>();
	LinkedList<Vertex> listVertex=new LinkedList<Vertex>();
	Vertex unVertex;
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	//element Edge
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
		
	LinkedList<LinkedList<Edge>> listEnsmbleEdge=new LinkedList<LinkedList<Edge>>();
	LinkedList<Edge> listEdge=new LinkedList<Edge>();
	Edge unEdge ;
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	//element Domain
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
		
	String dom;
	ArrayList<String> listDomain= new ArrayList<String>();
	int j=0;
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	//element nombre
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	String modalite;
	public int termine;
	
	public static int numberLinkFound = 0;
	public static int numberLinkTreated = 0;
	public static int numberVertex = 1;
	public static int numberEdge = 0;
	
	public int limite = 2;
	public boolean robot;
	public boolean site;
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------
// CONSTRUCTEUR
//----------------------------------------------------------------------------------------------------------------------------------------------------------
			
	public Graph(String url, String mod, int limite, boolean robot, boolean site) throws MalformedURLException {
		this.modalite = mod;
		this.termine = 0;
		if(limite == 0){ // pas de limite
			this.limite = 9999;
		} else {
			this.limite = limite;
		}
		this.robot = robot;
		this.site = site;
		//on donne au sommet de depart une url
		this.unVertex=new Vertex(url);
		//on pose la variable passed à true
		this.unVertex.passed=true;
		this.dom=getDomainTitle(url);
	}
	
	public void run(){
		if(modalite.equals("Page")){
			try {
				System.out.println(Thread.currentThread().getName());
				creationGraphEnLargeur();
				this.termine = 1;
				Interface.nb_t--;
				Interface.mustWait = false;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println(Thread.currentThread().getName());
				createDomainGraph();
				this.termine = 1;
				Interface.nb_t--;
				Interface.mustWait = false;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
//----------------------------------------------------------------------------------------------------------------------------------------------------------
//pour tester la fonciton
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public ArrayList<String> search(String url){
		ArrayList<String> ti = new ArrayList<String>();
		ti.add("https://www.youtube.com/?hl=fr&gl=FR");
		ti.add("https://www.youtube.com/feed/history");
		ti.add("https://twitter.com/?lang=fr");
		ti.add("https://twitter.com/?lang=fr");
		ti.add("https://www.google.com/?client=safari");
		return ti;
		
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------
//PAGE WEB
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	//CREER ARBRE POUR UN NOEUD
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void creationNode() throws MalformedURLException {
		//essaie du parseur
		Parser ps;
		if(this.robot == true){
			if(this.site == false){
				ps = new Parser(this.unVertex.getUrl(), Parser.RESPECT_ROBOT_TXT, Parser.NORESPECT_SITEMAP);
				//ps = new Parser(this.unVertex.getUrl(), true, false);
			} else {
				 if(unDomain(this.unVertex.getUrl())){
					 ps = new Parser(this.unVertex.getUrl(), Parser.RESPECT_ROBOT_TXT, Parser.RESPECT_SITEMAP);
					 //ps = new Parser(this.unVertex.getUrl(), true, true);
				 } else {
					 ps = new Parser(this.unVertex.getUrl(), Parser.RESPECT_ROBOT_TXT, Parser.NORESPECT_ROBOT_TXT);
					 //ps = new Parser(this.unVertex.getUrl(), true, false);
				 }
			}
		} else {
			if(this.site == false){
				ps = new Parser(this.unVertex.getUrl(), Parser.NORESPECT_ROBOT_TXT, Parser.NORESPECT_SITEMAP);
				//ps = new Parser(this.unVertex.getUrl(), false, false);
			} else {
				if(unDomain(this.unVertex.getUrl())){
					ps = new Parser(this.unVertex.getUrl(), Parser.NORESPECT_ROBOT_TXT, Parser.RESPECT_SITEMAP);
					//ps = new Parser(this.unVertex.getUrl(), false, true);
				 } else {
					 ps = new Parser(this.unVertex.getUrl(), Parser.NORESPECT_ROBOT_TXT, Parser.NORESPECT_ROBOT_TXT);
					// ps = new Parser(this.unVertex.getUrl(), false, false);
				 }
			}
		}
		this.tableauUrl= ps.linksOnPage();
		//this.tableauUrl=search(this.unVertex.getUrl());
		//this.tableauUrl=search(this.unVertex.getUrl());		//on rempli tableauUrl avec les liens recuperer
		this.listVertex.add(this.unVertex);			//on entre dans listSommet le sommet source
		for (int i=0;i<this.tableauUrl.size();i++) {	//pour chaque url on creer un noed et on relie
			numberVertex++;
			Vertex b=new Vertex(this.tableauUrl.get(i));
			numberEdge++;
			//la source avec ce nouveau sommet grace à un arc
			this.unEdge=new Edge(this.tableauUrl.get(i),this.unVertex,b,1,getDomainTitle(this.tableauUrl.get(i)));		
																				//on ajoute ce nouvel arc dans la list d'arc pour ce sommet source
			this.listEdge.add(unEdge);
																		//on ajoute le nouveau sommet dans la liste de sommet
			this.listVertex.add(b);
			numberLinkTreated++;
		}
		this.listEnsembleVertex.add(this.listVertex);
																	//apres avoir fait pour tout les urls j'ajoute la liste des 
																	//sommet a listEnsembleSommet
		this.listEnsmbleEdge.add(this.listEdge);		//on ajoute la liste d'arc à la list listEnsembleArc
		
		this.j++;
		
		
	}
	
	public boolean unDomain(String url) throws MalformedURLException {
        char c;
        int nb=0;
        URL b = new URL(url); 
        String domain= b.getHost();
        if (url.length()>domain.length()+8) {
            System.out.println("page");
            return false;
        }
        else {
            System.out.println("domaine");
            return true;
        }
    }
	
	/*
	public void essaiIte(ArrayList<String> ti) {
		creationNode(ti);
		for(int i=1; i<this.listEnsembleVertex.get(j-1).size();i++) {
			Vertex aux=this.listEnsembleVertex.get(j-1).get(i);
			//System.out.println("aux . url : "+aux.url);
			if (aux.passed==false) {
				LinkedList<Vertex> auxA=new LinkedList<Vertex>();
				this.listVertex=auxA;
				this.unVertex=aux;
				creationNode(ti);
			}
			else {
				System.out.println("la ligne : "+(j-1)+" a ete passéee");
			}
		}
	//	System.out.println("fin boucle for ");
		
		
	}
	*/
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// CREER L'ARBRE AVEC PLUSIEURS ITERATIONS
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
		
	public void creationGraphEnLargeur() throws MalformedURLException {
		creationNode();
		for (int l=0;l<this.limite;l++) {
			for(int i=1; i<listEnsembleVertex.get(l).size();i++) {
				Vertex aux=listEnsembleVertex.get(l).get(i);
				//System.out.println("aux . url : "+aux.url);
				if (aux.passed==false) {
					LinkedList<Vertex> auxA=new LinkedList<Vertex>();
					this.listVertex=auxA;
					this.unVertex=aux;
					creationNode();
				}
				else {
					System.out.println("la ligne : "+(j-1)+" a ete passéee");
				}
			}
		}
	//	System.out.println("fin boucle for ");
		
		
	}

	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// EXECUTE ET AFFICHE EDGE ET VERTEX
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
		
	public void essaiCatch() throws MalformedURLException{
		//essai(ti);
		creationGraphEnLargeur();
		for (int i=0; i<listEnsembleVertex.size();i++) {
			System.out.println("list size::: "+ listEnsembleVertex.size());
			System.out.println(listEnsembleVertex.get(i));
			System.out.println("[");
			for (int k=0;k<listEnsembleVertex.get(i).size();k++) {
				Vertex aux=listEnsembleVertex.get(i).get(k);
				System.out.println(aux.getUrl());
				//System.out.println("j::: "+(this.j-1));
			}
		}
		//System.out.println("]   j:: "+(this.j-1));
		
		System.out.println("arc :::: ");
		essaiCatchArc();	
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// EXECUTE AFFICHE EDGE
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
		
	public void essaiCatchArc() throws MalformedURLException {
		creationGraphEnLargeur();
		for (int i=0; i<listEnsmbleEdge.get(0).size();i++) {
			Edge aux=new Edge(listEnsmbleEdge.get(0).get(i).getLink(),listEnsmbleEdge.get(0).get(i).getSource(),listEnsmbleEdge.get(0).get(i).getTarget());
			//System.out.println("nom de l'arc: "+aux.getLink());
			System.out.println("source :::"+aux.getSource().getUrl()+":::  num sommet  :::: "+aux.getSource());
			System.out.println("cible ::::"+aux.getLink()+":::  num sommet ::: "+aux.getTarget());
			System.out.println("---------------- "+ i );
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// EXECUTE ET AFFICHE EDGE
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
			
	public void graphSite() throws MalformedURLException {
		essaiCatchArc();
	}
	
	
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------
//DOMAIN WEB	
//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	ArrayList<ArrayList<Vertex>> domainL = new ArrayList<ArrayList<Vertex>>();
	ArrayList<Vertex> domainI=new ArrayList<Vertex>();
	Vertex aux;
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	//RENVOIE LE DOMAINE DE L'URL
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
			
	public String getDomainTitle(String u) throws MalformedURLException {
		URL url = new URL(u); 
		String domain= url.getHost();
		System.out.println(domain);
		int nbr;
		char c;
		ArrayList<String> mots=new ArrayList();
		String mot = "";
	
		if (domain =="") {
			return "";
		}
		else {
			if (domain.startsWith("www.")) {
				for(int i = 0 ; i < domain.length()-1 ; i++){
				    c = domain.charAt(i);
				    //System.out.print(c);
				    
				    	if(c=='.') {
				    		mots.add(mot);
				    		mot="";
				    	}
				    	else {
				    		mot=mot+c;
				    	}
				    
				}
					return mots.get(mots.size()-1); 
				
			}
			else {
				for(int i = 0 ; i < domain.length()-1 ; i++){
				    c = domain.charAt(i);
				    //System.out.println(c);
				    
				    	if(c=='.') {
				    		c = domain.charAt(i);
				    		mots.add(mot);
				    		mot="";
				    	}
				    	else {
				    		mot=mot+c;
				    	}
				    
				}
				
					return mots.get(mots.size()-1); 
				
			}
		}	
	}
	 
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// FAIT ARBRE DOMAINE POUR UNE ITÉRATION
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
			
	public void getOneIterationOfDomain() throws MalformedURLException {
		//this.tableauUrl = search(this.unVertex.getUrl());
		Parser ps;
		if(this.robot == true){
			if(this.site == false){
				ps = new Parser(this.unVertex.getUrl(), Parser.RESPECT_ROBOT_TXT, Parser.NORESPECT_SITEMAP);
			} else {
				 if(unDomain(this.unVertex.getUrl())){
					 ps = new Parser(this.unVertex.getUrl(), Parser.RESPECT_ROBOT_TXT, Parser.RESPECT_SITEMAP);
				 } else {
					 ps = new Parser(this.unVertex.getUrl(), Parser.RESPECT_ROBOT_TXT, Parser.NORESPECT_ROBOT_TXT);
				 }
			}
		} else {
			if(this.site == false){
				ps = new Parser(this.unVertex.getUrl(), Parser.NORESPECT_ROBOT_TXT, Parser.NORESPECT_SITEMAP);
			} else {
				if(unDomain(this.unVertex.getUrl())){
					 ps = new Parser(this.unVertex.getUrl(), Parser.NORESPECT_ROBOT_TXT, Parser.RESPECT_SITEMAP);
				 } else {
					 ps = new Parser(this.unVertex.getUrl(), Parser.NORESPECT_ROBOT_TXT, Parser.NORESPECT_ROBOT_TXT);
				 }
			}
		}
		this.tableauUrl= ps.linksOnPage();
		this.domainI.add(this.unVertex);
		this.unVertex.setWeight(tableauUrl.size());
		for (int i=0;i<tableauUrl.size();i++) {
			//System.out.println("tab i  : "+ tableauUrl.get(i));
			String auxd=getDomainTitle(this.tableauUrl.get(i));
			//System.out.println("domaine : "+ auxd);
			if (listDomain.contains(auxd)) {
				//System.out.println("le domaine "+auxd+" est deja dans la liste domaine.");
				
				for (int h=0; h<this.listEdge.size();h++) {
					if (this.listEdge.get(h).getTitle().compareTo(auxd)==0) {
						int x = this.listEdge.get(h).getPonderation();
						x++;
						this.listEdge.get(h).setPonderation(x);
					}	
				}
			}
			else {
				if (auxd.compareTo(dom)==0) {
					this.unEdge = new Edge(tableauUrl.get(i),this.unVertex,this.unVertex,1,auxd);
					this.listEdge.add(unEdge);
					this.listVertex.add(this.unVertex);
					this.listDomain.add(auxd);
					numberEdge++;
				}
				else {
					Vertex newDomain = new Vertex(tableauUrl.get(i),auxd);
					this.unEdge = new Edge(tableauUrl.get(i),this.unVertex,newDomain,1,auxd);
					this.listEdge.add(unEdge);
					this.listVertex.add(newDomain);
					this.listDomain.add(auxd);
					numberVertex++;
					numberEdge++;
				}
			}
			this.numberLinkTreated++;
		}
		this.listEnsembleVertex.add(this.listVertex);
		this.listEnsmbleEdge.add(this.listEdge);		
		this.j++;
		
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// CREER L'ARBRE DES DOMAINES AVEC PLUSIEURS ITERATIONS
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
			
		public void createDomainGraph() throws MalformedURLException {
			getOneIterationOfDomain();
			for (int l=0;l<this.limite;l++) {

				for(int i=1; i<listEnsembleVertex.get(0).size();i++) {
					Vertex aux=listEnsembleVertex.get(0).get(i);
					if (aux.passed==false) {
						LinkedList<Vertex> auxA=new LinkedList<Vertex>();
						this.listVertex=auxA;
						this.unVertex=aux;
						getOneIterationOfDomain();
					}
					else {
						System.out.println("la ligne : "+(j-1)+" a ete passéee");
					}
				}
			}
		}

		
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	// AFFICHER LIST DOMAINE
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void afficherListDomain() throws MalformedURLException {
		createDomainGraph();
		System.out.println("Liste Domaine : ");
		System.out.println(this.listDomain);
		System.out.println("list ensemble edge :"+this.listEnsmbleEdge);
		System.out.println("size : "+this.listEnsmbleEdge.size());
		//System.out.println(listEnsmbleEdge.size());
		for (int i=0; i<listEnsmbleEdge.get(0).size();i++) {
			Edge aux=new Edge(listEnsmbleEdge.get(0).get(i).getLink(),listEnsmbleEdge.get(0).get(i).getSource(),listEnsmbleEdge.get(0).get(i).getTarget(),listEnsmbleEdge.get(0).get(i).getPonderation(),listEnsmbleEdge.get(0).get(i).getTitle());
			System.out.println("poids de l'arc: "+aux.getPonderation());
			System.out.println(aux.getSource().getUrl()+" -> "+aux.getLink()+" -> titre : "+aux.getTitle());
			System.out.println();
		}
	}
	
	/*public int getNombreUrl() {
		return numberLinkTreated;
	}
	
	public int getNombreNoeud() {
		return numberVertex;
	}
	
	public int getNombreLien() {
		return numberEdge;
	}*/

}

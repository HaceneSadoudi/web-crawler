
package fr.univavignon.ceri.webcrawl;

import java.util.LinkedList;
import java.util.ArrayList;


public class Graph {
	
	
	ArrayList<String> tableauUrl = new ArrayList<String>();
	LinkedList<LinkedList<Vertex>> listEnsembleVertex=new LinkedList<LinkedList<Vertex>>();
	LinkedList<Vertex> listVertex=new LinkedList<Vertex>();
	
	//creer arc
	LinkedList<LinkedList<Edge>> listEnsmbleEdge=new LinkedList<LinkedList<Edge>>();
	LinkedList<Edge> listEdge=new LinkedList<Edge>();
	Edge unEdge ;
	
	//int i=0;
	Vertex unVertex;
	int j=0;
	
	public Graph(String url ) {
		
		this.unVertex=new Vertex(url);
		this.unVertex.passed=true;
	}
	
	public ArrayList<String> search(String url){
		ArrayList<String> ti = new ArrayList<String>();
		ti.add("twitter.fr");
		ti.add("youtube.com");
		ti.add("wikipedia;com");
		return ti;
		
	}

	public void essai(ArrayList<String> ti) {
		//Parser ps = new Parser(this.unSommet.getUrl());
		//this.tableauUrl= ps.linksOnPage();
		this.tableauUrl=search(this.unVertex.getUrl());
		this.listVertex.add(this.unVertex);
		for (int i=0;i<this.tableauUrl.size();i++) {
			Vertex b=new Vertex(this.tableauUrl.get(i));
			this.unEdge=new Edge(this.tableauUrl.get(i),this.unVertex,b);
			this.listEdge.add(unEdge);
			this.listVertex.add(b);
		}
		this.listEnsembleVertex.add(this.listVertex);
		this.listEnsmbleEdge.add(this.listEdge);
		this.j++;
		
		
	}
	
	
	public void essaiIte(ArrayList<String> ti) {
		essai(ti);
		for(int i=1; i<this.listEnsembleVertex.get(j-1).size();i++) {
			Vertex aux=this.listEnsembleVertex.get(j-1).get(i);
			//System.out.println("aux . url : "+aux.url);
			if (aux.passed==false) {
				LinkedList<Vertex> auxA=new LinkedList<Vertex>();
				this.listVertex=auxA;
				this.unVertex=aux;
				essai(ti);
			}
			else {
				System.out.println("la ligne : "+(j-1)+" a ete passéee");
			}
		}
	//	System.out.println("fin boucle for ");
		
		
	}
	
	public void essaiIte2(ArrayList<String> ti) {
		essai(ti);
		for (int l=0;l<2;l++) {
			for(int i=1; i<listEnsembleVertex.get(l).size();i++) {
				Vertex aux=listEnsembleVertex.get(l).get(i);
				//System.out.println("aux . url : "+aux.url);
				if (aux.passed==false) {
					LinkedList<Vertex> auxA=new LinkedList<Vertex>();
					this.listVertex=auxA;
					this.unVertex=aux;
					essai(ti);
				}
				else {
					System.out.println("la ligne : "+(j-1)+" a ete passéee");
				}
			}
		}
	//	System.out.println("fin boucle for ");
		
		
	}
	
	
	
	public void essaiCatch(ArrayList<String> ti){
		//essai(ti);
		essaiIte2(ti);
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
		System.out.println("]   j:: "+(this.j-1));
		
		System.out.println("arc :::: ");
		essaiCatchArc(ti);	
	}
	

	
	public void essaiCatchArc(ArrayList<String> ti) {
		essaiIte2(ti);
		for (int i=0; i<listEnsmbleEdge.get(0).size();i++) {
			Edge aux=new Edge(listEnsmbleEdge.get(0).get(i).getLink(),listEnsmbleEdge.get(0).get(i).getSource(),listEnsmbleEdge.get(0).get(i).getTarget());
			//System.out.println("nom de l'arc: "+aux.getLink());
			System.out.println("source :::"+aux.getSource().getUrl()+":::  num sommet  :::: "+aux.getSource());
			System.out.println("cible ::::"+aux.getLink()+":::  num sommet ::: "+aux.getTarget());
			System.out.println("----------------");
		}
	}
	
	ArrayList<ArrayList<Vertex>> domainL = new ArrayList<ArrayList<Vertex>>();
	ArrayList<Vertex> domainI=new ArrayList<Vertex>();
	Vertex aux;
	
	public void essaiDomain(ArrayList<String> ti) {
		//Parser ps = new Parser(this.unSommet.getUrl());
		//this.tableauUrl= ps.linksOnPage();
		this.tableauUrl = search(this.unVertex.getUrl());
		this.domainI.add(this.unVertex);
		for (int i=0;i<this.tableauUrl.size();i++) {
			Vertex b=new Vertex(this.tableauUrl.get(i));
			this.unEdge=new Edge(this.tableauUrl.get(i),this.unVertex,b);
			this.listEdge.add(this.unEdge);
			this.listVertex.add(b);
		}
		this.listEnsembleVertex.add(this.listVertex);
		this.listEnsmbleEdge.add(this.listEdge);
		this.j++;
		
	}
	
	
	
	//affiche les sites sans ponderation
	public void graphSite(ArrayList<String> ti) {
		essaiCatchArc(ti);
	}
	
	
	//affiche les domaines
	public void graphDomain(ArrayList<String> ti) {
		
	}
	 
	 
	

}

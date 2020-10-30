package fr.univavignon.ceri.webcrawl;

import java.util.ArrayList;


public class Graph {
	
	ArrayList<String> tableauUrl = new ArrayList<String>();
	ArrayList<ArrayList<Sommet>> listEnsembleSommet=new ArrayList<ArrayList<Sommet>>();
	ArrayList<Sommet> listSommet=new ArrayList<Sommet>();
	
	//creer arc
	ArrayList<ArrayList<Arc>> listEnsmbleArc=new ArrayList<ArrayList<Arc>>();
	ArrayList<Arc> listArc=new ArrayList<Arc>();
	Arc unArc ;
	
	//int i=0;
	Sommet unSommet;
	int j=0;
	
	public Graph(String url ) {
		
		this.unSommet=new Sommet(url);
		this.unSommet.passed=true;
	}
	
	public ArrayList<String> search(String url){
		ArrayList<String> ti = new ArrayList<String>();
		ti.add("twitter.fr");
		ti.add("youtube.com");
		ti.add("wikipedia;com");
		return ti;
		
	}

	public void essai(ArrayList<String> ti) {
		Parser ps = new Parser(this.unSommet.getUrl());
		this.tableauUrl= ps.linksOnPage();
		this.listSommet.add(this.unSommet);
		for (int i=0;i<this.tableauUrl.size();i++) {
			Sommet b=new Sommet(this.tableauUrl.get(i));
			this.unArc=new Arc(this.tableauUrl.get(i),this.unSommet,b);
			this.listArc.add(unArc);
			this.listSommet.add(b);
		}
		this.listEnsembleSommet.add(this.listSommet);
		this.listEnsmbleArc.add(this.listArc);
		this.j++;
		
		
	}
	
	
	public void essaiIte(ArrayList<String> ti) {
		essai(ti);
		for(int i=1; i<this.listEnsembleSommet.get(j-1).size();i++) {
			Sommet aux=this.listEnsembleSommet.get(j-1).get(i);
			//System.out.println("aux . url : "+aux.url);
			if (aux.passed==false) {
				ArrayList<Sommet> auxA=new ArrayList<Sommet>();
				this.listSommet=auxA;
				this.unSommet=aux;
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
			for(int i=1; i<listEnsembleSommet.get(l).size();i++) {
				Sommet aux=listEnsembleSommet.get(l).get(i);
				//System.out.println("aux . url : "+aux.url);
				if (aux.passed==false) {
					ArrayList<Sommet> auxA=new ArrayList<Sommet>();
					this.listSommet=auxA;
					this.unSommet=aux;
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
		for (int i=0; i<listEnsembleSommet.size();i++) {
			System.out.println("list size::: "+ listEnsembleSommet.size());
			System.out.println(listEnsembleSommet.get(i));
			System.out.println("[");
			for (int k=0;k<listEnsembleSommet.get(i).size();k++) {
				Sommet aux=listEnsembleSommet.get(i).get(k);
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
		for (int i=0; i<listEnsmbleArc.get(0).size();i++) {
			Arc aux=new Arc(listEnsmbleArc.get(0).get(i).getLink(),listEnsmbleArc.get(0).get(i).getSource(),listEnsmbleArc.get(0).get(i).getTarget());
			//System.out.println("nom de l'arc: "+aux.getLink());
			System.out.println("source :::"+aux.getSource().getUrl()+":::  num sommet  :::: "+aux.getSource());
			System.out.println("cible ::::"+aux.getLink()+":::  num sommet ::: "+aux.getTarget());
			System.out.println("----------------");
		}
	}
	
	ArrayList<ArrayList<Sommet>> domainL = new ArrayList<ArrayList<Sommet>>();
	ArrayList<Sommet> domainI=new ArrayList<Sommet>();
	Sommet aux;
	
	public void essaiDomain(ArrayList<String> ti) {
		Parser ps = new Parser(this.unSommet.getUrl());
		this.tableauUrl= ps.linksOnPage();
		this.domainI.add(this.unSommet);
		for (int i=0;i<this.tableauUrl.size();i++) {
			Sommet b=new Sommet(this.tableauUrl.get(i));
			this.unArc=new Arc(this.tableauUrl.get(i),this.unSommet,b);
			this.listArc.add(this.unArc);
			this.listSommet.add(b);
		}
		this.listEnsembleSommet.add(this.listSommet);
		this.listEnsmbleArc.add(this.listArc);
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

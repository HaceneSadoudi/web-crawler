package fr.univavignon.ceri.webcrawl;

import java.util.ArrayList;
public class Sommet {

		protected String url;
		private int number;
		protected Sommet predecessor=null;
		protected  ArrayList<Sommet> successor=new ArrayList<Sommet> ();
		protected boolean passed=false;
		
		public Sommet (String url) {
			this.url=url;
		}
		
		public int getSize() {
			return this.successor.size();
		}
		
		public void addSucc(Sommet suc) {
			if (suc!=null) {
				this.successor.add(suc);
			}
			else return ;
		}
		
		public void assPred(Sommet preed) {
			this.predecessor=preed;
		}

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Sommet getPredecessor() {
			return predecessor;
		}

		public void setPredecessor(Sommet predecessor) {
			this.predecessor = predecessor;
		}
		
		 public void aff_graph()
			{
			 	
				//System.out.println(this.url);
				for (Sommet i: successor) {
					System.out.println(i.getUrl());
				}
			}
		 
		 public void passer() {
			 passed=true;
		 }
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		 
		 
		 
			
}
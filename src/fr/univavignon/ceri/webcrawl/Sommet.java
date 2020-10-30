package fr.univavignon.ceri.webcrawl;


public class Sommet {

		protected String url;


		protected boolean passed=false;
		
		public Sommet (String url) {
			this.url=url;
		}
		

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		 public void passer() {
			 passed=true;
		 }

		 
		 
		 
			
}
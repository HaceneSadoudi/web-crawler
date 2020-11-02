package fr.univavignon.ceri.webcrawl;


public class Vertex {

		protected String url;

		

		protected boolean passed=false;
		
		public Vertex (String url) {
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
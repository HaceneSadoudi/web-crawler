package fr.univavignon.ceri.webcrawl;


public class Vertex {

		protected String url;
		protected String titlle;
		public int weight;
		

		protected boolean passed=false;
		
		public Vertex (String url) {
			this.url=url;
		}
		
		public Vertex (String url,String title) {
			this.url=url;
			this.titlle=title;
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
		 
		 public void setWeight (int w) {
			 this.weight=w;
		 }
		 
		 public int getWeight () {
			 return this.weight;
		 }
		 
		 
		 
			
}
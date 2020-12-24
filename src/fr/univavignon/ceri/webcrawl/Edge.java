package fr.univavignon.ceri.webcrawl;


public class Edge {
	
	protected String link;
	protected String title;
	protected Vertex source;
	protected Vertex target;
	protected int ponderation;
	
	//si liens
	public Edge(String link,Vertex source ,Vertex target ) {
		// TODO Auto-generated constructor stub
		
		this.link=link;
		this.source=source;
		this.target=target;
		
	}
	
	//si domaine
	public Edge(String link,Vertex source ,Vertex target , int ponderation, String title) {
		// TODO Auto-generated constructor stub
		
		this.link=link;
		this.source=source;
		this.target=target;
		this.ponderation=ponderation;
		this.title=title;
		
	}
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getTitle() {
		return link;
	}

	public void setTitle(String link) {
		this.link = link;
	}
	
	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}

	public int getPonderation() {
		return ponderation;
	}

	public void setPonderation(int ponderation) {
		this.ponderation = ponderation;
	}
	
	

}

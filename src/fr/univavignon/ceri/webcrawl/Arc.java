package fr.univavignon.ceri.webcrawl;

public class Arc {
	
	protected String link;
	protected Sommet source;
	protected Sommet target;
	protected int ponderation;
	
	//si liens
	public Arc(String link,Sommet source ,Sommet target ) {
		// TODO Auto-generated constructor stub
		
		this.link=link;
		this.source=source;
		this.target=target;
		
	}
	
	//si domaine
	public Arc(String link,Sommet source ,Sommet target , int ponderation) {
		// TODO Auto-generated constructor stub
		
		this.link=link;
		this.source=source;
		this.target=target;
		this.ponderation=ponderation;
		
	}
	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Sommet getSource() {
		return source;
	}

	public void setSource(Sommet source) {
		this.source = source;
	}

	public Sommet getTarget() {
		return target;
	}

	public void setTarget(Sommet target) {
		this.target = target;
	}

	public int getPonderation() {
		return ponderation;
	}

	public void setPonderation(int ponderation) {
		this.ponderation = ponderation;
	}
	
	

}

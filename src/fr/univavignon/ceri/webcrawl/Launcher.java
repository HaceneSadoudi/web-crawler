package fr.univavignon.ceri.webcrawl;

import java.util.ArrayList;
public class Launcher {
public static void main(String[] args) {
ArrayList<String> b=new ArrayList<String>();
Graph g=new Graph("https://www.google.com");
g.graphSite(b);
createxml gml= new createxml();
gml.creaat(g);
}
}

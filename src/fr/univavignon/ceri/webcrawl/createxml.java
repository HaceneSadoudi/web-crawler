package fr.univavignon.ceri.webcrawl;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class createxml {
public static void main(Graph graphe) {
ArrayList<ArrayList<Arc>> auxArc=graphe.listEnsmbleArc;
try {
DocumentBuilderFactory docGraph = DocumentBuilderFactory.newInstance();
DocumentBuilder docGBuilder=docGraph.newDocumentBuilder();
Document gra = docGBuilder.newDocument();
Element graph = gra.createElement("graph");
//on parcours chaque itération 
for (int i=0;i<auxArc.size();i++) {
	//on parcours chaque arc
for (int j=0;j<auxArc.get(i).size();j++) {
//je recupere dans des variables les sommets
Sommet auxSource= new Sommet(auxArc.get(i).get(j).getSource().getUrl());
Sommet auxTarget= new Sommet(auxArc.get(i).get(j).getTarget().getUrl());
//on crée l'élement arc qui aura 2 atributs 
Element arc = gra.createElement("arc");
Attr sommetS = gra.createAttribute("sommetSource");
Attr sommetT = gra.createAttribute("sommetTarget");
//on met dans les sommets les Urls récupérees
sommetS.setValue(auxSource.getUrl());
sommetT.setValue(auxTarget.getUrl());
arc.setAttributeNode(sommetS);
arc.setAttributeNode(sommetT);
//on ajoute tout à l'element Graph
graph.appendChild(arc);
}
}
//on ajoute l'element graph dans le document gra
gra.appendChild(graph);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(gra);
        //exporter en graphml
        StreamResult result = new StreamResult(new File("file.graphml"));
        transformer.transform(source, result);
}
catch (ParserConfigurationException pce) {
        pce.printStackTrace();
}
catch (TransformerException tfe) {

        tfe.printStackTrace();
 }
}
}

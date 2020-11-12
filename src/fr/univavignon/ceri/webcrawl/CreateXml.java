package fr.univavignon.ceri.webcrawl;

import java.io.File;
import java.util.LinkedList;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;


public class CreateXml {
public static void creaat(Graph graphe) {
LinkedList<LinkedList<Edge>> auxArc=graphe.listEnsmbleEdge;
try {
DocumentBuilderFactory docGraph = DocumentBuilderFactory.newInstance();
DocumentBuilder docGBuilder=docGraph.newDocumentBuilder();
Document gra = docGBuilder.newDocument();
Element graph = gra.createElement("graph");
//on parcours chaque it�ration 
for (int i=0;i<auxArc.size();i++) {
	//on parcours chaque arc
for (int j=0;j<auxArc.get(i).size();j++) {
//je recupere dans des variables les sommets
Vertex auxSource= new Vertex(auxArc.get(i).get(j).getSource().getUrl());
Vertex auxTarget= new Vertex(auxArc.get(i).get(j).getTarget().getUrl());
//on cr�e l'�lement arc qui aura 2 atributs 
Element arc = gra.createElement("arc");
Attr sommetS = gra.createAttribute("sommetSource");
Attr sommetT = gra.createAttribute("sommetTarget");
//on met dans les sommets les Urls r�cup�rees
sommetS.setValue(auxSource.getUrl());
sommetT.setValue(auxTarget.getUrl());
arc.setAttributeNode(sommetS);
arc.setAttributeNode(sommetT);
//on ajoute tout � l'element Graph
graph.appendChild(arc);
}
}
//on ajoute l'element graph dans le document gra
gra.appendChild(graph);
        TransformerFactory transtory = TransformerFactory.newInstance();
        DOMSource targeet = new DOMSource(gra);
        Transformer changementexport = transtory.newTransformer();
        //exporter en graphml
        StreamResult graphhml = new StreamResult(new File("GRAPHMLLL.graphml"));
        changementexport.transform(targeet, graphhml);
}
catch (ParserConfigurationException pce) {
        pce.printStackTrace();
}
catch (TransformerException tfe) {
        tfe.printStackTrace();
 }
}
}

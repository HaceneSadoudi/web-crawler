package fr.univavignon.ceri.webcrawl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
Element graphml = gra.createElement("graphml");
Element key = gra.createElement("key");
Element key1 = gra.createElement("key");
Element key2 = gra.createElement("key");
//AJOUT DES ELEMENTS PRIMORDIAL AUX GRAPHE / CONCERNANT LA LECTURE
Attr attrXmlns = gra.createAttribute("xmlns");					
Attr attrXmlnsXsi = gra.createAttribute("xmlns:xsi");			
Attr attrXsiSchema = gra.createAttribute("xsi:schemaLocation");	
attrXmlnsXsi.setValue("http://www.w3.org/2001/XMLSchema-instance");														
attrXmlns.setValue("http://graphml.graphdrawing.org/xmlns");															
attrXsiSchema.setValue("http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd");
graphml.setAttributeNode(attrXmlns);	
graphml.setAttributeNode(attrXmlnsXsi);	
graphml.setAttributeNode(attrXsiSchema);	
System.out.println("");	

//CREATION DATA ET KEY

Attr id = gra.createAttribute("id");
Attr foor = gra.createAttribute("for");
Attr attrname = gra.createAttribute("attr.name");
Attr attrtype = gra.createAttribute("attr.type");
System.out.println("");
foor.setValue("node");
id.setNodeValue("d0");
attrname.setNodeValue("titre");
attrtype.setNodeValue("string");

key.setAttributeNode(id);
key.setAttributeNode(attrname);
key.setAttributeNode(foor);
key.setAttributeNode(attrtype);
System.out.println("");
//POUR LURL

Attr idd = gra.createAttribute("id");
Attr foorr = gra.createAttribute("for");
Attr attrnamee = gra.createAttribute("attr.name");
Attr attrtypee = gra.createAttribute("attr.type");
System.out.println("");

idd.setNodeValue("d3");
foorr.setNodeValue("node");
attrnamee.setNodeValue("url");
attrtypee.setNodeValue("string");
System.out.println("");
key1.setAttributeNode(idd);
key1.setAttributeNode(attrnamee);
key1.setAttributeNode(foorr);
key1.setAttributeNode(attrtypee);
System.out.println("");

System.out.println("");

//POUR LES EDGES
Attr ide = gra.createAttribute("id");
Attr fore = gra.createAttribute("for");
Attr attre = gra.createAttribute("attr.name");
Attr attrtypeeee = gra.createAttribute("attr.type");
System.out.println("");
ide.setValue("d1");
fore.setValue("edge");
attre.setValue("weight");
attrtypeeee.setValue("int");
System.out.println("");
key2.setAttributeNode(ide);
key2.setAttributeNode(fore);
key2.setAttributeNode(attre);
key2.setAttributeNode(attrtypeeee);
System.out.println("");

Element graph = gra.createElement("graph");
Attr idG = gra.createAttribute("id");
Attr edgeG = gra.createAttribute("edgedefault");
idG.setValue("G");
edgeG.setValue("undirected");
graph.setAttributeNode(idG);		
graph.setAttributeNode(edgeG);
graphml.appendChild(key);
graphml.appendChild(key1);
graphml.appendChild(key2);
graphml.appendChild(graph);

for (int i=0;i<auxArc.get(0).size();i++) {
	Edge ii = auxArc.get(0).get(i);
	//creation node
	Element node = gra.createElement("node");								
	Attr numeroID = gra.createAttribute("id");
	numeroID.setValue(auxArc.get(0).get(i).getTarget().toString());		
	node.setAttributeNode(numeroID);
	graph.appendChild(node);
	Element data1 = gra.createElement("data");
	Element data2 = gra.createElement("data");
	Attr keydata1 = gra.createAttribute("key");
	keydata1.setValue("d0");
	Attr keydata2 = gra.createAttribute("key");
	keydata2.setValue("d3");
	data2.setAttributeNode(keydata2);
	data2.appendChild(gra.createTextNode(ii.getLink()));													
	data1.setAttributeNode(keydata1);
	data1.appendChild(gra.createTextNode(ii.getTitle()));//pour titre / A CHANGER
	node.appendChild(data2);
	node.appendChild(data1);
	Element edge3 = gra.createElement("edge");				
	Attr sourcee = gra.createAttribute("source");
	Attr target = gra.createAttribute("target");
	target.setValue(ii.getTarget().toString());
	sourcee.setValue(ii.getSource().toString()); 
	graph.appendChild(edge3);
	edge3.setAttributeNode(sourcee);
	edge3.setAttributeNode(target);
	Element dataPoid = gra.createElement("data");		
	Attr pooid = gra.createAttribute("key");				
	pooid.setValue("d1");
	dataPoid.setAttributeNode(pooid);
	dataPoid.appendChild(gra.createTextNode(""+ii.getPonderation())); 
	edge3.appendChild(dataPoid);
	graph.appendChild(edge3);
	graph.appendChild(node);
}

//on ajoute l'element graph dans le document gra
gra.appendChild(graphml);
        TransformerFactory transtory = TransformerFactory.newInstance();
        DOMSource targeet = new DOMSource(gra);
        Transformer changementexport = transtory.newTransformer();
        //exporter en graphml
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        StreamResult graphhml = new StreamResult(new File(dtf.format(now) + ".graphml"));
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

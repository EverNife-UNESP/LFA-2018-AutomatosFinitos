package br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.FiniteAutomationApplication;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Aresta;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml.encapsulation.EdgeToXML;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml.encapsulation.VertexToXML;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Graph;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLFileManipulator {

    private static List<String> fileLines = new ArrayList<>();


    public static boolean saveOnFile(String fileName){
        fileLines.clear();
        Graph graph = FiniteAutomationApplication.getGraph();

        fileLines.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with FiniAutomation 2018.--><structure>;");
        fileLines.add("\t<type>fa</type>;");
        fileLines.add("\t<automaton>;");
        fileLines.add("\t\t<!--The list of states.-->;");
        for (Vertex vertex : graph.getAllVertex()){
            fileLines.addAll(VertexToXML.generateStringLines(vertex));
        }
        fileLines.add("\t\t<!--The list of transitions.-->;");
        for (Edge edge : graph.getEdges()){
            fileLines.addAll(EdgeToXML.generateStringLines(edge));
        }
        fileLines.add("\t</automaton>;");
        fileLines.add("</structure>");

        try {
            Path file = Paths.get(fileName+".jff");
            Files.write(file, fileLines, Charset.forName("UTF-8"));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean readFromXML(String fileName){

        try {
            File fXmlFile = new File( fileName + ".jff");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");


            FiniteAutomationApplication.clear();
            NodeList vertexex = doc.getElementsByTagName("state");
            for (int temp = 0; temp < vertexex.getLength(); temp++) {
                Node nNode = vertexex.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int iD = Integer.valueOf(eElement.getAttribute("id"));
                    String name = eElement.getAttribute("name");
                    float xCoord = Float.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
                    float yCoord = Float.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
                    boolean isFinal = eElement.getElementsByTagName("final").getLength() == 1;
                    boolean isInitial = eElement.getElementsByTagName("initial").getLength() == 1;


                    System.out.println("Vertex ID : " + iD);
                    System.out.println("Vertex Name : " + name);
                    System.out.println("Vertex xCoord : " + xCoord);
                    System.out.println("Vertex yCoord : " + yCoord);
                    System.out.println("Vertex isFinal : " + isFinal);
                    System.out.println("Vertex isInitial : " + isInitial);

                    FiniteAutomationApplication.getGraph().addCustomVertex(iD);
                    Vertex vertex = FiniteAutomationApplication.getGraph().getVertex(iD);

                    vertex.getCircle().setCenterX(xCoord);
                    vertex.getCircle().setCenterY(yCoord);
                    vertex.setFinale(isFinal);
                    vertex.setInitial(isInitial);
                    if (!name.equalsIgnoreCase("q" + iD)) vertex.setCustomName(name);

                }
            }

            NodeList edges = doc.getElementsByTagName("transition");
            for (int temp = 0; temp < edges.getLength(); temp++) {
                Node nNode = edges.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    int sourceId = Integer.parseInt(eElement.getElementsByTagName("from").item(0).getTextContent());
                    int targetId = Integer.parseInt(eElement.getElementsByTagName("to").item(0).getTextContent());
                    char value = eElement.getElementsByTagName("read").item(0).getTextContent().charAt(0);

                    System.out.println("Transition sourceId : " + sourceId);
                    System.out.println("Vertex targetId : " + targetId);
                    System.out.println("Vertex value : " + value);

                    Vertex sourceVertex = FiniteAutomationApplication.getGraph().getVertex(sourceId);
                    Vertex targetVertex = FiniteAutomationApplication.getGraph().getVertex(targetId);
                    Edge edge  = FiniteAutomationApplication.getGraph().getOrCreateEdge(sourceVertex,targetVertex);
                    Aresta aresta = edge.getOrCreateAresta(sourceVertex);
                    aresta.addGrammar(value);
                }
            }

            FiniteAutomationApplication.getGraph().reloadPage();
            FiniteAutomationApplication.getGraph().reloadPage();


            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

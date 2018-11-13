package br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.FiniteAutomationApplication;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Vertice;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml.encapsulation.EdgeToXML;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml.encapsulation.VertexToXML;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Graph;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLFileExporter {

    private static List<String> whatIWillSave = new ArrayList<>();


    public static void saveOnFile(String fileName){
        whatIWillSave.clear();
        Graph graph = FiniteAutomationApplication.getGraph();

        whatIWillSave.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with FiniAutomation 2018.--><structure>;");
        whatIWillSave.add("\t<type>fa</type>;");
        whatIWillSave.add("\t<automaton>;");
        whatIWillSave.add("\t\t<!--The list of states.-->;");
        for (Vertex vertex : graph.getAllVertex()){
            whatIWillSave.addAll(VertexToXML.generateStringLines(vertex));
        }
        whatIWillSave.add("\t\t<!--The list of transitions.-->;");
        for (Edge edge : graph.getEdges()){
            whatIWillSave.addAll(EdgeToXML.generateStringLines(edge));
        }
        whatIWillSave.add("\t</automaton>;");
        whatIWillSave.add("</structure>");

        try {
            Path file = Paths.get(fileName+".jff");
            Files.write(file, whatIWillSave, Charset.forName("UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadFromFile(String fileName){
        whatIWillSave.clear();

        try {
            Path file = Paths.get(fileName+".jff");
            whatIWillSave = new ArrayList<>(Files.readAllLines(file, Charset.forName("UTF-8")));

            whatIWillSave.remove(0);
            whatIWillSave.remove(0);
            whatIWillSave.remove(0);
            whatIWillSave.remove(0);

            String line = whatIWillSave.get(0);

            while (!line.equalsIgnoreCase("<!--The list of transitions.-->;")){



            }
        }catch (Exception e){
            e.printStackTrace();
        }


        Graph graph = FiniteAutomationApplication.getGraph();

        whatIWillSave.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with FiniAutomation 2018.--><structure>;");
        whatIWillSave.add("\t<type>fa</type>;");
        whatIWillSave.add("\t<automaton>;");
        whatIWillSave.add("\t\t<!--The list of states.-->;");
        for (Vertex vertex : graph.getAllVertex()){
            whatIWillSave.addAll(VertexToXML.generateStringLines(vertex));
        }
        whatIWillSave.add("\t\t<!--The list of transitions.-->;");
        for (Edge edge : graph.getEdges()){
            whatIWillSave.addAll(EdgeToXML.generateStringLines(edge));
        }
        whatIWillSave.add("\t</automaton>;");
        whatIWillSave.add("</structure>");

        try {
            Path file = Paths.get(fileName+".jff");
            Files.write(file, whatIWillSave, Charset.forName("UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

package br.com.finalcraft.unesp.lfa.finiteautomaton.application;

import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Graph;

public class FiniteAutomationApplication {

    // Reference to the main application.
    private static Graph graph = new Graph();

    public static Graph getGraph() {
        return graph;
    }

    public static void clear(){
        graph.clearAll();
    }



/*
    public static Grafo grafo = new Grafo(10, new ListaAdjacencia()); ///estrutura de dados;

    public static void defaultValues() {

        //graph = new Graph(10); ///desenho
        graph = new Graph(); ///desenho
        grafo = new Grafo(10, new ListaAdjacencia()); ///estrutura de dados

        //leitura das todasAsArestas
        String line;

        for (int i = 1; i<10 ;i++){
            int vIni = i - 1; //verticeInicial
            int vFim = i; //verticeFinal
            int vPeso = 2; //Peso do Vértice

            Vertex vS = graph.getAllVertex().get(vIni);
            Vertex vT = graph.getAllVertex().get(vFim);

            if(1 == 0){
                grafo.addAresta(vIni, vFim, vPeso); //estrutura de dados
                Edge e1 = new Edge(vS, vT, vPeso, 0); //desenho
                graph.addEdge(e1);    //desenho
                Edge e2 = new Edge(vT, vS, vPeso, 0); //desenho
                graph.addEdge(e2);    //desenho
            }else{
                grafo.addArestaD(vIni, vFim, vPeso); //estrutura de dados
                Edge e = new Edge(vS, vT, vPeso, 1); //desenho
                graph.addEdge(e);    //desenho
            }
        }

        graph.addObjects(Main.getBP());
    }
    */
}
    
 
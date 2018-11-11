package br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.FiniteAutomationApplication;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Aresta;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Vertice;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Graph;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Validator {


    public static List<Aresta> todasAsArestas = new ArrayList<Aresta>();
    public static List<Vertice> todosOsVertices = new ArrayList<Vertice>();

    public static Vertice verticeInicial = null;

    public static void loadGraph(){
        todasAsArestas.clear();
        todosOsVertices.clear();
        verticeInicial = null;

        Graph graph = FiniteAutomationApplication.getGraph();

        for (Edge edge : graph.getEdges()){
            if (edge.arestaOne.isValid()){
                todasAsArestas.add(edge.arestaOne);
            }
            if (edge.isBidiretec() && edge.arestaTwo.isValid()){
                todasAsArestas.add(edge.arestaTwo);
            }
        }

        for (Vertex vertex : graph.getAllVertex()){
            Vertice vertice = Vertice.encapsulate(vertex);
            vertice.extractOwnVertices(todasAsArestas);
            todosOsVertices.add(vertice);
            if (vertice.isInitial){
                verticeInicial = vertice;
            }
        }

        System.out.println("Todos os vertices: \n");

        todosOsVertices.forEach(vertice -> {
            System.out.println("\nq" + vertice.id + " :");
            vertice.arestasList.forEach(aresta -> {
                System.out.println("   q" + vertice.id + " --> q" + aresta.targetId + " == " + aresta.getGramarsString() );
            });
        });

        System.out.println("\n\n");

    }


    public static String theExpression;
    public static boolean validadeString(String expression){
        theExpression = expression;

        if (verticeInicial == null){
            return false;
        }

        onTimeChange(); // Reinicia o infiniteLoopPreventer
        HistoryLog historyLog = walk(new HistoryLog(), verticeInicial);

        System.out.println("[" + historyLog.time + "]  --> " + historyLog.path);
        return historyLog.math;
    }

    public static HistoryLog validadeStringWithLog(String expression){
        theExpression = expression;

        if (verticeInicial == null){
            return null;
        }

        onTimeChange(); // Reinicia o infiniteLoopPreventer
        HistoryLog historyLog = walk(new HistoryLog(), verticeInicial);

        System.out.println("[" + historyLog.time + "]  --> " + historyLog.path);
        return historyLog;
    }

    public static List<Integer> infiniteLoopPreventer = new ArrayList<Integer>();
    public static void onTimeChange(){
        infiniteLoopPreventer.clear();
    }
    public static HistoryLog walk(HistoryLog previousLog, Vertice vertice){

        //Se ele for final, ja retorna pq deu certo
        previousLog.addPath(vertice.id);
        if (vertice.isFinale && previousLog.time + 1 == theExpression.length()){
            System.out.println("Final <--");
            previousLog.math = true;
            return previousLog;
        }

        //Verifica os caminhos com vazio
        for (Aresta aresta : vertice.arestasList) {
            try {
                if (aresta.getGrammars().contains('\u03B5') && !infiniteLoopPreventer.contains(aresta.getTargetId())){
                    infiniteLoopPreventer.add(aresta.getTargetId());
                    System.out.println("Null Dislock  -:> " + aresta.getTargetId());
                    HistoryLog previousPrevious = previousLog.clone();
                    previousPrevious.addNullPathPrefix();

                    HistoryLog historyLog = walk(previousPrevious, getVerticeFromID(aresta.getTargetId()));
                    if (historyLog.math){
                        return historyLog;
                    }
                }
            }catch (Exception ignored){}
        }

        //Atualiza o tempo do estado
        previousLog.addTime();
        onTimeChange(); // Reinicia o infiniteLoopPreventer

        //
        for (Aresta aresta : vertice.arestasList) {
            try {
                char checkChar = theExpression.charAt(previousLog.time);
                if (aresta.getGrammars().contains(checkChar)){
                    System.out.println("[" + checkChar + "]Checking target  -:> " + aresta.getTargetId());

                    //Passa uma cópia do historilog atual
                    HistoryLog historyLog = walk(previousLog.clone(), getVerticeFromID(aresta.getTargetId()));
                    if (historyLog.math){
                        return historyLog;
                    }
                }
            }catch (Exception ignored){}
        }

        return previousLog;
    }


    public static List<Vertice> getFutureVertices(Vertice vertice){
        List<Vertice> verticeList = new ArrayList<Vertice>();
        for (Aresta aresta : vertice.arestasList){
            verticeList.add(getVerticeFromID(aresta.targetId));
        }
        return verticeList;
    }

    public static List<Vertice> getFutureNonSelfVertices(Vertice vertice){
        List<Vertice> verticeList = new ArrayList<Vertice>();
        for (Vertice aFutureVertice : getFutureVertices(vertice)){
            if (vertice != aFutureVertice){
                verticeList.add(aFutureVertice);
            }
        }
        return verticeList;
    }

    public static Vertice getVerticeFromID(int theId){
        for (Vertice vertice : todosOsVertices){
            if (vertice.id == theId){
                return vertice;
            }
        }
        return null;
    }

    public static Aresta getArestaBetween(int sourceId, int targetId){
        for (Aresta aresta : todasAsArestas){
            if (aresta.sourceId == sourceId && aresta.targetId == targetId){
                return aresta;
            }
        }
        return null;
    }




}
package br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data;

import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Vertice {

    public int id;
    public boolean isInitial = false;
    public boolean isFinale = false;
    public List<Aresta> arestasList = new ArrayList<Aresta>();



    private Vertice() {
    }

    public static Vertice encapsulate(Vertex vertex){
        Vertice vertice = new Vertice();
        vertice.id = vertex.getID();
        vertice.isInitial = vertex.isInitial();
        vertice.isFinale = vertex.isFinale();
        return vertice;
    }

    public void extractOwnVertices(List<Aresta> allArestas){
        for (Aresta aresta : allArestas){
            if (aresta.getSourceId() == this.id){
                this.arestasList.add(aresta);
            }
        }
    }


    public int getId() {
        return id;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public boolean isFinale() {
        return isFinale;
    }

    public List<Aresta> getArestasList() {
        return arestasList;
    }
}

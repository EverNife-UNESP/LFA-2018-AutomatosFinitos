package br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aresta {

    public int sourceId;
    public int targetId;

    public List<Character> grammars = new ArrayList<Character>();

    public Aresta(int sourceId, int destinyId) {
        this.sourceId = sourceId;
        this.targetId = destinyId;
    }

    public boolean addGrammar(char grammar){
        if (grammars.contains(grammar)) {
            return false;
        }
        grammars.add(grammar);
        Collections.sort(grammars);
        return true;
    }

    public List<Character> getGrammars() {
        return grammars;
    }

    public String getGramarsString() {
        if (grammars.isEmpty()) return "Truly Empty";
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : getGrammars()){
            stringBuilder.append( character + "|");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public boolean isValid() {
        return grammars.size() > 0;
    }

    public String getIdentifier(){
        return "q" + sourceId + " --> q" + targetId;
    }
}

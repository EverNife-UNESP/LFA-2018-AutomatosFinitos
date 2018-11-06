package br.com.finalcraft.unesp.lfa.grammar.application;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrammarExpression implements Comparable<GrammarExpression>{

    public String leftTerm;
    public String rightTerm;

    private static List<GrammarExpression> grammarExpressionList = new ArrayList<GrammarExpression>();

    public GrammarExpression(String leftTerm, String rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }


    public static List<GrammarExpression> getAllExpressions(){
        return grammarExpressionList;
    }

    public static List<GrammarExpression> getAllExpressionsThatStartWith(String leftTerm){
        List<GrammarExpression> expressions = new ArrayList<GrammarExpression>();
        for (GrammarExpression listedGrammar : grammarExpressionList){
            if (listedGrammar.getLeftTerm().equals(leftTerm)){
                expressions.add(listedGrammar);
            }
        }
        return expressions;
    }

    public static void addExpression(GrammarExpression grammarExpression){
        grammarExpressionList.add(grammarExpression);
        System.out.println(grammarExpression.leftTerm + " --> " + grammarExpression.rightTerm);
    }

    public static boolean removeExpression(GrammarExpression grammarExpression){
        for (GrammarExpression listedGrammar : grammarExpressionList){
            if (listedGrammar.isEquals(grammarExpression)){
                grammarExpressionList.remove(listedGrammar);
            }
        }
        return false;
    }

    public static boolean removeByIndex(int index){
        if (index >= 0 && index <= grammarExpressionList.size()){
            grammarExpressionList.remove(index);
            return true;
        }
        return false;
    }

    public static boolean contains(GrammarExpression grammarExpression){
        for (GrammarExpression listedGrammar : grammarExpressionList){
            if (listedGrammar.getRightTerm().isEmpty()){
                listedGrammar.rightTerm = "\u03B5";
            }
            if (listedGrammar.isEquals(grammarExpression)){
                return true;
            }
        }
        return false;
    }

    public static void readTable(ObservableList<GrammarExpression> observableList){

        List<GrammarExpression> emptyRightTerms = new ArrayList<GrammarExpression>();
        for (GrammarExpression grammarExpression : grammarExpressionList){
            if (grammarExpression.getRightTerm().isEmpty()){
                grammarExpression.rightTerm = "\u03B5";
            }
            if (grammarExpression.rightTerm == "\u03B5"){
                emptyRightTerms.add(grammarExpression);
            }
        }

        for (GrammarExpression aEmptyGrammar : emptyRightTerms) {
            grammarExpressionList.remove(aEmptyGrammar);
            grammarExpressionList.add(aEmptyGrammar);
        }

        Collections.sort(grammarExpressionList);

        observableList.clear();
        for (GrammarExpression grammarExpression : grammarExpressionList) {
            observableList.add(grammarExpression);
        }

    }

    public boolean isEquals(GrammarExpression otherGrammar){
        if (this.leftTerm.equals(otherGrammar.leftTerm)
                && this.rightTerm.equals(otherGrammar.rightTerm)){
            return true;
        }
        return false;
    }

    public String getLeftTerm() {
        return leftTerm;
    }

    public String getRightTerm() {
        return rightTerm;
    }

    @Override
    public int compareTo(GrammarExpression o) {
        return this.leftTerm.compareTo(o.leftTerm);
    }
}

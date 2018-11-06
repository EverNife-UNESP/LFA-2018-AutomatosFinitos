package br.com.finalcraft.unesp.lfa.grammar.application;

import java.util.ArrayList;
import java.util.List;

public class GrammarValidator {


    public static int iteratorCounter = 0;

    public static List<GrammarExpression> otimizedGrammarExpressions = new ArrayList<GrammarExpression>();


    public static String getUpperCaseFromString(String string){
        int size = string.length();
        for(int i = 0; i < size; i++){
            if(Character.isUpperCase(string.charAt(i))){
                return string.charAt(i) + "";
            }
        }
        return "";
    }


    /* Basicamente olha todas as regras gramaticais e elimita
     * aquelas que nunca poderão ser checadas.
     */
    public static void optimizeUnreachableGrammars(){

        long timeCounter = System.currentTimeMillis();
        otimizedGrammarExpressions.clear();

        List<String> recheableLetters = new ArrayList<String>();
        recheableLetters.add(GrammarExpression.getAllExpressions().get(0).leftTerm);

        for (GrammarExpression grammarExpression : GrammarExpression.getAllExpressions()){
            if (recheableLetters.contains(grammarExpression.leftTerm)){
                continue;
            }

            for (int i = 0; i < recheableLetters.size(); i++){
                for (GrammarExpression aGramar : GrammarExpression.getAllExpressionsThatStartWith(recheableLetters.get(i))){
                    String upperCaseLetter = getUpperCaseFromString(aGramar.rightTerm);
                    if ( !upperCaseLetter.isEmpty() && !recheableLetters.contains(upperCaseLetter)){
                        recheableLetters.add(upperCaseLetter);
                    }
                }
            }
        }
        for (String letter : recheableLetters){
            for (GrammarExpression aGramar : GrammarExpression.getAllExpressionsThatStartWith(letter)){
                otimizedGrammarExpressions.add(aGramar);
            }
        }


        //Remove ε from grammars
        for (GrammarExpression grammarExpression : otimizedGrammarExpressions){
            if (grammarExpression.getRightTerm().contains("\u03B5")){
                grammarExpression.rightTerm = grammarExpression.getRightTerm().replaceAll("\u03B5","");
            }
        }

        timeCounter = System.currentTimeMillis() - timeCounter;
        System.out.println("\n\n\n\n");
        System.out.println("A otimização custou " + timeCounter + " milisegundos");
        for (String letter : recheableLetters){
            System.out.println("Recheable Leters :   " + letter);
        }

    }

    public static boolean validade(String expression){
        optimizeUnreachableGrammars();
        for (GrammarExpression aGrammar : GrammarExpression.getAllExpressionsThatStartWith(otimizedGrammarExpressions.get(0).getLeftTerm())){
            infiniteLooperPreventer.clear();
            System.out.println("\n---- GrammarStarer at " + aGrammar.getRightTerm() + " ----");
            ValidateData validateData = validade(expression,"",aGrammar);

            if (validateData.isOk() && validateData.getRemainingString().equals(expression)){
                return true;
            }
        }
        return false;
    }

    private static List<String> infiniteLooperPreventer = new ArrayList<String>();

    private static ValidateData validade(String fullExpression, String currentCalculated, GrammarExpression grammarExpression){


        String rightTerm = grammarExpression.getRightTerm();
        String nonTerminal = getUpperCaseFromString(rightTerm);

        String beforeNonTerminal = "";
        String afterNonTerminal = "";
        if (nonTerminal.isEmpty()){
            beforeNonTerminal = rightTerm;
        }else {
            boolean chekingLeft = true;
            for (int i = 0; i < rightTerm.length(); i++){
                if (rightTerm.charAt(i) == nonTerminal.charAt(0)){
                    chekingLeft = false;
                    continue;
                }
                if (chekingLeft) {
                    beforeNonTerminal = beforeNonTerminal + rightTerm.charAt(i);
                }else {
                    afterNonTerminal = afterNonTerminal + rightTerm.charAt(i);
                }
            }
        }

        currentCalculated = currentCalculated + beforeNonTerminal;
        System.out.print("\nchecking [" + fullExpression + "] (left|nonTerminal|right) === " + beforeNonTerminal + "|" + nonTerminal + "|" + afterNonTerminal + ")   --> " + currentCalculated );
        if ( !fullExpression.contains(currentCalculated)) {
            return new ValidateData(false);
        }

        if (nonTerminal.isEmpty()){
            return new ValidateData(currentCalculated,true);
        }

                /*
        if (infiniteLooperPreventer.contains(currentCalculated + "-" + nonTerminal)){
            infiniteLooperPreventer.remove(currentCalculated + "-" + nonTerminal);
            return new ValidateData(false);
        }
        infiniteLooperPreventer.add(currentCalculated + "-" + nonTerminal);
        */


        if (infiniteLooperPreventer.contains(currentCalculated + "-" + beforeNonTerminal + nonTerminal + afterNonTerminal)){
            System.out.print("    -> return false (InfinitLooper)");
            infiniteLooperPreventer.remove(currentCalculated + "-" + beforeNonTerminal + nonTerminal + afterNonTerminal);
            return new ValidateData(false);
        }
        infiniteLooperPreventer.add(currentCalculated + "-" + beforeNonTerminal + nonTerminal + afterNonTerminal);



        for (GrammarExpression aGrammar : GrammarExpression.getAllExpressionsThatStartWith(nonTerminal)){
            ValidateData validateData = validade(fullExpression,currentCalculated,aGrammar);

            // Se começar ou terminar com
            if (validateData.isOk() &&
                    (fullExpression.startsWith(validateData.getRemainingString() + afterNonTerminal) || fullExpression.endsWith(beforeNonTerminal + validateData.getRemainingString()))){
                validateData.setRemainingString(validateData.getRemainingString() + afterNonTerminal);
                System.out.print("    -> return true");
                return validateData;
            }
        }

        System.out.print("    -> return false");
        return new ValidateData(false);
    }
    /*

    public static boolean validade(String fullExpression, String currentCalculated, GrammarExpression grammarExpression){

        for (GrammarExpression grammarExpression : otimizedGrammarExpressions){
            String rightTerm = grammarExpression.getRightTerm();
            String nonTerminal = getUpperCaseFromString(rightTerm);

            String beforeNonTerminal;
            String afterNonTerminal = "";
            if (!nonTerminal.isEmpty()){
                beforeNonTerminal = rightTerm;
            }else {
                String[] splitting = rightTerm.split(nonTerminal);
                beforeNonTerminal = splitting[0];
                if (splitting.length == 2){
                    afterNonTerminal = splitting[1];
                }
            }

            if (fullExpression.startsWith(currentCalculated + beforeNonTerminal)){

            }

            if (splitting.length == 2){
                afterNonTerminal = splitting[0];
            }

            String firstHalf = grammarExpression.getLeftTerm().split(getUpperCaseFromString())




        }

        return true;
    }
    */



}

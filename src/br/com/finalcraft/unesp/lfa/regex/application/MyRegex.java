package br.com.finalcraft.unesp.lfa.regex.application;

public class MyRegex {
    public static boolean validade(String regex, String expression){
        return expression.matches(regex);
    }
}

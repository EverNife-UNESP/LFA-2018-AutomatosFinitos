package br.com.finalcraft.unesp.lfa.grammar.application;

public class ValidateData {

    private String remainingString = "";
    private Boolean isOk = false;

    public ValidateData(String remainingString) {
        this.remainingString = remainingString;
    }

    public ValidateData(String remainingString, Boolean isOk) {
        this.remainingString = remainingString;
        this.isOk = isOk;
    }

    public ValidateData(Boolean isOk) {
        this.isOk = isOk;
    }

    public String getRemainingString() {
        return remainingString;
    }

    public void setRemainingString(String remainingString) {
        this.remainingString = remainingString;
    }

    public Boolean isOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        isOk = ok;
    }
}

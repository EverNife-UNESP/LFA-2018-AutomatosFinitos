package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions.neededdata;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CharacterToString {


    String value;

    public static ObservableList<CharacterToString> observableList(List<Character> characterList){
        ObservableList<CharacterToString> observableList = FXCollections.observableArrayList();
        for (Character character : characterList){
            observableList.add(new CharacterToString(character));
        }
        return observableList;
    }

    public CharacterToString(char character) {
        this.value = String.valueOf(character);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

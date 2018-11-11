package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.FiniteAutomationApplication;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.tester.FiniteAutomationTesterController;
import br.com.finalcraft.unesp.lfa.grammar.javafx.GrammarController;
import br.com.finalcraft.unesp.lfa.regex.javafx.RegexController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class MainController {


    public enum PressedButton{
        EDITAR_ESTADOS,
        EDITAR_TRANSICOES;
    }

    private static MainController instance;

    public static PressedButton getPressedRadioButton(){
        if (instance.mainmenu.getSelectedToggle().equals(instance.editStates)){
            return PressedButton.EDITAR_ESTADOS;
        }else {
            return PressedButton.EDITAR_TRANSICOES;
        }
    }

    public static boolean doubleClickIsEnabled(){
        return instance.doubleClickCheck.isSelected();
    }



    @FXML
    void initialize() {
        instance = this;
    }

    @FXML
    Button addStateButton;

    @FXML
    ToggleGroup mainmenu;

    @FXML
    RadioButton editStates;

    @FXML
    RadioButton editTransactions;

    @FXML
    CheckBox doubleClickCheck;

    @FXML
    void onAddState(ActionEvent event) {
        if (getPressedRadioButton() == PressedButton.EDITAR_ESTADOS){
            FiniteAutomationApplication.getGraph().addVertex();
        }
        //FiniteAutomationApplication.defaultValues();
    }

    @FXML
    void openGramar() {
        GrammarController.show();
    }

    @FXML
    void openRegex() {
        RegexController.show();
    }

    @FXML
    void onStateChange() {
        FiniteAutomationApplication.getGraph().getAllVertex().forEach(vertex -> vertex.repaint());
        if (getPressedRadioButton() == PressedButton.EDITAR_TRANSICOES){
            addStateButton.setOpacity(0.3);
            addStateButton.setDisable(true);
        }else {
            addStateButton.setOpacity(1);
            addStateButton.setDisable(false);

        }
        // :D
     }

    @FXML
    void onTestarAutomato() {
        FiniteAutomationTesterController.show();
    }

}

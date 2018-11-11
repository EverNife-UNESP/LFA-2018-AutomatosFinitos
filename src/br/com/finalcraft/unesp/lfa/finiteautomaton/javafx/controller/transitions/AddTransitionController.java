package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.FiniteAutomationApplication;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Aresta;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.Main;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view.MyFXMLs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddTransitionController {


    private static AddTransitionController instance;
    private static Stage dialog;

    private static Edge edge;
    private static Vertex startVertex;
    private static Vertex endVertex;


    @FXML
    void initialize() {
        instance = this;


    }

    public static void setUp(){
        dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        // Defines a modal window that blocks events from being
        // delivered to any other application window.
        dialog.initOwner(Main.thePrimaryStage);

        Scene newSceneWindow1 = new Scene(MyFXMLs.add_transition);
        dialog.setScene(newSceneWindow1);
    }

    public static void show(Vertex start, Vertex end){
        startVertex = start;
        endVertex = end;

        instance.stateIdentifier.setText("q" + startVertex.getID() + " --> " + "q" + endVertex.getID());
        instance.transaction_value.setText("");

        dialog.show();
    }

    @FXML
    private TextField transaction_value;

    @FXML
    private Label stateIdentifier;

    @FXML
    private CheckBox initial;

    @FXML
    private CheckBox finale;

    @FXML
    void onSaveChanges(ActionEvent event) {
        Edge edge = FiniteAutomationApplication.getGraph().getOrCreateEdge(startVertex, endVertex);
        Aresta aresta = edge.getOrCreateAresta(startVertex);

        String transactionValueText = transaction_value.getText();

        if ( transactionValueText.isEmpty()){
            aresta.addGrammar('\u03B5');
        }else {
            for (char charactere : transactionValueText.toCharArray()){
                aresta.addGrammar(charactere);
            }
        }

        edge.calculateAndSetTextOne();
        dialog.close();
    }

    @FXML
    void onCancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void stateName(ActionEvent event) {

    }


}

package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions;

import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.Main;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view.MyFXMLs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Deprecated
public class TrasactionEditorController {


    private static TrasactionEditorController instance;
    private static Stage dialog;
    private static Edge theEdge;


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

        Scene newSceneWindow1 = new Scene(MyFXMLs.transition_editor);
        dialog.setScene(newSceneWindow1);
    }

    public static void show(Edge edge){
        theEdge = edge;

        instance.identifierLeft.setText("Estado q" + edge.getSource().getID() + " para q" + edge.getTarget().getID());
        instance.identifierRight.setText("Estado q" + edge.getTarget().getID() + " para q" + edge.getSource().getID());

        dialog.show();
    }
    @FXML
    private Label identifierLeft;

    @FXML
    private TableView<?> leftTable;

    @FXML
    private TableColumn<?, ?> leftColumn;

    @FXML
    private Label identifierRight;

    @FXML
    private TableView<?> rightTable;

    @FXML
    private TableColumn<?, ?> rightColumn;

    @FXML
    void onRemoveTermButtonLeft(ActionEvent event) {

    }

    @FXML
    void onRemoveTermButtonRight(ActionEvent event) {

    }

    @FXML
    void onTransactionDelete(ActionEvent event) {

    }



}

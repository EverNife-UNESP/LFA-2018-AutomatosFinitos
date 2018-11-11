package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Aresta;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.Main;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions.neededdata.CharacterToString;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view.MyFXMLs;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SideOnlyEditTransitionController {


    private static SideOnlyEditTransitionController instance;
    private static Stage dialog;

    private static Edge edge;
    private static Aresta aresta;
    private static ObservableList<CharacterToString> observableList;


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

        Scene newSceneWindow1 = new Scene(MyFXMLs.edit_transition_sideonly);
        dialog.setScene(newSceneWindow1);
        dialog.setResizable(false);
        instance.mainColumn.setCellValueFactory(new PropertyValueFactory<CharacterToString,String>("value"));
    }

    public static void show(Edge edge) {
        show(edge,1);
    }
    public static void show(Edge anEdge, int arestarNumber){
        edge = anEdge;

        if (arestarNumber == 1){
            aresta = edge.arestaOne;
        }else {
            aresta = edge.arestaTwo;
        }

        instance.stateIdentifier.setText("q" + aresta.getSourceId() + " --> " + "q" + aresta.getTargetId());

        observableList = CharacterToString.observableList(aresta.getGrammars());
        instance.tableOfValues.setItems(observableList);
        dialog.show();
    }

    @FXML
    private TableView<CharacterToString> tableOfValues;

    @FXML
    private TableColumn<CharacterToString,String> mainColumn;




    @FXML
    private Label stateIdentifier;

    @FXML
    private CheckBox initial;

    @FXML
    private CheckBox finale;


    @FXML
    private void onRemoveTermButton(ActionEvent event) {

        int selectedLine = mainColumn.getTableView().getSelectionModel().getSelectedIndex();

        if (selectedLine < 0){
            return;
        }

        if (removeByIndex(selectedLine)){
            System.out.println("Char removido com sucesso!");
        }
    }

    @FXML
    private void onDeleteTransition(ActionEvent event) {
        aresta.getGrammars().clear();
        edge.deleteIfInvalid();
        dialog.close();
    }

    public boolean removeByIndex(int index){
        if (index >= 0 && index < aresta.getGrammars().size()){
            aresta.getGrammars().remove(index);
            observableList.remove(index);
            edge.calculateAndSetTextOne();
            if (edge.deleteIfInvalid()){
                dialog.close();
            }
            return true;
        }
        return false;
    }
}

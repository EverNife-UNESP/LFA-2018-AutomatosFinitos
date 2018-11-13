package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.filemanager;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.xml.XMLFileExporter;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.Main;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view.MyFXMLs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaverController {


    private static SaverController instance;
    private static Stage dialog;

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

        Scene newSceneWindow1 = new Scene(MyFXMLs.save_to_file);
        dialog.setScene(newSceneWindow1);
    }

    public static void show(){
        dialog.show();
    }

    @FXML
    private TextField fileName;

    @FXML
    void onSaveChanges(ActionEvent event) {
        if (!fileName.getText().isEmpty()){
            XMLFileExporter.saveOnFile(fileName.getText());
            dialog.close();
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        dialog.close();
    }
}

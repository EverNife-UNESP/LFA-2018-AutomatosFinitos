package br.com.finalcraft.unesp.lfa.regex.javafx;

import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.Main;
import br.com.finalcraft.unesp.lfa.regex.application.MyRegex;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegexController implements Initializable {

    public static Parent regexController;

    static {
        try {
            regexController = FXMLLoader.load(RegexController.class.getResource("/br/com/finalcraft/unesp/lfa/regex/javafx/regex.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static RegexController instance;
    private static Stage dialog;

    public RegexController() {
        instance = this;
    }

    public static void setUp(){
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        // Defines a modal window that blocks events from being
        // delivered to any other application window.
        dialog.initOwner(Main.thePrimaryStage);

        Scene newSceneWindow1 = new Scene(regexController);
        dialog.setScene(newSceneWindow1);
        dialog.setTitle("Regex Tool - LFA 2018");
    }

    public static void show(){
        if (dialog.isShowing()){
            dialog.close();
        }
        dialog.show();
    }

    @FXML
    private Label validateTextResult;

    @FXML
    private TextField insertRegex;

    @FXML
    private TextField insertText;

    @FXML
    private void onValidadeButton(ActionEvent event){

        String regex = insertRegex.getText();
        String expression = insertText.getText();

        if (expression.isEmpty() || regex.isEmpty()){
            return;
        }

        if (MyRegex.validade(regex,expression)){
            validateTextResult.setText("✔✔✔✔✔ Expressão válida ✔✔✔✔");
        }else {
            validateTextResult.setText("✖✖✖✖✖ Expressão inválida ✖✖✖✖✖");
        }

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                validateTextResult.setText("....");
            }
        });
        new Thread(sleeper).start();

    }



    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

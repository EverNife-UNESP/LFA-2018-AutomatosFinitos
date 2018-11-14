package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller;

import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.filemanager.LoaderController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.filemanager.SaverController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.states.StateEditorController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.tester.FiniteAutomationTesterController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions.AddTransitionController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions.SideOnlyEditTransitionController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.transitions.TrasactionEditorSelectorController;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view.MyFXMLs;
import br.com.finalcraft.unesp.lfa.grammar.javafx.GrammarController;
import br.com.finalcraft.unesp.lfa.regex.javafx.RegexController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static Stage thePrimaryStage;
    public static BorderPane rootLayout;

    public static BorderPane getBP(){
        return rootLayout;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        thePrimaryStage = primaryStage;
        thePrimaryStage.setTitle("Linguagens Formais e Autômatos - 2018");

        initRootLayout();

        //Iniciando todos as abas para deixar no grau!
        StateEditorController.setUp();
        TrasactionEditorSelectorController.setUp();
        GrammarController.setUp();
        RegexController.setUp();
        AddTransitionController.setUp();
        SideOnlyEditTransitionController.setUp();
        FiniteAutomationTesterController.setUp();
        SaverController.setUp();
        LoaderController.setUp();
    }

    private static long currentTime = 0;
    private static long lastTime = 0;
    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            rootLayout = (BorderPane) MyFXMLs.main_screen;

            Text t = new Text();
            t.setText("Seja bem-vindo(a) ao trabalho de Linguagens Formais e Autômatos.\n                            Adicione um estado para continuar.");
            rootLayout.setCenter(t);

            // Mostra a scene (cena) contendo oroot layout.
            Scene scene = new Scene(rootLayout);
            thePrimaryStage.setScene(scene);

            //Ter certeza de que as janelas serão fechadas corretamente!
            thePrimaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            thePrimaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {


        /*
        JPanel panel = new JPanel();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(layout);

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setVisible(true);
*/




        launch(args);
    }
}

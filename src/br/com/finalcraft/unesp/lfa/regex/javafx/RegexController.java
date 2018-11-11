package br.com.finalcraft.unesp.lfa.regex.javafx;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.Validator;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Aresta;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Vertice;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;
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
import java.util.*;

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

        if (regex.isEmpty()){
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void onConvertAfToRegex(ActionEvent event) {
        Validator.loadGraph();

        boolean noStart = true;
        boolean noEnd = true;

        Vertice initial = null;
        List<Vertice> allFinals = new ArrayList<Vertice>();

        for (Vertice vertice : Validator.todosOsVertices){
            if(vertice.isInitial()){
                initial = vertice;
                noStart = false;
            }
            if (vertice.isFinale()){
                allFinals.add(vertice);
                noEnd = false;
            }
        }

        if ( noStart || noEnd ){  //Caso não tenha inicio ou fim, então, pode colocar uma regra vazia e ta tudo certo!
            insertRegex.setText("^\\b$\n");
            return;
        }

        Map<String,String> mapOfallRegexRulles = new HashMap<String, String>();
        for (Aresta aresta : Validator.todasAsArestas){
            String arestaRegex = mountTheRegexFrom(aresta);
            mapOfallRegexRulles.put(aresta.getIdentifier(),arestaRegex);
            System.out.println(aresta.getIdentifier() + " === " + arestaRegex);
        }



        List<String> finalRegexRules = new ArrayList<>();
        for (Vertice finalVertice : allFinals){

            List<Vertice> pathOfArestas = mountThePathBetween(initial,finalVertice);

            StringBuilder regexRule = new StringBuilder();
            for (int  i = 0; i < pathOfArestas.size() -1 ; i++){

                Aresta selfAresta = Validator.getArestaBetween(pathOfArestas.get(i).id,pathOfArestas.get(i).id);
                Aresta nextAresta = Validator.getArestaBetween(pathOfArestas.get(i).id,pathOfArestas.get(i+1).id);

                if (selfAresta != null){
                    regexRule.append(mapOfallRegexRulles.getOrDefault(selfAresta.getIdentifier(),""));
                }
                regexRule.append(mapOfallRegexRulles.getOrDefault(nextAresta.getIdentifier(),""));
            }

            String finalRule = regexRule.toString();
            if (!finalRule.isEmpty()){
                finalRegexRules.add(finalRule);
                System.out.println("Path Between " + initial.getId() + " and " + finalVertice.getId() + " has th regex == "  + finalRule);
            }
        }


        StringBuilder theNewRegexCommand  = new StringBuilder();
        for (String finalRule : finalRegexRules){
            theNewRegexCommand.append("^" + finalRule + "$|");
        }
        theNewRegexCommand.setLength(theNewRegexCommand.length() - 1);
        insertRegex.setText(theNewRegexCommand.toString().replaceAll("ε",""));
    }

    @FXML
    void onConvertRegexToAf(ActionEvent event) {

    }



    private static String mountTheRegexFrom(Aresta aresta){
        boolean isCyclic = aresta.getTargetId() == aresta.getSourceId();

        StringBuilder stringBuilder = new StringBuilder("(");
        for (char value : aresta.getGrammars()){
            stringBuilder.append(value + "|");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(")");
        if (isCyclic) stringBuilder.append("*");
        return stringBuilder.toString();
    }



    private static List<Vertice> mountThePathBetween(Vertice initial, Vertice finale){

        List<Vertice> path = new ArrayList<>();

        boolean foundFinal = false;

        Vertice currentVertice = initial;
        List<Integer> lookedVertices = new ArrayList<>();
        System.out.println("\n\n");
        while (true){

            if (lookedVertices.contains(currentVertice.id)){
                break;
            }

            path.add(currentVertice);
            lookedVertices.add(currentVertice.id);

            if (currentVertice == finale){
                foundFinal = true;
                break;
            }

            List<Vertice> futureVertices = Validator.getFutureNonSelfVertices(currentVertice);

            if (futureVertices.isEmpty()){
                break;
            }

            Vertice nextVertice = futureVertices.get(0);
            currentVertice = nextVertice;
        }

        if (foundFinal){
            return path;
        }
        return new ArrayList<>();
    }
}

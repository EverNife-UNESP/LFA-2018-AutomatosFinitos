package br.com.finalcraft.unesp.lfa.grammar.javafx;

import br.com.finalcraft.unesp.lfa.finiteautomaton.application.FiniteAutomationApplication;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.Validator;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Aresta;
import br.com.finalcraft.unesp.lfa.finiteautomaton.application.validator.data.Vertice;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Edge;
import br.com.finalcraft.unesp.lfa.finiteautomaton.desenho.Vertex;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.controller.Main;
import br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view.MyFXMLs;
import br.com.finalcraft.unesp.lfa.grammar.application.GrammarExpression;
import br.com.finalcraft.unesp.lfa.grammar.application.GrammarValidator;
import br.com.finalcraft.unesp.lfa.grammar.myown.Sleeper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrammarController {

    public static Parent grammar;

    static {
        try {
            grammar = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/grammar/javafx/Grammar.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static GrammarController instance;
    private static Stage dialog;

    public GrammarController() {
        instance = this;
    }

    public static void setUp(){
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        // Defines a modal window that blocks events from being
        // delivered to any other application window.
        dialog.initOwner(Main.thePrimaryStage);

        Scene newSceneWindow1 = new Scene(grammar);
        dialog.setScene(newSceneWindow1);
        dialog.setTitle("Grammar Tool - LFA 2018");


        observableList = FXCollections.observableArrayList();
    }

    public static void show(){

        if (dialog.isShowing()){
            dialog.close();
        }


        GrammarExpression.readTable(observableList);
        instance.tableOfValues.setItems(observableList);

        instance.leftColumn.setCellValueFactory(new PropertyValueFactory<GrammarExpression,String>("leftTerm"));
        instance.rightColumn.setCellValueFactory(new PropertyValueFactory<GrammarExpression,String>("rightTerm"));

        dialog.show();
    }

    @FXML
    private Label actionResult;

    @FXML
    private Label actionValidator;

    @FXML
    private TextField leftTextField;

    @FXML
    private TextField rightTextField;

    @FXML
    private TextField validateTextField;

    @FXML
    private TableView tableOfValues;

    @FXML
    private TableColumn<GrammarExpression,String> leftColumn;
    @FXML
    private TableColumn<GrammarExpression,String> rightColumn;

    private static ObservableList<GrammarExpression> observableList;

    @FXML
    private void onAddTermButton(ActionEvent event) {

        String termoDaEsquerda = leftTextField.getText().toUpperCase();
        String termoDaDireita = rightTextField.getText();

        if (termoDaEsquerda.isEmpty()) {
            return;
        }

        if (termoDaEsquerda.length() != 1 || !termoDaEsquerda.matches("[a-z]|[A-Z]")){
            actionResult.setText("Você só pode inserir uma letra a esquerda!");
            new Sleeper(){
                @Override
                public void andDo() {
                    actionResult.setText("....");
                }
            }.runAfter(1500L);
            return;
        }

        if (termoDaDireita.isEmpty()){
            termoDaDireita = "\u03B5"; // 'GREEK SMALL LETTER EPSILON'
        }

        if (!termoDaDireita.matches("([a-z]|[A-Z]|[0-9]|\u03B5)*")){
            actionResult.setText("O lado direito precisa ser alfanumérico, ou vazio!");
            new Sleeper(){
                @Override
                public void andDo() {
                    actionResult.setText("....");
                }
            }.runAfter(1500L);
            return;
        }

        if (!termoDaDireita.matches("ε|(([a-z]|[0-9])*[A-Z]{0,1}([a-z]|[0-9])*)")){
            actionResult.setText("O lado direito só pode conter um nãoTerminal!");
            new Sleeper(){
                @Override
                public void andDo() {
                    actionResult.setText("....");
                }
            }.runAfter(1500L);
            return;
        }



        GrammarExpression grammarExpression = new GrammarExpression(termoDaEsquerda, termoDaDireita);
        if (GrammarExpression.contains(grammarExpression)){
            actionResult.setText("Essa regra gramatical já foi inserida antes T.T");
        }else {
            GrammarExpression.addExpression(new GrammarExpression(termoDaEsquerda, termoDaDireita));
            GrammarExpression.readTable(observableList);
            actionResult.setText("Regra adicionada com sucesso! ^^");
        }

        new Sleeper(){
            @Override
            public void andDo() {
                actionResult.setText("....");
            }
        }.runAfter(1500L);
    }

    @FXML
    private void onRemoveTermButton(ActionEvent event) {

        int selectedLine = leftColumn.getTableView().getSelectionModel().getSelectedIndex();

        if (selectedLine < 0){
            return;
        }

        if (GrammarExpression.removeByIndex(selectedLine)){
            GrammarExpression.readTable(observableList);
            actionResult.setText("Linha " + (selectedLine + 1) + " removida com sucesso!");
        }else {
            actionResult.setText("Erro ao remover a linha " + (selectedLine + 1) + " !");
        }

        new Sleeper(){
            @Override
            public void andDo() {
                actionResult.setText("....");
            }
        }.runAfter(1500L);
    }

    @FXML
    private void onValidadeButton(ActionEvent event) {

        String termoASerValidado = validateTextField.getText();

        if (GrammarExpression.getAllExpressions().size() == 0) {
            actionValidator.setText("Você precisa inserir ao menos uma gramática!");
            new Sleeper(){
                @Override
                public void andDo() {
                    actionValidator.setText("....");
                }
            }.runAfter(1500L);
            return;
        }

        if (!GrammarValidator.validade(termoASerValidado)){
            actionValidator.setText("✘✘✘✘ Esse termo não faz parte dessa gramática! ✘✘✘✘");
        }else {
            actionValidator.setText("✔✔✔✔ Esse termo faz parte dessa gramática! ✔✔✔✔");
        }

        new Sleeper(){
            @Override
            public void andDo() {
                actionValidator.setText("....");
            }
        }.runAfter(5000L);
    }

    @FXML
    private void onConvertAfToGrammar(ActionEvent event) {
        Validator.loadGraph();
        GrammarExpression.getAllExpressions().clear();


        Validator.todasAsArestas.forEach(aresta -> {
            char origin = (char) (66 + aresta.getSourceId());
            char target = (char) (66 + aresta.getTargetId());
            for (char value : aresta.getGrammars()){
                GrammarExpression.addExpression(new GrammarExpression(origin + "", (value + "").toLowerCase() + target));
            }
        });

        Vertice initialVertice = null;
        for (Vertice vertice : Validator.todosOsVertices){
            if (vertice.isInitial()){
                initialVertice = vertice;
            }
            if (vertice.isFinale()){
                char origin = (char) (66 + vertice.getId());
                GrammarExpression.addExpression(new GrammarExpression(origin + "", ""));
            }
        }

        if (initialVertice != null){
            char origin = (char) (66 + initialVertice.getId());
            GrammarExpression.addExpression(new GrammarExpression("A", "" + origin));
        }else {
            GrammarExpression.addExpression(new GrammarExpression("A", "A"));
        }

        GrammarExpression.readTable(observableList);

    }

    @FXML
    private void onConvertGrammarToAf(ActionEvent event) {
        FiniteAutomationApplication.clear();
        GrammarValidator.optimizeUnreachableGrammars();

        List<String> leftTerms = new ArrayList<String>();

        for (GrammarExpression grammarExpression : GrammarValidator.otimizedGrammarExpressions){
            if (!leftTerms.contains(grammarExpression.getLeftTerm())){
                leftTerms.add(grammarExpression.getLeftTerm());
            }
        }

        int i = 0;
        Map<String,Integer> mapOfNewIds = new HashMap<String, Integer>();
        for (String leftTerm : leftTerms) {
            FiniteAutomationApplication.getGraph().addVertex();
            mapOfNewIds.put(leftTerm,i);
            i++;
        }

        FiniteAutomationApplication.getGraph().addVertex();
        mapOfNewIds.put("",i);

        FiniteAutomationApplication.getGraph().getVertex(0).setInitial(true);                               // Pega o primeiro vertex e seta ele como primário!
        Vertex nullVertex = FiniteAutomationApplication.getGraph().getVertex(i); nullVertex.setFinale(true);    // Cria um vertex extra para ser o final, um vertex nulo


        for (GrammarExpression grammarExpression : GrammarValidator.otimizedGrammarExpressions){
            int originVertexId = mapOfNewIds.get(grammarExpression.getLeftTerm());
            Vertex origin = FiniteAutomationApplication.getGraph().getVertex(originVertexId);

            String upperCaseRight = GrammarValidator.getUpperCaseFromString(grammarExpression.getRightTerm());
            int targetVertexId = mapOfNewIds.getOrDefault(upperCaseRight,-1);
            Vertex target = null;
            if (targetVertexId >= 0){
                target = FiniteAutomationApplication.getGraph().getVertex(targetVertexId);
            }

            Edge edge;
            if (target == null){
                edge = FiniteAutomationApplication.getGraph().getOrCreateEdge(origin, nullVertex);
            }else {
                edge = FiniteAutomationApplication.getGraph().getOrCreateEdge(origin, target);
            }
            Aresta aresta = edge.getOrCreateAresta(origin);

            String transactionValueText = grammarExpression.getRightTerm().replaceAll(upperCaseRight,"");


            if ( transactionValueText.isEmpty()){
                aresta.addGrammar('\u03B5');
            }else {
                for (char charactere : transactionValueText.toCharArray()){
                    aresta.addGrammar(charactere);
                }
            }

            edge.calculateAndSetTextOne();
        }

        FiniteAutomationApplication.getGraph().reloadPage();

        System.out.println("onConvertGrammarToAf");
    }

    @FXML
    private void onApagarTodaGramatica(ActionEvent event) {
        GrammarExpression.getAllExpressions().clear();
        GrammarExpression.readTable(observableList);
    }

}

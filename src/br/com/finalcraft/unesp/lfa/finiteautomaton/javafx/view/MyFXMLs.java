package br.com.finalcraft.unesp.lfa.finiteautomaton.javafx.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MyFXMLs {

    public static Parent main_screen;
    public static Parent state_editor;

    @Deprecated
    public static Parent transition_editor;

    public static Parent select_edit_trasition;
    public static Parent add_transition;
    public static Parent edit_transition_sideonly;

    public static Parent finiteautomation_tester;

    public static Parent save_to_file;



    static {
        try {
            main_screen = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/main_screen.fxml"));
            state_editor = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/state_editor.fxml"));
            transition_editor = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/transition_editor.fxml"));
            select_edit_trasition = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/select_edit_trasition.fxml"));
            add_transition = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/add_transition.fxml"));
            edit_transition_sideonly = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/sideonly_edit_transition.fxml"));
            finiteautomation_tester = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/finiteautomation_tester.fxml"));
            save_to_file = FXMLLoader.load(MyFXMLs.class.getResource("/br/com/finalcraft/unesp/lfa/finiteautomaton/javafx/view/save_to_file.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

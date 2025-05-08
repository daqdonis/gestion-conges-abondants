package com.groupe14ing2.gestioncongesabondants.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionComptesController {

    @FXML
    private Button add_Acc_button;

    @FXML
    private Button admin_acc_button;

    @FXML
    private Button all_acc_button;

    @FXML
    private TextField filtrer_textField;

    @FXML
    private Button install_jst_button;

    @FXML
    private Button regulaire_acc_button;

    @FXML
    private VBox requestsContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane table_pan;

    @FXML
    private Button traiter_jst_button;

    @FXML
    private Label user_Name_Lable;

    @FXML
    public void ajouter_compte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/ajouter-compte.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une demande");

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

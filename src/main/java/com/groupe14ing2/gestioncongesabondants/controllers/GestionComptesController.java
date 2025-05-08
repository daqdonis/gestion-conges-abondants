package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.Conge;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
    public void initialize() {
        refreshTable();
    }

    @FXML
    public void ajouter_compte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/ajouter-compte.fxml"));
            Parent root = loader.load();

            AjouterCompteController compteController = loader.getController();
            compteController.setGestionComptesController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une demande");

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void refreshTable() {
        System.out.println("Refreshing table...");
        try {
            DatabaseController db = new DatabaseController();
            List<Admin> admins = db.getAllAdmins();

            requestsContainer.getChildren().clear();

            for (Admin admin : admins) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/TupleComptes.fxml"));
                    HBox tupleView = loader.load(); // charger le FXML

                    TupleAdminController tupleController = loader.getController();

                    tupleController.setData(admin); // injecter les données
                    tupleController.setGestionComptesController(this); // injecter le contrôleur MenuViewController


                    requestsContainer.getChildren().add(tupleView); // ajouter au container
                } catch (IOException e) {
                    System.err.println("Erreur de chargement du TupleAdmin.fxml");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur base de données");
            e.printStackTrace();
        }
    }
}

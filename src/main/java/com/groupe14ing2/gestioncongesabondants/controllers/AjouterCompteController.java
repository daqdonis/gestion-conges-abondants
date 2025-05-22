package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.RoleAdmin;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AjouterCompteController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Pane mainPanel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private Button AjouterCompte_button;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField prenomTextField;

    @FXML
    private ChoiceBox<RoleAdmin> roleChoiceBox;

    @FXML
    private Button exitButton;

    private GestionComptesController gestionComptesController;

    @FXML
    public void initialize() {
        mainPanel.getStylesheets().add(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/LoginPageStyleSheet.css").toExternalForm());

        roleChoiceBox.getItems().addAll(RoleAdmin.values());
    }

    @FXML

    private void login(javafx.event.ActionEvent actionEvent) {

        try {
            DatabaseController db = new DatabaseController();

            db.addAdmin(new Admin(
                    nomTextField.getText(),
                    prenomTextField.getText(),
                    roleChoiceBox.getValue(),
                    userIdTextField.getText(),
                    passwordField.getText()
            ));
            gestionComptesController.refreshTable();
            exit();
        } catch (Exception e) {
            System.out.println("Error adding admin: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Unable to add admin: " + e.getMessage());
        }

    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setGestionComptesController(GestionComptesController gestionComptesController) {
        this.gestionComptesController = gestionComptesController;
    }
}

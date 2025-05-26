package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.RoleAdmin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifierCompteController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button modifierButton;
    @FXML
    private Button annulerButton;
    @FXML
    private AnchorPane mainPane;

    private Admin adminToModify;
    private GestionComptesController gestionComptesController;

    @FXML
    public void initialize() {
        annulerButton.setOnAction(e -> {
            Stage stage = (Stage) annulerButton.getScene().getWindow();
            stage.close();
        });

        modifierButton.setOnAction(e -> handleModification());
    }

    public void setGestionComptesController(GestionComptesController controller) {
        this.gestionComptesController = controller;
    }

    public void setAdminToModify(Admin admin) {
        this.adminToModify = admin;
        populateFields();
    }

    private void populateFields() {
        if (adminToModify != null) {
            nomField.setText(adminToModify.getNom());
            prenomField.setText(adminToModify.getPrenom());
            emailField.setText(adminToModify.getEmail());
        }
    }

    private void handleModification() {
        if (validateFields()) {
            try {
                DatabaseController db = new DatabaseController();
                String newPassword = passwordField.getText();
                
                Admin updatedAdmin = new Admin(
                    adminToModify.getIdAdmin(),
                    nomField.getText(),
                    prenomField.getText(),
                    adminToModify.getRoles(),
                    emailField.getText(),
                    newPassword.isEmpty() ? null : newPassword // Only set password if not empty
                );

                db.updateAdmin(updatedAdmin);
                gestionComptesController.refreshTable();

                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le compte a été modifié avec succès.");
                Stage stage = (Stage) modifierButton.getScene().getWindow();
                stage.close();
            } catch (SQLException ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification du compte.");
                ex.printStackTrace();
            }
        }
    }

    private boolean validateFields() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs obligatoires.");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 
package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ActionsController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextArea textArea;

    @FXML
    private BorderPane mainPanel;

    private Admin admin;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        textArea.setEditable(false);
    }

    @FXML
    private void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    public void initializeData() {
        try {
            DatabaseController databaseController = new DatabaseController();
            databaseController.getAllActionAdmin(admin.getIdAdmin()).forEach(actionAdmin -> {
                if (!textArea.getText().isBlank())
                    textArea.setText(textArea.getText() + "\n" + actionAdmin.getTempsAction() + " > " + actionAdmin.getAction());
                else
                    textArea.setText(actionAdmin.getTempsAction() + " > " + actionAdmin.getAction());
            });
        } catch (SQLException e) {
            exit();
        }
    }
}

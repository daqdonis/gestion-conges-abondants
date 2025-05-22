package com.groupe14ing2.gestioncongesabondants.controllers;


import java.io.IOException;

import com.groupe14ing2.gestioncongesabondants.models.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class LoginViewController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane loginPanel;

    @FXML
    private AnchorPane infoPanel;

    @FXML
    private BorderPane mainPanel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView userIcon;

    @FXML
    private TextField userIdTextField;

    @FXML
    private Button AjouterCompte_button;

    @FXML
    private Button minimizeButton;

    private Admin admin;

    @FXML
    public void initialize() {
        mainPanel.getStylesheets().add(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/LoginPageStyleSheet.css").toExternalForm());
    }

    @FXML

   private void login(javafx.event.ActionEvent actionEvent) throws IOException {

        String username = userIdTextField.getText();
        String password = passwordField.getText();

        System.out.println("Login attempt for: " + username);

        try {
            UserLoginController userLoginController = new UserLoginController();
            Admin admin = userLoginController.login(username, password);
            this.admin = admin;
            if (admin != null) {
                System.out.println("Login successful for: " + admin.getNom());

                switch (admin.getRoles()) {
                    case ADMINCONGE:
                        switchToMenu(actionEvent);
                        break;
                    case ADMINABONDANT:
                        switchToAbondantMenu(actionEvent);
                        break;
                    case ADMINCOMPTES:
                        switchToComptesMenu(actionEvent);
                }

            } else {
                System.out.println("Login failed - invalid credentials");
                showAlert("Login Failed", "Invalid username or password");
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Login failed: " + e.getMessage());
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
    private void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/Menu.fxml"));
        root = loader.load();
        ((MenuViewController)loader.getController()).setAdmin(admin);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/Menu_stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void switchToAbondantMenu(javafx.event.ActionEvent actionEvent) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/Menu-Gestion-Abandonment.fxml"));
        root = loader.load();
        ((MenuGestionAbdandenementController) loader.getController()).setAdmin(admin);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/Menu_stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void switchToComptesMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/Gestion-Comptes.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);

        String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/Menu_stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void exit(){
        System.exit(0);
    }

    @FXML
    private void minimizeWindow() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
}

package com.groupe14ing2.gestioncongesabondants.controllers;

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

import javax.security.auth.login.FailedLoginException;
import java.io.IOException;
import java.net.Socket;

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
    private Button loginButton;

    @FXML
    private Button minimizeButton;

    @FXML
    public void initialize() {
        mainPanel.getStylesheets().add(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/LoginPageStyleSheet.css").toExternalForm());
    }

    @FXML
    private void login(javafx.event.ActionEvent actionEvent) throws IOException {
        UserLoginController userLoginController = new UserLoginController();

        try {
            Admin admin = userLoginController.login(userIdTextField.getText(), passwordField.getText());
            switchToMenu(actionEvent);
        }   catch (IOException | FailedLoginException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    private void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/Menu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);

//      switching the css to Menu_stylesheet
        String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/Menu_stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
//
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

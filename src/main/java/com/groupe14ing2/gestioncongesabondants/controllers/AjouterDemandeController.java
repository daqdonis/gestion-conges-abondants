package com.groupe14ing2.gestioncongesabondants.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AjouterDemandeController {

    @FXML
    private Button AJT_D_ConfirmerButton;

    @FXML
    private TextField AJT_D_Date;

    @FXML
    private TextField AJT_D_GroupID;

    @FXML
    private Button AJT_D_MotifButton;

    @FXML
    private TextField AJT_D_Nom;

    @FXML
    private TextField AJT_D_PreNom;

    @FXML
    private TextField AJT_D_matricule;

    @FXML
    private Button fermer_button;

    @FXML
    private Pane main_Panel;

    @FXML
    public void fermer_button_onAction() {
        Stage stage = (Stage) fermer_button.getScene().getWindow();
        stage.close();
    }

}

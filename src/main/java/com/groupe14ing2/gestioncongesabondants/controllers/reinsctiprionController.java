package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class reinsctiprionController {

    @FXML
    private Button AJT_D_ConfirmerButton;

    @FXML
    private Button fermer_button;

    @FXML
    private Pane main_Panel;

    @FXML
    private TextField reinsctiprion_Date;

    @FXML
    private TextField reinsctiprion_GroupID;

    @FXML
    private TextField reinsctiprion_Nom;

    @FXML
    private TextField reinsctiprion_PreNom;

    @FXML
    private TextField reinsctiprion_matricule;

    @FXML
    void fermer_button_onAction(ActionEvent event) {

        ((Stage) fermer_button.getScene().getWindow()).close();
    }

    @FXML
    void handleConfirmerButton(ActionEvent event) {
    }

    public void setStudentData(Etudiant etudiant) {
        reinsctiprion_matricule.setText(String.valueOf(etudiant.getIdEtu()));
        reinsctiprion_Nom.setText(etudiant.getNom());
        reinsctiprion_PreNom.setText(etudiant.getPrenom());
        reinsctiprion_GroupID.setText(etudiant.getIdGroupe());
        reinsctiprion_Date.setText(etudiant.getDateNaiss().toString());
    }
}
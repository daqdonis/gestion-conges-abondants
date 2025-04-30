package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TraiterDemandeController {

    @FXML
    private Button button_accepter;

    @FXML
    private Button button_justification;

    @FXML
    private Button button_resuser;

    @FXML
    private Button fermer_button;

    @FXML
    private Circle shape;

    @FXML
    private TextField traiter_date_de_nessance;

    @FXML
    private TextField traiter_idGroupe;

    @FXML
    private TextField traiter_matricule_etudiant;

    @FXML
    private TextField traiter_nom;

    @FXML
    private TextField traiter_numero_demande;

    @FXML
    private TextField traiter_prenom;
   


    @FXML
    public void fermer_button_onAction() {
        Stage stage = (Stage) fermer_button.getScene().getWindow();
        stage.close();
    }
    public void setConge(Conge conge) {
    if (conge != null && conge.getEtudiant() != null) {
       traiter_nom.setText(conge.getEtudiant().getNom());
        traiter_prenom.setText(conge.getEtudiant().getPrenom());
       traiter_matricule_etudiant.setText(String.valueOf(conge.getEtudiant().getIdEtu()));
        traiter_idGroupe.setText(String.valueOf(conge.getEtudiant().getIdGroupe()));
        
        traiter_numero_demande.setText(String.valueOf(conge.getIdDemande()));
       traiter_date_de_nessance.setText(String.valueOf(conge.getEtudiant().getDateNaiss()));
    }
}


}

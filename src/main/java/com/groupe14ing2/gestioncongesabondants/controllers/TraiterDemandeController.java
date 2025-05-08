package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TraiterDemandeController  {

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

      // Référence du contrôleur MenuViewController
   private MenuViewController menuController;


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
@FXML
private void handleRefuser() {
    try {
        int idDemande = Integer.parseInt(traiter_numero_demande.getText());

        DatabaseController dbController = new DatabaseController();
        
       
        dbController.updateCongeEtat(idDemande, EtatTraitement.REFUSÉ);  

        showAlert("Succès", "Demande refusée avec succès.");
        fermer_button_onAction(); // fermer la fenêtre après refus
        
        if (menuController != null) {
            menuController.refreshTable();  // Cette méthode va rafraîchir la table avec les nouvelles demandes
        }
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Erreur", "Erreur lors du refus : " + e.getMessage());
    }
}


@FXML
private void handleAccepter() {
    try {
        int idDemande = Integer.parseInt(traiter_numero_demande.getText());

        DatabaseController dbController = new DatabaseController();
        dbController.updateCongeEtat(idDemande, EtatTraitement.ACCEPTÉ);

        showAlert("Succès", "Demande acceptée avec succès.");
        fermer_button_onAction(); // fermer la fenêtre après acceptation

        
        if (menuController != null) {
            menuController.refreshTable();  // Cette méthode va rafraîchir la table avec les nouvelles demandes
        }
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Erreur", "Erreur lors de l'acceptation : " + e.getMessage());
    }

    
}

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setMenuController(MenuViewController menuController) {
        this.menuController = menuController;
    }
}

   

    




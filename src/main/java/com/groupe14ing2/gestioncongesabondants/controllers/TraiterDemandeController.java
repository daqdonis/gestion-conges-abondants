package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.function.Consumer;
import java.util.function.Function;

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

    @FXML
    private TextField traiter_type_conge;

    // Référence du contrôleur MenuViewController
    private MenuViewController menuController;
    private Conge conge;
    private Runnable onStatusUpdated;

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
            traiter_type_conge.setText(String.valueOf(conge.getType()));
        }
    }

    public void setOnStatusUpdated(Runnable callback) {
        this.onStatusUpdated = callback;
    }

    @FXML
    private void handleRefuser() {
        try {
            String idDemande = traiter_numero_demande.getText();

            DatabaseController dbController = new DatabaseController();
            dbController.updateCongeEtat(idDemande, EtatTraitement.REFUSÉ);

            // Call the status update callback first
            if (onStatusUpdated != null) {
                javafx.application.Platform.runLater(() -> {
                    onStatusUpdated.run();
                });
            }

            // Then refresh the table
            if (menuController != null) {
                javafx.application.Platform.runLater(() -> {
                    menuController.refreshTable();
                });
            }

            showAlert("Succès", "Demande refusée avec succès.");
            fermer_button_onAction(); // fermer la fenêtre après refus

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du refus : " + e.getMessage());
        }
    }

    @FXML
    private void handleAccepter() {
        try {
            String idDemande = traiter_numero_demande.getText();
            DatabaseController dbController = new DatabaseController();
            String updateDateSql = "UPDATE Conge SET date_demande = CURRENT_DATE() WHERE id_demande = ?";
            try (PreparedStatement stmt = dbController.getConnection().prepareStatement(updateDateSql)) {
                stmt.setString(1, idDemande);
                stmt.executeUpdate();
            }
            dbController.updateCongeEtat(idDemande, EtatTraitement.ACCEPTÉ);
            if (onStatusUpdated != null) {
                javafx.application.Platform.runLater(() -> {
                    onStatusUpdated.run();
                });
            }
            if (menuController != null) {
                javafx.application.Platform.runLater(() -> {
                    menuController.refreshTable();
                });
            }

            showAlert("Succès", "Demande acceptée avec succès.");
            fermer_button_onAction(); // fermer la fenêtre après acceptation

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de l'acceptation : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setMenuController(MenuViewController menuController) {
        this.menuController = menuController;
    }

    public void setButton_justificationAction(Consumer<Void> f) {
        button_justification.setOnAction(e -> f.accept(null));
    }
}

   

    




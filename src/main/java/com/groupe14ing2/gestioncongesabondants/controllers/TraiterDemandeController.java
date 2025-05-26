package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;

import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.groupe14ing2.gestioncongesabondants.utils.EmailUtils;


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

        this.conge = conge; // Store the Conge object

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

            if (conge == null) {
                showAlert("Erreur", "Données de congé non disponibles");
                return;
            }

            Etudiant etudiant = conge.getEtudiant();
            if (etudiant == null) {
                showAlert("Erreur", "Données de l'étudiant non disponibles");
                return;
            }

            String m = "Bonjour "+ etudiant.getNom() + " " + etudiant.getPrenom() +"\n" +
                    "\n" +
                    "Nous vous informons que votre demande de congé a été refusée.\n" +
                    "\n" +
                    "Détails de la demande :\n" +
                    "- Congé numero : '"+ conge.getIdDemande() +"\n" +
                    "- Durée : 1 ans \n" +
                    "- Type de congé : " + conge.getType() + "\n" +
                    "\n" +
                    "Pour toute question ou pour obtenir des précisions sur les raisons du refus, veuillez nous contacter.\n" +
                    "\n" +
                    "Cordialement,\n" +
                    "L'équipe de gestion des congés";

            String idDemande = conge.getIdDemande();
            DatabaseController dbController = new DatabaseController();
            dbController.updateCongeEtat(idDemande, EtatTraitement.REFUSÉ);
            // logs the admins action
            conge.setEtat(EtatTraitement.REFUSÉ);
            AddActionAdmin.changeEtat(menuController.getAdmin(), etudiant, conge);


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

            
            if (etudiant.getemail_etu() != null && !etudiant.getemail_etu().isEmpty()) {
                sendEmail(etudiant.getemail_etu(), m);
            } else {
                System.out.println("Warning: Student email not available, skipping email notification");
            }
            
            fermer_button_onAction(); // fermer la fenêtre après refus


        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du refus : " + e.getMessage());
        }
    }

    @FXML
    private void handleAccepter() {
        try {

            if (conge == null) {
                showAlert("Erreur", "Données de congé non disponibles");
                return;
            }

            Etudiant etudiant = conge.getEtudiant();
            if (etudiant == null) {
                showAlert("Erreur", "Données de l'étudiant non disponibles");
                return;
            }

            String m = "Bonjour "+ etudiant.getNom() + " " + etudiant.getPrenom() +
                    "\n" +
                    "Nous avons le plaisir de vous informer que votre demande de congé a été acceptée.\n" +
                    "\n" +
                    "Détails de la demande :\n" +
                    "- Congé numero : '"+ conge.getIdDemande() +"\n" +
                    "- Durée : 1 ans \n" +
                    "- Type de congé : " + conge.getType() + "\n" +
                    "\n" +
                    "Veuillez nous contacter si vous avez des questions ou besoin de précisions supplémentaires.\n" +
                    "\n" +
                    "Cordialement,\n" +
                    "L'équipe de gestion des congés\n";

            String idDemande = conge.getIdDemande();
            DatabaseController dbController = new DatabaseController();
            

            String updateDateSql = "UPDATE Conge SET date_demande = CURRENT_DATE() WHERE id_demande = ?";
            try (PreparedStatement stmt = dbController.getConnection().prepareStatement(updateDateSql)) {
                stmt.setString(1, idDemande);
                stmt.executeUpdate();
            }

            dbController.updateCongeEtat(idDemande, EtatTraitement.ACCEPTÉ);
            // logs the admins action
            conge.setEtat(EtatTraitement.ACCEPTÉ);
            AddActionAdmin.changeEtat(menuController.getAdmin(), etudiant, conge);


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

            
            if (etudiant.getemail_etu() != null && !etudiant.getemail_etu().isEmpty()) {
                sendEmail(etudiant.getemail_etu(), m);
            } else {
                System.out.println("Warning: Student email not available, skipping email notification");
            }
            

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

    private void sendEmail(String to, String msg) {
        EmailUtils.sendEmail(to, "Réponse à votre demande de congé", msg);
    }

    public int stringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-numeric string)
            throw new IllegalArgumentException("Invalid number format: " + str, e);
        }
    }

}








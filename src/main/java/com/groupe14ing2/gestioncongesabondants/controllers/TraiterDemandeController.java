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
            String m = "Bonjour "+ traiter_nom.getText()+ " " + traiter_prenom.getText() +"\n" +
                    "\n" +
                    "Nous vous informons que votre demande de congé a été refusée.\n" +
                    "\n" +
                    "Détails de la demande :\n" +
                    "- Congé numero : '"+traiter_numero_demande.getText()+"\n" +
                    "- Durée : 1 ans \n" +
                    "- Type de congé : Académique\n" +
                    "\n" +
                    "Pour toute question ou pour obtenir des précisions sur les raisons du refus, veuillez nous contacter.\n" +
                    "\n" +
                    "Cordialement,\n" +
                    "L'équipe de gestion des congés";
            String idDemande = traiter_numero_demande.getText();
            int idE = stringToInt(traiter_matricule_etudiant.getText());
            DatabaseController dbController = new DatabaseController();
            Etudiant etudiant = dbController.getEtudiant(idE);
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
            sendEmail(etudiant.getemail_etu(),m);
            fermer_button_onAction(); // fermer la fenêtre après refus

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du refus : " + e.getMessage());
        }
    }

    @FXML
    private void handleAccepter() {
        try {
            String m = "Bonjour "+ traiter_nom.getText()+ " " + traiter_prenom.getText() +
                    "\n" +
                    "Nous avons le plaisir de vous informer que votre demande de congé a été acceptée.\n" +
                    "\n" +
                    "Détails de la demande :\n" +
                    "- Congé numero : '"+traiter_numero_demande.getText()+"\n" +
                    "- Durée : 1 ans \n" +
                    "- Type de congé : Académique\n" +
                    "\n" +
                    "Veuillez nous contacter si vous avez des questions ou besoin de précisions supplémentaires.\n" +
                    "\n" +
                    "Cordialement,\n" +
                    "L'équipe de gestion des congés\n";

            String idDemande = traiter_numero_demande.getText();
            DatabaseController dbController = new DatabaseController();
            Etudiant etudiant = dbController.getEtudiant(conge.getIdEtu());
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
            sendEmail(etudiant.getemail_etu(),m);
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

    private void sendEmail(String recipientMail,String mess) throws MessagingException {
        Properties properties = new Properties() ;

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail = "Abdallahbenmoussa488@gmail.com" ;
        String passWord = "sulj xqma ewsn lsbw";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,passWord);
            }
        });

        Message message = prepareMesage(session,myEmail,recipientMail,mess);

        Transport.send(message);
        if(message != null){
            new Alert(Alert.AlertType.CONFIRMATION,"send EmailController succefuly").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Erreur try Againe").show();
        }
    }

    private Message prepareMesage(Session session, String myEmail, String recipientMail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO,
                    new InternetAddress[]{
                            new InternetAddress(recipientMail)
                    });

            message.setSubject("Mise à jour de votre demande de congé");
            message.setText(msg);

            return message;
        }catch (Exception e){
            Logger.getLogger(TraiterDemandeController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
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








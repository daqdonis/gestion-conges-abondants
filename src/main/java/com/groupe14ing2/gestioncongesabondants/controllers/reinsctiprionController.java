package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Abondant;
import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class reinsctiprionController {

    @FXML
    private Button AJT_D_ConfirmerButton;

    @FXML
    private Button AJT_D_RefuserButton;

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
    private DatePicker abandonmentDatePicker;

    @FXML
    private TextField abandonmentYearField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label studentNameLabel;

    private Etudiant etudiant;
    private String abandonmentYear;
    private MenuGestionAbdandenementController menuController;

    @FXML
    public void initialize() {
        // Make student info fields read-only
        reinsctiprion_matricule.setEditable(false);
        reinsctiprion_Nom.setEditable(false);
        reinsctiprion_PreNom.setEditable(false);
        reinsctiprion_GroupID.setEditable(false);
        reinsctiprion_Date.setEditable(false);
        abandonmentYearField.setEditable(false);

        // Set up refuse button action
        AJT_D_RefuserButton.setOnAction(e -> handleRefuserButton());
        AJT_D_ConfirmerButton.setOnAction(e -> handleConfirmerButton());
    }

    private void handleConfirmerButton() {
        try {
            if (!isEligible()) {
                showAlert("Error", "L'étudiant n'est pas éligible pour la réinscription");
                return;
            }

            if (etudiant == null) {
                showAlert("Error", "Données de l'étudiant non disponibles");
                return;
            }

            DatabaseController db = new DatabaseController();
            db.removeAbondant(etudiant.getIdEtu());

            // Send email notification
            if (etudiant.getemail_etu() != null && !etudiant.getemail_etu().isEmpty()) {
                String emailMessage = "Bonjour " + etudiant.getNom() + " " + etudiant.getPrenom() + ",\n\n" +
                        "Nous avons le plaisir de vous informer que votre demande de réinscription après abandon a été acceptée.\n\n" +
                        "Détails de la demande :\n" +
                        "- Matricule : " + etudiant.getIdEtu() + "\n" +
                        "- Date de soumission : " + LocalDate.now() + "\n" +
                        "- Groupe : " + etudiant.getIdGroupe() + "\n\n" +
                        "Vous pouvez maintenant reprendre vos études.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe de gestion des congés";
                
                sendEmail(etudiant.getemail_etu(), emailMessage);
            }

            menuController.refreshTable();
            showAlert("Success", "Réinscription acceptée avec succès");
            Stage stage = (Stage) AJT_D_ConfirmerButton.getScene().getWindow();
            stage.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Erreur lors de la réinscription: " + ex.getMessage());
        }
    }

    private void handleRefuserButton() {
        try {
            if (etudiant == null) {
                showAlert("Error", "Données de l'étudiant non disponibles");
                return;
            }

            // Send email notification
            if (etudiant.getemail_etu() != null && !etudiant.getemail_etu().isEmpty()) {
                String emailMessage = "Bonjour " + etudiant.getNom() + " " + etudiant.getPrenom() + ",\n\n" +
                        "Nous regrettons de vous informer que votre demande de réinscription après abandon a été refusée.\n\n" +
                        "Détails de la demande :\n" +
                        "- Matricule : " + etudiant.getIdEtu() + "\n" +
                        "- Date de soumission : " + LocalDate.now() + "\n" +
                        "- Groupe : " + etudiant.getIdGroupe() + "\n\n" +
                        "Pour toute question concernant cette décision, veuillez nous contacter.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe de gestion des congés";
                
                sendEmail(etudiant.getemail_etu(), emailMessage);
            }

            showAlert("Information", "La demande de réinscription a été refusée");
            Stage stage = (Stage) AJT_D_RefuserButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Erreur lors du refus: " + e.getMessage());
        }
    }

    private void sendEmail(String to, String msg) {
        if (to == null || to.trim().isEmpty()) {
            System.out.println("❌ Email non envoyé: adresse email manquante");
            return;
        }

        String from = "gestioncongesabondants@gmail.com";
        Properties properties = new Properties();
        
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        try {
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("abdallahbenmoussa488@gmail.com", "sulj xqma ewsn lsbw");
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Réponse à votre demande de réinscription", "UTF-8");
            message.setText(msg, "UTF-8");
            Transport.send(message);
            
            System.out.println("✅ Email envoyé avec succès à: " + to);
            
        } catch (MessagingException mex) {
            System.out.println("❌ Échec de l'envoi de l'email à: " + to);
            mex.printStackTrace();
            showAlert("Erreur d'envoi", "L'email n'a pas pu être envoyé: " + mex.getMessage());
        }
    }

    @FXML
    void fermer_button_onAction(ActionEvent event) {
        ((Stage) fermer_button.getScene().getWindow()).close();
    }

    private boolean isEligible() {
        if (abandonmentYear == null) return false;

        String[] years = abandonmentYear.split("/");
        int abandonmentYear = Integer.parseInt(years[0]);
        int currentYear = LocalDate.now().getYear();

        return (currentYear - abandonmentYear) <= 2;
    }

    private void showAlert(String title, String message) {
        Alert.AlertType type = title.equals("Success") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR;
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setStudentData(Etudiant etudiant) {
        this.etudiant = etudiant;
        reinsctiprion_matricule.setText(String.valueOf(etudiant.getIdEtu()));
        reinsctiprion_Nom.setText(etudiant.getNom());
        reinsctiprion_PreNom.setText(etudiant.getPrenom());
        reinsctiprion_GroupID.setText(etudiant.getIdGroupe());
        reinsctiprion_Date.setText(etudiant.getDateNaiss().toString());
        studentNameLabel.setText(etudiant.getNom() + " " + etudiant.getPrenom());
        updateEligibilityStatus();
    }

    public void setAbandonmentDate(String academicYear) {
        this.abandonmentYear = academicYear;
        if (abandonmentYearField != null) {
            abandonmentYearField.setText(academicYear);
            abandonmentYearField.setEditable(false);
            updateEligibilityStatus();
        }
    }

    private void updateEligibilityStatus() {
        if (etudiant != null && abandonmentYear != null) {
            if (isEligible()) {
                statusLabel.setText("Eligible pour réinscription");
                statusLabel.setStyle("-fx-text-fill: green;");
                AJT_D_ConfirmerButton.setDisable(false);
            } else {
                statusLabel.setText("Non éligible - Délai dépassé");
                statusLabel.setStyle("-fx-text-fill: red;");
                AJT_D_ConfirmerButton.setDisable(true);
            }
        }
    }

    public void setMenuController(MenuGestionAbdandenementController controller) {
        this.menuController = controller;
    }
}
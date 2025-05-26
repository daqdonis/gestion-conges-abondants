package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.DemReins;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;
import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.groupe14ing2.gestioncongesabondants.utils.EmailUtils;

public class ReintegrationController {

    @FXML private TextField matriculeField;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField groupeField;
    @FXML private TextField anneeScolaireField;
    @FXML private Button motifButton;
    @FXML private Button confirmerButton;
    @FXML private Button fermerButton;
    @FXML private Label statusLabel;
    @FXML private Pane main_Panel;

    private File selectedFile;
    private MenuViewController menuController;
    private Conge currentConge;

    @FXML
    public void initialize() {
        // Disable confirm button by default
        confirmerButton.setDisable(true);

        // Initialize academic year field with current academic year
        anneeScolaireField.setText(getCurrentAcademicYear());
        anneeScolaireField.setEditable(false); // Make it read-only

        // Add listener to matricule field
        matriculeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                checkStudentStatus(Long.parseLong(newValue));
            }
        });
    }

    private int getAcademicYear(LocalDate date) {
        // Academic year starts in September
        if (date.getMonth().getValue() >= Month.SEPTEMBER.getValue()) {
            return date.getYear();
        } else {
            return date.getYear() - 1;
        }
    }

    private String getCurrentAcademicYear() {
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();

        // Academic year calculation:
        // If current month is September-December: currentYear/nextYear
        // If current month is January-August: previousYear/currentYear
        if (month >= Month.SEPTEMBER.getValue()) {
            return year + "/" + (year + 1);
        } else {
            return (year - 1) + "/" + year;
        }
    }

    private boolean isWithinSameOrNextAcademicYear(LocalDate leaveDate, LocalDate currentDate) {
        // Debug information
        System.out.println("Leave Date: " + leaveDate);
        System.out.println("Current Date: " + currentDate);

        // Get the academic year start for both dates
        LocalDate leaveAcademicStart = getAcademicYearStartDate(leaveDate);
        LocalDate currentAcademicStart = getAcademicYearStartDate(currentDate);

        // Debug information
        System.out.println("Leave Academic Year Start: " + leaveAcademicStart);
        System.out.println("Current Academic Year Start: " + currentAcademicStart);

        // Calculate the difference in years
        long yearsDiff = ChronoUnit.YEARS.between(leaveAcademicStart, currentAcademicStart);
        System.out.println("Years Difference: " + yearsDiff);

        return yearsDiff <= 1;
    }

    private LocalDate getAcademicYearStartDate(LocalDate date) {
        // Academic year starts in September
        int year = date.getYear();
        if (date.getMonth().getValue() < Month.SEPTEMBER.getValue()) {
            year = year - 1;
        }
        return LocalDate.of(year, Month.SEPTEMBER, 1);
    }

    private void checkStudentStatus(long matricule) {
        try {
            DatabaseController db = new DatabaseController();
            Conge conge = db.getCongeByEtudiant(matricule);

            if (conge == null) {
                showAlert("Error", "No leave request found for this student.");
                clearFields();
                return;
            }

            currentConge = conge;
            Etudiant etudiant = conge.getEtudiant();

            // Fill student information
            nomField.setText(etudiant.getNom());
            prenomField.setText(etudiant.getPrenom());
            groupeField.setText(etudiant.getIdGroupe());

            // Check if the student's leave request is accepted
            if (conge.getEtat() != EtatTraitement.ACCEPTÉ) {
                statusLabel.setText("Student's leave request is not accepted");
                statusLabel.setStyle("-fx-text-fill: #ff0000;");
                confirmerButton.setDisable(true);
                return;
            }

            // Calculate time difference based on academic years
            LocalDate leaveDate = conge.getDateDemande().toLocalDate();
            LocalDate currentDate = LocalDate.now();

            boolean isEligible = isWithinSameOrNextAcademicYear(leaveDate, currentDate);

            if (!isEligible) {
                statusLabel.setText("Time limit exceeded (more than 1 year)");
                statusLabel.setStyle("-fx-text-fill: #ff0000;");
                confirmerButton.setDisable(true);
                return;
            }

            // Enable confirmation if all checks pass
            statusLabel.setText("Student eligible for reintegration");
            statusLabel.setStyle("-fx-text-fill: #3cd14e;");
            confirmerButton.setDisable(false);

        } catch (Exception e) {
            showAlert("Error", "Error checking student status: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMotifButton() {
        if (selectedFile != null) {
            // If file is already selected, open it
            if (Desktop.isDesktopSupported()) {
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().open(selectedFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Platform.runLater(() -> showAlert("Error", "Impossible d'ouvrir le fichier"));
                    }
                }).start();
            }
            return;
        }

        // If no file is selected, show file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier de justification");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedFile = fileChooser.showOpenDialog(main_Panel.getScene().getWindow());

        if (selectedFile != null) {
            motifButton.setText("Voir fichier");
            confirmerButton.setDisable(false);
        }
    }

    @FXML
    private void handleConfirmerButton() {
        try {
            if (selectedFile == null) {
                showAlert("Error", "Please select a justification file.");
                return;
            }

            if (currentConge == null) {
                showAlert("Error", "No valid leave request found.");
                return;
            }

            // Check time limit again
            LocalDate leaveDate = currentConge.getDateDemande().toLocalDate();
            LocalDate currentDate = LocalDate.now();

            if (!isWithinSameOrNextAcademicYear(leaveDate, currentDate)) {
                showAlert("Error", "Time limit exceeded (more than 1 year)");
                return;
            }

            // Create reintegration request
            DatabaseController db = new DatabaseController();
            FileInputStream fileInputStream = new FileInputStream(selectedFile);

            DemReins demReins = new DemReins(
                    currentConge.getEtudiant().getIdEtu(),
                    Date.valueOf(LocalDate.now()),
                    fileInputStream,
                    EtatTraitement.ACCEPTÉ
            );

            try {
                // Add reintegration request
                db.addDemReins(demReins, 'C'); // 'C' for Conge type

                // Mark student as reintegrated before removing the congé record
                db.addReintegratedStudent(currentConge.getEtudiant().getIdEtu(), currentConge.getIdDemande());

                // Remove the congé record
                db.removeConge(currentConge.getIdDemande());

                // Send email notification
                if (currentConge.getEtudiant() != null && currentConge.getEtudiant().getemail_etu() != null) {
                    String emailMessage = "Bonjour " + currentConge.getEtudiant().getNom() + " " + currentConge.getEtudiant().getPrenom() + ",\n\n" +
                            "Nous avons le plaisir de vous informer que votre demande de réintégration a été acceptée.\n\n" +
                            "Détails de la demande :\n" +
                            "- Matricule : " + currentConge.getEtudiant().getIdEtu() + "\n" +
                            "- Date de soumission : " + LocalDate.now() + "\n" +
                            "- Groupe : " + currentConge.getEtudiant().getIdGroupe() + "\n\n" +
                            "Vous pouvez maintenant reprendre vos études.\n\n" +
                            "Cordialement,\n" +
                            "L'équipe de gestion des congés";
                    
                    sendEmail(currentConge.getEtudiant().getemail_etu(), emailMessage);
                }

                // Show success message
                showAlert("Success", "Reintegration request accepted successfully.");

                // Clear the form
                clearFields();
                selectedFile = null;
                motifButton.setText("Choisir un fichier");
                confirmerButton.setDisable(true);

                // Update the menu view if available
                if (menuController != null) {
                    menuController.refreshTable();
                }

                // Close the window
                Stage stage = (Stage) confirmerButton.getScene().getWindow();
                stage.close();

            } catch (SQLException e) {
                showAlert("Error", "Error submitting reintegration request: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            showAlert("Error", "Error processing reintegration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefuserButton() {
        try {
            if (currentConge == null) {
                showAlert("Error", "No valid leave request found.");
                return;
            }

            Etudiant etudiant = currentConge.getEtudiant();
            if (etudiant == null) {
                showAlert("Error", "Student data not available.");
                return;
            }

            // Send email notification
            if (etudiant.getemail_etu() != null && !etudiant.getemail_etu().isEmpty()) {
                String emailMessage = "Bonjour " + etudiant.getNom() + " " + etudiant.getPrenom() + ",\n\n" +
                        "Nous regrettons de vous informer que votre demande de réintégration a été refusée.\n\n" +
                        "Détails de la demande :\n" +
                        "- Matricule : " + etudiant.getIdEtu() + "\n" +
                        "- Date de soumission : " + LocalDate.now() + "\n" +
                        "- Groupe : " + etudiant.getIdGroupe() + "\n\n" +
                        "Pour toute question concernant cette décision, veuillez nous contacter.\n\n" +
                        "Cordialement,\n" +
                        "L'équipe de gestion des congés";
                
                sendEmail(etudiant.getemail_etu(), emailMessage);
            }

            // Show message and close window
            showAlert("Information", "La demande de réintégration a été refusée.");
            Stage stage = (Stage) fermerButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            showAlert("Error", "Error processing refusal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendEmail(String to, String msg) {
        EmailUtils.sendEmail(to, "Confirmation de demande de réintégration", msg);
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        groupeField.clear();
        anneeScolaireField.clear();
        statusLabel.setText("");
        confirmerButton.setDisable(true);
    }

    private void showAlert(String title, String message) {
        Alert.AlertType type = title.equals("Success") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR;
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void fermerButtonOnAction() {
        Stage stage = (Stage) fermerButton.getScene().getWindow();
        stage.close();
    }

    public void setMenuController(MenuViewController menuController) {
        this.menuController = menuController;
    }

    public void setCongeData(Conge conge) {
        this.currentConge = conge;
        matriculeField.setText(String.valueOf(conge.getEtudiant().getIdEtu()));
        nomField.setText(conge.getEtudiant().getNom());
        prenomField.setText(conge.getEtudiant().getPrenom());
        groupeField.setText(conge.getEtudiant().getIdGroupe());
        anneeScolaireField.setText(getCurrentAcademicYear());
        if (conge.getJustificatif() != null) {
            try {
                selectedFile = File.createTempFile("justification", ".pdf");
                Files.copy(new FileInputStream(conge.getJustificatif()), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                motifButton.setText("Voir fichier");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Erreur lors de la récupération du fichier de justification");
            }
        }
        checkStudentStatus(conge.getEtudiant().getIdEtu());
    }
}
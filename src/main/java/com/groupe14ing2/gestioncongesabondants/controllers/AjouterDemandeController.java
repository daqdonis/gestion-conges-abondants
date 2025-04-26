package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;
import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjouterDemandeController {

    @FXML private Button AJT_D_ConfirmerButton;
    @FXML private TextField AJT_D_Date;
    @FXML private TextField AJT_D_GroupID;
    @FXML private Button AJT_D_MotifButton;
    @FXML private TextField AJT_D_Nom;
    @FXML private TextField AJT_D_PreNom;
    @FXML private TextField AJT_D_matricule;
    @FXML private Button fermer_button;
    @FXML private Pane main_Panel;

    private File selectedFile;

    @FXML
    public void initialize() {
        // Set today's date as default
        AJT_D_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @FXML
    private void handleMotifButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Justification File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedFile = fileChooser.showOpenDialog(main_Panel.getScene().getWindow());

        if (selectedFile != null) {
            AJT_D_MotifButton.setText("File Selected");
        }
    }

    @FXML
    private void handleConfirmerButton() {
        try {
            // Validate inputs
            if (selectedFile == null) {
                // Show error alert
                return;
            }

            // Create Conge object
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            Conge conge = new Conge(
                    Date.valueOf(AJT_D_Date.getText()),
                    30, // Default duration of 30 days - adjust as needed
                    EtatTraitement.ENATTENTE,
                    fileInputStream
            );

            // Get student ID (you'll need to implement this)
            int etudiantId = Integer.parseInt(AJT_D_matricule.getText());

            // Add to database (using your existing DatabaseController)
            DatabaseController dbController = new DatabaseController();
            dbController.addConge(conge);

            // Update student record with conge ID
            Etudiant etudiant = dbController.getEtudiant(etudiantId);
            if (etudiant != null) {
                etudiant.setIdConge((int) conge.getIdDemande());
                dbController.updateEtudiant(etudiant);
            }

            // Close the window
            fermer_button_onAction();

        } catch (Exception e) {
            e.printStackTrace();
            // Show error alert
        }
    }

    @FXML
    public void fermer_button_onAction() {
        Stage stage = (Stage) fermer_button.getScene().getWindow();
        stage.close();
    }
}
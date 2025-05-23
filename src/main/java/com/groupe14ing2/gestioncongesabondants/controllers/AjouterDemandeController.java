package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.groupe14ing2.gestioncongesabondants.models.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    @FXML private ChoiceBox<TypeConge> typeChoiceBox;

    private File selectedFile;
    private MenuViewController menuController;  // Référence du contrôleur MenuViewController

    // Initialisation, définir la date par défaut
    @FXML
    public void initialize() {
        AJT_D_Date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        typeChoiceBox.getItems().addAll(TypeConge.values());
    }

    // Gérer le bouton de sélection du fichier de justification
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

    // Gérer la confirmation de la demande
    @FXML
    private void handleConfirmerButton() {
        try {
            // Validation des entrées
            if (selectedFile == null) {
                showAlert("Error", "Please select a justification file.");
                return;
            }

            if (AJT_D_matricule.getText().isEmpty()) {
                showAlert("Error", "Student ID cannot be empty.");
                return;
            }

            // ID étudiant
            long etudiantId = Long.parseLong(AJT_D_matricule.getText());

            // Vérifier si l'étudiant existe
            DatabaseController dbController = new DatabaseController();
            Etudiant etudiant = dbController.getEtudiant(etudiantId);
            if (etudiant == null) {
                showAlert("Error", "Student not found with ID: " + etudiantId);
                return;
            }

            // Créer l'objet Conge avec les données
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            Conge conge = new Conge(
                    Date.valueOf(AJT_D_Date.getText()),
                    2, // Durée par défaut
                    EtatTraitement.ENATTENTE,
                    fileInputStream
            );
            conge.setEtudiant(etudiant);

            // Ajouter à la base de données
            dbController.addConge(conge);
            System.out.println(conge.getIdDemande());
            System.out.println(menuController.getAdmin().getNom());
            dbController.addActionAdmin(new ActionAdmin(
                    menuController.getAdmin().getIdAdmin(),
                    "nouveau congé pour " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom(),
                    "C" + (Date.valueOf(AJT_D_Date.getText()).getYear() - 100) + etudiant.getIdEtu(),
                    'C'
            ));

            // Afficher l'alerte de succès
            showAlert("Success", "Demand added successfully!");

            // Fermer la fenêtre actuelle
            fermer_button_onAction();

            // Actualiser la table dans le MenuViewController
            if (menuController != null) {
                menuController.refreshTable();  // Cette méthode va rafraîchir la table avec les nouvelles demandes
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error adding demand: " + e.getMessage());
        }
    }
    @FXML
    private void handleFillButton() {
        try {
            if (AJT_D_matricule.getText().isEmpty()) {
                showAlert("Error", "Please enter a matricule first.");
                return;
            }

            long matricule = Long.parseLong(AJT_D_matricule.getText());
            DatabaseController dbController = new DatabaseController();
            Etudiant etudiant = dbController.getEtudiant(matricule);

            if (etudiant == null) {
                showAlert("Error", "No student found with matricule: " + matricule);
                return;
            }

            // Fill the fields with student information
            AJT_D_Nom.setText(etudiant.getNom());
            AJT_D_PreNom.setText(etudiant.getPrenom());
            AJT_D_GroupID.setText(String.valueOf(etudiant.getIdGroupe()));
            AJT_D_Date.setText(etudiant.getDateNaiss().toString());

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid matricule format. Please enter a valid number.");
        } catch (Exception e) {
            showAlert("Error", "Error retrieving student information: " + e.getMessage());
        }
    }

    // Méthode pour afficher les alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fermer la fenêtre
    @FXML
    public void fermer_button_onAction() {
        Stage stage = (Stage) fermer_button.getScene().getWindow();
        stage.close();
    }

    // Set MenuViewController reference (pour rafraîchir la table après ajout de la demande)
    public void setMenuController(MenuViewController menuController) {
        this.menuController = menuController;
    }
}

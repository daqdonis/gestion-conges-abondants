package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.groupe14ing2.gestioncongesabondants.models.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML private Button fillButton;
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

            // check if the student is an abandon
            if (dbController.getAbondant(etudiantId) != null) {
                showAlert("Error", "L'étudiant(e) est déclaré(e) comme abandont.");
                return;
            }

            Etudiant etudiant = dbController.getEtudiant(etudiantId);
            if (etudiant == null) {
                showAlert("Error", "Student not found with ID: " + etudiantId);
                return;
            }

            // Créer l'objet Conge avec les données*
            Conge tempConge = dbController.getCongeByEtudiant(etudiantId);
            if (tempConge != null && tempConge.getEtat() != EtatTraitement.REFUSÉ) {
                showAlert("Error", "L'étudiant(e) a déja un congé");
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            Conge conge = new Conge(
                    Date.valueOf(LocalDate.now()),
                    2, // Durée par défaut
                    EtatTraitement.ENATTENTE,
                    fileInputStream,
                    typeChoiceBox.getSelectionModel().getSelectedItem()
            );
            if (tempConge != null) {
                try {
                    conge.setIdDemande(tempConge.getIdDemande().substring(0,14) + (Integer.parseInt(String.valueOf(tempConge.getIdDemande().charAt(15))) + 1));
                } catch (IndexOutOfBoundsException e) {
                    conge.setIdDemande(tempConge.getIdDemande() + "1");
                }

            }
            conge.setEtudiant(etudiant);
            conge.setType(typeChoiceBox.getSelectionModel().getSelectedItem());
            // Ajouter à la base de données
            dbController.addConge(conge);
            System.out.println(conge.getIdDemande());
            System.out.println(menuController.getAdmin().getNom());
            System.out.println("C" + (conge.getDateDemande().getYear() - 100) + etudiant.getIdEtu());
            // logs the admins action
            AddActionAdmin.addConge(menuController.getAdmin(), etudiant, conge);

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

            // checks if the student abandoned
            if (dbController.getAbondant(matricule) != null) {
                showAlert("Error", "L'étudiant(e) est déclaré(e) comme abandont.");
                return;
            }

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

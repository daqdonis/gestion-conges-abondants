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
        AJT_D_RefuserButton.setOnAction(e -> {
            // Just close the window when refuse is clicked
            ((Stage) AJT_D_RefuserButton.getScene().getWindow()).close();
        });

        AJT_D_ConfirmerButton.setOnAction(e -> {
            try {
                if (isEligible()) {
                    DatabaseController db = new DatabaseController();
                    db.removeAbondant(etudiant.getIdEtu());
                    menuController.refreshTable();
                    Stage stage = (Stage) AJT_D_ConfirmerButton.getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
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
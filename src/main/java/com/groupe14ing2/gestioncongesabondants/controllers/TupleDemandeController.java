package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.EtatTraitement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.groupe14ing2.gestioncongesabondants.controllers.MenuViewController.*;

import java.awt.*;
import java.io.*;

public class TupleDemandeController extends MenuViewController {

    @FXML
    private Label matriculeLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label etatLabel;
    @FXML
    private Button voir_jst_button;
    @FXML
    private Button traiter_jst_button;
    @FXML
    private Button reintegrationButton;
    @FXML
    private Button traiterButton;

    private MenuViewController menuController;
    private Conge conge;

    @FXML
    public void initialize() {
    }

    public void setData(Conge conge) {
        this.conge = conge;

        if (conge.getEtudiant() != null) {
            matriculeLabel.setText(String.valueOf(conge.getEtudiant().getIdEtu()));
            nomLabel.setText(conge.getEtudiant().getNom());
            prenomLabel.setText(conge.getEtudiant().getPrenom());
        } else {
            matriculeLabel.setText("N/A");
            nomLabel.setText("N/A");
            prenomLabel.setText("N/A");
        }
        etatLabel.setText(conge.getEtat().toString());
        if (conge.getEtat() == EtatTraitement.ACCEPTÉ) {
            reintegrationButton.setDisable(false);
            reintegrationButton.getStyleClass().remove("reintegration-button-disabled");
            reintegrationButton.getStyleClass().add("reintegration-button-enabled");
        } else {
            reintegrationButton.setDisable(true);
            reintegrationButton.getStyleClass().remove("reintegration-button-enabled");
            reintegrationButton.getStyleClass().add("reintegration-button-disabled");
        }

        voir_jst_button.setOnAction(e -> {
            viewJustification();
        });

        traiter_jst_button.setOnAction(e -> {
            System.out.println("Traiter demande " + conge.getIdDemande());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml"));
                Parent root = loader.load();

                TraiterDemandeController controller = loader.getController();
                controller.setMenuController(menuController);
                controller.setConge(conge);
                controller.setOnStatusUpdated(() -> {
                    if (menuController != null) {
                        menuController.refreshTable();
                    }
                });

                controller.setButton_justificationAction(event -> viewJustification());

                Stage stage = new Stage();
                stage.setTitle("Traiter une demande");
                stage.setScene(new Scene(root));



                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void updateReintegrationButton() {
        if (conge.getEtat() == EtatTraitement.ACCEPTÉ) {
            // Check time limit
            LocalDate leaveDate = conge.getDateDemande().toLocalDate();
            LocalDate currentDate = LocalDate.now();

            int leaveYear = getAcademicYear(leaveDate);
            int currentYear = getAcademicYear(currentDate);

            if (currentYear - leaveYear <= 1) {
                reintegrationButton.setDisable(false);
                reintegrationButton.getStyleClass().remove("reintegration-button-disabled");
                reintegrationButton.getStyleClass().add("reintegration-button-enabled");
            } else {
                reintegrationButton.setDisable(true);
                reintegrationButton.getStyleClass().remove("reintegration-button-enabled");
                reintegrationButton.getStyleClass().add("reintegration-button-disabled");
            }
        } else {
            reintegrationButton.setDisable(true);
            reintegrationButton.getStyleClass().remove("reintegration-button-enabled");
            reintegrationButton.getStyleClass().add("reintegration-button-disabled");
        }
    }

    private int getAcademicYear(LocalDate date) {
        if (date.getMonth().getValue() >= Month.SEPTEMBER.getValue()) {
            return date.getYear();
        } else {
            return date.getYear() - 1;
        }
    }

    @FXML
    private void handleReintegrationButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/reintegration.fxml"));
            Parent root = loader.load();
            updateReintegrationButton();

            ReintegrationController controller = loader.getController();
            controller.setMenuController(menuController);

            // Pre-fill the form with student data
            controller.setCongeData(conge);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Réintégration");


            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMenuController(MenuViewController menuController) {
        this.menuController = menuController;
    }

    private void viewJustification() {
        System.out.println(conge.getJustificatif());
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(conge.getJustificatif());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }).start();
        }
    }
}

package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.IOException;

import com.groupe14ing2.gestioncongesabondants.models.Conge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TupleDemandeController {

    @FXML
    private Text matriculeText;

    @FXML
    private Text nomText;

    @FXML
    private Text prenomText;

    @FXML
    private Text statutText;

    @FXML
    private Button voir_jst_button;

    @FXML
    private Button traiter_jst_button;

    @FXML
    private Button reinscription_button;

    private MenuViewController menuController;

    public void setData(Conge conge) {
        if (conge.getEtudiant() != null) {
            matriculeText.setText(String.valueOf(conge.getEtudiant().getIdEtu()));
            nomText.setText(conge.getEtudiant().getNom());
            prenomText.setText(conge.getEtudiant().getPrenom());
        } else {
            matriculeText.setText("N/A");
            nomText.setText("N/A");
            prenomText.setText("N/A");
        }
        statutText.setText(conge.getEtat().toString());

        // Voir Justificatif Button
        voir_jst_button.setOnAction(e -> {
            System.out.println(conge.getJustificatif());
            if(Desktop.isDesktopSupported()) {
                new Thread(() -> {
                    try {
                        Desktop.getDesktop().open(conge.getJustificatif());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }).start();
            }
        });

        // Traiter Demande Button
        traiter_jst_button.setOnAction(e -> {
            System.out.println("Traiter demande " + conge.getIdDemande());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml"));
                Parent root = loader.load();

                TraiterDemandeController controller = loader.getController();
                controller.setMenuController(menuController);
                controller.setConge(conge);

                Stage stage = new Stage();
                stage.setTitle("Traiter une demande");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Reinscription Button
        reinscription_button.setOnAction(e -> {
            System.out.println("Reinscription for demande " + conge.getIdDemande());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/reinscription.fxml"));
                Parent root = loader.load();

                reinsctiprionController controller = loader.getController();
                if (conge.getEtudiant() != null) {
                    controller.setStudentData(conge.getEtudiant());
                }

                Stage stage = new Stage();
                stage.setTitle("Reinscription");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void setMenuController(MenuViewController controller) {
        System.out.println("the controller is: " + menuController);
        this.menuController = controller;
    }
}
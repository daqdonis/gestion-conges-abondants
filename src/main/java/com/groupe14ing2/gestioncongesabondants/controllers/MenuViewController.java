package com.groupe14ing2.gestioncongesabondants.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MenuViewController {

    @FXML
    private Button button_ajouter_demande;

    @FXML
    private Button gestino_des_cong_button;

    @FXML
    private Button gestion_des_abondant_button;

    @FXML
    private Pane left_bar_menu;

    @FXML
    private Pane main_background;

    @FXML
    private Pane main_window;

    @FXML
    private Button more_button;

    @FXML
    private PieChart myPieChart;

    @FXML
    private Button profile_button;

    @FXML
    private Pane switch_chap;

    @FXML
    private Pane table_pan;

    @FXML
    private TextField text_field_rechercher_demande;

    @FXML
    public void initialize() {
        myPieChart.getData().addAll(
                new PieChart.Data("", 20),
                new PieChart.Data("", 40),
                new PieChart.Data("", 12)
        );

    }


    //it s not working try to fix it  FXMLFILE is not Loading

    @FXML
    public void ajouterDemandeFenetre() {
        try {
            // Étape 1 : Obtenir le chemin du fichier FXML
            String fxmlPath = "com.groupe14ing2.gestioncongesabondants.ajouter-demande.fxml";
            InputStream fxmlStream = getClass().getClassLoader().getResourceAsStream(fxmlPath);

            if (fxmlStream == null) {
                System.err.println("❌ Fichier FXML introuvable : " + fxmlPath);
                return;
            }

            // Étape 2 : Créer un FXMLLoader et charger le contenu
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlStream);

            // Étape 3 : Créer une nouvelle scène et une nouvelle fenêtre
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter Demande");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement de la fenêtre :");
            e.printStackTrace();
        }

    }
}

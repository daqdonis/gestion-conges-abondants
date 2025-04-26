package com.groupe14ing2.gestioncongesabondants.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;


public class MenuViewController {


    @FXML
    private Button button_ajouter_demande;

    @FXML
    private Button button_traiter_demande;

    @FXML
    private Button gestino_des_cong_button;

    @FXML
    private Button gestion_des_abondant_button;

    @FXML
    private Pane left_bar_menu;

    @FXML
    private Pane main_background;
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

    @FXML
    private Parent mainRoot; // Tu dois lier fx:id="mainRoot" dans ton FXML principal

    @FXML
    public void traiter_demande() {
        ouvrirFenetreAvecEffet("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml", "Traiter demande");
    }
    @FXML
    public void ajouter_demande() {
        ouvrirFenetreAvecEffet("/com/groupe14ing2/gestioncongesabondants/ajouter-demande.fxml", "ajouter-demande.fxml");
    }
    private void ouvrirFenetreAvecEffet(String cheminFXML, String titre) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent newRoot = fxmlLoader.load();
            Scene scene = new Scene(newRoot);
            Stage stage = new Stage();
            stage.setTitle(titre);
            stage.setScene(scene);
            // Positionner et attacher à la fenêtre principale
            Window mainWindow = mainRoot.getScene().getWindow();
            stage.initOwner(mainWindow);
            stage.initModality(Modality.WINDOW_MODAL); // rend la fenêtre modale
            stage.centerOnScreen();

            // Appliquer un effet de flou
            GaussianBlur blur = new GaussianBlur(10);
            mainRoot.setEffect(blur);

            // Retirer le flou à la fermeture
            stage.setOnHidden(e -> mainRoot.setEffect(null));
            String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/traiter-une-demande.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la fenêtre : " + cheminFXML);
            ex.printStackTrace();
        }
    }

    @FXML
    private void exit(){
        System.exit(0);
    }
}

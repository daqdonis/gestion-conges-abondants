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

    @FXML
    public void traiter_demande() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Traiter demande");
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ex.printStackTrace();
        }
    }
}

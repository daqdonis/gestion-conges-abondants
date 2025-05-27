package com.groupe14ing2.gestioncongesabondants.controllers;


import com.groupe14ing2.gestioncongesabondants.models.*;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.stage.*;


import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MenuGestionAbdandenementController {

    @FXML
    private Button button_ajouter_demande;

    @FXML
    private Button button_traiter_demande;

    @FXML
    private Button gestion_des_abondant_button;

    @FXML
    private Button gestion_des_conges_button;

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


    public void setAdmin(Admin admin) {
        this.admin = admin;
    }


    @FXML
    private TextField search_field;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox requestsContainer;


    @FXML
    private Label Totalstudent;

    @FXML
    private Label Totalabondant;

    private File selectedFile;
    private Admin admin;
    private MenuViewController menuController;
    private List<Abondant> allAbondants;

    @FXML
    public void initialize() {
        // Initialize search functionality
        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAbondants(newValue);
        });
        
        refreshTable();
        updateCounters();
    }

    private void filterAbondants(String searchText) {
        try {
            if (searchText == null || searchText.trim().isEmpty()) {
                // If search is empty, show all records
                displayAbondants(allAbondants);
                return;
            }

            // Filter abondants by matricule
            List<Abondant> filteredAbondants = allAbondants.stream()
                .filter(abondant -> String.valueOf(abondant.getIdEtu()).contains(searchText.trim()))
                .collect(Collectors.toList());

            // Display filtered results
            displayAbondants(filteredAbondants);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error filtering abondants: " + e.getMessage());
        }
    }

    private void displayAbondants(List<Abondant> abondants) {
        requestsContainer.getChildren().clear();

        for (Abondant request : abondants) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/groupe14ing2/gestioncongesabondants/TupleAbandonment.fxml"));
                HBox tupleView = loader.load();

                TupleDemandeAbandonmentController tupleController = loader.getController();
                tupleController.setMenuController(this);
                tupleController.setData(request);

                requestsContainer.getChildren().add(tupleView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCounters() {
        try {
            DatabaseController db = new DatabaseController();
            int totalStudents = db.getTotalStudents();
            int totalAbondants = db.getTotalAbondants();
            
            Totalstudent.setText("Nombre total d'étudiants: " + totalStudents);
            Totalabondant.setText("Nombre d'abandons: " + totalAbondants);
            
            // Style the labels
            Totalstudent.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1f75ff;");
            Totalabondant.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #ff4444;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void refreshTable() {
        System.out.println("Refreshing table...");

        try {
            DatabaseController db = new DatabaseController();
            allAbondants = db.getAbondant(); // Store all abondants for filtering
            displayAbondants(allAbondants);
            updateCounters();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML

    private void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Liste des étudiants");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        selectedFile = fileChooser.showOpenDialog(main_background.getScene().getWindow());

        if (selectedFile != null) {
            System.out.println("File selected : " + selectedFile.getAbsolutePath());
            try {
                DatabaseController db = new DatabaseController();
                GetAbandonts.getIdsFromFile(selectedFile).forEach(id -> {
                    System.out.println("student id : " + id);
                    try {
                        if(db.getCongeByEtudiant(id) == null) {
                            db.addAbondant(new Abondant(
                                    id,
                                    admin.getIdAdmin(),
                                    Date.valueOf(LocalDate.now())
                            ));
                            // logs the admins action
                            AddActionAdmin.addAbondant(menuController.getAdmin(), db.getEtudiant(id));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            refreshTable();
        }

    }


    public Admin getAdmin() {
        return admin;
    }

    @FXML
    private void exit() {
        System.exit(0);
    }


    @FXML
    public void setSwitchAction(Consumer<ActionEvent> f) {
        gestion_des_abondant_button.setOnAction(e -> f.accept(e));
    }

    public void setMenuController(MenuViewController menuController) {
        this.menuController = menuController;

    }
}

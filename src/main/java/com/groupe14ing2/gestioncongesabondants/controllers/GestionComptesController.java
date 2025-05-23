package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionComptesController {

    @FXML
    private Button add_Acc_button;

    @FXML
    private Button admin_acc_button;

    @FXML
    private Button add_students_button;

    @FXML
    private TextField filtrer_textField;

    @FXML
    private Button install_jst_button;

    @FXML
    private Button regulaire_acc_button;

    @FXML
    private VBox requestsContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane table_pan;

    @FXML
    private Button traiter_jst_button;

    @FXML
    private Label user_Name_Lable;

    @FXML
    private Pane parentPane;

    File selectedFile;

    @FXML
    public void initialize() {
        refreshTable();
    }

    @FXML
    public void ajouter_compte() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/ajouter-compte.fxml"));
            Parent root = loader.load();

            AjouterCompteController compteController = loader.getController();
            compteController.setGestionComptesController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une demande");

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void refreshTable() {
        System.out.println("Refreshing table...");
        try {
            DatabaseController db = new DatabaseController();
            List<Admin> admins = db.getAllAdmins();

            requestsContainer.getChildren().clear();

            for (Admin admin : admins) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/TupleComptes.fxml"));
                    HBox tupleView = loader.load(); // charger le FXML

                    TupleAdminController tupleController = loader.getController();

                    tupleController.setData(admin); // injecter les données
                    tupleController.setGestionComptesController(this); // injecter le contrôleur MenuViewController


                    requestsContainer.getChildren().add(tupleView); // ajouter au container
                } catch (IOException e) {
                    System.err.println("Erreur de chargement du TupleAdmin.fxml");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur base de données");
            e.printStackTrace();
        }
    }

    @FXML
    private void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Liste des étudiants");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        selectedFile = fileChooser.showOpenDialog(parentPane.getScene().getWindow());

        if (selectedFile != null) {
            try {
                String[] HEADERS = { "code", "nom", "prénom", "date de naissance", "groupe", "section", "cycle", "filière", "année", "année universitaire"};
                CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                        .setHeader(HEADERS)
                        .setSkipHeaderRecord(true)
                        .build();

                CSVParser csvParser = new CSVParser(new FileReader(selectedFile), csvFormat);
                Iterable<CSVRecord> etudiants = csvParser.getRecords();
                try {
                    DatabaseController db = new DatabaseController();
                    etudiants.forEach( etudiant -> {
                            try {
                                Filiere filiere = new Filiere(
                                        etudiant.get("filière").substring(0,3).toUpperCase(),
                                        etudiant.get("filière")
                                );

                                db.addFiliere(filiere);

                                Cycle cycle = new Cycle(
                                        etudiant.get("cycle").substring(0,3).toUpperCase(),
                                        etudiant.get("cycle"),
                                        filiere.getIdFiliere()
                                );

                                db.addCycle(cycle);

                                Section section = new Section(
                                        cycle.getIdCycle() + etudiant.get("année") + filiere.getIdFiliere() + etudiant.get("année universitaire").replaceAll("/", "") + etudiant.get("section"),
                                        Integer.parseInt(etudiant.get("section")),
                                        cycle.getIdCycle()
                                );

                                Semestre semestrePair = new Semestre(
                                        section.getIdSection() + Integer.parseInt(etudiant.get("année"))*2,
                                        Integer.parseInt(etudiant.get("année"))*2,
                                        cycle.getIdCycle()
                                );

                                Semestre semestreImpair = new Semestre(
                                        section.getIdSection() + (Integer.parseInt(etudiant.get("année"))*2 -1),
                                        Integer.parseInt(etudiant.get("année"))*2 - 1,
                                        cycle.getIdCycle()
                                );

                                db.addSemestre(semestrePair);
                                db.addSemestre(semestreImpair);

                                db.addSection(section);

                                Groupe groupe = new Groupe(
                                        section.getIdSection() + Long.parseLong(etudiant.get("groupe")),
                                        Long.parseLong(etudiant.get("groupe")),
                                        section.getIdSection(),
                                        semestrePair.getIdSemestre(),
                                        semestreImpair.getIdSemestre()
                                );

                                db.addGroupe(groupe);

                                db.addEtudiant(new Etudiant(
                                        Long.parseLong(etudiant.get("code")),
                                        etudiant.get("nom"),
                                        etudiant.get("prénom"),
                                        Date.valueOf(etudiant.get("date de naissance")),
                                        groupe.getIdGroupe()
                                ));
                            } catch (SQLException e1) {
                               e1.printStackTrace();
                            }

                    });
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    private void exit(){
        System.exit(0);
    }

    @FXML
    private void fermer_button_onAction() {
        Stage stage = (Stage) table_pan.getScene().getWindow();
        stage.close();
    }
}

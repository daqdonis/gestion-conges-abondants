package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import com.groupe14ing2.gestioncongesabondants.models.Abondant;

import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TupleDemandeAbandonmentController {

    @FXML
    private Text matriculeText;

    @FXML
    private Text nomText;

    @FXML
    private Text prenomText;

    @FXML
    private Text statutText;

    @FXML
    private Button reinscription_button;

    private MenuGestionAbdandenementController menuController;

    public void setData(Abondant abondant) {
        try {
            DatabaseController db = new DatabaseController();
            Etudiant etudiant = db.getEtudiant(abondant.getIdEtu());

            matriculeText.setText(String.valueOf(abondant.getIdEtu()));
            nomText.setText(etudiant.getNom());
            prenomText.setText(etudiant.getPrenom());

            // Format the date as dd/MM/yyyy
            LocalDate date = abondant.getDateDec().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            statutText.setText(date.format(formatter));

            reinscription_button.setOnAction(e -> {
                System.out.println("Reinscription for demande " + abondant.getIdEtu());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "/com/groupe14ing2/gestioncongesabondants/reinscriptionAbandonment.fxml"));
                    Parent root = loader.load();

                    reinsctiprionController controller = loader.getController();
                    controller.setStudentData(etudiant);
                    controller.setMenuController(menuController);
                    
                    // Set the academic year based on abandonment date
                    LocalDate abandonmentDate = abondant.getDateDec().toLocalDate();
                    int year = abandonmentDate.getMonthValue() >= Month.SEPTEMBER.getValue() ? 
                             abandonmentDate.getYear() : abandonmentDate.getYear() - 1;
                    String academicYear = year + "/" + (year + 1);
                    controller.setAbandonmentDate(academicYear);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Reinscription Abandonment");
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (SQLException e3) {
            e3.printStackTrace();
        }
    }

    public void setMenuController(MenuGestionAbdandenementController controller) {
        this.menuController = controller;
    }
}
package com.groupe14ing2.gestioncongesabondants.controllers;

import java.io.IOException;

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
    private Button traiter_jst_button;

    @FXML
    private Button reinscription_button;

    private MenuViewController menuController;

    public void setData(Abondant abondant) {
        try {
            DatabaseController db = new DatabaseController();
            Etudiant etudiant = db.getEtudiant(abondant.getIdEtu());


            matriculeText.setText(String.valueOf(abondant.getIdEtu()));
            nomText.setText(etudiant.getNom());
            prenomText.setText(etudiant.getPrenom());

            statutText.setText(abondant.getDateDec().toString());

            traiter_jst_button.setOnAction(e -> {
            /*System.out.println("Traiter demande " + abondant.getIdDemande());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml"));
                Parent root = loader.load();

                TraiterDemandeController controller = loader.getController();
                controller.setMenuController(menuController);
                controller.setConge(abondant);

                Stage stage = new Stage();
                stage.setTitle("Traiter une demande");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }*/
            });

            reinscription_button.setOnAction(e -> {
                System.out.println("Reinscription for demande " + abondant.getIdEtu());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "/com/groupe14ing2/gestioncongesabondants/reinscriptionAbandonment.fxml"));
                    Parent root = loader.load();

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

    public void setMenuController(MenuViewController controller) {
        System.out.println("the controller is: " + menuController);
        this.menuController = controller;
    }
}
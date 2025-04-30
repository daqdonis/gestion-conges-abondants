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

        voir_jst_button.setOnAction(e -> {
            System.out.println("Voir justification pour " + conge.getIdDemande());
            // Appelle ici la méthode pour voir la justification
        });

       traiter_jst_button.setOnAction(e -> {
    System.out.println("Traiter demande " + conge.getIdDemande());
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml"));
        Parent root = loader.load();

      
        TraiterDemandeController controller = loader.getController();

       
        controller.setConge(conge);

       
        Stage stage = new Stage();
        stage.setTitle("Traiter une demande");
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException ex) {
        ex.printStackTrace();
    }
});

    }
    public void setMenuController(MenuViewController controller) {
        this.menuController = controller;
    }
}

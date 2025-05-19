package com.groupe14ing2.gestioncongesabondants.controllers;


import com.groupe14ing2.gestioncongesabondants.models.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class TupleAdminController {

    @FXML
    private Text emailText;

    @FXML
    private Text nomText;

    @FXML
    private Text prenomText;

    @FXML
    private Text roleText;

    @FXML
    private Button view_actions;

    @FXML
    private Button modifier_button;

    @FXML
    private Button supp_button;

    private GestionComptesController gestionComptesController;

    public void setGestionComptesController(GestionComptesController gestionComptesController) {
        this.gestionComptesController = gestionComptesController;
    }

    public void setData(Admin admin) {
        nomText.setText(admin.getNom());
        prenomText.setText(admin.getPrenom());
        emailText.setText(admin.getEmail());
        roleText.setText(admin.getRoles().toString().replace('_', ' '));

        supp_button.setOnAction(e -> {
            try {
                DatabaseController db = new DatabaseController();
                db.removeAdmin(admin.getIdAdmin());
                gestionComptesController.refreshTable();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        view_actions.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/actions-admin.fxml"));
                Scene scene = new Scene(loader.load());
                ActionsController controller = loader.getController();
                controller.setAdmin(admin);
                controller.initializeData(); // Call explicitly AFTER setAdmin()
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}

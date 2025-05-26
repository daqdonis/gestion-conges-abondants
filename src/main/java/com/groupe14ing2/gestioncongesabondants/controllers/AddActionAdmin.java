package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.ActionAdmin;
import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
;

public class AddActionAdmin {
    @FXML
    private Button exitButton;

    private static void addAction(Admin admin, String action, String idActionFait, char actionType) {
        try {
            DatabaseController db = new DatabaseController();
            db.addActionAdmin(new ActionAdmin(
                    admin.getIdAdmin(),
                    action,
                    idActionFait,
                    actionType
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addConge(Admin admin, Etudiant etudiant, Conge conge) {
        addAction(
                admin,
                "nouveau congé pour " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom(),
                conge.getIdDemande(),
                'C'
        );
    }

    public static void addAbondant(Admin admin, Etudiant etudiant) {
        addAction(
                admin,
                "Déclaration d'abandon pour " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom(),
                String.valueOf(etudiant.getIdEtu()),
                'A'
        );
    }

    public static void changeEtat(Admin admin, Etudiant etudiant, Conge conge) {
        addAction(
                admin,
                "Changement d'état de congé pour " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom() + " vers " + conge.getEtat(),
                conge.getIdDemande(),
                'C'
        );
    }

    public static void reinscrit(Admin admin, Etudiant etudiant) {
        addAction(
                admin,
                "Réinscription pour " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom(),
                String.valueOf(etudiant.getIdEtu()),
                'A'
        );
    }

    public static void reintegrer(Admin admin, Etudiant etudiant, Conge conge) {
        addAction(
                admin,
                "Réintégration pour " + etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom(),
                conge.getIdDemande(),
                'C'
        );
    }
    @FXML
    private void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}

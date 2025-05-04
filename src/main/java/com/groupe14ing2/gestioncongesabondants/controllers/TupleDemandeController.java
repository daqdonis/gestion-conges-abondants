package com.groupe14ing2.gestioncongesabondants.controllers;


import com.groupe14ing2.gestioncongesabondants.models.Conge;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
                //File file = File.createTempFile("conge", ".pdf");

                //Files.copy(conge.getJustificatif(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println(conge.getJustificatif());
                if( Desktop.isDesktopSupported() )
                {
                    new Thread(() -> {
                        try {
                            Desktop.getDesktop().open(conge.getJustificatif());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }).start();
                }
        });

        traiter_jst_button.setOnAction(e -> {
            System.out.println("Traiter demande " + conge.getIdDemande());
            // Appelle ici la méthode pour traiter la demande
        });
    }
}

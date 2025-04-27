package com.groupe14ing2.gestioncongesabondants.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class JustificationViewerController {
    @FXML private ImageView imageView;
    @FXML private Label fileNameLabel;

    public void loadJustification(long demandeId) {
        try {
            DatabaseController db = new DatabaseController();
            InputStream is = db.getJustificationFile((int) demandeId);

            if (is != null) {
                // Try to display image if it's an image file
                try {
                    Image image = new Image(is);
                    imageView.setImage(image);
                    fileNameLabel.setText("Image Preview");
                } catch (Exception e) {
                    // If not an image, offer download
                    offerFileDownload(demandeId, is);
                }
            }
        } catch (Exception e) {
            fileNameLabel.setText("Error loading file: " + e.getMessage());
        }
    }

    private void offerFileDownload(long demandeId, InputStream is) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Justification File");
        fileChooser.setInitialFileName("justification_" + demandeId + ".pdf");
        File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

        if (file != null) {
            try {
                Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                fileNameLabel.setText("File saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                fileNameLabel.setText("Error saving file: " + e.getMessage());
            }
        }
    }
}
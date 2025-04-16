package com.groupe14ing2.gestioncongesabondants.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MenuViewController {
    @FXML
    private Pane General_menu_window;

    @FXML
    private Pane Menu_header;

    @FXML
    private Button button_ajouter_demande;

    @FXML
    private Button button_traiter_demande;

    @FXML
    private Button formulaire_anullerButton;

    @FXML
    private Button formulaire_confirmerButton;

    @FXML
    private DatePicker formulaire_date_picker;

    @FXML
    private TextField formulaire_text_field_groupID;

    @FXML
    private TextField formulaire_text_field_matricule;

    @FXML
    private TextField formulaire_text_field_nom;

    @FXML
    private TextField formulaire_text_field_prenom;

    @FXML
    private Button formulaire_uploadButton;

    @FXML
    private Pane gestion_des_conges_slide;

    @FXML
    private Pane list_demande_table_pan;

    @FXML
    private Pane list_student_table_pan;

    @FXML
    private Pane menu_slide_left_bar;

    @FXML
    private Pane nombre_demande_pan;

    @FXML
    private Pane nombre_etudiant_pan;

    @FXML
    private TextField search_field_demande;

    @FXML
    private TextField search_field_etudiant;

    @FXML
    private Pane stats_pan;

    @FXML
    private Label text_field_groupID;


    @FXML
    private void handleUploadFile(ActionEvent event) {


    }


}

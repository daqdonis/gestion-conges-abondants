package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Conge;
import com.groupe14ing2.gestioncongesabondants.models.Etudiant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.text.Text;



public class MenuViewController {


    @FXML
    private Button button_ajouter_demande;

    @FXML
    private Button button_traiter_demande;

    @FXML
    private Button gestino_des_cong_button;

    @FXML
    private Button gestion_des_abondant_button;

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

    @FXML
    private Pane table_pan;

    @FXML
    private TextField text_field_rechercher_demande;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox requestsContainer;

    @FXML
    public void initialize() {
        myPieChart.getData().addAll(
                new PieChart.Data("", 20),
                new PieChart.Data("", 40),
                new PieChart.Data("", 12)
        );

        refreshTable();
    }

    public void refreshTable() {
        System.out.println("Refreshing table...");
        try {
            DatabaseController db = new DatabaseController();
            List<Conge> requests = db.getAllCongesWithStudents();

            System.out.println("Received " + requests.size() + " requests from DB");
            requestsContainer.getChildren().clear();

            for (Conge request : requests) {
                try {
                    HBox row = createRequestRow(request);
                    if (!row.getChildren().isEmpty()) {  // Only add non-empty rows
                        requestsContainer.getChildren().add(row);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating row for request " + request.getIdDemande());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error:");
            e.printStackTrace();
        }
    }

    private void loadLeaveRequests() {
        try {
            DatabaseController db = new DatabaseController();
            List<Conge> requests = db.getAllCongesWithStudents();

            requestsContainer.getChildren().clear();

            for (Conge request : requests) {
                HBox requestRow = createRequestRow(request);
                requestsContainer.getChildren().add(requestRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Show error alert
        }
    }

    private HBox createRequestRow(Conge request) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 5;");
        row.setPadding(new Insets(8.0, 8.0, 8.0, 8.0));
        row.setSpacing(20);

        // Handle null student case
        Etudiant etudiant = request.getEtudiant();
        if (etudiant == null) {
            System.out.println("Warning: Found conge without student: " + request.getIdDemande());
            return row;
        }

        // Matricule
        Text matricule = new Text(String.valueOf(etudiant.getIdEtu()));
        matricule.setWrappingWidth(150);
        matricule.setStyle("-fx-font-size: 14px;");

        // Name
        Text nom = new Text(etudiant.getNom());
        nom.setWrappingWidth(150);
        nom.setStyle("-fx-font-size: 14px;");

        // Surname
        Text prenom = new Text(etudiant.getPrenom());
        prenom.setWrappingWidth(150);
        prenom.setStyle("-fx-font-size: 14px;");

        // Status
        Text status = new Text(request.getEtat().toString());
        status.setWrappingWidth(150);
        status.setStyle("-fx-font-size: 14px;");

        // View Button
        Button viewBtn = new Button("View");
        viewBtn.setStyle("-fx-background-color: #1f75ff; -fx-text-fill: white; -fx-font-size: 14px;");
        viewBtn.setOnAction(e -> viewJustification(request));

        // Add all elements to row
        row.getChildren().addAll(matricule, nom, prenom, status, viewBtn);

        return row;
    }
    private void viewJustification(Conge request) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/groupe14ing2/gestioncongesabondants/JustificationViewer.fxml"));
            Parent root = loader.load();

            JustificationViewerController controller = loader.getController();
            controller.loadJustification(request.getIdDemande());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Justification Viewer");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Parent mainRoot; // Tu dois lier fx:id="mainRoot" dans ton FXML principal

    @FXML
    public void traiter_demande() {
        ouvrirFenetreAvecEffet("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml", "Traiter demande");
    }
    @FXML
    public void ajouter_demande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/groupe14ing2/gestioncongesabondants/ajouter-demande.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une demande");

            // Pass reference to this controller
            stage.setUserData(this);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void ouvrirFenetreAvecEffet(String cheminFXML, String titre) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent newRoot = fxmlLoader.load();
            Scene scene = new Scene(newRoot);
            Stage stage = new Stage();
            stage.setTitle(titre);
            stage.setScene(scene);
            //positionner et attacher à la fenêtre principale
            Window mainWindow = mainRoot.getScene().getWindow();
            stage.initOwner(mainWindow);
            stage.initModality(Modality.WINDOW_MODAL); // rend la fenêtre modale
            stage.centerOnScreen();

            // Appliquer un effet de flou
            GaussianBlur blur = new GaussianBlur(10);
            mainRoot.setEffect(blur);

            // Retirer le flou à la fermeture
            stage.setOnHidden(e -> mainRoot.setEffect(null));
            String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/traiter-une-demande.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la fenêtre : " + cheminFXML);
            ex.printStackTrace();
        }
    }

    @FXML
    private void exit(){
        System.exit(0);
    }
}

package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.Conge;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import java.util.stream.Collectors;

public class MenuViewController {

    @FXML private Button button_ajouter_demande;
    @FXML private Button button_traiter_demande;
    @FXML private Button gestino_des_cong_button;
    @FXML private Button gestion_des_abondant_button;
    @FXML private Pane left_bar_menu;
    @FXML private Pane main_background;
    @FXML private Button more_button;
    @FXML private PieChart myPieChart;
    @FXML private Button profile_button;
    @FXML private Pane switch_chap;
    @FXML private Pane table_pan;
    @FXML private TextField text_field_rechercher_demande;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox requestsContainer;

    private List<Conge> allConges; // Store all conges
    private Admin currentAdmin; // Store the current admin user

    public void setAdmin(Admin admin) {
        this.currentAdmin = admin;
    }

    public Admin getAdmin() {
        return this.currentAdmin;
    }

    @FXML
    public void initialize() {
        myPieChart.getData().addAll(
                new PieChart.Data("acp", 0),
                new PieChart.Data("att", 0),
                new PieChart.Data("ref", 0)
        );

        // Add listener to search field
        text_field_rechercher_demande.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDemands(newValue);
        });

        refreshTable();
    }

    private void filterDemands(String matricule) {
        if (allConges == null) return;

        requestsContainer.getChildren().clear();

        // Reset pie chart values
        myPieChart.getData().forEach(data -> data.setPieValue(0));

        List<Conge> filteredConges;
        if (matricule == null || matricule.trim().isEmpty()) {
            filteredConges = allConges; // Show all if no search term
        } else {
            // Filter conges by matricule
            filteredConges = allConges.stream()
                    .filter(conge -> conge.getEtudiant() != null &&
                            String.valueOf(conge.getEtudiant().getIdEtu()).contains(matricule))
                    .collect(Collectors.toList());
        }

        // Display filtered results
        for (Conge conge : filteredConges) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/TupleDemande.fxml"));
                HBox tupleView = loader.load();

                TupleDemandeController tupleController = loader.getController();
                tupleController.setData(conge);

                requestsContainer.getChildren().add(tupleView);

                // Update pie chart
                switch (conge.getEtat()) {
                    case REFUSÉ:
                        myPieChart.getData().get(0).setPieValue(myPieChart.getData().get(0).getPieValue() + 1);
                        break;
                    case ACCEPTÉ:
                        myPieChart.getData().get(2).setPieValue(myPieChart.getData().get(2).getPieValue() + 1);
                        break;
                    case ENATTENTE:
                        myPieChart.getData().get(1).setPieValue(myPieChart.getData().get(1).getPieValue() + 1);
                        break;
                }
            } catch (IOException e) {
                System.err.println("Error loading TupleDemande.fxml for demand " + conge.getIdDemande());
                e.printStackTrace();
            }
        }
    }

    public void refreshTable() {
        System.out.println("Refreshing table...");
        try {
            DatabaseController db = new DatabaseController();
            allConges = db.getAllCongesWithStudents(); // Store all conges
            filterDemands(text_field_rechercher_demande.getText()); // Apply current filter
        } catch (SQLException e) {
            System.err.println("Database error");
            e.printStackTrace();
        }
    }

    private void loadLeaveRequests() {
        try {
            DatabaseController db = new DatabaseController();
            List<Conge> requests = db.getAllCongesWithStudents();

            requestsContainer.getChildren().clear();

            for (Conge request : requests) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("com/groupe14ing2/gestioncongesabondants/TupleDemande.fxml"));
                    HBox tupleView = loader.load(); // charge la vue FXML

                    TupleDemandeController tupleController = loader.getController();
                    tupleController.setData(request); // injecte les données dans la ligne

                    requestsContainer.getChildren().add(tupleView); // ajoute au container
                } catch (IOException e) {
                    System.err.println("Erreur de chargement du TupleDemande.fxml pour la demande " + request.getIdDemande());
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur base de données lors du chargement des demandes");
            e.printStackTrace();
        }
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
    private Parent mainRoot;

    @FXML
    public void traiter_demande() {
        ouvrirFenetreAvecEffet("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml", "Traiter demande");
    }
    public void ajouter_demande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/ajouter-demande.fxml"));
            Parent root = loader.load();

            // Passer la référence du MenuViewController au contrôleur AjouterDemandeController
            AjouterDemandeController controller = loader.getController();
            controller.setMenuController(this);  // Passer la référence du contrôleur MenuViewController

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter une demande");

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void ouvrirFenetreAvecEffet(String cheminFXML, String titre) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent newRoot = fxmlLoader.load();
            ((TraiterDemandeController)fxmlLoader.getController()).setMenuController(this);
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

package com.groupe14ing2.gestioncongesabondants.controllers;

import com.groupe14ing2.gestioncongesabondants.models.Admin;
import com.groupe14ing2.gestioncongesabondants.models.Conge;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
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
    @FXML private Parent mainRoot;
    @FXML private Button return_button;
    @FXML private VBox slid_sbox;
    @FXML private Pane mainRoot_profile;
    @FXML private Button Profile_GestionDesComptButton;
    @FXML private Button Profile_Logout_button;
    @FXML private Label PROFILE_userName_Lable;
    @FXML private Text nom;
    @FXML private Text prenom;
    @FXML private Text email;

    private List<Conge> allConges;
    private Admin currentAdmin;

    @FXML
    public void initialize() {
        myPieChart.setLabelsVisible(false);
        updatePieChart(0, 0, 0);
        text_field_rechercher_demande.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDemands(newValue);
        });
        refreshTable();
        
        // Add event handler for Gestion Des Comptes button
        Profile_GestionDesComptButton.setOnAction(event -> {
            try {
                switchToComptesMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to switch to Gestion Des Comptes view");
            }
        });
        
        // Add event handler for Logout button
        Profile_Logout_button.setOnAction(event -> {
            try {
                logout(event);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to logout");
            }
        });

        // Set profile information if admin is already set
        if (currentAdmin != null) {
            if (PROFILE_userName_Lable != null) {
                PROFILE_userName_Lable.setText(currentAdmin.getNom() + " " + currentAdmin.getPrenom());
            }
            if (nom != null) {
                nom.setText(currentAdmin.getNom());
            }
            if (prenom != null) {
                prenom.setText(currentAdmin.getPrenom());
            }
            if (email != null) {
                email.setText(currentAdmin.getEmail());
            }
        }
    }

    public void setAdmin(Admin admin) {
        this.currentAdmin = admin;
        // Load the abandonment management view after admin is set
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/Menu-Gestion-Abandonment.fxml"));

            Pane a = fxmlLoader.load();
            MenuGestionAbdandenementController tupleController = fxmlLoader.getController();
            tupleController.setMenuController(this);
            tupleController.setAdmin(this.currentAdmin);
            slid_sbox.getChildren().add(a);

            // Update profile information
            if (PROFILE_userName_Lable != null) {
                PROFILE_userName_Lable.setText(admin.getNom() + " " + admin.getPrenom());
            }
            if (nom != null) {
                nom.setText(admin.getNom());
            }
            if (prenom != null) {
                prenom.setText(admin.getPrenom());
            }
            if (email != null) {
                email.setText(admin.getEmail());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Admin getAdmin() {
        return this.currentAdmin;
    }

    private void updatePieChart(int accepteCount, int enAttenteCount, int refuseCount) {
        myPieChart.getData().clear();

        PieChart.Data accepteData = new PieChart.Data("Accepté", accepteCount);
        PieChart.Data enAttenteData = new PieChart.Data("En attente", enAttenteCount);
        PieChart.Data refuseData = new PieChart.Data("Refusé", refuseCount);

        myPieChart.getData().addAll(accepteData, enAttenteData, refuseData);

        if (!myPieChart.getData().isEmpty()) {
            myPieChart.getData().get(0).getNode().setStyle("-fx-pie-color: #32CD32;");
            myPieChart.getData().get(1).getNode().setStyle("-fx-pie-color: #FFA500;");
            myPieChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        }
    }

    private void filterDemands(String matricule) {
        if (allConges == null) return;

        requestsContainer.getChildren().clear();
        int accepteCount = 0;
        int enAttenteCount = 0;
        int refuseCount = 0;

        List<Conge> filteredConges = matricule == null || matricule.trim().isEmpty() ?
                allConges :
                allConges.stream()
                        .filter(conge -> conge.getEtudiant() != null &&
                                String.valueOf(conge.getEtudiant().getIdEtu()).contains(matricule))
                        .collect(Collectors.toList());

        for (Conge conge : filteredConges) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/TupleDemande.fxml"));
                HBox tupleView = loader.load();
                TupleDemandeController tupleController = loader.getController();
                tupleController.setMenuController(this);
                tupleController.setData(conge);
                requestsContainer.getChildren().add(tupleView);

                switch (conge.getEtat()) {
                    case ACCEPTÉ: accepteCount++; break;
                    case ENATTENTE: enAttenteCount++; break;
                    case REFUSÉ: refuseCount++; break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final int finalAccepteCount = accepteCount;
        final int finalEnAttenteCount = enAttenteCount;
        final int finalRefuseCount = refuseCount;

        Platform.runLater(() -> updatePieChart(finalAccepteCount, finalEnAttenteCount, finalRefuseCount));
    }

    public void refreshTable() {
        Platform.runLater(() -> {
            try {
                requestsContainer.getChildren().clear();
                DatabaseController dbController = new DatabaseController();
                allConges = dbController.getAllCongesWithStudents();

                int accepteCount = 0;
                int enAttenteCount = 0;
                int refuseCount = 0;

                for (Conge conge : allConges) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/TupleDemande.fxml"));

                    try {
                        HBox hBox = fxmlLoader.load();
                        TupleDemandeController tupleController = fxmlLoader.getController();
                        tupleController.setMenuController(this);
                        tupleController.setData(conge);
                        requestsContainer.getChildren().add(hBox);

                        switch (conge.getEtat()) {
                            case ACCEPTÉ: accepteCount++; break;
                            case ENATTENTE: enAttenteCount++; break;
                            case REFUSÉ: refuseCount++; break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                updatePieChart(accepteCount, enAttenteCount, refuseCount);

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to refresh table: " + e.getMessage());
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void traiter_demande() {
        ouvrirFenetreAvecEffet("/com/groupe14ing2/gestioncongesabondants/traiter-une-demande.fxml", "Traiter demande");
    }

    public void ajouter_demande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/ajouter-demande.fxml"));
            Parent root = loader.load();
            AjouterDemandeController controller = loader.getController();
            controller.setMenuController(this);

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

            Window mainWindow = mainRoot.getScene().getWindow();
            stage.initOwner(mainWindow);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.centerOnScreen();

            GaussianBlur blur = new GaussianBlur(10);
            mainRoot.setEffect(blur);
            stage.setOnHidden(e -> mainRoot.setEffect(null));

            String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/traiter-une-demande.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }


    @FXML
    public void switchScene2 () {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.3), slid_sbox);
        slide.setToY(0);
        slide.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.seconds(0.3), switch_chap);
        slide2.setToY(0);
        slide2.play();
    }

    @FXML
    public void switchScene1 () {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.3), slid_sbox);
        slide.setToY(-750);
        slide.play();
        TranslateTransition slide2 = new TranslateTransition(Duration.seconds(0.3), switch_chap);
        slide2.setToY(51);
        slide2.play();
    }
    @FXML
    public void Profile_button () {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.2), mainRoot_profile);
        slide.setToX(255);
        slide.play();
    }
    @FXML
    public void Return_button () {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.2), mainRoot_profile);
        slide.setToX(0);
        slide.play();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/login-view.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void switchToComptesMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/groupe14ing2/gestioncongesabondants/Gestion-Comptes.fxml"));
        Parent root = loader.load();

        // Get the controller and set the admin
        GestionComptesController controller = loader.getController();
        if (controller != null && currentAdmin != null) {
            controller.setAdmin(currentAdmin);
        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        
        String css = getClass().getResource("/com/groupe14ing2/gestioncongesabondants/style/Menu_stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}

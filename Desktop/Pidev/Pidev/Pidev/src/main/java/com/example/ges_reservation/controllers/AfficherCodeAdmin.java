package com.example.ges_reservation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.example.ges_reservation.models.CodePromo;
import com.example.ges_reservation.services.PromoService;
import com.example.ges_reservation.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AfficherCodeAdmin {

    @FXML
    private ListView<CodePromo> LVcmd;
    @FXML
    private LineChart<String, Number> CodePie;

    private PromoService promoService;

    public AfficherCodeAdmin() {
        try {
            promoService = new PromoService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            List<CodePromo> codesPromo = promoService.recupperer();

            // Afficher les données dans la ListView
            afficherDonneesDansListView(codesPromo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherDonneesDansListView(List<CodePromo> codesPromo) {
        ObservableList<CodePromo> codePromoList = FXCollections.observableArrayList(codesPromo);

        LVcmd.setItems(codePromoList);

        LVcmd.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CodePromo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    String formattedText = String.format("ID: %s%nCode: %s%nDate exp: %s%nUtilisé: %s%nUser ID: %s",
                            item.getId(), item.getCode(), item.getDate_exp(), item.getUtilise(), item.getUser_id());
                    setText(formattedText);
                    setStyle("-fx-background-color: #9d9797; -fx-padding: 10px;");
                }
            }
        });
    }



    @FXML
    void ModifierCodeAdmin(ActionEvent actionEvent) {
        CodePromo selectedCode = LVcmd.getSelectionModel().getSelectedItem();
        if (selectedCode == null) {
            afficherAlerte("Aucun code promo sélectionné", "Veuillez sélectionner un code promo à modifier.");
            return;
        }

        int codeId = selectedCode.getId();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la modification");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce code promo ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCodeAdmin.fxml"));
                Parent root = loader.load();
                com.example.ges_reservation.controllers.ModifierCodeAdmin controller = loader.getController();
                controller.initData(codeId); // Passer l'ID du code promo au contrôleur de la fenêtre de modification
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void SupprimerCodeAdmin(ActionEvent event) {
        CodePromo selectedCode = LVcmd.getSelectionModel().getSelectedItem();
        if (selectedCode == null) {
            afficherAlerte("Aucun code promo sélectionné", "Veuillez sélectionner un code promo à supprimer.");
            return;
        }

        int codeId = selectedCode.getId();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce code promo ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                promoService.supprimer(codeId);

                initialize(); // Rafraîchir l'affichage après la suppression

                afficherAlerte("Suppression réussie", "Le code promo a été supprimé avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur de suppression", "Une erreur s'est produite lors de la suppression du code promo.");
            }
        }
    }

    @FXML
    void BackToCodeAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test.fxml"));
        try {
            LVcmd.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }



}

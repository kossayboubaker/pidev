package com.example.ges_reservation.controllers;
import com.example.ges_reservation.models.Sieges;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.SiegeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherSiege {
    @FXML
    private ListView<Sieges> ListSiege;

    private final SiegeService ps = new SiegeService();

    //afficher les Siéges
    @FXML
    public void initialize() {
        try {
            List<Sieges> sieges = ps.recuperer();
        ObservableList<Sieges> observableList = FXCollections.observableArrayList(sieges);
            ListSiege.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }








    @FXML
    void modifS(ActionEvent event) {
        // Récupérer le siège sélectionné dans la ListView
        Sieges siegeSelectionne = ListSiege.getSelectionModel().getSelectedItem();

        if (siegeSelectionne != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierSiege.fxml"));
            Parent root;
            try {
                root = loader.load();
                modifierSiege controller = loader.getController();
                controller.initData(siegeSelectionne);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                // Mettre à jour la ListView après la modification
                List<Sieges> sieges = ps.recuperer(); // Récupérez la liste mise à jour
                ObservableList<Sieges> observableList = FXCollections.observableArrayList(sieges);
                ListSiege.setItems(observableList); // Mettez à jour les données affichées
            } catch (IOException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la modification");
                alert.setContentText("Une erreur s'est produite lors de la modification du siège : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un siège à modifier.");
            alert.showAndWait();
        }
    }
    @FXML
    void suppS(ActionEvent event) {
        // Récupérer le siège sélectionné dans la ListView
        Sieges siegeSelectionne = ListSiege.getSelectionModel().getSelectedItem();

        if (siegeSelectionne != null) {
            // Demander une confirmation à l'utilisateur avant de supprimer le siège
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Voulez-vous vraiment supprimer ce siège ?");
            confirmation.setContentText("Cette action est irréversible.");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ps.supprimer(siegeSelectionne.getSiegeID());

                    // Actualiser la liste des sièges affichée dans la ListView
                    List<Sieges> sieges = ps.recuperer();
                    ObservableList<Sieges> observableList = FXCollections.observableArrayList(sieges);
                    ListSiege.setItems(observableList);

                    // Afficher une notification de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setContentText("Le siège a été supprimé avec succès !");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // En cas d'erreur lors de la suppression, afficher une alerte d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de la suppression");
                    alert.setContentText("Une erreur s'est produite lors de la suppression du siège : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Afficher une alerte si aucun siège n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un siège à supprimer.");
            alert.showAndWait();
        }

}

    public void retourner(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterSiege.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ListSiege.getScene().getWindow();
            stage.close();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
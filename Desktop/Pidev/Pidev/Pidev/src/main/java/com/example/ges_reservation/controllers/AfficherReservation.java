package com.example.ges_reservation.controllers;
import com.example.ges_reservation.controllers.modifierReservation;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.services.ReservationService;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AfficherReservation {
    public TextField RecherchID;
    @FXML
    private ListView<Reservations> ListRes;
    private final ReservationService ps = new ReservationService();


    //afficher les reservations
    @FXML
    public void initialize() {
        try {
            List<Reservations> reservations = ps.recuperer();
            ObservableList<Reservations> observableList = FXCollections.observableArrayList(reservations);
            ListRes.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

// supprision reservation

    @FXML
    public void supprimerR(javafx.event.ActionEvent actionEvent) {
        Reservations reservationSelectionnee = ListRes.getSelectionModel().getSelectedItem();
        if (reservationSelectionnee != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Voulez-vous vraiment supprimer cette réservation ?");
            confirmation.setContentText("Cette action est irréversible.");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int idReservation = reservationSelectionnee.getReservationID();
                    ps.supprimer(idReservation);
                    // Mettez à jour  ListView
                    List<Reservations> reservations = ps.recuperer();
                    ObservableList<Reservations> observableList = FXCollections.observableArrayList(reservations);
                    ListRes.setItems(observableList);

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de la suppression");
                    alert.setContentText("Une erreur s'est produite lors de la suppression de la réservation : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une réservation à supprimer.");
            alert.showAndWait();
        }
    }

    public void modifierR(ActionEvent actionEvent) {
        Reservations reservationSelectionnee = ListRes.getSelectionModel().getSelectedItem();
        if (reservationSelectionnee != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierReservation.fxml"));
            Parent root;
            try {
                root = loader.load();
                modifierReservation controller = loader.getController();
                controller.init(reservationSelectionnee);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                // Mettez à jour votre ListView après la modification
                List<Reservations> reservations = ps.recuperer();
                ObservableList<Reservations> observableList = FXCollections.observableArrayList(reservations);
                ListRes.setItems(observableList); // Mettez à jour les données affichées
            } catch (IOException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la modification");
                alert.setContentText("Une erreur s'est produite lors de la modification de la réservation : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une réservation à modifier.");
            alert.showAndWait();
        }
    }

    public void retourner(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ListRes.getScene().getWindow();
            stage.close();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chercherID(ActionEvent actionEvent) {
        String idText = RecherchID.getText();
        if (!idText.isEmpty()) {
            try {
                int searchID = Integer.parseInt(idText);
                List<Reservations> matchingReservations = new ArrayList<>();
                for (Reservations reservation : ListRes.getItems()) {
                    if (String.valueOf(reservation.getReservationID()).contains(idText)) {
                        matchingReservations.add(reservation);
                    }
                }
                if (!matchingReservations.isEmpty()) {
                    ObservableList<Reservations> items = FXCollections.observableArrayList(matchingReservations);
                    ListRes.setItems(items);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Réservation non trouvée");
                    alert.setContentText("Aucune réservation correspondante n'a été trouvée pour l'ID : " + idText);
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir un ID de réservation valide.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setContentText("Veuillez saisir un ID de réservation.");
            alert.showAndWait();
        }
    }

}
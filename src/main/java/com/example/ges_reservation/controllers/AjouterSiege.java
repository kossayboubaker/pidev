package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.ReservationService;
import com.example.ges_reservation.services.SiegeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterSiege {
    @FXML
    private TextField numeroSiege;

    @FXML
    private TextField reservationID;

    @FXML
    private TextField statut;

    private final SiegeService ps = new SiegeService();

    @FXML
    void AjoutS(ActionEvent event) {
        // Récupérer les valeurs des champs de texte
        String numero = numeroSiege.getText();
        String idReservation = reservationID.getText();
        String status = statut.getText();

        // Vérifier si les champs sont vides
        if (numero.isEmpty() || idReservation.isEmpty() || status.isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur que tous les champs doivent être remplis
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        } else {
            // Vérifier si le statut est valide ("disponible" ou "non disponible")
            if (status.matches("(disponible|non disponible)")) {
                try {
                    // Ajouter le siège en utilisant le service SiegeService
                    ps.ajouter(new Sieges(4, Integer.parseInt(idReservation), numero, status));

                    // Réinitialiser les champs de texte
                    numeroSiege.clear();
                    reservationID.clear();
                    statut.clear();

                    // Afficher une confirmation que le siège a été ajouté avec succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Siège ajouté");
                    alert.setHeaderText(null);
                    alert.setContentText("Le siège a été ajouté avec succès.");
                    alert.showAndWait();
                } catch (NumberFormatException e) {
                    // Afficher une alerte si les valeurs saisies ne sont pas valides
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir des valeurs valides pour le numéro de siège et l'identifiant de réservation.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // Afficher une alerte en cas d'erreur lors de l'ajout du siège
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Une erreur s'est produite lors de l'ajout du siège : " + e.getMessage());
                    alert.showAndWait();
                }
            } else {
                // Afficher une alerte si le statut n'est pas valide
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Statut invalide");
                alert.setHeaderText(null);
                alert.setContentText("Le statut du siège doit être 'disponible' ou 'non disponible'.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void afficheS(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherSiege.fxml"));
            Stage window = (Stage) numeroSiege.getScene().getWindow();

            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

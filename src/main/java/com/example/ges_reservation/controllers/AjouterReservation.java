package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.ReservationService;
import com.example.ges_reservation.services.SiegeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static java.sql.Date.valueOf;

public class AjouterReservation {
    private SideBarFXMLController sideBarController;

    @FXML
    private DatePicker DateReservationTF;

    @FXML
    private TextField HeureReservationTF;



    @FXML
    private TextField NombrePlacesReserveesTF;

    @FXML
    private TextField nomfilmTF;


    @FXML
    private TextField numeroSiege;

    @FXML
    private TextField reservationID;

    @FXML
    private TextField statut;


    private final SiegeService ps = new SiegeService();



    private final ReservationService Rs = new ReservationService();

    @FXML
    void AjouterR(ActionEvent event) {
        try {
            // Vérifier si tous les champs sont remplis
            if (DateReservationTF.getValue() == null || HeureReservationTF.getText().isEmpty() || nomfilmTF.getText().isEmpty() || NombrePlacesReserveesTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champs vides");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time heureReservation = new Time(timeFormat.parse(HeureReservationTF.getText()).getTime());

            Rs.ajouterResBack(new Reservations(Integer.parseInt(nomfilmTF.getText()), valueOf(DateReservationTF.getValue()), heureReservation,0, Integer.parseInt(NombrePlacesReserveesTF.getText())));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réservation réussie");
            alert.setContentText("La réservation a été effectuée avec succès !");
            alert.showAndWait();
        } catch (ParseException | NumberFormatException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherReservation.fxml"));
            Stage window = (Stage) DateReservationTF.getScene().getWindow();

            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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

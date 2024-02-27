package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.services.ReservationService;
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
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static java.sql.Date.valueOf;

public class AjouterReservation {

    @FXML
    private DatePicker DateReservationTF;

    @FXML
    private TextField HeureReservationTF;

    @FXML
    private TextField IDUtilisateurTF;

    @FXML
    private TextField NombrePlacesReserveesTF;

    @FXML
    private TextField nomfilmTF;



    private final ReservationService ps = new ReservationService();

    @FXML
    void AjouterR(ActionEvent event) {
        try {
            // Vérifier si tous les champs sont remplis
            if (DateReservationTF.getValue() == null || HeureReservationTF.getText().isEmpty() || IDUtilisateurTF.getText().isEmpty() || nomfilmTF.getText().isEmpty() || NombrePlacesReserveesTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champs vides");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time heureReservation = new Time(timeFormat.parse(HeureReservationTF.getText()).getTime());

            ps.ajouter(new Reservations(Integer.parseInt(nomfilmTF.getText()),Integer.parseInt(IDUtilisateurTF.getText()), valueOf(DateReservationTF.getValue()), heureReservation, Integer.parseInt(NombrePlacesReserveesTF.getText())));
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

}

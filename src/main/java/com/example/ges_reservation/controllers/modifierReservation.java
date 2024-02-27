package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class modifierReservation {

    @FXML
    private TextField DateReservationTF;

    @FXML
    private TextField HeureReservationTF;

    @FXML
    private TextField IDUtilisateurTF;

    @FXML
    private TextField NombrePlacesReserveesTF;

    @FXML
    private TextField nomfilmTF;

    private final ReservationService ps = new ReservationService();

    private Reservations reservation;
    public void init(Reservations reservation) {
        this.reservation = reservation;
        // Initialiser les champs avec les valeurs de la réservation actuelle
        DateReservationTF.setText(reservation.getDateReservation().toString());
        HeureReservationTF.setText(reservation.getHeureReservation().toString());
        IDUtilisateurTF.setText(String.valueOf(reservation.getUtilisateurID()));
        NombrePlacesReserveesTF.setText(String.valueOf(reservation.getNombrePlacesReservees()));
        nomfilmTF.setText(String.valueOf(reservation.getFilmID()));
    }
    @FXML
    void enregistrerModification(ActionEvent event) {

        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time heureReservation = new Time(timeFormat.parse(HeureReservationTF.getText()).getTime());

            // Mettre à jour les valeurs de la réservation avec les nouvelles données
            reservation.setDateReservation(java.sql.Date.valueOf(LocalDate.parse(DateReservationTF.getText())));
            reservation.setHeureReservation(heureReservation);
            reservation.setUtilisateurID(Integer.parseInt(IDUtilisateurTF.getText()));
            reservation.setNombrePlacesReservees(Integer.parseInt(NombrePlacesReserveesTF.getText()));
            reservation.setFilmID(Integer.parseInt(nomfilmTF.getText()));

            ps.modifier(reservation);

            // Afficher une notification de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setContentText("La réservation a été modifiée avec succès !");
            alert.showAndWait();
        } catch (ParseException | NumberFormatException | SQLException e) {
            // En cas d'erreur, afficher une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de la modification");
            alert.setContentText("Une erreur s'est produite lors de la modification de la réservation : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void retourner(ActionEvent actionEvent) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) DateReservationTF.getScene().getWindow();
        stage.close();
    }

}
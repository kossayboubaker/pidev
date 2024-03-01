package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.services.ReservationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AjouterFrontRes {

    @FXML
    private ComboBox<String> nomFilmCombo; // Renommé idfilm en nomFilmCombo pour refléter le changement

    @FXML
    private ComboBox<String> dateresf;

    @FXML
    private ComboBox<String> heureResf;

    @FXML
    private TextField nbreplacef;

    @FXML
    private Button validerButton;

    private ReservationService reservationService;

    public AjouterFrontRes() {
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        try {
            // Charger les noms de films dans le ComboBox nomFilmCombo
            List<String> filmNames = reservationService.getAllFilmNames();
            nomFilmCombo.setItems(FXCollections.observableArrayList(filmNames));

            // Gérer le changement de sélection dans nomFilmCombo pour charger les dates correspondantes dans dateresf
            nomFilmCombo.setOnAction(event -> {
                try {
                    String selectedFilmName = nomFilmCombo.getValue(); // Nom du film sélectionné
                    int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName); // Obtenir l'ID du film
                    List<String> dates = reservationService.getAllDatesForFilm(selectedFilmID); // Obtenir les dates pour ce film
                    dateresf.setItems(FXCollections.observableArrayList(dates)); // Mettre à jour la liste des dates
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérer l'exception correctement
                }
            });

            // Gérer le changement de sélection dans dateresf pour charger les heures correspondantes dans heureResf
            dateresf.setOnAction(event -> {
                try {
                    String selectedFilmName = nomFilmCombo.getValue(); // Nom du film sélectionné
                    int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName); // Obtenir l'ID du film
                    String selectedDate = dateresf.getValue(); // Date sélectionnée
                    List<String> heures = reservationService.getAllHeuresForFilmAndDate(selectedFilmID, selectedDate); // Obtenir les heures pour ce film et cette date
                    heureResf.setItems(FXCollections.observableArrayList(heures)); // Mettre à jour la liste des heures
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérer l'exception correctement
                }
            });
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }

    @FXML
    public void valider(ActionEvent event) {
        try {
            String selectedFilmName = nomFilmCombo.getValue();
            int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
            String selectedDate = dateresf.getValue();
            String selectedHeure = heureResf.getValue();
            int nombrePlaces = Integer.parseInt(nbreplacef.getText());

            // Vérifier d'abord si le nombre de places est valide
            int placesDisponibles = reservationService.getNombrePlacesDisponibles(selectedFilmID, selectedDate, selectedHeure);
            if (nombrePlaces > placesDisponibles) {
                // Afficher un message d'erreur ou gérer le dépassement de capacité d'une autre manière
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Places insuffisantes");
                alert.setHeaderText(null);
                alert.setContentText("Le nombre de places disponibles est insuffisant.");
                alert.showAndWait();
                return;
            }

            Reservations reservation = new Reservations();
            reservation.setFilmID(selectedFilmID);
            reservation.setDateReservation(new java.sql.Date(new Date().getTime()));
            reservation.setHeureReservation(java.sql.Time.valueOf(selectedHeure));
            reservation.setNombrePlacesReservees(nombrePlaces);

            reservationService.ajouterResBack(reservation);

            reservationService.decrementerNombrePlaces(selectedFilmID, selectedDate, selectedHeure, nombrePlaces);

        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }
}

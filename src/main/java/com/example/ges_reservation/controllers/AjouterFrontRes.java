package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AjouterFrontRes {

    @FXML
    private ComboBox<String> nomFilmCombo;

    @FXML
    private ComboBox<String> dateresf;

    @FXML
    private ComboBox<String> heureResf;

    @FXML
    private TextField nbreplacef;

    @FXML
    private ListView<String> NumsSieges;

    private ReservationService reservationService;

    private static final String WARNING_TITLE = "Avertissement";
    private static final String ERROR_TITLE = "Erreur";

    public AjouterFrontRes() {
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        try {
            List<String> filmNames = reservationService.getAllFilmNames();
            nomFilmCombo.setItems(FXCollections.observableArrayList(filmNames));

            nomFilmCombo.setOnAction(event -> {
                try {
                    updateDatesForSelectedFilm();
                    updateSiegeNumbersWithReservationStatus();
                } catch (SQLException e) {
                    showErrorAlert(ERROR_TITLE, "Erreur lors de la récupération des dates pour le film sélectionné.");
                }
            });

            dateresf.setOnAction(event -> {
                try {
                    updateHeuresForSelectedDate();
                    updateSiegeNumbersWithReservationStatus();
                } catch (SQLException e) {
                    showErrorAlert(ERROR_TITLE, "Erreur lors de la récupération des heures pour la date sélectionnée.");
                }
            });

            heureResf.setOnAction(event -> updateSiegeNumbersWithReservationStatus());

            NumsSieges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (SQLException e) {
            showErrorAlert(ERROR_TITLE, "Erreur lors de l'initialisation de la page.");
        }
    }

    @FXML
    public void valider(ActionEvent event) {
        try {
            String selectedFilmName = nomFilmCombo.getValue();
            if (selectedFilmName == null) {
                showAlert(WARNING_TITLE, "Sélectionner un film", "Veuillez sélectionner un film.");
                return;
            }

            int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);

            String selectedDate = dateresf.getValue();
            if (selectedDate == null) {
                showAlert(WARNING_TITLE, "Sélectionner une date", "Veuillez sélectionner une date.");
                return;
            }
            String selectedHeure = heureResf.getValue();
            if (selectedHeure == null) {
                showAlert(WARNING_TITLE, "Sélectionner une heure", "Veuillez sélectionner une heure.");
                return;
            }

            int nombrePlaces;
            try {
                nombrePlaces = Integer.parseInt(nbreplacef.getText());
            } catch (NumberFormatException e) {
                showAlert(WARNING_TITLE, "Nombre de places invalide", "Veuillez entrer un nombre valide pour le nombre de places.");
                return;
            }

            int placesDisponibles = reservationService.getNombrePlacesDisponibles(selectedFilmID, selectedDate, selectedHeure);
            if (nombrePlaces > placesDisponibles) {
                showAlert(WARNING_TITLE, "Places insuffisantes", "Le nombre de places disponibles est insuffisant.");
                return;
            }

            List<String> selectedSiegeNumbers = NumsSieges.getSelectionModel().getSelectedItems();
            if (selectedSiegeNumbers.size() != nombrePlaces) {
                showAlert(WARNING_TITLE, "Sélection de sièges incorrecte", "Le nombre de sièges sélectionnés ne correspond pas au nombre de places à réserver.");
                return;
            }

            for (String selectedSiegeNumber : selectedSiegeNumbers) {
                if (selectedSiegeNumber.endsWith("(Réservé)")) {
                    showAlert(WARNING_TITLE, "Siège déjà réservé", "Au moins un des sièges sélectionnés est déjà réservé.");
                    return;
                }
            }

            for (String selectedSiegeNumber : selectedSiegeNumbers) {
                reservationService.marquerSiegeIndisponible(selectedSiegeNumber);
            }

            for (int i = 0; i < nombrePlaces; i++) {
                Reservations reservation = new Reservations();
                reservation.setFilmID(selectedFilmID);
                reservation.setDateReservation(new java.sql.Date(new Date().getTime()));
                reservation.setHeureReservation(java.sql.Time.valueOf(selectedHeure));
                reservation.setNombrePlacesReservees(nombrePlaces);
                reservationService.ajouterResBack(reservation);
            }

            reservationService.decrementerNombrePlaces(selectedFilmID, selectedDate, selectedHeure, nombrePlaces);
            updateSiegeNumbersWithReservationStatus();
        } catch (SQLException e) {
            showErrorAlert(ERROR_TITLE, "Erreur lors de la validation de la réservation.");
        }
    }

    private void updateDatesForSelectedFilm() throws SQLException {
        String selectedFilmName = nomFilmCombo.getValue();
        int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
        List<String> dates = reservationService.getAllDatesForFilm(selectedFilmID);
        dateresf.setItems(FXCollections.observableArrayList(dates));
    }

    private void updateHeuresForSelectedDate() throws SQLException {
        String selectedFilmName = nomFilmCombo.getValue();
        int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
        String selectedDate = dateresf.getValue();
        List<String> heures = reservationService.getAllHeuresForFilmAndDate(selectedFilmID, selectedDate);
        heureResf.setItems(FXCollections.observableArrayList(heures));
    }

    private void updateSiegeNumbersWithReservationStatus() {
        try {
            String selectedFilmName = nomFilmCombo.getValue();
            int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
            String selectedDate = dateresf.getValue();
            String selectedHeure = heureResf.getValue();

            List<String> siegeNumbers = reservationService.getSiegeNumbersForFilmDateAndTime(selectedFilmID, selectedDate, selectedHeure);

            ObservableList<String> updatedSiegeNumbers = FXCollections.observableArrayList();
            for (String siegeNumber : siegeNumbers) {
                if (reservationService.isSiegeIndisponible(selectedFilmID, selectedDate, selectedHeure, siegeNumber)) {
                    updatedSiegeNumbers.add(siegeNumber + " (Réservé)");
                } else {
                    updatedSiegeNumbers.add(siegeNumber);
                }
            }
            NumsSieges.setItems(updatedSiegeNumbers);
        } catch (SQLException e) {
            showErrorAlert(ERROR_TITLE, "Erreur lors de la mise à jour des sièges réservés.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void afficherImage(MouseEvent mouseEvent) {

        try {
            // Charger l'image à afficher
            Image image = new Image(getClass().getResourceAsStream("/images/salle.png"));

            // Créer une nouvelle fenêtre pour afficher l'image
            Stage stage = new Stage();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
            Scene scene = new Scene(new Group(imageView));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

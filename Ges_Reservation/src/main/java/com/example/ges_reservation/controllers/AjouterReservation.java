package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.ReservationService;
import com.example.ges_reservation.services.SiegeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Date.valueOf;

public class AjouterReservation {
    @FXML
    private DatePicker DateReservationTF;

    @FXML
    private TextField HeureReservationTF;

    @FXML
    private TextField NombrePlacesReserveesTF;

    @FXML
    private ComboBox<String> nomFilmCombo;

    @FXML
    private TextField reservationID;

    @FXML
    private TextField statut;

    private final SiegeService siegeService = new SiegeService();
    private final ReservationService reservationService = new ReservationService();

    @FXML
    public void initialize() {
        try {
            // Charger les noms de film dans le ComboBox nomFilmCombo
            List<String> filmNames = reservationService.getAllFilmNames();
            nomFilmCombo.setItems(FXCollections.observableArrayList(filmNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AjouterR(ActionEvent event) {
        try {
            // Vérifier si tous les champs sont remplis
            if (DateReservationTF.getValue() == null || HeureReservationTF.getText().isEmpty() || nomFilmCombo.getValue() == null || NombrePlacesReserveesTF.getText().isEmpty()) {
                afficherAlerte("Champs vides", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
                return;
            }
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Time heureReservation = new Time(timeFormat.parse(HeureReservationTF.getText()).getTime());

            // Récupérer l'ID du film sélectionné
            String nomFilm = nomFilmCombo.getValue();
            int filmID = reservationService.getFilmIDByName(nomFilm);

            // Ajouter la réservation avec l'ID du film
            reservationService.ajouterResBack(new Reservations(filmID, valueOf(DateReservationTF.getValue()), heureReservation, 0, Integer.parseInt(NombrePlacesReserveesTF.getText())));

            afficherAlerte("Réservation réussie", "La réservation a été effectuée avec succès !", Alert.AlertType.INFORMATION);
        } catch (ParseException | NumberFormatException | SQLException e) {
            afficherAlerte("Erreur", e.getMessage(), Alert.AlertType.ERROR);
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
        String idReservation = reservationID.getText();
        String status = statut.getText();

        // Vérifier si les champs sont vides
        if (idReservation.isEmpty() || status.isEmpty()) {
            afficherAlerte("Champs vides", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
        } else {
            if (status.matches("(disponible|non disponible)")) {
                try {
                    // Récupérer le nombre de places disponibles
                    int nombrePlacesDisponibles = reservationService.getNombrePlacessDisponibles(Integer.parseInt(idReservation));

                    // Générer les numéros de siège
                    List<String> numerosSieges = genererNumerosSieges(nombrePlacesDisponibles);

                    for (String numero : numerosSieges) {
                        siegeService.ajouter(new Sieges(4, Integer.parseInt(idReservation), numero, status));
                    }

                    // Réinitialiser les champs de texte
                    reservationID.clear();
                    statut.clear();

                    afficherAlerte("Sièges ajoutés", "Les sièges ont été ajoutés avec succès.", Alert.AlertType.INFORMATION);
                } catch (NumberFormatException e) {
                    afficherAlerte("Erreur", "Veuillez saisir un identifiant de réservation valide.", Alert.AlertType.ERROR);
                } catch (SQLException e) {
                    // Afficher une alerte en cas d'erreur lors de l'ajout des sièges
                    afficherAlerte("Erreur", "Une erreur s'est produite lors de l'ajout des sièges : " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                afficherAlerte("Statut invalide", "Le statut du siège doit être 'disponible' ou 'non disponible'.", Alert.AlertType.WARNING);
            }
        }
    }

    // Méthode pour générer les numéros de siège
    private List<String> genererNumerosSieges(int nombrePlacesDisponibles) {
        List<String> numerosSieges = new ArrayList<>();
        char[] lettres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        int siegesParLigne = 10;
        int nombreDeLignes = (int) Math.ceil((double) nombrePlacesDisponibles / siegesParLigne);

        int nombrePlacesRestantes = nombrePlacesDisponibles;
        for (int i = 0; i < nombreDeLignes; i++) {
            for (char lettre : lettres) {
                for (int j = 1; j <= siegesParLigne && nombrePlacesRestantes > 0; j++) {
                    numerosSieges.add(String.valueOf(lettre) + j);
                    nombrePlacesRestantes--;
                }
            }
        }

        return numerosSieges;
    }



    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}

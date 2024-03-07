package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.example.ges_reservation.models.cinema;
import com.example.ges_reservation.services.cinemaservice;

import java.sql.SQLException;

public class Ajoutercinema {
    cinemaservice cs = new cinemaservice();

    @FXML
    private ListView<cinema> listcinema;

    @FXML
    private TextField etat;

    @FXML
    private TextField id;


    @FXML
    private TextField nbsalle;

    @FXML
    private TextField nom;

    @FXML
    private TextField place;


    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Récupération des données saisies dans les champs de texte
            int nombreSalles;
            String placeCinema = place.getText();
            String etatCinema = etat.getText();
            String nomCinema = nom.getText();

            // Vérification des champs vides
            if (placeCinema.isEmpty() || etatCinema.isEmpty() || nomCinema.isEmpty()) {
                afficherErreur("Erreur", "Veuillez remplir tous les champs.");
                return; // Sortir de la méthode
            }

            // Vérification du format du nombre de salles
            if (nbsalle.getText().isEmpty()) {
                afficherErreur("Erreur", "Veuillez saisir le nombre de salles.");
                return; // Sortir de la méthode
            } else {
                nombreSalles = Integer.parseInt(nbsalle.getText());
            }

            // Boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir ajouter ces informations ?");

            ButtonType buttonYes = new ButtonType("Oui");
            ButtonType buttonNo = new ButtonType("Non");

            confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == buttonYes) {
                    try {
                        // Ajout de l'élément
                        cs.ajouter(new cinema(nombreSalles, placeCinema, etatCinema, nomCinema));

                        // Affichage d'un message de confirmation
                        afficherMessage("Succès", "L'ajout a été effectué avec succès.");
                    } catch (SQLException e) {
                        // Affichage d'une boîte de dialogue d'erreur en cas d'échec de l'ajout
                        afficherErreur("Erreur", e.getMessage());
                    }
                } else {
                    // Annuler l'opération d'ajout si l'utilisateur clique sur "Non"
                    System.out.println("L'ajout a été annulé par l'utilisateur.");
                }
            });
        } catch (NumberFormatException e) {
            // Gérer une exception si le nombre de salles n'est pas un nombre valide
            afficherErreur("Erreur", "Veuillez saisir un nombre valide pour le nombre de salles.");
        }
    }

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}












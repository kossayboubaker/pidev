package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class FrontFXMLController {

    @FXML
    private MenuButton EveClient;

    @FXML
    private MenuButton MarketClient;

    @FXML
    private MenuButton cataClient;

    @FXML
    private MenuButton reservationFrontbuttonmenu;
    @FXML
    private StackPane mainStackPane;

    @FXML
    void ReserverClient(ActionEvent event) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFrontRes.fxml"));
            Parent afficheReservationView = loader.load();
            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(afficheReservationView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

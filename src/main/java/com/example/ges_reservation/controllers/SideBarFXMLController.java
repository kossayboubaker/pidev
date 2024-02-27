package com.example.ges_reservation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


import java.io.IOException;

public class SideBarFXMLController {

    @FXML
    private Button evenementbutton;

    @FXML
    private Button filmbutton;

    @FXML
    private Button marketbutton;

    @FXML
    private Button reservationbutton;

    @FXML
    private Button userbutton;

    @FXML
    private StackPane mainStackPane;

    @FXML
    void gesRes(MouseEvent event) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent ajouterReservationView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }
}

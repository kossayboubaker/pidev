package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton; // Importer MenuButton au lieu de Button
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class SideBarFXMLController {

    public MenuButton reservationbuttonmenu;
    @FXML
    private MenuButton evenementbutton; // Remplacer Button par MenuButton

    @FXML
    private MenuButton filmbutton; // Remplacer Button par MenuButton

    @FXML
    private MenuButton marketbutton; // Remplacer Button par MenuButton

    @FXML
    private MenuButton reservationbutton; // Remplacer Button par MenuButton

    @FXML
    private MenuButton userbutton; // Remplacer Button par MenuButton

    @FXML
    private StackPane mainStackPane;

    public StackPane getMainStackPane() {
        return mainStackPane;
    }


    public void handleReservationSubButton1Click(ActionEvent actionEvent) {
    }

    public void handleReservationSubButton2Click(ActionEvent actionEvent) {
    }

    public void AjoutRSBack(ActionEvent actionEvent) {
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

    public void ReserBack(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent afficherReservationView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(afficherReservationView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    public void SiegeBack(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSiege.fxml"));
            Parent afficherSiegeView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(afficherSiegeView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }
}

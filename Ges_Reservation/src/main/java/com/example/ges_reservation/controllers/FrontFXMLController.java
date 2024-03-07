package com.example.ges_reservation.controllers;

import com.example.ges_reservation.test.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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


    public void ButtonClickCinema(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichecinemafront.fxml"));
            Parent ajoutercinemaView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajoutercinemaView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    public void GererClient(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GererCompte.fxml"));
            Parent ajoutercinemaView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajoutercinemaView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    @FXML


    public void acheterproduit(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientHome.fxml"));
            Parent ajoutercinemaView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajoutercinemaView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }



    public void categorieback2(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/AfficherCategorie.fxml"));
            Parent ajouterReservationView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    public void filmback2(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/affichecinemafront.fxml"));
            Parent ajoutercategorieView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajoutercategorieView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }}

    public void filmback3(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/Recommendation.fxml"));
            Parent ajoutercategorieView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajoutercategorieView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }}
}

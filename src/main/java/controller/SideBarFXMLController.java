package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class SideBarFXMLController {



        @FXML
        private MenuButton evenementbutton;

        @FXML
        private MenuButton filmbutton;

        @FXML
        private StackPane mainStackPane;

        @FXML
        private MenuButton marketbutton;

        @FXML
        private MenuButton reservationbuttonmenu;

        @FXML
        private MenuButton userbutton;

        @FXML
        void gesRes(MouseEvent event) {

        }


        @FXML
        void handleCinemaSubButton1Click(ActionEvent event) {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cinema.fxml"));
                        Parent ajoutercinemaView = loader.load();

                        // Ajouter la vue chargée au StackPane principal
                        mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
                        mainStackPane.getChildren().add(ajoutercinemaView);
                } catch (IOException e) {
                        e.printStackTrace(); // Gérer les erreurs de chargement de la vue
                }

        }

        @FXML
        void handleEvenementSubButton2Click(ActionEvent event) {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/evenement.fxml"));
                        Parent ajoutercinemaView = loader.load();

                        // Ajouter la vue chargée au StackPane principal
                        mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
                        mainStackPane.getChildren().add(ajoutercinemaView);
                } catch (IOException e) {
                        e.printStackTrace(); // Gérer les erreurs de chargement de la vue
                }
        }

        @FXML
        void handleevenementButtonClick(ActionEvent event) {

        }

        @FXML
        void getcinema(MouseEvent event) {


        }


}



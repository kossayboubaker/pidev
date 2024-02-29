package com;
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
        private StackPane mainStackPane;

        @FXML
        private Button marketbutton;

        @FXML
        private Button reservationbuttuon;

        @FXML
        private Button userbutton;


        @FXML
        void gestmarketplace(MouseEvent event) {
            try {
                // Charger la vue AjouterReservation depuis son fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterproduit .fxml"));
                Parent ajouterproduitView = loader.load();

                // Ajouter la vue chargée au StackPane principal
                mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
                mainStackPane.getChildren().add(ajouterproduitView);
            } catch (IOException e) {
                e.printStackTrace(); // Gérer les erreurs de chargement de la vue
            }
        }

    }



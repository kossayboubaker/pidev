package tn.esprit.gestionproduit.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
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
    void ajouterproduit(ActionEvent event) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProduit.fxml"));
            Parent AddProduitView = loader.load();


            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(AddProduitView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    @FXML
    void getcommande(ActionEvent event) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCommande.fxml"));
            Parent AddProduitView = loader.load();


            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(AddProduitView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    @FXML
    void getproduit(ActionEvent event) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowProduit.fxml"));
            Parent AddProduitView = loader.load();


            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(AddProduitView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }



    public void getgategorie(ActionEvent actionEvent) {

    }

    public void ajouterCategorie(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCategorie.fxml"));
            Parent AddProduitView = loader.load();


            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(AddProduitView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }

    }
}



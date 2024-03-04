package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.util.Optional;

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

    public void ADDUser(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AjouterUserAdmin.fxml"));
            Parent ajouterReservationView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }

    }
        public void exmpUser (ActionEvent actionEvent){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherUserAdmin.fxml"));
                Parent ajouterReservationView = loader.load();

                mainStackPane.getChildren().clear();
                mainStackPane.getChildren().add(ajouterReservationView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    public void AjouterCode(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AjouterCodeAdmin.fxml"));
            Parent ajouterReservationView = loader.load();

            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AfficherCode(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/AfficherCodeAdmin.fxml"));
            Parent ajouterReservationView = loader.load();

            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void quitter(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir quitter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Fermer l'application
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            stage.close();
        }
    }


    public void SignOut(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/LOGIN.fxml"));
        try {
            reservationbuttonmenu.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
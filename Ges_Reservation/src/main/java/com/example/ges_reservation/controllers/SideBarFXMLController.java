package com.example.ges_reservation.controllers;

import com.example.ges_reservation.test.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton; // Importer MenuButton au lieu de Button
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

    public void ADDUser(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUserAdmin.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUserAdmin.fxml"));
            Parent ajouterReservationView = loader.load();

            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void AjouterCode(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCodeAdmin.fxml"));
            Parent ajouterReservationView = loader.load();

            mainStackPane.getChildren().clear();
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AfficherCode(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCodeAdmin.fxml"));
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/LOGIN.fxml"));
        try {
            reservationbuttonmenu.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
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
    public void handleReservationSubButton1Click(ActionEvent actionEvent) {
    }

    public void handleReservationSubButton2Click(ActionEvent actionEvent) {
    }
    public void categorieback(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/AjouterCategorie.fxml"));
            Parent ajouterReservationView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    public void filmback(ActionEvent actionEvent) {
        try {
            // Charger la vue AjouterReservation depuis son fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/AjouterFilms.fxml"));
            Parent ajouterReservationView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajouterReservationView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }
    }

    public void categorieback1(ActionEvent actionEvent) {
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

    public void filmback1(ActionEvent actionEvent) {try {
        // Charger la vue AjouterReservation depuis son fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/AfficherFilm.fxml"));
        Parent ajouterReservationView = loader.load();

        // Ajouter la vue chargée au StackPane principal
        mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
        mainStackPane.getChildren().add(ajouterReservationView);
    } catch (IOException e) {
        e.printStackTrace(); // Gérer les erreurs de chargement de la vue
    }
    }
}

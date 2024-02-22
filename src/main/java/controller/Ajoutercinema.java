package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.cinema;
import services.cinemaservice;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Ajoutercinema implements Initializable {
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
            int nombreSalles = Integer.parseInt(nbsalle.getText());
            String placeCinema = place.getText();
            String etatCinema = etat.getText();
            String nomCinema = nom.getText();

            // Validation des données saisies
            if (nombreSalles <= 0) {
                afficherErreur("Erreur", "Le nombre de salles doit être supérieur à zéro.");
                return;
            }
            if (!Character.isUpperCase(nomCinema.charAt(0))) {
                afficherErreur("Erreur", "Veuillez saisir une place valide.");
                return;
            }
            if (!Character.isUpperCase(nomCinema.charAt(0))) {
                afficherErreur("Erreur", "Veuillez saisir un état valide.");
                return;
            }
            if (!Character.isUpperCase(nomCinema.charAt(0))) {
                afficherErreur("Erreur", "Veuillez saisir un nom valide.");
                return;
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




    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<cinema> cinemas = cs.recuperer();
            ObservableList<cinema> observableList = FXCollections.observableArrayList(cinemas);
            listcinema.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void Supprimer(ActionEvent event) throws SQLException {

        try {
            int idS = Integer.parseInt(id.getText());

            // Boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet élément ?");

            // Ajout des boutons de confirmation
            ButtonType buttonYes = new ButtonType("Oui");
            ButtonType buttonNo = new ButtonType("Non");

            confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

            // Affichage du dialogue de confirmation et traitement de la réponse
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == buttonYes) {
                    try {
                        // Suppression de l'élément
                        cinemaservice cs = new cinemaservice();
                        cs.supprimer(idS);

                        afficherDialogueInformation("Succès", "L'élément a été supprimé avec succès.");
                    } catch (SQLException e) {
                        afficherDialogueErreur("Erreur", e.getMessage());
                    }
                } else {
                    // Si l'utilisateur clique sur "Non", annulez l'opération
                    System.out.println("La suppression a été annulée par l'utilisateur.");
                }
            });
        } catch (NumberFormatException e) {
            // Gérer une exception si l'ID n'est pas un nombre
            afficherDialogueErreur("Erreur", "Veuillez saisir un ID valide.");
        }


    }

    private void afficherDialogueInformation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher une boîte de dialogue d'erreur
    private void afficherDialogueErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Refresh(ActionEvent event)
    {
        try {
            List<cinema> cinemas = cs.recuperer();
            ObservableList<cinema> observableList = FXCollections.observableArrayList(cinemas);
            listcinema.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void Modifier() {
        try {
            // Récupérer les données saisies dans les champs de texte
            int idd = Integer.parseInt(id.getText());
            int nbsallee =  Integer.parseInt(nbsalle.getText());
            String nomm = nom.getText();
            String placee = place.getText();
            String etatt = etat.getText();

            // Créer un objet User avec les nouvelles valeurs
            cinema cs = new cinema(idd, nbsallee, placee, nomm, etatt);

            // Boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir modifier cet élément ?");

            // Ajout des boutons de confirmation
            ButtonType buttonYes = new ButtonType("Oui");
            ButtonType buttonNo = new ButtonType("Non");

            confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

            // Affichage du dialogue de confirmation et traitement de la réponse
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == buttonYes) {
                    try {
                        cinemaservice cinemaservice = new cinemaservice();
                        cinemaservice.modifer(cs);

                        // Afficher un message de confirmation
                        afficherMessage("Succès", "L'utilisateur a été modifié avec succès.");
                    } catch (SQLException e) {
                        afficherErreur("Erreur", "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
                    }
                } else {
                    // Si l'utilisateur clique sur "Non", annuler l'opération de modification
                    System.out.println("La modification a été annulée par l'utilisateur.");
                }
            });
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID valide.");
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

    @FXML
    void Evenement(ActionEvent event) throws IOException {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ajouterevenement.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            nom.getScene().setRoot(root);

        }

}












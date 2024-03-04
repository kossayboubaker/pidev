package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.crud.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Reclamation {

    @FXML
    private ChoiceBox<String> choixSujet;

    @FXML
    private TextArea bodyTextField;

    @FXML
    private Button sendButton;

    private UserService userService;

    @FXML
    public void initialize() {
        // Ajouter des éléments à la ChoiceBox sujet
        choixSujet.getItems().addAll("Descrivez votre problème", "Problème d'inscription", "Problème de connexion",
                "Compte bloqué ou suspendu", "Gestion des abonnements", "Notifications indésirables",
                "Support client", "Performances de l'application", "Autres problèmes");
        // Sélectionner le premier élément par défaut
        choixSujet.getSelectionModel().selectFirst();

        // Initialiser le service utilisateur
        try {
            userService = new UserService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void sendButton() {
        String subject = choixSujet.getValue();
        String body = bodyTextField.getText();

        // Vérifier si les champs ne sont pas vides
        if (subject != null && !body.isEmpty()) {
            // Envoyer la réclamation par e-mail
            userService.sendEmail("manideliro@gmail.com", subject, body);
            // Effacer les champs après l'envoi
            bodyTextField.clear();
            // Afficher une alerte de confirmation à l'utilisateur
            showConfirmationAlert();
        } else {
            // Afficher un message d'erreur si les champs sont vides
            showErrorAlert("Veuillez remplir tous les champs !");
        }
    }

    // Afficher une alerte de confirmation
    private void showConfirmationAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Réclamation envoyée");
        alert.setHeaderText(null);
        alert.setContentText("Votre réclamation a bien été envoyée au support.");
        alert.showAndWait();
    }

    // Afficher une alerte d'erreur avec un message spécifique
    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void BackToLogin(ActionEvent event) {

        try {
            AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/tn/esprit/crud/Login.fxml"));
            Scene scene = new Scene(pane);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

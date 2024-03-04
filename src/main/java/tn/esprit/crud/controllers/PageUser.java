package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.crud.models.User;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.util.Optional;

public class PageUser {

    @FXML
    private Label labelNom;

    @FXML
    private Label labelPrenom;

    public void setUser(User user) {
        if (user != null) {
            labelNom.setText(user.getNom());
            labelPrenom.setText(user.getPrenom());
        }
    }

    public void setNom(String nom) {
        labelNom.setText(nom);
    }

    // Méthode pour mettre à jour le prénom de l'utilisateur dans l'interface utilisateur
    public void setPrenom(String prenom) {
        labelPrenom.setText(prenom);
    }
    @FXML
    void Quitter(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir quitter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Fermer l'application
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }


    @FXML
    void ToLogin(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si l'utilisateur confirme la déconnexion, charger la page de connexion
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/LOGIN.fxml"));
            try {
                labelNom.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }


}

package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.ges_reservation.services.UserService;
import com.example.ges_reservation.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MotDePasseOublier {

    @FXML
    private TextField CodeRecu;

    @FXML
    private TextField EmailOublier;

    @FXML
    private Button envoyerCodeButton;

    @FXML
    void ToChangeMdp(ActionEvent event) {
        String codeSaisi = CodeRecu.getText(); // Récupérer le code saisi par l'utilisateur
        String codeEnvoye = UserService.getCodeRecuperation(); // Récupérer le code envoyé par e-mail depuis le service

        // Vérifier si le code saisi correspond au code envoyé par e-mail
        if (codeSaisi.equals(codeEnvoye)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ChangerMotDePasse.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Fermer la fenêtre actuelle si nécessaire
                ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le code saisi est incorrect !");
        }
    }



    @FXML
    void envoyerCodeAction(ActionEvent event) throws SQLException {
        String email = EmailOublier.getText();
        UserService userService = new UserService();

        // Vérifier si l'email existe dans la base de données
        if (!userService.emailExiste(email)) {
            // Afficher une alerte indiquant que l'email n'existe pas
            afficherAlerte("Erreur", "Veuillez entrer une adresse email valide");
            return; // Sortir de la méthode sans envoyer de code de récupération
        }

        // Demander une confirmation à l'utilisateur avant d'envoyer le code de récupération
        boolean confirmation = confirmerAction("Confirmation", "Voulez-vous vraiment envoyer un code de récupération à l'adresse email : " + email + " ?");
        if (confirmation) {
            // Générer un code de récupération aléatoire
            String codeRecuperation = userService.generateRandomCode();
            // Envoyer le code de récupération par e-mail
            userService.envoyerEmailRecuperation(email, codeRecuperation);
            // Afficher un message indiquant que le code de récupération a été envoyé avec succès
            afficherAlerte("Succès", "Code de récupération envoyé avec succès à l'adresse email : " + email);
        }
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private boolean confirmerAction(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }





    @FXML
    void BackLogin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/LOGIN.fxml"));
        try {
            CodeRecu.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.ges_reservation.models.User;
import com.example.ges_reservation.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import com.example.ges_reservation.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SIGNUP {

    @FXML
    private TextField nomSignUp;

    @FXML
    private TextField prenomSignUp;

    @FXML
    private TextField adresseSignUp;

    @FXML
    private TextField emailSignUp;

    @FXML
    private PasswordField mdpSignUp;

    @FXML
    private PasswordField confirmeMdpSignUp;

    @FXML
    void SignUp(ActionEvent event) {
        // Récupérer les informations saisies par l'utilisateur
        String nom = nomSignUp.getText();
        String prenom = prenomSignUp.getText();
        String adresse = adresseSignUp.getText();
        String email = emailSignUp.getText();
        String mdp = mdpSignUp.getText();
        String confirmeMdp = confirmeMdpSignUp.getText();

        // Vérifier si les champs sont vides
        if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || email.isEmpty() || mdp.isEmpty() || confirmeMdp.isEmpty()) {
            afficherErreur("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si les mots de passe correspondent
        if (!mdp.equals(confirmeMdp)) {
            afficherErreur("Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Vérifier si l'email est de la forme @gmail.com
        if (!email.endsWith("@gmail.com")) {
            afficherErreur("Erreur", "Veuillez saisir une adresse email valide se terminant par '@gmail.com'.");
            return;
        }

        // Vérifier si l'email existe déjà dans la base de données
        UserService userService = null;
        try {
            userService = new UserService();
            if (userService.emailExiste(email)) {
                afficherErreur("Erreur", "L'adresse email est déjà utilisée.");
                return;
            }
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de la vérification de l'email : " + e.getMessage());
            return;
        }

        // Hashage du mot de passe avant de l'ajouter à la base de données
        String mdpHash = BCrypt.hashpw(mdp, BCrypt.gensalt());

        // Créer un nouvel utilisateur avec les informations saisies
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setAdresse(adresse);
        user.setEmail(email);
        user.setMdp(mdpHash);

        // Ajouter l'utilisateur à la base de données
        try {
            userService.ajouter(user);
            // Afficher une alerte de succès
            afficherMessageAvecBouton("Succès", "Utilisateur ajouté avec succès. Cliquez sur 'Se connecter' pour vous connecter.", event);
        } catch (SQLException e) {
            // En cas d'erreur, afficher une alerte d'erreur
            afficherErreur("Erreur", "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }
    private void afficherMessageAvecBouton(String titre, String contenu, ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);

        // Ajouter un bouton personnalisé "Se connecter" à l'alerte
        ButtonType loginButton = new ButtonType("Se connecter");
        alert.getButtonTypes().setAll(loginButton);

        // Attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == loginButton) {
                // Rediriger l'utilisateur vers la page de connexion
                redirectToLoginPage(event);
            }
        });
    }



    private void redirectToLoginPage(ActionEvent event) {
        // Récupérer la scène actuelle
        Scene scene = ((Node) event.getSource()).getScene();
        // Récupérer la fenêtre actuelle
        Stage stage = (Stage) scene.getWindow();

        // Charger la vue de la page de connexion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LOGIN.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Créer une nouvelle scène avec la vue de la page de connexion
        Scene loginScene = new Scene(root);

        // Définir la nouvelle scène sur la fenêtre
        stage.setScene(loginScene);
        // Afficher la fenêtre
        stage.show();
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void BackToLogin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/LOGIN.fxml"));
        try {
            nomSignUp.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
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
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}

package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AjouterUserAdmin {

    @FXML
    private TextField adresseUserAdmin;

    @FXML
    private TextField emailUserAdmin;

    @FXML
    private TextField mdpUserAdmin;

    @FXML
    private TextField nomUserAdmin;

    @FXML
    private TextField prenomUserAdmin;

    private UserService userService;

    public AjouterUserAdmin() {
        try {
            userService = new UserService();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception de connexion à la base de données
        }
    }

    @FXML
    void AjouterUserAdmin(ActionEvent event) {
        // Récupérer les valeurs des champs TextField
        String nom = nomUserAdmin.getText();
        String prenom = prenomUserAdmin.getText();
        String adresse = adresseUserAdmin.getText();
        String email = emailUserAdmin.getText();
        String mdp = mdpUserAdmin.getText();

        // Crypter le mot de passe avec BCrypt
        String mdpCrypte = BCrypt.hashpw(mdp, BCrypt.gensalt());

        try {
            // Créer un nouvel objet utilisateur avec les valeurs récupérées
            User nouvelUtilisateur = new User();
            nouvelUtilisateur.setNom(nom);
            nouvelUtilisateur.setPrenom(prenom);
            nouvelUtilisateur.setAdresse(adresse);
            nouvelUtilisateur.setEmail(email);
            nouvelUtilisateur.setMdp(mdpCrypte);

            // Appeler la méthode de service pour ajouter l'utilisateur à la base de données
            userService.ajouter(nouvelUtilisateur);

            // Afficher une alerte de succès
            afficherAlerte("Utilisateur ajouté avec succès", "L'utilisateur a été ajouté à la base de données avec succès.");

            // Effacer les champs de texte après l'ajout
            effacerChampsTexte();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception d'ajout de l'utilisateur
            afficherAlerte("Erreur lors de l'ajout", "Une erreur est survenue lors de l'ajout de l'utilisateur à la base de données.");
        }
    }

    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour effacer les champs de texte
    private void effacerChampsTexte() {
        nomUserAdmin.clear();
        prenomUserAdmin.clear();
        adresseUserAdmin.clear();
        emailUserAdmin.clear();
        mdpUserAdmin.clear();
    }
    @FXML
    void ReturnToUser(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/test.fxml"));
        try {
            mdpUserAdmin.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ToCodeAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/CodeAdmin.fxml"));
        try {
            mdpUserAdmin.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ToDashboard(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/Dashboard.fxml"));
        try {
            mdpUserAdmin.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ToUserAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/UserAdmin.fxml"));
        try {
            mdpUserAdmin.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void BackToLogin(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Charger la vue de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/LOGIN.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Configurer la scène
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    }


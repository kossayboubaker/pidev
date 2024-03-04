package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterUser {
    @FXML
    private TextField adresseTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField mdpTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    void afficherUsers(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AfficherUsers.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterUser(ActionEvent event) throws SQLException {
        // Vérifier si les champs obligatoires sont remplis
        if (nomTF.getText().isEmpty() || prenomTF.getText().isEmpty() || adresseTF.getText().isEmpty() ||
                emailTF.getText().isEmpty() || mdpTF.getText().isEmpty()) {
            afficherErreur("Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        // Vérifier si l'adresse email est valide
        String email = emailTF.getText();
        if (!isValidEmailAddress(email)) {
            afficherErreur("Erreur", "Adresse email invalide.");
            return;
        }

        // Hasher le mot de passe
        String hashedPassword = BCrypt.hashpw(mdpTF.getText(), BCrypt.gensalt());

        // Ajouter l'utilisateur
        UserService userService = new UserService();
        User user = new User();
        user.setNom(nomTF.getText());
        user.setPrenom(prenomTF.getText());
        user.setAdresse(adresseTF.getText());
        user.setEmail(email);
        user.setMdp(hashedPassword); // Utiliser le mot de passe hashé

        try {
            userService.ajouter(user);
            afficherMessage("Succès", "Utilisateur ajouté avec succès.");
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean isValidEmailAddress(String email) {
        // Utiliser une expression régulière pour valider l'adresse email
        // Cette expression régulière peut être simple ou complexe selon vos besoins
        // Voici un exemple basique pour vérifier la présence d'un @ et d'un . :
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private void afficherMessage(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void VersModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ModifierUser.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void RetourMenu(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/GestionUser.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SupprimerUser.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

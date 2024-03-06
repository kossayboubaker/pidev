package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class GererCompte {

    @FXML
    private Label AdresseUser;

    @FXML
    private Label EmailUser;

    @FXML
    private Label NomUser;

    @FXML
    private Label PrenomUser;

    @FXML
    private PasswordField mdpUtilisateur;

    @FXML
    private TextField NouveauAdresse;

    @FXML
    private TextField NouveauNom;

    @FXML
    private TextField NouveauPrenom;

    private UserService userService;

    public GererCompte() throws SQLException {
        this.userService = new UserService();
    }

    public void setUserData(String nom, String prenom, String adresse, String email) {
        NomUser.setText(nom);
        PrenomUser.setText(prenom);
        AdresseUser.setText(adresse);
        EmailUser.setText(email);
    }

    @FXML
    void Modifier(ActionEvent event) {
        try {
            // Récupérer les nouvelles informations entrées par l'utilisateur
            String nouveauNom = NouveauNom.getText();
            String nouveauPrenom = NouveauPrenom.getText();
            String nouvelleAdresse = NouveauAdresse.getText();
            String nouveauEmail = EmailUser.getText(); // Récupérer l'e-mail saisi par l'utilisateur

            // Appeler la méthode de service pour modifier les informations de l'utilisateur
            userService.modifierInformationsUtilisateur(nouveauEmail, nouveauNom, nouveauPrenom, nouvelleAdresse, nouveauEmail);

            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Modifications effectuées", "Vos informations ont été mises à jour avec succès.");
        } catch (SQLException e) {
            // Gérer les erreurs SQL
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de modification", "Une erreur s'est produite lors de la modification de vos informations.");
            e.printStackTrace();
        }
    }

    @FXML
    void SupprimerCompte(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer le mot de passe saisi par l'utilisateur
            String motDePasseSaisi = mdpUtilisateur.getText();
            // Vérifier si le mot de passe est correct avant de supprimer le compte
            if (verifierMotDePasse(motDePasseSaisi)) {
                try {
                    // Récupérer l'e-mail de l'utilisateur
                    String userEmail = EmailUser.getText();
                    // Supprimer le compte de l'utilisateur
                    userService.supprimerCompte(userEmail);
                    // Afficher un message de succès
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Suppression de compte", "Votre compte a été supprimé avec succès.");
                    // Rediriger vers la page de connexion
                    retournerALogin(event);
                } catch (SQLException e) {
                    // Gérer les erreurs SQL
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de suppression", "Une erreur s'est produite lors de la suppression de votre compte.");
                    e.printStackTrace();
                }
            } else {
                // Afficher un message d'erreur si le mot de passe est incorrect
                showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe incorrect", "Veuillez saisir un mot de passe correct.");
            }
        }
    }

    // Méthode pour vérifier si le mot de passe saisi correspond au mot de passe de l'utilisateur
    private boolean verifierMotDePasse(String motDePasseSaisi) {
        // Récupérez l'e-mail de l'utilisateur
        String emailUtilisateur = EmailUser.getText();
        // Récupérez le mot de passe de l'utilisateur depuis la base de données
        try {
            User user = userService.getByEmail(emailUtilisateur);
            if (user != null) {
                String motDePasseUtilisateur = user.getMdp();
                // Vérifiez si le mot de passe saisi correspond au mot de passe de l'utilisateur
                return BCrypt.checkpw(motDePasseSaisi, motDePasseUtilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Méthode pour afficher une boîte de dialogue
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // Méthode pour retourner à la page de connexion
    private void retournerALogin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/LOGIN.fxml"));
        try {
            Parent root = fxmlLoader.load();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setUser(User user) {
        if (user != null) {
            NomUser.setText(user.getNom());
            PrenomUser.setText(user.getPrenom());
            AdresseUser.setText(user.getAdresse());
            EmailUser.setText(user.getEmail());
        }
    }

}

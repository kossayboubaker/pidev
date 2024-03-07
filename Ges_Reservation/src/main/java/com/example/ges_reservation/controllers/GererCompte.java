package com.example.ges_reservation.controllers;

import com.example.ges_reservation.test.MainFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;
import com.example.ges_reservation.models.User;
import com.example.ges_reservation.services.UserService;
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
            String nouveauNom = NouveauNom.getText();
            String nouveauPrenom = NouveauPrenom.getText();
            String nouvelleAdresse = NouveauAdresse.getText();
            String nouveauEmail = EmailUser.getText();

            if (nouveauNom.isEmpty() && nouveauPrenom.isEmpty() && nouvelleAdresse.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Aucune modification", "Aucune modification détectée", "Veuillez remplir au moins un champ pour effectuer une modification.");
            } else {
                userService.modifierInformationsUtilisateur(nouveauEmail, nouveauNom, nouveauPrenom, nouvelleAdresse, nouveauEmail);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Modifications effectuées", "Vos informations ont été mises à jour avec succès.");
            }
        } catch (SQLException e) {
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
            String motDePasseSaisi = mdpUtilisateur.getText();
            if (verifierMotDePasse(motDePasseSaisi)) {
                try {
                    String userEmail = EmailUser.getText();
                    userService.supprimerCompte(userEmail);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Suppression de compte", "Votre compte a été supprimé avec succès.");
                    retournerALogin(event);
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de suppression", "Une erreur s'est produite lors de la suppression de votre compte.");
                    e.printStackTrace();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe incorrect", "Veuillez saisir un mot de passe correct.");
            }
        }
    }

    private boolean verifierMotDePasse(String motDePasseSaisi) {
        String emailUtilisateur = EmailUser.getText();
        try {
            User user = userService.getByEmail(emailUtilisateur);
            if (user != null) {
                String motDePasseUtilisateur = user.getMdp();
                return BCrypt.checkpw(motDePasseSaisi, motDePasseUtilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void retournerALogin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/LOGIN.fxml"));
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
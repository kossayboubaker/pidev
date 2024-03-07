package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.example.ges_reservation.models.CodePromo;
import com.example.ges_reservation.services.PromoService;
import com.example.ges_reservation.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


public class AjouterCode {

    @FXML
    private TextField codeTF;

    @FXML
    private DatePicker date_expTF;

    @FXML
    private TextField user_idTF;

    @FXML
    private TextField utiliseTF;




    @FXML
    void RetourMenu(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/GestionUser.fxml"));
        try {
            codeTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        date_expTF.setValue(LocalDate.now());
    }


    @FXML
    void ajouterCode(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (codeTF.getText().isEmpty() || date_expTF.getValue() == null || user_idTF.getText().isEmpty() || utiliseTF.getText().isEmpty()) {
            afficherErreur("Erreur", "Tous les champs sont obligatoires.");
            return;
        }

        // Vérifier si les valeurs saisies sont valides
        try {
            int code = Integer.parseInt(codeTF.getText());
            LocalDate date_exp = date_expTF.getValue();
            int user_id = Integer.parseInt(user_idTF.getText());
            int utilise = Integer.parseInt(utiliseTF.getText());

            // Ajouter le code promo
            PromoService promoService = new PromoService();
            CodePromo codePromo = new CodePromo();
            codePromo.setCode(code);
            codePromo.setDate_exp(java.sql.Date.valueOf(date_exp));
            codePromo.setUser_id(user_id);
            codePromo.setUtilise(utilise);

            promoService.ajouter(codePromo);
            afficherMessage("Succès", "Code promo ajouté avec succès.");
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir des valeurs numériques valides pour le code et l'utilisateur.");
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de l'ajout du code promo : " + e.getMessage());
            e.printStackTrace(); // Affichez la pile d'erreur pour un débogage approfondi
        }
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
    void VersAfficher(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherCode.fxml"));
        try {
            codeTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void VersModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ModifierCode.fxml"));
        try {
            codeTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SupprimerCode.fxml"));
        try {
            codeTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

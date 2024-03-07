package com.example.ges_reservation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.example.ges_reservation.services.PromoService;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import com.example.ges_reservation.test.HelloApplication;

import java.sql.SQLException;



public class SupprimerCode {

    @FXML
    private TextField supprimerTF; // TextField pour saisir l'ID du code promo à supprimer

    private PromoService promoService;

    {
        try {
            promoService = new PromoService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void VersAfficher(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherCode.fxml"));
        try {
            supprimerTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AjouterCode.fxml"));
        try {
            supprimerTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ModifierCode.fxml"));
        try {
            supprimerTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerCode(ActionEvent event) {
        // Récupérer l'ID du code promo à partir du TextField
        String idCodePromoStr = supprimerTF.getText();

        // Vérifier si l'ID est un entier valide
        try {
            int idCodePromo = Integer.parseInt(idCodePromoStr);

            // Appeler la méthode de service pour supprimer le code promo par son ID
            promoService.supprimer(idCodePromo);
            System.out.println("Le code promo avec l'ID " + idCodePromo + " a été supprimé avec succès.");
            // Vous pouvez ajouter ici des actions supplémentaires après la suppression du code promo
        } catch (NumberFormatException e) {
            System.err.println("L'ID du code promo doit être un entier valide.");
            // Gérer l'erreur si l'utilisateur entre un ID non valide (par exemple, afficher un message d'erreur à l'utilisateur)
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du code promo : " + e.getMessage());
            // Gérer l'erreur (afficher un message à l'utilisateur, journaliser l'erreur, etc.)
        }

        // Afficher un message de confirmation à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setContentText("Le code promo a été supprimé avec succès.");
        alert.showAndWait();
    }
}

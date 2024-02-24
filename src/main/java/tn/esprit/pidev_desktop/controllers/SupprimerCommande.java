package tn.esprit.pidev_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.pidev_desktop.services.CommandeService;

import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class SupprimerCommande {
    @FXML
    private TextField suuprimeridTF;

    private CommandeService commandeService;{
        commandeService = new CommandeService();
    }


    @FXML
    void supprimerCommande(ActionEvent event) {
        String idCommandeStr = suuprimeridTF.getText().trim();

        if (idCommandeStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un ID");
            alert.showAndWait();
            return; // Sortir de la destinationViewController si l'ID est vide

        }

        try {
            int idCommande = Integer.parseInt(idCommandeStr);

            // Vérifier si l'ID est valide
            if (idCommande <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("ID invalide");
                alert.showAndWait();
                return; // Sortir de la destinationViewController si l'ID est invalide
            }

            try {
                commandeService.supprimer(idCommande);

                System.out.println("Produit supprimé avec succès");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setContentText("Commande supprimé avec suceess");
                alert.showAndWait();


        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("ID doit etre un entier");
            alert.showAndWait();
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    }

    @FXML
    void retourecommande(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AfficherCommande.fxml"));
        try {
            suuprimeridTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }


}

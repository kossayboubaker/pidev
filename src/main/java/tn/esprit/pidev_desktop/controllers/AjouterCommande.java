package tn.esprit.pidev_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCommande {


    @FXML
    private TextField id_prodN;

    @FXML
    private TextField id_userN;

    @FXML
    private TextField montanttotalN;

    @FXML
    private TextField nom_userN;

    @FXML
    private TextField prenom_userN;

    @FXML
    private TextField quantiteN;


    public void showCommand(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AfficherCommande.fxml"));
        try {
            id_prodN.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void addproduit(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AjouterProduit.fxml"));
        try {
            id_prodN.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void ajouterCommande(ActionEvent actionEvent) {
        CommandeService cs = new CommandeService();
        Commande commande = new Commande();

        // Contrôle de saisie pour tous les champs
        if (id_userN.getText().isEmpty() || id_prodN.getText().isEmpty() || montanttotalN.getText().isEmpty() || nom_userN.getText().isEmpty() || prenom_userN.getText().isEmpty() || quantiteN.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }

        // Contrôle de saisie pour le nom et le prénom
        if (!isString(nom_userN.getText()) || !isString(prenom_userN.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le nom et le prénom doivent être des chaînes de caractères");
            alert.showAndWait();
            return;
        }

        // Contrôle de saisie pour le montant total
        try {
            float montantTotal = Float.parseFloat(montanttotalN.getText());
            if (montantTotal <= 0) {
                throw new NumberFormatException();
            }
            commande.setMontantTotal(montantTotal);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le montant total doit être un nombre valide et supérieur à zéro");
            alert.showAndWait();
            return;
        }

        // Contrôle de saisie pour la quantité
        try {
            int quantite = Integer.parseInt(quantiteN.getText());
            if (quantite <= 0) {
                throw new NumberFormatException();
            }
            commande.setQauntite(quantite);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La quantité doit être un nombre entier valide et supérieure à zéro");
            alert.showAndWait();
            return;
        }

        // Contrôle de saisie pour l'id user et l'id produit
        try {
            int id_user = Integer.parseInt(id_userN.getText());
            int id_prod = Integer.parseInt(id_prodN.getText());
            commande.setUser_id((id_user));
            commande.setPro_id((id_prod));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("L'ID utilisateur et l'ID produit doivent être des nombres entiers valides");
            alert.showAndWait();
            return;
        }

        // Contrôle de saisie pour l'existance de l'id user et l'id produit
        if (cs.checkUserExistence(id_userN.getText()) && cs.checkProductExistence(id_prodN.getText())) {
            // Code to proceed if both user and product exist
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("L'ID utilisateur ou l'ID produit n'existe pas");
            alert.showAndWait();
            return;
        }


        // Aucune erreur de saisie détectée, ajouter la commande
        try {
            commande.setNom_user(nom_userN.getText());
            commande.setPrenom_user(prenom_userN.getText());

            cs.ajouter(commande);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Commande ajoutée avec succès");
            alert.showAndWait();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean isString(String text) {
        return text.matches("^[a-zA-Z\\s]+$");
    }
}

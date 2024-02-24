package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class AfficherCommande {


    @FXML
    private ListView<String> listCommande;




    @FXML
    void pagemodifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/ModifierCommande.fxml"));
        try {
            listCommande.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void returnToajoute(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AjouterCommande.fxml"));
        try {
            listCommande.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @FXML
    private ListView<String> ListProduit;
    @FXML
    void initialize() {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.recuperer();

            // Trier la liste des produits par ID avant de les ajouter à l'ObservableList
            produits.sort(Comparator.comparingInt(Produit::getId));

            // Créer une ObservableList pour stocker les données des produits
            ObservableList<String> produitDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-20s %-20s %-40s %-20s %-20s %-20s", "ID", "Nom", "DescriptioN", "Prix", "Stock", "Image");
            produitDataList.add(columnTitles);

            // Itérer à travers la liste des produits et ajouter leurs détails à la produitDataList
            for (Produit produit: produits) {
                String produitData = String.format("%-20s %-20s %-40s %-20s %-20s %-20s",
                        produit.getId(),
                        produit.getNom(),
                        produit.getDescription(),
                        produit.getPrix(),
                        produit.getStock(),
                        produit.getImage()
                );
                produitDataList.add(produitData);
            }

            // Définir les éléments pour la ListView
            ListProduit.setItems(produitDataList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    public void supprimerProduit(ActionEvent actionEvent) {
        String selectedCommand = ListProduit.getSelectionModel().getSelectedItem();
        if (selectedCommand == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune commande sélectionnée");
            alert.setContentText("Veuillez sélectionner une commande à supprimer.");
            alert.showAndWait();
            return; // Sortir de la méthode si aucune commande n'est sélectionnée
        }

        // Récupérer l'ID de la commande à partir de la chaîne sélectionnée
        String[] commandParts = selectedCommand.split("\\s+"); // Diviser la chaîne en mots
        int produitId = Integer.parseInt(commandParts[0]); // Le premier mot est l'ID de la commande

        // Demander confirmation à l'utilisateur
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette commande ?");

        // Attendre la réponse de l'utilisateur

        java.util.Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a confirmé la suppression, appeler le service pour supprimer la commande
            ProduitService produitService = new ProduitService();
            try {
                produitService.supprimer(produitId);

                // Rafraîchir la liste des commandes après la suppression
                initialize();

                // Afficher une confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commande supprimée");
                alert.setContentText("La commande a été supprimée avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                // En cas d'erreur lors de la suppression
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setContentText("Une erreur s'est commande lors de la suppression de la repas : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }}



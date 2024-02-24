package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AfficherProduit {


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
            for (Produit produit : produits) {
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
            alert.setTitle("Aucune produit sélectionnée");
            alert.setContentText("Veuillez sélectionner une produit à supprimer.");
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
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette produit ?");

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
                alert.setTitle("produit supprimée");
                alert.setContentText("La produit a été supprimée avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {
                // En cas d'erreur lors de la suppression
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setContentText("Une erreur s'est produite lors de la suppression de la repas : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }


    public void returnToajoute(ActionEvent ActionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AjouterProduit.fxml"));
        try {
            ListProduit.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void pagemodifier(ActionEvent actionEvent) {
        // Récupérer l'élément sélectionné dans la ListView
        String selectedProduit = ListProduit.getSelectionModel().getSelectedItem();

        // Vérifier si un élément est sélectionné
        if (selectedProduit == null || selectedProduit.startsWith("ID")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune commande sélectionnée");
            alert.setContentText("Veuillez sélectionner une commande à modifier.");
            alert.showAndWait();
            return; // Sortir de la méthode si aucune commande n'est sélectionnée
        }

        // Extraire l'ID de la commande sélectionnée
        int id;
        try {
            id = Integer.parseInt(selectedProduit.split("\\s+")[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de conversion");
            alert.setContentText("Impossible de convertir l'ID en entier.");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Modifier la commande");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier cette commande ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Ouvrir une boîte de dialogue de modification de la commande
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier la produit");
            dialog.setHeaderText(null);

            // Charger le fichier FXML de la boîte de dialogue de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/pidev_desktop/ModifierProduit.fxml"));
            try {
                dialog.getDialogPane().setContent(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // récupérer le donne de la boocide de dialogue
            DialogPane dialogPane = dialog.getDialogPane();



            // Assuming there is a method setCommandeId in the controller to set the ID
            ModifierProduit controller = loader.getController();
            controller.setId(id);
            // Ajouter les boutons "Confirmer" et "Annuler" à la boîte de dialogue
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Attendre la réponse de l'utilisateur
            Optional<ButtonType> dialogResult = dialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                // Rafraîchir la liste des commandes après la modification
                initialize();
            }
        }
    }


}
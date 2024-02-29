package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class AfficherCommande {


    @FXML
    private ListView<String> listCommand;






    @FXML
    void returnToajoute(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AjouterCommande.fxml"));
        try {
            listCommand.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @FXML
    void initialize() {
        CommandeService commandeService = new CommandeService();
        try {
            List<Commande> commandes = commandeService.recuperer();

            // Trier la liste des commandes par ID avant de les ajouter à l'ObservableList
            commandes.sort(Comparator.comparingInt(Commande::getId));

            // Créer une ObservableList pour stocker les données des commandes
            ObservableList<String> CommandeDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-4s %-10s %-20s %-20s %-20s %-20s ", "ID","Quantite", "Date", "Montant Total", "Nom client", "Prenom client");
            CommandeDataList.add(columnTitles);

            // Itérer à travers la liste des commandes et ajouter leurs détails à la CommandeDataList
            for (Commande commande : commandes) {
                String commandeData = String.format("%-5s %-12s %-20s %-30s %-20s %-20s ",
                        commande.getId(),
                        commande.getQuantite(),
                        commande.getDate_comd(),
                        commande.getMontantTotal(),
                        commande.getNom_user(),
                        commande.getPrenom_user()

                        );
                CommandeDataList.add(commandeData);
            }

            // Définir les éléments pour la ListView
            listCommand.setItems(CommandeDataList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



    public void supprimerCommande(ActionEvent actionEvent) {
        String selectedCommand = listCommand.getSelectionModel().getSelectedItem();
        if (selectedCommand == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune produit sélectionnée");
            alert.setContentText("Veuillez sélectionner une produit à supprimer.");
            alert.showAndWait();
            return; // Sortir de la méthode si aucune commande n'est sélectionnée
        }

        // Récupérer l'ID de la commande à partir de la chaîne sélectionnée
        String[] commandParts = selectedCommand.split("\\s+"); // Diviser la chaîne en mots
        int id = Integer.parseInt(commandParts[0]); // Le premier mot est l'ID de la commande

        // Demander confirmation à l'utilisateur
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette produit ?");

        // Attendre la réponse de l'utilisateur

        java.util.Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a confirmé la suppression, appeler le service pour supprimer la commande
            CommandeService commandeService = new CommandeService();
            try {
                commandeService.supprimer(id);

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


    public void gesmarketplace(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherProduit.fxml"));
        try {
            listCommand.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void afficherpageproduit(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherProduit.fxml"));
        try {
            listCommand.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void pagestat(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/statistique.fxml"));
        try {
            listCommand.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}



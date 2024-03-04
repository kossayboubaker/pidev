package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherUsers {




    @FXML
    private ListView<String> ListUsers;

    @FXML
    void PageSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SupprimerUser.fxml"));
        try {
            ListUsers.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void PageModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ModifierUser.fxml"));
        try {
            ListUsers.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ReturnToAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AjouterUser.fxml"));
        try {
            ListUsers.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @FXML
    void initialize() throws SQLException {
        UserService userService = new UserService();
        try {
            List<User> users = userService.recupperer();

            // Créer une ObservableList pour stocker les données des commandes
            ObservableList<String> userDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-20s %-20s %-20s %-20s", "ID", "Nom", "Prenom", "Adresse");
            userDataList.add(columnTitles);

            // Itérer à travers la liste des commandes et ajouter leurs détails à la commandeDataList
            for (User user : users) {
                String commandeData = String.format("%-20s %-20s %-20s %-20s",
                        user.getId(),
                        user.getNom(),
                        user.getPrenom(),
                        user.getAdresse());
                userDataList.add(commandeData);
            }

            // Définir les éléments pour la ListView
            ListUsers.setItems(userDataList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void SupprimerUserListe(ActionEvent actionEvent) {
        String selectedCommand = ListUsers.getSelectionModel().getSelectedItem();
        if(selectedCommand == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune commande sélectionnée");
            alert.setContentText("Veuillez sélectionner une commande à supprimer.");
            alert.showAndWait();
            return; // Sortir de la méthode si aucune commande n'est sélectionnée
        }

        // Récupérer l'ID de la commande à partir de la chaîne sélectionnée
        String[] commandParts = selectedCommand.split("\\s+"); // Diviser la chaîne en mots
        int commandId = Integer.parseInt(commandParts[0]); // Le premier mot est l'ID de la commande

        // Demander confirmation à l'utilisateur
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce user ?");

        // Attendre la réponse de l'utilisateur

        java.util.Optional<ButtonType> result = confirmationAlert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            // L'utilisateur a confirmé la suppression, appeler le service pour supprimer la commande
            UserService userService = null;
            try {
                userService = new UserService();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                userService.supprimer(commandId);

                // Rafraîchir la liste des commandes après la suppression
                initialize();

                // Afficher une confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User supprimée");
                alert.setContentText("Le User a été supprimée avec succès.");
                alert.showAndWait();
            } catch(SQLException e) {
                // En cas d'erreur lors de la suppression
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setContentText("Une erreur s'est produite lors de la suppression du User : " + e.getMessage());
                alert.showAndWait();
            }
        }

    }
}

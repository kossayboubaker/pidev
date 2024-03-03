package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AfficherProduit {

    @FXML
    private TextField keywordTextField;

    @FXML
    private Button returnpage;


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
            String columnTitles = String.format("%-4s %-40s %-50s %-30s %-10s ", "ID", "Nom", "DescriptioN" , "Prix", "Stock");
            produitDataList.add(columnTitles);

            // Itérer à travers la liste des produits et ajouter leurs détails à la produitDataList
            for (Produit produit : produits) {
                String produitData = String.format("%-4s %-40s %-55s %-30s %-10s",
                       produit.getId(),
                        produit.getNom(),
                        produit.getDescription(),
                        produit.getPrix(),
                        produit.getStock()
                     //   produit.getImage()
                );
                produitDataList.add(produitData);
            }

            // Définir les éléments pour la ListView
            ListProduit.setItems(produitDataList);



        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }
// methode pour selectionner un produit
@FXML
public void selectionnerProduit(MouseEvent event) {
    ListProduit.setOnMouseClicked(event1 -> {
        if (event1.getClickCount() == 2) {
            String selectedProduit = ListProduit.getSelectionModel().getSelectedItem();
            if (selectedProduit == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun produit sélectionné");
                alert.setContentText("Veuillez sélectionner un produit.");
                alert.showAndWait();
                return;
            }

            // Passer la valeur sélectionnée au contrôleur de navigation
            navigateToModifierProduit(selectedProduit);
        }
    });
}

    // Méthode pour naviguer vers la page "modifierProduit"
    private void navigateToModifierProduit(String selectedProduit) {
        // Code pour naviguer vers la page "modifierProduit" et passer la valeur sélectionnée
FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyProduit.fxml"));
try {
    Parent root = loader.load();
    ModifierProduit controller = loader.getController();
    controller.setProduitId(selectedProduit); // Passer la valeur sélectionnée au contrôleur de la page de modification
    Scene scene = new Scene(root);
    Stage stage = (Stage) ListProduit.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
} catch (IOException e) {
    e.printStackTrace();
}



        // à travers le contrôleur de navigation ou un autre mécanisme de gestion de la navigation.
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

    public void to_afficher(ActionEvent actionEvent) {

    }

    public void returnToajoute(ActionEvent ActionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AjouterProduit.fxml"));
        try {
            ListProduit.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void pagemodifier(ActionEvent actionEvent) {


            if (ListProduit.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun produit sélectionné");
                alert.setContentText("Veuillez sélectionner un produit à modifier.");
                alert.showAndWait();
                return;
            }
        String selectedProduit = ListProduit.getSelectionModel().getSelectedItem();

        if (selectedProduit == null || selectedProduit.trim().isEmpty() || selectedProduit.startsWith("ID")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection invalide");
            alert.setContentText("Veuillez sélectionner un produit valide à modifier.");
            alert.showAndWait();
            return;
        }

        int produitId = Integer.parseInt(selectedProduit.trim().split("\\s+")[0]);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la modification");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce produit ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyProduit.fxml"));

            try {
                Parent root = loader.load();
                ModifierProduit controller = loader.getController();
                controller.initData(produitId);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {

            }
            }

        }




    public void gesmarketplace(MouseEvent mouseEvent) {
    }

    @FXML
    void Trie(ActionEvent event) {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.tri();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Produit produit: produits) {
                String eventData = String.format("%-20s %-20s %-20s %-20s ",
                        produit.getId(),
                        produit.getNom(),
                        produit.getDescription(),
                        produit.getPrix(),
                        produit.getStock());



                observableList.add(eventData);
            }
            ListProduit.setItems(observableList);
            // Set custom ListCell to format the data in columns
            // LVcmd.setCellFactory(listView -> new ColumnListViewCell());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // methode recherche par nom, prix et stock



    public void rechercheParNomPrixStock(ActionEvent actionEvent) {
        ProduitService produitService = new ProduitService();
        try {
            List<Produit> produits = produitService.tri();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (Produit produit : produits) {
                String eventData = String.format("%-20s %-20s %-20s %-20s ",
                        produit.getId(),
                        produit.getNom(),
                        produit.getDescription(),
                        produit.getPrix(),
                        produit.getStock());


                observableList.add(eventData);
            }
            ListProduit.setItems(observableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Affichepagecommande(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherCommande.fxml"));
        try {
            ListProduit.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    }

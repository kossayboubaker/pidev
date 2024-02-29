package tn.esprit.pidev_desktop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Statistique implements Initializable {

    @FXML
    private TextField totalProduitsLabel;

    @FXML
    private ListView<String> ListChart;

    @FXML
    private PieChart ProdChart;

    @FXML
    private ListView<String> ComdList;

    @FXML
    private PieChart ComdChart;

    private ProduitService produitService;
    private CommandeService commandeService;

    public Statistique() {
        produitService = new ProduitService();
        commandeService = new CommandeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherListeProduits();
        afficherGraphiqueProduits();
        afficherListeCommandes();
        afficherGraphiqueCommandes();
    }

    private void afficherListeProduits() {
        try {
            List<Produit> produits = produitService.recuperer();

            ObservableList<String> items = FXCollections.observableArrayList();
            for (Produit produit : produits) {
                String item = String.format("%s - Prix: %.2f - Stock: %d",
                        produit.getNom(), produit.getPrix(), produit.getStock());
                items.add(item);
            }

            ListChart.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    private void afficherGraphiqueProduits() {
        try {
            List<Produit> produits = produitService.recuperer();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Produit produit : produits) {
                pieChartData.add(new PieChart.Data(produit.getNom(), produit.getStock()));
            }

            ProdChart.setData(pieChartData);
            ProdChart.setLegendVisible(false); // Masquer la légende
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    private void afficherListeCommandes() {
        try {
            List<Commande> commandes = commandeService.recuperer();

            ObservableList<String> items = FXCollections.observableArrayList();
            for (Commande commande : commandes) {
                String item = String.format("Quantité: %d - Date: %s - Montant: %.2f",
                        commande.getQuantite(), commande.getDate_comd(), commande.getMontantTotal());
                items.add(item);
            }

            ComdList.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    private void afficherGraphiqueCommandes() {
        try {
            List<Commande> commandes = commandeService.recuperer();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Commande commande : commandes) {
                pieChartData.add(new PieChart.Data(commande.getDate_comd().toString(), commande.getMontantTotal()));
            }

            ComdChart.setData(pieChartData);
            ComdChart.setLegendVisible(false); // Masquer la légende
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    // methode de compter de nombre de produits dans le base de donne
    private void afficherTotalProduits() {
        try {
            int totalProduits = produitService.countProduits();
            totalProduitsLabel.setText("Nombre total de produits : " + totalProduits);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    public void afficherproduitN(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AfficherProduit.fxml"));
        try {
            ListChart.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

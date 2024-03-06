package tn.esprit.gestionproduit.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.gestionproduit.entity.Commande;
import tn.esprit.gestionproduit.entity.Produit;

import java.net.URL;
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

    private Produit produitService;
    private Commande commandeService;

    public Statistique() {
        produitService = new Produit();
        commandeService = new Commande();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

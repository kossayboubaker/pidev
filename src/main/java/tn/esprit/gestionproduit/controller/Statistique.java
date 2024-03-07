package tn.esprit.gestionproduit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.gestionproduit.entity.Produit;
import tn.esprit.gestionproduit.service.ProduitService;

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

    private ProduitService produitService;

    public Statistique() {
        produitService = ProduitService.getInstance(); // Utilisation du singleton

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
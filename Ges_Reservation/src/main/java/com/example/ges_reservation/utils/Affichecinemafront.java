package com.example.ges_reservation.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.example.ges_reservation.models.cinema;
import com.example.ges_reservation.services.cinemaservice;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class Affichecinemafront implements Initializable {


   cinemaservice cs = new cinemaservice();
    @FXML
    private GridPane grid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduitsDansGridPane();
    }
    private void afficherProduitsDansGridPane() {
        int column = 0;
        int row = 1;
        try {
            List<cinema> produits = cs.recuperer();
            for (int i = 0; i < produits.size(); i++) {

                card productCardController = new card();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/card.fxml"));
                fxmlLoader.setController(productCardController);
                VBox productCard = fxmlLoader.load();
                productCardController.setProductData(produits.get(i));
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                grid.add(productCard,column++,row);
                GridPane.setMargin(productCard, new Insets(0, 20, 20, 10));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }


    }
}

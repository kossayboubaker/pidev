package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Categories;
import com.example.ges_reservation.models.Films;
import com.example.ges_reservation.services.CategoriesService;
import com.example.ges_reservation.services.FilmsService;
import com.example.ges_reservation.test.MainFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Recommendation implements Initializable {
    CategoriesService cs = new CategoriesService();
    FilmsService fs = new FilmsService();

    @FXML
    private GridPane grid2;

    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;

    Categories c = new Categories();
    Films f = new Films();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduitsDansGridPane2();
    }

    private void afficherProduitsDansGridPane2() {
        int column = 0;
        int row = 1;
        int id = MainFX.data.id;
        try {
            List<Films> films = fs.recuperer();
            for (int i = 0; i < films.size(); i++) {
                if ("Excellent".equals(films.get(i).getSynopsis())) {
                    card1 productCardController = new card1();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/card1.fxml"));
                    fxmlLoader.setController(productCardController);
                    VBox productCard = fxmlLoader.load();
                    productCardController.setProductData2(films.get(i));
                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    grid2.add(productCard, column++, row);
                    GridPane.setMargin(productCard, new Insets(0, 20, 20, 10));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }
}
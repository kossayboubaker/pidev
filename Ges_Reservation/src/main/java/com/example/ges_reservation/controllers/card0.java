package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Categories;

import com.example.ges_reservation.models.Films;
import com.example.ges_reservation.services.FilmsService;
import com.example.ges_reservation.test.MainFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class card0 {
    @FXML
    private HBox consultez;

    @FXML
    private Text desc;

    @FXML
    private Text id;

    @FXML
    private Text nom;

    public void setProductData(Categories categories) {
        id.setText(String.valueOf(categories.getCategorieID()));
        desc.setText("" + categories.getDescriptionCategorie());
        nom.setText("" + categories.getNomCategorie());

        consultez.setOnMouseClicked(event -> {
            System.out.println("loooool" + categories.getCategorieID());

            FilmsService filmsService = new FilmsService();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/afficheevenementfront.fxml"));

            try {
                // Récupérer les films associés à la catégorie sélectionnée
                List<Films> films = filmsService.recupererByCategorie(categories.getCategorieID());
                // Passer l'identifiant de la catégorie sélectionnée à la vue suivante
                MainFX.data.id = categories.getCategorieID();
                // Afficher la vue afficheevenementfront.fxml
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException | SQLException e) {
                e.printStackTrace(); // Gérer correctement l'exception
            }
        });
    }
}

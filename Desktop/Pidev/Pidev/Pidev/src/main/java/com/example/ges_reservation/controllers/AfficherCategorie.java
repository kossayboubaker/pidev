package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Categories;
import com.example.ges_reservation.models.Films;
import com.example.ges_reservation.services.CategoriesService;
import com.example.ges_reservation.services.FilmsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class AfficherCategorie {

    @FXML
    private ListView<Categories> ListCategorie;

    @FXML
    private PieChart CategoriePie;
    private final CategoriesService categoriesService = new CategoriesService();
    private final FilmsService filmsService = new FilmsService();

    @FXML
    public void initialize() {
        try {
            List<Categories> categories = categoriesService.recuperer();

            // Créer une liste pour stocker les catégories avec leurs nombres de films
            List<Pair<Categories, Integer>> categoriesAvecNombres = new ArrayList<>();

            // Calculer le nombre de films pour chaque catégorie
            for (Categories categorie : categories) {
                int nombreFilms = countFilmsInCategory(categorie.getCategorieID());
                categoriesAvecNombres.add(new Pair<>(categorie, nombreFilms));
            }

            // Trier la liste en fonction du nombre de films dans l'ordre décroissant
            categoriesAvecNombres.sort(Comparator.comparingInt((Pair<Categories, Integer> pair) -> pair.getValue()).reversed());

            // Créer une liste de données pour le PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Pair<Categories, Integer> pair : categoriesAvecNombres) {
                pieChartData.add(new PieChart.Data(pair.getKey().getNomCategorie(), pair.getValue()));
            }

            // Mettre à jour le PieChart avec les données
            CategoriePie.setData(pieChartData);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors de l'affichage des catégories les plus utilisées");
            alert.setContentText("Une erreur s'est produite lors de la récupération des catégories les plus utilisées : " + e.getMessage());
            alert.showAndWait();
        }
    }

    // Méthode pour compter le nombre de films dans une catégorie donnée
    private int countFilmsInCategory(int categorieID) throws SQLException {
        // Récupérer la liste des films dans la catégorie donnée
        List<Films> films = filmsService.recupererByCategorie(categorieID);

        // Retourner le nombre de films dans cette catégorie
        return films.size();
    }


    @FXML
    void SuppCategorie(ActionEvent event) {
        // Récupérer la catégorie sélectionnée dans la ListView
        Categories categorieSelectionne = ListCategorie.getSelectionModel().getSelectedItem();

        if (categorieSelectionne != null) {
            // Demander une confirmation à l'utilisateur avant de supprimer la catégorie
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Voulez-vous vraiment supprimer cette Catégorie ?");
            confirmation.setContentText("Cette action est irréversible.");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Appeler le service pour supprimer la catégorie
                    categoriesService.supprimer(categorieSelectionne.getCategorieID());

                    // Actualiser la liste des catégories affichée dans la ListView
                    List<Categories> categories = categoriesService.recuperer();
                    ObservableList<Categories> observableList = FXCollections.observableArrayList(categories);
                    ListCategorie.setItems(observableList);

                    // Afficher une notification de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setContentText("La catégorie a été supprimée avec succès !");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // En cas d'erreur lors de la suppression, afficher une alerte d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de la suppression");
                    alert.setContentText("Une erreur s'est produite lors de la suppression de la catégorie : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Afficher une alerte si aucune catégorie n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une catégorie à supprimer.");
            alert.showAndWait();
        }
    }

    public void modifCategorie(ActionEvent actionEvent) {
        // Récupérer la catégorie sélectionnée dans la ListView
        Categories categorieSelectionne = ListCategorie.getSelectionModel().getSelectedItem();

        if (categorieSelectionne != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/ModifierCategorie.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifierCategorie controller = loader.getController();
                controller.initData(categorieSelectionne);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Mettre à jour la ListView après la modification
                List<Categories> categories = categoriesService.recuperer(); // Récupérez la liste mise à jour
                ObservableList<Categories> observableList = FXCollections.observableArrayList(categories);
                ListCategorie.setItems(observableList); // Mettez à jour les données affichées
            } catch (IOException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la modification");
                alert.setContentText("Une erreur s'est produite lors de la modification de la catégorie : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner une catégorie à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    public void trieCategorie(ActionEvent actionEvent) {
        try {
            List<Categories> categories = categoriesService.recuperer();

            // Sort the categories based on their categorieID in ascending order
            Collections.sort(categories, Comparator.comparingInt(Categories::getCategorieID));

            ObservableList<Categories> observableList = FXCollections.observableArrayList(categories);
            ListCategorie.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors du tri");
            alert.setContentText("Une erreur s'est produite lors du tri des catégories : " + e.getMessage());
            alert.showAndWait();
        }
    }
}

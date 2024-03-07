package com.example.ges_reservation.controllers;
import com.example.ges_reservation.models.Categories;
import com.example.ges_reservation.services.CategoriesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ModifierCategorie {


    @FXML
    private TextField nomCategorieTF;

    @FXML
    private TextField descriptionCategorieTF;

    private final CategoriesService categoriesService = new CategoriesService();

    private Categories categorie;

    public void initData(Categories categorie) {
        this.categorie = categorie;
        // Initialize fields with the values of the current category
        nomCategorieTF.setText(categorie.getNomCategorie());
        descriptionCategorieTF.setText(categorie.getDescriptionCategorie());
    }

    @FXML
    void enregistrerMod1(ActionEvent event) {
        try {
            // Check for empty fields
            if (nomCategorieTF.getText().isEmpty() || descriptionCategorieTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champs vides");
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return; // Exit the method if any field is empty
            }

            // Update category information
            categorie.setNomCategorie(nomCategorieTF.getText());
            categorie.setDescriptionCategorie(descriptionCategorieTF.getText());

            // Save modifications to the database
            categoriesService.modifier(categorie);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setContentText("Les modifications ont été enregistrées avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            // In case of an error, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de l'enregistrement des modifications : " + e.getMessage());
            alert.showAndWait();
        }
    }
}

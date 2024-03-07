package com.example.ges_reservation.controllers;
import com.example.ges_reservation.models.Categories;
import com.example.ges_reservation.services.CategoriesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterCategorie {

    @FXML
    private TextField NomCategorieF;

    @FXML
    private TextField DescriptionCategorieF;

    private final CategoriesService categoriesService = new CategoriesService();

    @FXML
    public void AjouterFF(ActionEvent actionEvent) {
        if (NomCategorieF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        try {
            categoriesService.ajouter(new Categories(
                    NomCategorieF.getText(), DescriptionCategorieF.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout réussi");
            alert.setContentText("La catégorie a été ajoutée avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void afficherFF(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/ges_reservation/AfficherCategorie.fxml"));
            Stage window = (Stage) NomCategorieF.getScene().getWindow();
            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

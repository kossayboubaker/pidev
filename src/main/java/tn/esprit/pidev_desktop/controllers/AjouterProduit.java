package tn.esprit.pidev_desktop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjouterProduit {
    @FXML
    private TextField descriptionTF;

    @FXML
    private ImageView imageview;

    @FXML
    private Button import_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prixTF;

    @FXML
    private TextField stockTF;



    private Image image;

    @FXML
    void afficherProduit(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AfficherProduit.fxml"));
        try {
            nomTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }

    @FXML
    void ajouterProduit(ActionEvent event) {
        ProduitService ps = new ProduitService();
        Produit produit = new Produit();

        String nom = nomTF.getText().trim();
        String description = descriptionTF.getText().trim();
        String prixText = prixTF.getText().trim();
        String stockText = stockTF.getText().trim();
        String image = imageview.getImage().toString();



        if (nom.isEmpty() || description.isEmpty() || prixText.isEmpty() || stockText.isEmpty() || Data.path == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return; // Sortir de la méthode si un champ est vide
        }

        if (!isString(nom)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le nom doit être une chaîne de caractères");
            alert.showAndWait();
            return; // Sortir de la méthode si le nom n'est pas une chaîne de caractères valide
        }

        try {

            float prix = Float.parseFloat(prixText);
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setPrix(prix);
            produit.setStock(Integer.parseInt(stockText));
            produit.setImage(Data.path);

            ps.ajouter(produit);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Produit ajouté avec succès");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le prix doit être un nombre valide");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le stock doit être un nombre valide");
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        // controle de saisir pour le stock


    }

    public void importimagebtn() {
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        if (file != null) {
            Data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 120, 127, false, true);
            imageview.setImage(image);
             }


    }




    // Ajoutez votre logique de vérification du type d'image ici
    // Add your image type verification logic here
    private boolean verifyImageType(String imagePath) {
        // Ajoutez votre logique de vérification du type d'image ici
        return imagePath.endsWith(".jpg") || imagePath.endsWith(".png") || imagePath.endsWith(".jpeg");
    }
    // Méthode utilitaire pour vérifier si une chaîne est un nom valide
    private boolean isString(String str) {
        return str.matches("^[a-zA-Z\\s]+$");
    }

    public void ajoutecommandeN(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AjouterCommande.fxml"));
        try {
            descriptionTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

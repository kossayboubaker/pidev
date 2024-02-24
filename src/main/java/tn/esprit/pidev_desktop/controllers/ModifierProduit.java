package tn.esprit.pidev_desktop.controllers;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.test.HelloApplication;

import java.io.IOException;
import java.util.List;


public class ModifierProduit {

    @FXML
    private ListView<String> ListProduit;
        @FXML
        private TextArea descriptionN;

        @FXML
        private ImageView imageview;

        @FXML
        private Button import_btn;

        @FXML
        private TextArea nomN;

        @FXML
        private TextArea prixN;

        @FXML
        private TextArea stockN;

        @FXML
        void importimagebtn(ActionEvent event) {

        }









    @FXML

    void modifierproduitN(ActionEvent event) {
        // Récupérer les valeurs des champs de texte
        String description = descriptionN.getText();
        String nom = nomN.getText();
        String prix = prixN.getText();
        String stock = stockN.getText();

        // Récupérer le produit sélectionné de la liste des produits


        // Modifier les labels de l'interface graphique avec les nouvelles valeurs
        descriptionN.setText(description);
        nomN.setText(nom);
        prixN.setText(prix);
        stockN.setText(stock);

        // Modifier les informations du produit sélectionné

        // Exemple d'utilisation :
        // labelDescription.setText(description);
        // labelNom.setText(nom);
        // labelPrix.setText(prix);
        // labelStock.setText(stock);
    }



    private boolean isString(String text) {
        return text.matches("[a-zA-Z\\s]+");
    }

    private void afficherErreur(String erreur, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(erreur);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessage(String succès, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(succès);
        alert.setContentText(message);
        alert.showAndWait();
    }




    public void returneN(ActionEvent actionEvent)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AfficherProduit.fxml"));
        try {
            nomN.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setId(int id) {
    }
}

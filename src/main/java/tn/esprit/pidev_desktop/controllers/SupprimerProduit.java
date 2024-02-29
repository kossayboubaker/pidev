package tn.esprit.pidev_desktop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.pidev_desktop.services.ProduitService;

public class SupprimerProduit {
    @FXML
    private TextField idCol;
    @FXML
    private TextField supprimeTF;

    private ProduitService produitService;{
        produitService = new ProduitService();
    }

/*
void supprimerProduit(ActionEvent actionEvent) {
    String idProduitStr = supprimeTF.getText().trim();

    if (idProduitStr.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez saisir un ID");
        alert.showAndWait();
        return; // Sortir de la méthode si l'ID est vide
    }

    try {
        int idProduit = Integer.parseInt(idProduitStr);

        // Vérifier si l'ID est valide
        if (idProduit <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("ID invalide");
            alert.showAndWait();
            return; // Sortir de la méthode si l'ID est invalide
        }

        produitService.supprimer(idProduit);

        System.out.println("Produit supprimé avec succès");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("Produit supprimé avec succès");
        alert.showAndWait();
    } catch (NumberFormatException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("ID doit etre un entier");
        alert.showAndWait();
    } catch (SQLException e) {
        System.err.println(e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}



    public void returnToAfficher(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/pidev_desktop/AfficherProduit.fxml"));
        try {
            supprimeTF.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }*/
}




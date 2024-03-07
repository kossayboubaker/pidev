package com.example.ges_reservation.controllers;
import com.example.ges_reservation.models.Films;
import com.example.ges_reservation.services.FilmsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class ModifierFilm {

    @FXML
    private TextField anneeSortieTF;

    @FXML
    private TextField categorieTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField dureeTF;


    @FXML
    private TextField realisateurTF;

    @FXML
    private TextField titreTF;


    private final FilmsService ps = new FilmsService();

    private Films film;


    public void initData(Films film) {
        this.film = film;
        // Initialiser les champs avec les valeurs du film actuel
        titreTF.setText(film.getTitre());
        realisateurTF.setText(film.getRealisateur());
        anneeSortieTF.setText(String.valueOf(film.getAnneeSortie()));
        dureeTF.setText(String.valueOf(film.getDuree()));
        descriptionTF.setText(film.getSynopsis());
        categorieTF.setText(String.valueOf(film.getCategorieID()));
    }

    @FXML
    void enregistrerMod(ActionEvent event) {

        try {
            // Vérification des champs de saisie
            if (titreTF.getText().isEmpty() || realisateurTF.getText().isEmpty() || anneeSortieTF.getText().isEmpty()|| dureeTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || categorieTF.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Champs vides");
                alert.setContentText("Veuillez remplir tous les champs !");
                alert.showAndWait();
                return; // Sortie de la méthode si un champ est vide
            }



            // Mettre à jour les informations du film
            film.setTitre(titreTF.getText());
            film.setRealisateur(realisateurTF.getText());
            film.setAnneeSortie(Integer.parseInt(anneeSortieTF.getText()));
            film.setDuree(Integer.parseInt(dureeTF.getText()));
            film.setSynopsis(descriptionTF.getText());
            film.setCategorieID(Integer.parseInt(categorieTF.getText()));

            // Enregistrer les modifications dans la base de données
            ps.modifier(film);
            // Affichage d'un message de réussite
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setContentText("Les modifications ont été enregistrées avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            // En cas d'erreur, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de l'enregistrement des modifications : " + e.getMessage());
            alert.showAndWait();
        }
    }
}

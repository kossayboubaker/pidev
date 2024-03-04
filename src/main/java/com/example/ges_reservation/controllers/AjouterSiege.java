package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.SiegeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjouterSiege {

    @FXML
    private TextField reservationID;

    @FXML
    private TextField statut;

    private final SiegeService siegeService = new SiegeService();

    @FXML
    void ajouterSieges(ActionEvent event) {
        // Récupérer les valeurs des champs de texte
        String idReservation = reservationID.getText();
        String status = statut.getText();

        if (idReservation.isEmpty() || status.isEmpty()) {
            // Afficher une alerte pour informer l'utilisateur que tous les champs doivent être remplis
            afficherAlerte("Champs vides", "Veuillez remplir tous les champs.", Alert.AlertType.WARNING);
        } else {
            try {
                int nombrePlacesDisponibles = Integer.parseInt(idReservation); // Modifier pour obtenir NombrePlacesDisponibles

                // Vérifier si le nombre de places disponibles est divisible par 10
                if (nombrePlacesDisponibles % 10 != 0) {
                    afficherAlerte("Erreur", "Le nombre total de places doit être divisible par 10 pour créer des rangées complètes de sièges.", Alert.AlertType.ERROR);
                    return;
                }

                // Calculer le nombre de rangées (10 sièges par rangée)
                int nombreDeRangees = nombrePlacesDisponibles / 10;

                // Définir les lettres de siège à utiliser
                String[] lettresSiege = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

                // Générer et ajouter les sièges en fonction des lettres de siège définies
                for (int i = 0; i < nombreDeRangees; i++) {
                    // Récupérer la lettre de la rangée
                    String lettreRangee = lettresSiege[i];

                    // Générer les numéros de siège correspondants pour chaque lettre (de 1 à 10)
                    for (int j = 1; j <= 10; j++) {
                        // Construire le numéro de siège (par exemple, "A1", "A2", ..., "B1", "B2", ...)
                        String numeroSiege = lettreRangee + j;

                        // Ajouter le siège en utilisant le service SiegeService
                        siegeService.ajouter(new Sieges(4, Integer.parseInt(idReservation), numeroSiege, status));
                    }
                }

                // Réinitialiser les champs de texte
                reservationID.clear();
                statut.clear();

                // Afficher une confirmation que les sièges ont été ajoutés avec succès
                afficherAlerte("Sièges ajoutés", "Les sièges ont été ajoutés avec succès.", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                // Afficher une alerte si l'identifiant de réservation n'est pas valide
                afficherAlerte("Erreur", "Veuillez saisir un nombre valide pour NombrePlacesDisponibles.", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                // Afficher une alerte en cas d'erreur lors de l'ajout des sièges
                afficherAlerte("Erreur", "Une erreur s'est produite lors de l'ajout des sièges : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}

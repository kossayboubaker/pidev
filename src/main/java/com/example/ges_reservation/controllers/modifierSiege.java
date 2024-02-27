package com.example.ges_reservation.controllers;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.ReservationService;
import com.example.ges_reservation.services.SiegeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class modifierSiege {
    @FXML
    private TextField numeroSiegeTFF;

    @FXML
    private TextField reservationIDTFF;

    @FXML
    private TextField statutTF;

    private final SiegeService ps = new SiegeService();

    private Sieges siege;


    public void initData(Sieges siege) {
        this.siege = siege;
        // Initialiser les champs avec les valeurs du siège actuel
        numeroSiegeTFF.setText(siege.getNumeroSiege());
        reservationIDTFF.setText(String.valueOf(siege.getReservationID()));
        statutTF.setText(siege.getStatut());
    }

    @FXML
    void enregistrerModS(ActionEvent event) {
        try {
            if (siege != null) {
                String statut = statutTF.getText().trim();

                if (!statut.matches("disponible|non disponible")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Statut invalide");
                    alert.setContentText("Le statut doit être 'disponible' ou 'non disponible'.");
                    alert.showAndWait();
                    return;
                }
                siege.setNumeroSiege(numeroSiegeTFF.getText());
                siege.setReservationID(Integer.parseInt(reservationIDTFF.getText()));
                siege.setStatut(statut);
                ps.modifier(siege);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification réussie");
                alert.setContentText("Les modifications du siège ont été enregistrées avec succès.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setContentText("Veuillez sélectionner un siège à modifier.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir un numéro de réservation valide.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de la modification du siège : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void retourner(ActionEvent actionEvent) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) numeroSiegeTFF.getScene().getWindow();
        stage.close();
    }
}


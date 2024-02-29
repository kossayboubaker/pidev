import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.evenement;
import services.evenementservice;

import java.sql.SQLException;
import java.util.Date;

import static java.sql.Date.valueOf;

public class Ajouterevenement  {
    evenementservice cs = new evenementservice();
    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private TextField idcinema;

    @FXML
    private TextField nomev;

    @FXML
    private TextField periode;

    @FXML
    public void Ajouter(ActionEvent actionEvent) {
        try {
            // Récupérer les données saisies dans les champs de texte
            int idCinema = Integer.parseInt(idcinema.getText());
            String nomEv = nomev.getText();
            String descriptionEv = description.getText();
            Date dateEv = valueOf(date.getValue());
            String periodeEv = periode.getText();


            evenement evenement = new evenement(idCinema, nomEv, descriptionEv, dateEv, periodeEv);

            // Boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir ajouter cet événement ?");

            // Ajout des boutons de confirmation
            ButtonType buttonYes = new ButtonType("Oui");
            ButtonType buttonNo = new ButtonType("Non");

            confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

            // Affichage du dialogue de confirmation et traitement de la réponse
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == buttonYes) {
                    try {
                        // Ajout de l'événement
                        cs.ajouter(evenement);

                        // Affichage d'un message de confirmation
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Succès");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("L'événement a été ajouté avec succès.");
                        successAlert.showAndWait();
                    } catch (SQLException e) {
                        // Affichage d'une boîte de dialogue d'erreur en cas d'échec de l'ajout
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.showAndWait();
                    }
                } else {
                    // Annuler l'opération d'ajout si l'utilisateur clique sur "Non"
                    System.out.println("L'ajout de l'événement a été annulé par l'utilisateur.");
                }
            });
        } catch (NumberFormatException e) {
            // Gérer une exception si un champ numérique est mal saisi
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir des données valides.");
            alert.showAndWait();
        }

    }


    }


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.evenement;
import services.evenementservice;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class Ajouterevenement implements Initializable {
    evenementservice cs = new evenementservice();
    @FXML
    private TextField date;

    @FXML
    private TextField description;

    @FXML
    private TextField id;

    @FXML
    private TextField idcinema;

    @FXML
    private ListView<evenement> listevenement;

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
            String dateEv = date.getText();
            String periodeEv = periode.getText();

            // Créer un objet Evenement avec les nouvelles valeurs
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
    @FXML
    public void Supprimer(ActionEvent actionEvent) throws SQLException {

        int idS = Integer.parseInt(id.getText());

        evenementservice cs = new evenementservice();

// Boîte de dialogue confirmative
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

// Ajout des boutons de confirmation
        ButtonType buttonYes = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

// Affichage du dialogue de confirmation et traitement de la réponse
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            try {
                cs.supprimer(idS);

                // Boîte de dialogue affirmative
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'événement a été supprimé avec succès.");
                successAlert.showAndWait();
            } catch (SQLException e) {
                // Boîte de dialogue d'erreur en cas d'échec de suppression
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setContentText(e.getMessage());
                errorAlert.showAndWait();
            }
        } else {
            System.out.println("Suppression de l'événement annulée par l'utilisateur.");
        }

    }
    @FXML
    void Refresh(ActionEvent event)
    {
        try {
            List<evenement> evenements = cs.recuperer();
            ObservableList<evenement> observableList = FXCollections.observableArrayList(evenements);
            listevenement.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void Modifier() {
        try {
            // Récupérer les données saisies dans les champs de texte
            int idd = Integer.parseInt(id.getText());
            int idc = Integer.parseInt(idcinema.getText());
            String nomm = nomev.getText();
            String descriptionn = description.getText();
            String datee = date.getText();
            String periodee = periode.getText();

            // Créer un objet Evenement avec les nouvelles valeurs
            evenement cs = new evenement(idd, idc, nomm, descriptionn, datee, periodee);
            evenementservice evenementservice = new evenementservice();

            // Boîte de dialogue confirmative
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir modifier cet événement ?");

            // Ajout des boutons de confirmation
            ButtonType buttonYes = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmationDialog.getButtonTypes().setAll(buttonYes, buttonNo);

            // Affichage du dialogue de confirmation et traitement de la réponse
            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                // Modification de l'événement
                evenementservice.modifer(cs);

                // Afficher un message de confirmation
                afficherMessage("Succès", "L'événement a été modifié avec succès.");
            } else {
                System.out.println("Modification de l'événement annulée par l'utilisateur.");
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur", "Veuillez saisir un ID valide.");
        } catch (SQLException e) {
            afficherErreur("Erreur", "Erreur lors de la modification de l'événement : " + e.getMessage());
        }


    }
    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<evenement> evenements = cs.recuperer();
            ObservableList<evenement> observableList = FXCollections.observableArrayList(evenements);
            listevenement.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void Retour(ActionEvent event) throws IOException {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/ajoutercinema.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        date.getScene().setRoot(root);

    }
}

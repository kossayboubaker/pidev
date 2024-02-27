import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.cinema;
import services.cinemaservice;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Affichercinema  {

    cinemaservice cs = new cinemaservice();
    @FXML
    private TextField etat;


    @FXML
    private TextField nbsalle;

    @FXML
    private TextField nom;

    @FXML
    private TextField place;
    @FXML
    private ListView<cinema> listcinema;
    private cinema cinema;
    public void initialize() {
        try {
            List<cinema> cinemas = cs.recuperer();
            ObservableList<cinema> observableList = FXCollections.observableArrayList(cinemas);
            listcinema.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    public void Supprimer(ActionEvent actionEvent) {
        cinema cinemaSelectionne = listcinema.getSelectionModel().getSelectedItem();

        if (cinemaSelectionne != null) {
            // Demander une confirmation à l'utilisateur avant de supprimer le siège
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Voulez-vous vraiment supprimer ce siège ?");
            confirmation.setContentText("Cette action est irréversible.");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    cs.supprimer(cinemaSelectionne.getId());

                    // Actualiser la liste des sièges affichée dans la ListView
                    List<cinema> sieges = cs.recuperer();
                    ObservableList<cinema> observableList = FXCollections.observableArrayList(sieges);
                    listcinema.setItems(observableList);

                    // Afficher une notification de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setContentText("Le siège a été supprimé avec succès !");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // En cas d'erreur lors de la suppression, afficher une alerte d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de la suppression");
                    alert.setContentText("Une erreur s'est produite lors de la suppression du siège : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Afficher une alerte si aucun siège n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un siège à supprimer.");
            alert.showAndWait();
        }
    }

       @FXML
       void Modifier(ActionEvent event) throws SQLException {
           cinema cinemaSelectionne = listcinema.getSelectionModel().getSelectedItem();
           if (cinemaSelectionne != null) {
               // Créer une boîte de dialogue de confirmation
               Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
               confirmationDialog.setTitle("Confirmation de la modification");
               confirmationDialog.setHeaderText("Modifier les détails du cinéma ?");
               confirmationDialog.setContentText("Êtes-vous sûr de vouloir modifier les détails du cinéma sélectionné ?");

               Optional<ButtonType> result = confirmationDialog.showAndWait();
               if (result.isPresent() && result.get() == ButtonType.OK) {
                   try {
                       // Mettre à jour les détails du cinéma
                       cinemaSelectionne.setNb_salle(Integer.parseInt(nbsalle.getText()));
                       cinemaSelectionne.setEtat(etat.getText());
                       cinemaSelectionne.setNom(nom.getText());
                       cinemaSelectionne.setPlace(place.getText());

                       // Appeler la méthode de service pour mettre à jour les détails du cinéma dans la base de données
                       cs.modifer(cinemaSelectionne);

                       // Mettre à jour la ListView avec les détails du cinéma modifiés
                       List<cinema> cinemas = cs.recuperer();
                       ObservableList<cinema> observableList = FXCollections.observableArrayList(cinemas);
                       listcinema.setItems(observableList);

                       // Afficher un message de succès
                       Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                       successAlert.setTitle("Modification réussie");
                       successAlert.setContentText("Les détails du cinéma ont été modifiés avec succès !");
                       successAlert.showAndWait();
                   } catch (NumberFormatException e) {
                       // Gérer NumberFormatException si la conversion échoue
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Erreur de format");
                       alert.setContentText("Veuillez saisir un nombre valide pour le nombre de salles.");
                       alert.showAndWait();
                   } catch (SQLException e) {
                       // Gérer SQLException
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Erreur lors de la modification");
                       alert.setContentText("Une erreur s'est produite lors de la modification du cinéma : " + e.getMessage());
                       alert.showAndWait();
                   }
               }
           } else {
               // Afficher un avertissement si aucun cinéma n'est sélectionné
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Aucune sélection");
               alert.setContentText("Veuillez sélectionner un cinéma à modifier.");
               alert.showAndWait();
           }
    }


    public void modclicked(MouseEvent event) {
            cinema cinemaSelectionne = listcinema.getSelectionModel().getSelectedItem();
            // Assurez-vous que l'objet cinema est initialisé et non null
            if (cinemaSelectionne != null){
                nbsalle.setText(String.valueOf(cinemaSelectionne.getNb_salle()));
                etat.setText(cinemaSelectionne.getEtat());
                nom.setText(cinemaSelectionne.getNom());
                place.setText(cinemaSelectionne.getPlace());
            } else {
                System.out.println("L'objet affichercinema n'est pas initialisé.");
            }

    }
}

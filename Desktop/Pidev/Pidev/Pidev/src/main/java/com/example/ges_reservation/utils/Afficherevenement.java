package com.example.ges_reservation.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.example.ges_reservation.models.evenement;
import com.example.ges_reservation.services.evenementservice;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Date.valueOf;

public class Afficherevenement {

    evenementservice cs = new evenementservice();
    ObservableList <evenement> observableList;
    @FXML
    private TextField search;
    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private TextField idcinema;

    @FXML
    private ListView<evenement> listevenement;

    @FXML
    private TextField nomev;

    @FXML
    private TextField periode;

    public void initialize() {
        try {
            List<evenement> cinemas = cs.recuperer();
            ObservableList<evenement> observableList = FXCollections.observableArrayList(cinemas);
            listevenement.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void Supprimer(ActionEvent actionEvent) {
    evenement evenementSelectionne = listevenement.getSelectionModel().getSelectedItem();

        if (evenementSelectionne != null) {
            // Demander une confirmation à l'utilisateur avant de supprimer le siège
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Voulez-vous vraiment supprimer ce siège ?");
            confirmation.setContentText("Cette action est irréversible.");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    cs.supprimer(evenementSelectionne.getId());

                    // Actualiser la liste des sièges affichée dans la ListView
                    List<evenement> evenements = cs.recuperer();
                    ObservableList<evenement> observableList = FXCollections.observableArrayList(evenements);
                    listevenement.setItems(observableList);

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

    public void Modifier(ActionEvent actionEvent) throws SQLException{
        evenement evenementSelectionne = listevenement.getSelectionModel().getSelectedItem();
        if (evenementSelectionne != null) {
            // Créer une boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de la modification");
            confirmationDialog.setHeaderText("Modifier les détails du cinéma ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir modifier les détails du cinéma sélectionné ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Mettre à jour les détails du cinéma
                    evenementSelectionne.setId_cinema(Integer.parseInt(idcinema.getText()));
                    evenementSelectionne.setNom_ev(nomev.getText());
                    evenementSelectionne.setDescription(description.getText());
                    evenementSelectionne.setPeriode(periode.getText());
                    evenementSelectionne.setDate(valueOf(date.getValue()));

                    // Appeler la méthode de service pour mettre à jour les détails du cinéma dans la base de données
                    cs.modifer(evenementSelectionne);

                    // Mettre à jour la ListView avec les détails du cinéma modifiés
                    List<evenement> evenements = cs.recuperer();
                    ObservableList<evenement> observableList = FXCollections.observableArrayList(evenements);
                    listevenement.setItems(observableList);

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
        evenement evenementSelectionne = listevenement.getSelectionModel().getSelectedItem();
        // Assurez-vous que l'objet cinema est initialisé et non null
        if (evenementSelectionne != null){
            idcinema.setText(String.valueOf(evenementSelectionne.getId_cinema()));
            nomev.setText(evenementSelectionne.getNom_ev());
            description.setText(evenementSelectionne.getDescription());
            periode.setText(evenementSelectionne.getPeriode());
            date.setValue(LocalDate.parse(String.valueOf(evenementSelectionne.getDate())));

        } else {
            System.out.println("L'objet affichercinema n'est pas initialisé.");
        }
    }

    @FXML
    void stat(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/stat.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nomev.getScene().setRoot(root);
    }


    @FXML
    void search(KeyEvent event) throws SQLException {
        String searchText = search.getText();
        if(searchText.isEmpty())
        {

            listevenement.setItems(observableList);


        }else
        {
            List<evenement> searchResults = search(searchText);
            listevenement.setItems(FXCollections.observableList(searchResults));
            cs.recuperer();

        }
    }

    private List<evenement> search(String searchText) {

        List<evenement> searchResults = new ArrayList<>();
        try {
            searchResults = cs.search(searchText);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return searchResults;
    }

}




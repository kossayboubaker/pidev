package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AfficherUserAdmin {
    @FXML
    private PieChart ListChart;
    public TextField searchTextField;
    public ListView<String> listdesusers;

    private final UserService userService;

    public AfficherUserAdmin() {
        try {
            userService = new UserService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        try {
            List<User> utilisateurs = userService.recuppererSansChampsSensibles2();
            Map<String, Integer> adressesCount = new HashMap<>();

            // Compter le nombre d'occurrences de chaque adresse
            for (User utilisateur : utilisateurs) {
                String adresse = utilisateur.getAdresse();
                adressesCount.put(adresse, adressesCount.getOrDefault(adresse, 0) + 1);
            }

            // Calculer le total des adresses
            int totalAdresses = utilisateurs.size();

            // Ajouter les données au PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Map.Entry<String, Integer> entry : adressesCount.entrySet()) {
                String adresse = entry.getKey();
                int count = entry.getValue();
                double percentage = (count / (double) totalAdresses) * 100;
                pieChartData.add(new PieChart.Data(adresse, percentage));
            }

            ListChart.setData(pieChartData);

            listdesusers.getItems().clear();
            listdesusers.getItems().add(String.format("%-5s %-20s %-20s %-30s %s", "ID", "Nom", "Prenom", "Adresse", "Email"));
            for (User utilisateur : utilisateurs) {
                listdesusers.getItems().add(String.format("%-5s %-20s %-20s %-30s %s",
                        utilisateur.getId(),
                        utilisateur.getNom(),
                        utilisateur.getPrenom(),
                        utilisateur.getAdresse(),
                        utilisateur.getEmail()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception de récupération des utilisateurs depuis la base de données
        }
    }

    public void BackToUserAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/test.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void ModifierUserAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        String selectedItem = listdesusers.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.trim().isEmpty() && !selectedItem.startsWith("ID")) {
            int userId = Integer.parseInt(selectedItem.split("\\s+")[0]);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/ModifierUserAdmin.fxml"));
            Parent root = loader.load();
            ModifierUserAdmin controller = loader.getController();
            controller.initData(userId);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun utilisateur sélectionné");
            alert.setContentText("Veuillez sélectionner un utilisateur à modifier.");
            alert.showAndWait();
        }
    }

    public void rechercherUtilisateurs(KeyEvent keyEvent) {
        // Implémenter la recherche d'utilisateurs ici
    }

    public void ToCodeAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/CodeAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void ToDashboard(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void ToUserAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/UserAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void BackToLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/LOGIN.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void SupprimerBoutton(javafx.event.ActionEvent actionEvent) {
        String selectedItem = listdesusers.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.trim().isEmpty() && !selectedItem.startsWith("ID")) {
            int userId = Integer.parseInt(selectedItem.split("\\s+")[0]);
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    userService.supprimer(userId);
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur lors de la suppression");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur s'est produite lors de la suppression de l'utilisateur.");
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun utilisateur sélectionné");
            alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.showAndWait();
        }
    }

}
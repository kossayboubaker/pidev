package com.example.ges_reservation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.example.ges_reservation.models.User;
import com.example.ges_reservation.services.UserService;
import com.example.ges_reservation.test.HelloApplication;

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
            listdesusers.getItems().add(String.format("%-12s | %-30s | %-30s | %-40s | %s", "ID", "Nom", "Prenom", "Adresse", "Email"));
            for (User utilisateur : utilisateurs) {
                listdesusers.getItems().add(String.format("%-12s | %-30s | %-30s | %-40s | %s",
                        utilisateur.getId(),
                        utilisateur.getNom(),
                        utilisateur.getPrenom(),
                        utilisateur.getAdresse(),
                        utilisateur.getEmail()));
            }

            // Appliquer le style CSS à chaque élément de la ListView
            listdesusers.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("-fx-background-color: transparent;");
                    } else {
                        setText(item);
                        setStyle("-fx-background-color: " + (getIndex() % 2 == 0 ? "#f0f0f0;" : "#e0e0e0;") + // Couleur de fond alternée
                                "-fx-padding: 10px;" +
                                "-fx-font-family: Constantia;" + // Police Constantia
                                "-fx-font-weight: bold;" + // Texte en gras
                                "-fx-font-size: 14px;"); // Taille de police
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception de récupération des utilisateurs depuis la base de données
        }
    }

    public void BackToUserAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUserAdmin.fxml"));
            Parent root = loader.load();
            com.example.ges_reservation.controllers.ModifierUserAdmin controller = loader.getController();
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/CodeAdmin.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void ToDashboard(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void ToUserAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/UserAdmin.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LOGIN.fxml"));
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

    @FXML
    void Search(ActionEvent event) {
        String searchText = searchTextField.getText().toLowerCase(); // Récupérer le texte de recherche et le mettre en minuscules

        ObservableList<String> items = listdesusers.getItems(); // Récupérer la liste des éléments actuels
        ObservableList<String> filteredItems = FXCollections.observableArrayList(); // Créer une nouvelle liste pour stocker les éléments filtrés

        for (String item : items) {
            if (item.toLowerCase().contains(searchText)) { // Vérifier si l'élément contient le texte de recherche
                filteredItems.add(item); // Ajouter l'élément filtré à la nouvelle liste
            }
        }

        listdesusers.setItems(filteredItems); // Mettre à jour la liste avec les éléments filtrés
    }
    public void TrierListe(ActionEvent actionEvent) {
        try {
            List<User> utilisateurs = userService.recuppererUtilisateursTriesParNom();
            ObservableList<String> userList = FXCollections.observableArrayList();

            userList.add(String.format("%-12s | %-30s | %-30s | %-40s | %s", "ID", "Nom", "Prenom", "Adresse", "Email"));
            for (User utilisateur : utilisateurs) {
                userList.add(String.format("%-12s | %-30s | %-30s | %-40s | %s",
                        utilisateur.getId(),
                        utilisateur.getNom(),
                        utilisateur.getPrenom(),
                        utilisateur.getAdresse(),
                        utilisateur.getEmail()));
            }

            listdesusers.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception lors du tri des utilisateurs
        }
    }

}

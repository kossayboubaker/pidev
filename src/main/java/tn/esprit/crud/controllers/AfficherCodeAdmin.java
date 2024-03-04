package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.crud.models.CodePromo;
import tn.esprit.crud.services.PromoService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AfficherCodeAdmin {

    @FXML
    private ListView<String> LVcmd;
    @FXML
    private PieChart CodePie;

    private PromoService promoService;

    public AfficherCodeAdmin() {
        try {
            promoService = new PromoService();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            List<CodePromo> codesPromo = promoService.recupperer();

            // Map pour stocker le nombre de codes promos par utilisateur
            Map<Integer, Integer> codePromoCountMap = new HashMap<>();

            // Compter le nombre de codes promos par utilisateur
            for (CodePromo codePromo : codesPromo) {
                int userId = codePromo.getUser_id();
                codePromoCountMap.put(userId, codePromoCountMap.getOrDefault(userId, 0) + 1);
            }

            // Ajouter les données regroupées au PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Integer userId : codePromoCountMap.keySet()) {
                pieChartData.add(new PieChart.Data("User " + userId, codePromoCountMap.get(userId)));
            }

            // Définir les données du PieChart
            CodePie.setData(pieChartData);

            // Afficher les pourcentages à côté des tranches de PieChart
            CodePie.setLabelsVisible(true);
            CodePie.setStartAngle(180);

            // Configurer la légende si nécessaire
            CodePie.legendVisibleProperty().setValue(true);

            // Mettre à jour le titre du PieChart
            CodePie.setTitle("Nombre de codes promos par utilisateur");

            // Maintenant, vous pouvez afficher la liste des codes promos dans LVcmd
            ObservableList<String> codePromoList = FXCollections.observableArrayList();
            String columnTitles = String.format("  %-20s %-20s %-20s %-20s %-20s", "ID", "Code", "Date d'expiration", "Utilisé" , "User ID");
            codePromoList.add(columnTitles);

            for (CodePromo codePromo : codesPromo) {
                String codePromoData = String.format(" %-20s %-20s %-20s %-20s %-20s",
                        codePromo.getId(),
                        codePromo.getCode(),
                        codePromo.getDate_exp(),
                        codePromo.getUtilise(),
                        codePromo.getUser_id());

                codePromoList.add(codePromoData);
            }

            LVcmd.setItems(codePromoList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ModifierCodeAdmin(ActionEvent actionEvent) {
        if (LVcmd.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun code promo sélectionné");
            alert.setContentText("Veuillez sélectionner un code promo à modifier.");
            alert.showAndWait();
            return;
        }

        String selectedCode = LVcmd.getSelectionModel().getSelectedItem();

        if (selectedCode == null || selectedCode.trim().isEmpty() || selectedCode.startsWith("ID")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection invalide");
            alert.setContentText("Veuillez sélectionner un code promo valide à modifier.");
            alert.showAndWait();
            return;
        }

        int codeId = Integer.parseInt(selectedCode.split("\\s+")[1]);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la modification");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce code promo ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/ModifierCodeAdmin.fxml"));
            try {
                Parent root = loader.load();
                ModifierCodeAdmin controller = loader.getController();
                // Passer l'ID du code promo au contrôleur de la fenêtre de modification
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void SupprimerCodeAdmin(ActionEvent event) {
        if (LVcmd.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun code promo sélectionné");
            alert.setContentText("Veuillez sélectionner un code promo à supprimer.");
            alert.showAndWait();
            return;
        }

        String selectedCode = LVcmd.getSelectionModel().getSelectedItem();

        if (selectedCode == null || selectedCode.trim().isEmpty() || selectedCode.startsWith("ID")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sélection invalide");
            alert.setContentText("Veuillez sélectionner un code promo valide à supprimer.");
            alert.showAndWait();
            return;
        }

        int codeId = Integer.parseInt(selectedCode.split("\\s+")[1]);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce code promo ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Appeler la méthode de suppression du service PromoService en utilisant l'ID du code promo
                promoService.supprimer(codeId);

                // Rafraîchir l'affichage après la suppression
                initialize();

                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Suppression réussie");
                successAlert.setContentText("Le code promo a été supprimé avec succès.");
                successAlert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                // Afficher une alerte en cas d'erreur lors de la suppression
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur de suppression");
                errorAlert.setContentText("Une erreur s'est produite lors de la suppression du code promo.");
                errorAlert.showAndWait();
            }

        }

    }



    @FXML
    void BackToCodeAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/test.fxml"));
        try {
            LVcmd.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
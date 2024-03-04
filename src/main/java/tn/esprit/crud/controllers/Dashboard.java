package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import tn.esprit.crud.models.CodePromo;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.PromoService;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.test.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard {

    @FXML
    private PieChart CodeChart;

    @FXML
    private PieChart AdresseChart;

    @FXML
    private ListView<String> ListChart;

    @FXML
    private ListView<String> ListCode;

    private UserService userService;
    private PromoService codePromoService;

    public Dashboard() {
        try {
            userService = new UserService();
            codePromoService = new PromoService();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    @FXML
    public void initialize() {
        afficherListeUtilisateurs();
        afficherGraphiqueAdresse();
        afficherGraphiqueCodesPromos();
    }

    private void afficherListeUtilisateurs() {
        try {
            List<User> users = userService.recupperer();

            for (User user : users) {
                String nomAdresse = user.getNom() + " - " + user.getAdresse();
                ListChart.getItems().add(nomAdresse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    private void afficherGraphiqueAdresse() {
        try {
            List<User> users = userService.recupperer();

            // Map pour stocker le nombre d'utilisateurs par adresse
            Map<String, Integer> adresseCountMap = new HashMap<>();

            // Compter le nombre d'utilisateurs par adresse
            for (User user : users) {
                String adresse = user.getAdresse();
                adresseCountMap.put(adresse, adresseCountMap.getOrDefault(adresse, 0) + 1);
            }

            // Ajouter les données regroupées au PieChart
            for (String adresse : adresseCountMap.keySet()) {
                AdresseChart.getData().add(new PieChart.Data(adresse, adresseCountMap.get(adresse)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    private void afficherGraphiqueCodesPromos() {
        try {
            List<CodePromo> codesPromos = codePromoService.recupperer();

            // Map pour stocker le nombre de codes promos par utilisateur
            Map<Integer, Integer> codePromoCountMap = new HashMap<>();

            // Compter le nombre de codes promos par utilisateur
            for (CodePromo codePromo : codesPromos) {
                int userId = codePromo.getUserId();
                codePromoCountMap.put(userId, codePromoCountMap.getOrDefault(userId, 0) + 1);
            }

            // Ajouter les données regroupées au PieChart
            for (Integer userId : codePromoCountMap.keySet()) {
                User user = userService.getById(userId);
                String userFullName = user.getNom() + " " + user.getPrenom();
                ListCode.getItems().add(userFullName + " - " + codePromoCountMap.get(userId));
            }

            // Ajouter les données au PieChart pour les codes promos
            for (String userFullName : ListCode.getItems()) {
                String[] userInfo = userFullName.split(" - ");
                String userName = userInfo[0];
                int codePromoCount = Integer.parseInt(userInfo[1]);
                CodeChart.getData().add(new PieChart.Data(userName, codePromoCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception correctement
        }
    }

    @FXML
    void backToAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/PageAdmin.fxml"));
        try {
            ListChart.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ToCodeAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/PageAdmin.fxml"));
        try {
            ListChart.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ToUserAdmin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/UserAdmin.fxml"));
        try {
            ListChart.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

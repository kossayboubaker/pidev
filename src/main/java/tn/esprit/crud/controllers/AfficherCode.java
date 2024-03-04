package tn.esprit.crud.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import tn.esprit.crud.models.CodePromo;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import tn.esprit.crud.test.HelloApplication;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.PromoService;
import tn.esprit.crud.services.UserService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AfficherCode {

    @FXML
    private ListView<Integer> codeCol;

    @FXML
    private ListView<Date> dateCol;

    @FXML
    private ListView<Integer> idCol;

    @FXML
    private ListView<Integer> useridCol;

    @FXML
    private ListView<Integer> utiliseCol;



    @FXML
    void VersAjouter(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/AjouterCode.fxml"));
        try {
            idCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VersModifier(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/ModifierCode.fxml"));
        try {
            idCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @FXML
    void VersSupprimer(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/SupprimerCode.fxml"));
        try {
            idCol.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        PromoService promoService = null;
        try {
            promoService = new PromoService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            List<CodePromo> codesPromo = promoService.recupperer();
            ObservableList<Integer> codeList = FXCollections.observableArrayList();
            ObservableList<Date> dateList = FXCollections.observableArrayList();
            ObservableList<Integer> idList = FXCollections.observableArrayList();
            ObservableList<Integer> userIdList = FXCollections.observableArrayList();
            ObservableList<Integer> utiliseList = FXCollections.observableArrayList();

            for (CodePromo codePromo : codesPromo) {
                codeList.add(codePromo.getCode());
                dateList.add(codePromo.getDate_exp());
                idList.add(codePromo.getId());
                userIdList.add(codePromo.getUser_id());
                utiliseList.add(codePromo.getUtilise());
            }

            codeCol.setItems(codeList);
            dateCol.setItems(dateList);
            idCol.setItems(idList);
            useridCol.setItems(userIdList);
            utiliseCol.setItems(utiliseList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

}

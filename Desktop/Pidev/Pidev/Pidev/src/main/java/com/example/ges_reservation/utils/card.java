package com.example.ges_reservation.utils;

import com.example.ges_reservation.models.cinema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.ges_reservation.models.cinema;
import com.example.ges_reservation.models.evenement;
import com.example.ges_reservation.services.evenementservice;
import com.example.ges_reservation.test.MainFX;

import java.io.IOException;
import java.sql.SQLException;

public class card {

    @FXML
    private HBox consultez;
    @FXML
    private Text etat;

    @FXML
    private Text id;

    @FXML
    private Text nbsalle;

    @FXML
    private Text nom;

    @FXML
    private Text place;


    public void setProductData(cinema cinema) {
        id.setText("" + cinema.getId());
        nom.setText("" + cinema.getNom());
        nbsalle.setText("" + cinema.getNb_salle());
        place.setText("" + cinema.getPlace());
        etat.setText("" + cinema.getEtat());

        consultez.setOnMouseClicked(event -> {


            evenementservice evenementservice = new evenementservice();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/afficheevenementfront.fxml"));
            try {

                evenement evenement = evenementservice.recuperere(cinema.getId());
                MainFX.data.id = cinema.getId();
                System.out.println("CINEMA" +MainFX.data.id);
                //System.out.println(""+evenement.getId());
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

}



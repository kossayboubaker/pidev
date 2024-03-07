package com.example.ges_reservation.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.example.ges_reservation.models.Review;
import com.example.ges_reservation.models.evenement;
import org.controlsfx.control.Rating;
import com.example.ges_reservation.services.evenementservice;
import com.example.ges_reservation.test.MainFX;

import java.sql.SQLException;

public class FormAvis {

    evenementservice evenementservice = new evenementservice();

    @FXML
    private Button btn_rev;

    @FXML
    private TextArea comments;

    @FXML
    private TextField title;

    @FXML
    private Rating value_stars;

    @FXML
    void Ajouter_Review(ActionEvent event) throws SQLException {
        try {
            evenement evenement = evenementservice.getEvenementByID(MainFX.data.id_evenement);
            evenementservice.addReview(new Review(title.getText(),comments.getText(), (int) value_stars.getRating(),evenement.getId()));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}

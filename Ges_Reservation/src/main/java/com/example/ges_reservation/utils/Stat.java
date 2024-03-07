package com.example.ges_reservation.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import com.example.ges_reservation.services.evenementservice;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Stat implements Initializable{

        evenementservice evenementservice=new evenementservice();
        @FXML
        private Pane content_area;



    @FXML
    private PieChart piechart;
        @Override
        public void initialize(URL location, ResourceBundle resources) {
            ObservableList<String> Management = FXCollections.observableArrayList( "Liste evenements", "Statistiques");

            populatePieChart();

        }


     /*   @FXML
        void retour(MouseEvent event) {


            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/Ajoutertrotinette.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pieChart.getScene().setRoot(root);

        }*/

        private void populatePieChart() {
            Map<String, Long> evenementCountsByLocation = evenementservice.getevenementCountByStationWithLieu();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Map.Entry<String, Long> entry : evenementCountsByLocation.entrySet()) {
                String nom = entry.getKey();
                long evenementCount = entry.getValue();

                // Create a label that includes the location and the number of trotinettes
                String label = nom + " (" + evenementCount + " evenement)";

                // Add data to the PieChart with the customized label
                PieChart.Data data = new PieChart.Data(label, evenementCount);
                pieChartData.add(data);
            }
            piechart.setData(pieChartData);
        }

    public void retour(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/back.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        piechart.getScene().setRoot(root);
    }
}

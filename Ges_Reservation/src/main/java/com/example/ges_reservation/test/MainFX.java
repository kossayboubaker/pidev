package com.example.ges_reservation.test;

import com.example.ges_reservation.models.Review;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public  static class data {

        public static int id =0;
        public static int id_evenement =0;

        public static Review review ;
    }


    @Override
    public void start(Stage primaryStage) {


/*
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("CineHub");

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        Stage seatStage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/LOGIN.fxml"));
            Parent seatRoot = fxmlLoader.load();
            Scene seatScene = new Scene(seatRoot);

            seatScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            seatStage.setScene(seatScene);
            seatStage.setTitle("CineHub");
            seatStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

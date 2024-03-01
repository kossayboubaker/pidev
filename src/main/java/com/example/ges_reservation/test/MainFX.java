package com.example.ges_reservation.test;

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

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/front.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFrontRes.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

           // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("CineHub");

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage seatStage = new Stage();

        try {
            FXMLLoader seatLoader = new FXMLLoader(getClass().getResource("/back.fxml"));
            Parent seatRoot = seatLoader.load();
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

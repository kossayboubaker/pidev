package com.example.ges_reservation.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);


        try {
            Parent root =loader.load() ;
            Scene scene=new Scene(root);
            // Load the CSS stylesheet

            primaryStage.setTitle("reservation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage seatStage = new Stage();
        FXMLLoader seatLoader = new FXMLLoader(getClass().getResource("/AjouterSiege.fxml"));

        try {
            Parent seatRoot = seatLoader.load();
            Scene seatScene = new Scene(seatRoot);
        //  seatScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            seatStage.setTitle("Ajouter un siège");
            seatStage.setScene(seatScene);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'interface utilisateur pour l'ajout de siège", e);
        }



        // Affichez la fenêtre d'ajout de siège
        seatStage.show();
    }
}

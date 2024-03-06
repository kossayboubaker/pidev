package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Review;

import java.io.IOException;

public class mainFX extends Application {


    public  static class data {

       public static int id =0;
       public static int id_evenement =0;

       public static Review review ;
    }

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front.fxml"));
        primaryStage.initStyle(StageStyle.UTILITY);
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }}


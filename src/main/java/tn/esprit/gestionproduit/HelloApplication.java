package tn.esprit.gestionproduit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) {
       //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test (1).fxml"));
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
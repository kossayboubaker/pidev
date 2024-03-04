package tn.esprit.crud.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/tn/esprit/crud/LOGIN.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CRUD");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void showSecondStage() {
        Stage seatStage = new Stage();
        try {
            FXMLLoader seatLoader = new FXMLLoader(getClass().getResource("/tn/esprit/crud/Dashboard.fxml"));
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

package tn.esprit.pidev_desktop.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test (1).fxml"));
        Parent root = fxmlLoader.load();
        //  CardProduct controller = fxmlLoader.getController();
       // ProduitService ps = new ProduitService();
       //
        Scene scene = new Scene(root);
        stage.setTitle("Cinehub");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
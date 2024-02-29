import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Front {

    @FXML
    private MenuButton MarketClient;

    @FXML
    private MenuButton cataClient;

    @FXML
    private MenuButton evenementbutton;

    @FXML
    private MenuButton reservationFrontbuttonmenu;

    @FXML
    private StackPane mainStackPane;

    @FXML
    void ButtonClickCinema(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/affichecinemafront.fxml"));
            Parent ajoutercinemaView = loader.load();

            // Ajouter la vue chargée au StackPane principal
            mainStackPane.getChildren().clear(); // Supprimer tout contenu précédent
            mainStackPane.getChildren().add(ajoutercinemaView);
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement de la vue
        }

    }

    @FXML
    void ReserverClient(ActionEvent event) {

    }
}

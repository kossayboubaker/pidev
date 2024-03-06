import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.cinema;
import models.evenement;
import services.evenementservice;
import tests.mainFX;
import utils.card2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Afficheevenementfront implements Initializable {
    evenementservice ps = new evenementservice();

    @FXML
    private GridPane grid2;




    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;

    cinema cs = new cinema();
    evenement ds = new evenement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduitsDansGridPane2();
        qrCodeImgModel.setVisible(false);



    }

    private void afficherProduitsDansGridPane2() {
        int column = 0;
        int row = 1;
        int id = mainFX.data.id;
        try {

                List<evenement> evenements = ps.joiner(id);
                for (int i = 0; i < evenements.size(); i++) {

                    card2 productCardController = new card2();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/card2.fxml"));
                    fxmlLoader.setController(productCardController);
                    VBox productCard = fxmlLoader.load();
                    productCardController.setProductData2(evenements.get(i));
                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    grid2.add(productCard, column++, row);
                    GridPane.setMargin(productCard, new Insets(0, 20, 20, 10));
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace(); // Gérer l'exception correctement
            }


        }
    @FXML
    void qrCodeImgModel(MouseEvent event) {
        qrCodeImgModel.setVisible(false);
    }

}



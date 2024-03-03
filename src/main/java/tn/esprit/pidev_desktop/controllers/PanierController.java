package tn.esprit.pidev_desktop.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.pidev_desktop.models.PanierItem;
import tn.esprit.pidev_desktop.services.PanierItemService;

import java.io.IOException;
import java.util.List;

public class PanierController {

    @FXML
    private Button btncon; // Button for checkout

    @FXML
    private AnchorPane menuform;

    @FXML
    private VBox menuContent; // Container for PanierItems

    @FXML
    private ScrollPane menuscroll; // ScrollPane for scrolling PanierItems

    private List<PanierItem> fetchAllpanierItems () {
        PanierItemService service = new PanierItemService();

        try {
            return service.getAllPanierItems();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initialize() {
        // Load PanierItems from the Panier and display them
        displayPanierItems();
    }

    private void displayPanierItems() {
        // Clear previous content
        menuContent.getChildren().clear();

        List<PanierItem> panierItems = fetchAllpanierItems();

        // Add each PanierItem to the menuContent VBox
        for (PanierItem panierItem : panierItems) {
            // Load PanierItemComponent.fxml and set its PanierItem
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panierItemComponent.fxml"));
            try {
                VBox panierItemComponent = loader.load();
                PanierItemComponentController controller = loader.getController();
                controller.initialize(panierItem); // Initialize PanierItemComponent with PanierItem data
                menuContent.getChildren().add(panierItemComponent);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception according to your application's requirements
            }
        }
    }

    @FXML
    private void checkout() {
        // Implement checkout logic
    }
}

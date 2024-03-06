import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import models.Review;
import models.evenement;
import services.evenementservice;
import tests.mainFX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListReviewController implements Initializable {


    evenementservice evenementservice = new evenementservice();
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane content_review;

    List<CommentsController> commentsControllers = new ArrayList<>();
    public void ReviewList() throws SQLException, IOException {
        List<Review> reviewList = evenementservice.getAllComments(mainFX.data.id_evenement);
        if (reviewList != null && !reviewList.isEmpty()) {
            GridPane gridPane = new GridPane();

            for (int i = 0; i < reviewList.size(); i++) {
                Review review = reviewList.get(i);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CommentsItem.fxml"));
                    HBox root = fxmlLoader.load();
                    CommentsController controller = fxmlLoader.getController();
                    controller.SetDataComment(review);
                    gridPane.add(root, 0, i);
                    // Add the controller to the list for later reference
                    commentsControllers.add(controller);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Set the content of the ScrollPane after adding all reviews
            ScrollPane scrollPane1 = (ScrollPane) content_review.getChildren().get(0);
            scrollPane1.setContent(gridPane);
        }
    }


    private evenement evenement1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ReviewList();
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

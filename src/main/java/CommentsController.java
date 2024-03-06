import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.Review;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentsController implements Initializable {

    @FXML
    private Text comment;

    @FXML
    private Text titlee;

    @FXML
    private Rating valueStars;



    public void SetDataComment(Review review) {
        titlee.setText(review.getTitle());
        comment.setText(review.getComments());
        valueStars.setRating(review.getValue()); // Use setRating to set the rating value
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valueStars.setDisable(true);
    }
}

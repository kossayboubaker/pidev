module tn.esprit.pidev_desktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens tn.esprit.pidev_desktop to javafx.fxml;
    exports tn.esprit.pidev_desktop;
}
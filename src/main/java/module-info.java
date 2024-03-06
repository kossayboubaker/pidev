module tn.esprit.gestionoeuvre {
    requires javafx.controls;
    requires javafx.fxml;
    requires stripe.java;
    requires com.google.zxing;
    requires twilio;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.google.zxing.javase;
    opens tn.esprit.gestionproduit.controller to javafx.fxml;
    exports tn.esprit.gestionproduit.entity;
    opens tn.esprit.gestionproduit to javafx.fxml;
    exports tn.esprit.gestionproduit;
    exports tn.esprit.gestionproduit.controller;
}
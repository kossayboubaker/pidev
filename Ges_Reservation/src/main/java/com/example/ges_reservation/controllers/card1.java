package com.example.ges_reservation.controllers;
import com.example.ges_reservation.models.Films;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


import java.awt.image.BufferedImage;
import java.io.File;


public class card1 {
    @FXML
    private Text annesortie;

    @FXML
    private Text duree;

    @FXML
    private Text id;

    @FXML
    private Text realisateur;


    @FXML
    private Text synopsis;

    @FXML
    private Text titre;

    private Text categorieID;

    @FXML
    private ImageView image;

    private final double imgWidth = 100.0; // Width of the image
    private final double imgHeight = 150.0; // Height of the image

    private final ImageView imageView = new ImageView();

    @FXML
    void synopsis(ActionEvent event) {

    }


/*
    public void setProductData2(Films films) {
        // Set the text fields
        titre.setText(" " + films.getTitre());
        realisateur.setText(" " + films.getRealisateur());
        annesortie.setText(" " + films.getAnneeSortie());
        duree.setText(" " + films.getDuree());
        synopsis.setText(" " + films.getSynopsis());

        try {
            String imagePath = films.getImage();
            if (imagePath != null) {
                imagePath = imagePath.trim(); // Trim extra spaces
                System.out.println("Image Path: " + imagePath);

                // Debug: Check the URL created for the Image constructor
                String imageUrl = new File(imagePath).toURI().toString();
                System.out.println("Image URL: " + imageUrl);

                Image image = new Image(imageUrl, imgWidth, imgHeight, false, true);
                imageView.setImage(image);  // Correct usage of setImage on the imageView
            } else {
                // Handle the case where the image path is null
                System.err.println("Image path is null.");
                imageView.setImage(null); // Set a default or placeholder image
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            // Set a default or placeholder image if necessary
            imageView.setImage(null);
        }
        System.out.println("setProductData2 completed"); // Add this line for tracking the method execution
    }
*/
public void setProductData2(Films films) {
    // Set the text fields
    titre.setText(" " + films.getTitre());
    realisateur.setText(" " + films.getRealisateur());
    annesortie.setText(" " + films.getAnneeSortie());
    duree.setText(" " + films.getDuree());
    synopsis.setText(" " + films.getSynopsis());

    try {
        String imagePath = films.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            imagePath = imagePath.trim(); // Trim extra spaces
            System.out.println("Image Path: " + imagePath);

            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString(), imgWidth, imgHeight, false, true);
                this.image.setImage(image);
            } else {
                // Handle case where image file does not exist
                System.err.println("Image file does not exist.");
                this.image.setImage(null); // Set a default or placeholder image
            }
        } else {
            // Handle the case where the image path is null or empty
            System.err.println("Image path is null or empty.");
            this.image.setImage(null); // Set a default or placeholder image
        }
    } catch (Exception e) {
        System.err.println("Error loading image: " + e.getMessage());
        // Set a default or placeholder image if necessary
        this.image.setImage(null);
    }
    System.out.println("setProductData2 completed"); // Add this line for tracking the method execution
}
}

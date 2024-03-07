package com.example.ges_reservation.controllers;
import com.example.ges_reservation.services.FilmsService;
import javafx.scene.control.Alert;
import com.example.ges_reservation.models.Films;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AjouterFilms {
    @FXML
    private TextField anneeSortieTF;

    @FXML
    private TextField categorieTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField dureeTF;


    @FXML
    private TextField realisateurTF;

    @FXML
    private TextField titreTF;

    @FXML
    private ImageView imagev;


    private final FilmsService af = new FilmsService();

    private String filePath;


    @FXML
    void imageb(ActionEvent event) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
        if (selectedFile != null && isImageFile(selectedFile)) {
            // Store the file path with forward slashes
            filePath = selectedFile.getAbsolutePath().replace("\\", "/");
            System.out.println("File path stored: " + filePath);

            // Set the image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imagev.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }

    }


    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFilePath() {
        return filePath;
    }

    @FXML
    public void AjouterF(ActionEvent actionEvent) {
        if (titreTF.getText().isEmpty() || realisateurTF.getText().isEmpty() || anneeSortieTF.getText().isEmpty() || dureeTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || categorieTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        try {
            String path = getFilePath();
            Films newFilm = new Films(
                    titreTF.getText(), realisateurTF.getText(), Integer.parseInt(anneeSortieTF.getText()), Integer.parseInt(dureeTF.getText()), descriptionTF.getText(), Integer.parseInt(categorieTF.getText()), path);

            // Afficher la boîte de dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Souhaitez-vous envoyer un email à bargouguiamir2000@gmail.com ?");
            ButtonType yesButton = new ButtonType("Oui");
            ButtonType noButton = new ButtonType("Non");
            confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == yesButton) {
                // L'utilisateur a cliqué sur Oui, envoyer l'e-mail
                sendEmail("bargouguiamir2000@gmail.com", "Nouveau film ajouté", "Un nouveau film a été ajouté:\n\n" + newFilm.toString());
            }

            // Ajouter le film à la base de données
            af.ajouter(newFilm);

            // Afficher la boîte de dialogue de réussite
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Réservation réussie");
            successAlert.setContentText("La réservation a été effectuée avec succès !");
            successAlert.showAndWait();
        } catch (SQLException | NumberFormatException | MessagingException e) {
            // Gérer les erreurs
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    // Ne pas oublier d'ajouter cette méthode à votre classe AjouterFilms
    private void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        af.sendEmail(recipientEmail, subject, body);
    }

    @FXML
    public void afficherF(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/ges_reservation/AfficherFilm.fxml"));
            Stage window = (Stage) realisateurTF.getScene().getWindow();

            window.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



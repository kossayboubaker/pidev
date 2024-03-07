package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Films;
import com.example.ges_reservation.services.FilmsService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class AfficherFilm {
    @FXML
    private ListView<Films> ListFillm;

    @FXML
    private TextField recherchef;

    @FXML
    private Button codeqr;

    private final FilmsService ps = new FilmsService();

    @FXML
    public void initialize() {
        displayFilm();
        // Add event listener to handle movie selection
        ListFillm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                generatePDF(newValue);
            }
        });
    }

    private void generatePDF(Films film) {
        try {
            PdfWriter writer = new PdfWriter("film_details.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("Titre: " + film.getTitre()));
            document.add(new Paragraph("Réalisateur: " + film.getRealisateur()));
            document.add(new Paragraph("Année de sortie: " + film.getAnneeSortie()));
            document.add(new Paragraph("Durée: " + film.getDuree()));
            document.add(new Paragraph("Synopsis: " + film.getSynopsis()));
            document.close();
            // Display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setContentText("Film details saved to film_details.pdf");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Display error message if PDF generation fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while generating PDF: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML

    public void SuppFilm(ActionEvent actionEvent) {
        // Récupérer le siège sélectionné dans la ListView
        Films siegeSelectionne = ListFillm.getSelectionModel().getSelectedItem();

        if (siegeSelectionne != null) {
            // Demander une confirmation à l'utilisateur avant de supprimer le siège
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmer la suppression");
            confirmation.setHeaderText("Voulez-vous vraiment supprimer ce Film ?");
            confirmation.setContentText("Cette action est irréversible.");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Appeler le service pour supprimer le siège
                    ps.supprimer(siegeSelectionne.getFilmID());

                    // Actualiser la liste des sièges affichée dans la ListView
                    List<Films> sieges = ps.recuperer();
                    ObservableList<Films> observableList = FXCollections.observableArrayList(sieges);
                    ListFillm.setItems(observableList);

                    // Afficher une notification de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setContentText("Le siège a été supprimé avec succès !");
                    alert.showAndWait();
                } catch (SQLException e) {
                    // En cas d'erreur lors de la suppression, afficher une alerte d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur lors de la suppression");
                    alert.setContentText("Une erreur s'est produite lors de la suppression du siège : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        } else {
            // Afficher une alerte si aucun siège n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un siège à supprimer.");
            alert.showAndWait();
        }

    }

    public void modifFilm(ActionEvent actionEvent) {

// Récupérer le siège sélectionné dans la ListView
        Films filmSelectionne = ListFillm.getSelectionModel().getSelectedItem();

        if (filmSelectionne != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ges_reservation/ModifierFilm.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifierFilm controller = loader.getController();
                controller.initData(filmSelectionne);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
                // Mettre à jour la ListView après la modification
                List<Films> sieges = ps.recuperer(); // Récupérez la liste mise à jour
                ObservableList<Films> observableList = FXCollections.observableArrayList(sieges);
                ListFillm.setItems(observableList); // Mettez à jour les données affichées
            } catch (IOException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la modification");
                alert.setContentText("Une erreur s'est produite lors de la modification du film : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setContentText("Veuillez sélectionner un film à modifier.");
            alert.showAndWait();
        }
    }


    private void displayFilm() {
        FilmsService filmsService = new FilmsService();
        try {
            List<Films> films = filmsService.recuperer();
            ObservableList<Films> filmDataList = FXCollections.observableArrayList(films);
            ListFillm.setItems(filmDataList);

            ListFillm.setCellFactory(listView -> new ListCell<Films>() {
                private final ImageView imageView = new ImageView();
                private final double imgWidth = 100.0; // Width of the image
                private final double imgHeight = 150.0; // Height of the image

                @Override
                protected void updateItem(Films film, boolean empty) {
                    super.updateItem(film, empty);
                    if (empty || film == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        String text = String.format("%-5s %-5s %-5s %-5s %-5s %-5s %-5s",
                                film.getTitre(),
                                film.getRealisateur(),
                                film.getAnneeSortie(),
                                film.getDuree(),
                                film.getSynopsis(),
                                film.getCategorieID(),
                                ""); // Image is handled separately

                        try {
                            String imagePath = film.getImage().trim(); // Trim extra spaces
                            System.out.println("Image Path: " + imagePath);

                            // Debug: Check the URL created for the Image constructor
                            String imageUrl = new File(imagePath).toURI().toString();
                            System.out.println("Image URL: " + imageUrl);

                            Image image = new Image(imageUrl, imgWidth, imgHeight, false, true);
                            imageView.setImage(image);

                            // Create an HBox to hold text and image
                            HBox hbox = new HBox(10); // Adjust the spacing between text and image
                            hbox.getChildren().addAll(new Label(text), imageView);
                            setGraphic(hbox);
                        } catch (Exception e) {
                            System.err.println("Error loading image: " + e.getMessage());
                            setGraphic(null); // Set a default or placeholder image if necessary
                        }
                    }
                }
            });
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public void trierFilm(ActionEvent actionEvent) {
        try {
            List<Films> films = ps.recuperer();

            // Sort the films based on their titles in ascending order
            Collections.sort(films, Comparator.comparing(Films::getTitre));

            ObservableList<Films> observableList = FXCollections.observableArrayList(films);
            ListFillm.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur lors du tri");
            alert.setContentText("Une erreur s'est produite lors du tri des films : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void recherche(ActionEvent actionEvent) {
        try {
            String searchQuery = recherchef.getText().trim().toLowerCase();

            if (!searchQuery.isEmpty()) {
                List<Films> films = ps.recuperer();
                List<Films> filteredFilms = new ArrayList<>();

                // Filter films based on the search query (checking if the title contains the query)
                for (Films film : films) {
                    if (film.getTitre().toLowerCase().contains(searchQuery)) {
                        filteredFilms.add(film);
                    }
                }

                if (!filteredFilms.isEmpty()) {
                    ObservableList<Films> observableList = FXCollections.observableArrayList(filteredFilms);
                    ListFillm.setItems(observableList);
                } else {
                    // Clear the list if no matching films are found
                    ListFillm.getItems().clear();
                }
            } else {
                // If the search query is empty, display all films
                displayAllFilms();
            }
        } catch (SQLException e) {
            showSearchErrorAlert(e);
        }
    }

    // Helper method to display all films
    private void displayAllFilms() {
        try {
            List<Films> films = ps.recuperer();
            ObservableList<Films> observableList = FXCollections.observableArrayList(films);
            ListFillm.setItems(observableList);
        } catch (SQLException e) {
            showSearchErrorAlert(e);
        }
    }

    // Helper method to show an error alert for search-related issues
    private void showSearchErrorAlert(SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur lors de la recherche");
        alert.setContentText("Une erreur s'est produite lors de la recherche des films : " + e.getMessage());
        alert.showAndWait();
    }


    @FXML
    void generer(ActionEvent event) {
        ObservableList<Films> selectedFilms = ListFillm.getSelectionModel().getSelectedItems();

        for (Films film : selectedFilms) {
            try {
                String titre = film.getTitre();
                String realisateur = film.getRealisateur();
                int anneeSortie = film.getAnneeSortie();
                int duree = film.getDuree();
                String synopsis = film.getSynopsis();

                String data = "Titre: " + titre + "\nRéalisateur: " + realisateur + "\nAnnée de sortie: " + anneeSortie +
                        "\nDurée: " + duree + "\nSynopsis: " + synopsis;

                String filePath = "" + titre + ".png";
                int width = 300;
                int height = 300;

                generateQRCode(data, filePath, width, height);
                System.out.println("QR code generated successfully for film: " + titre);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void generateQRCode(String data, String filePath, int width, int height) throws IOException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // or any other character set you prefer

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);

            // Convert BitMatrix to BufferedImage
            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            // Save the BufferedImage to file
            File qrFile = new File(filePath);
            ImageIO.write(qrImage, "PNG", qrFile);
            System.out.println("QR code generated successfully for film: " + qrFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}




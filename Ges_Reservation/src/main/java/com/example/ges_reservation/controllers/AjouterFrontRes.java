package com.example.ges_reservation.controllers;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.services.ReservationService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.LineSeparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.kernel.colors.ColorConstants;


public class AjouterFrontRes {

    @FXML
    private ComboBox<String> nomFilmCombo;

    @FXML
    private ComboBox<String> dateresf;

    @FXML
    private ComboBox<String> heureResf;

    @FXML
    private TextField nbreplacef;

    @FXML
    private ListView<String> NumsSieges;

    private ReservationService reservationService;

    private static final String WARNING_TITLE = "Avertissement";
    private static final String ERROR_TITLE = "Erreur";
    private static final String SUCCESS_TITLE = "Succès";

    public AjouterFrontRes() {
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        try {
            List<String> filmNames = reservationService.getAllFilmNames();
            nomFilmCombo.setItems(FXCollections.observableArrayList(filmNames));

            nomFilmCombo.setOnAction(event -> {
                try {
                    updateDatesForSelectedFilm();
                    updateSiegeNumbersWithReservationStatus();
                } catch (SQLException e) {
                }
            });

            dateresf.setOnAction(event -> {
                try {
                    updateHeuresForSelectedDate();
                    updateSiegeNumbersWithReservationStatus();
                } catch (SQLException e) {
                }
            });

            heureResf.setOnAction(event -> updateSiegeNumbersWithReservationStatus());

            NumsSieges.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (SQLException e) {
        }
    }

    @FXML
    public void valider(ActionEvent event) {
        try {
            String selectedFilmName = nomFilmCombo.getValue();
            if (selectedFilmName == null) {
                showAlert(WARNING_TITLE, "Sélectionner un film", "Veuillez sélectionner un film.");
                return;
            }

            int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);

            String selectedDate = dateresf.getValue();
            if (selectedDate == null) {
                showAlert(WARNING_TITLE, "Sélectionner une date", "Veuillez sélectionner une date.");
                return;
            }
            String selectedHeure = heureResf.getValue();
            if (selectedHeure == null) {
                showAlert(WARNING_TITLE, "Sélectionner une heure", "Veuillez sélectionner une heure.");
                return;
            }

            int nombrePlaces;
            try {
                nombrePlaces = Integer.parseInt(nbreplacef.getText());
            } catch (NumberFormatException e) {
                showAlert(WARNING_TITLE, "Nombre de places invalide", "Veuillez entrer un nombre valide pour le nombre de places.");
                return;
            }

            int placesDisponibles = reservationService.getNombrePlacesDisponibles(selectedFilmID, selectedDate, selectedHeure);
            if (nombrePlaces > placesDisponibles) {
                showAlert(WARNING_TITLE, "Places insuffisantes", "Le nombre de places disponibles est insuffisant.");
                return;
            }

            List<String> selectedSiegeNumbers = NumsSieges.getSelectionModel().getSelectedItems();
            if (selectedSiegeNumbers.size() != nombrePlaces) {
                showAlert(WARNING_TITLE, "Sélection de sièges incorrecte", "Le nombre de sièges sélectionnés ne correspond pas au nombre de places à réserver.");
                return;
            }

            for (String selectedSiegeNumber : selectedSiegeNumbers) {
                if (selectedSiegeNumber.endsWith("(Réservé)")) {
                    showAlert(WARNING_TITLE, "Siège déjà réservé", "Au moins un des sièges sélectionnés est déjà réservé.");
                    return;
                }
            }

            for (String selectedSiegeNumber : selectedSiegeNumbers) {
                reservationService.marquerSiegeIndisponible(selectedSiegeNumber);
            }

            for (int i = 0; i < nombrePlaces; i++) {
                Reservations reservation = new Reservations();
                reservation.setFilmID(selectedFilmID);
                reservation.setDateReservation(new java.sql.Date(new Date().getTime()));
                reservation.setHeureReservation(java.sql.Time.valueOf(selectedHeure));
                reservation.setNombrePlacesReservees(nombrePlaces);
                reservationService.ajouterResBack(reservation);
            }

            reservationService.decrementerNombrePlaces(selectedFilmID, selectedDate, selectedHeure, nombrePlaces);
            updateSiegeNumbersWithReservationStatus();

            // Créer une instance de réservation avec les données de réservation réelles
            Reservations reservation = new Reservations();
            reservation.setFilmID(selectedFilmID);
            reservation.setDateReservation(java.sql.Date.valueOf(selectedDate));
            reservation.setHeureReservation(java.sql.Time.valueOf(selectedHeure));
            reservation.setNombrePlacesReservees(nombrePlaces);
            // Vous pouvez également définir d'autres champs de la réservation ici en fonction des besoins.

            // Générer le code QR pour la réservation
            genererQRCodeReservation(reservation, selectedSiegeNumbers);
            genererPDF(selectedSiegeNumbers);

            // Vider les champs après validation
            nomFilmCombo.setValue(null);
            dateresf.setValue(null);
            heureResf.setValue(null);
            nbreplacef.clear();
            NumsSieges.getItems().clear();

            // Afficher un message de succès
            showAlert(SUCCESS_TITLE, "Réservation effectuée", "La réservation a été effectuée avec succès.");

        } catch (SQLException e) {
            showErrorAlert(ERROR_TITLE, "Erreur lors de la validation de la réservation.");
        }
    }

    private void updateDatesForSelectedFilm() throws SQLException {
        String selectedFilmName = nomFilmCombo.getValue();
        int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
        List<String> dates = reservationService.getAllDatesForFilm(selectedFilmID);
        dateresf.setItems(FXCollections.observableArrayList(dates));
    }

    private void updateHeuresForSelectedDate() throws SQLException {
        String selectedFilmName = nomFilmCombo.getValue();
        int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
        String selectedDate = dateresf.getValue();
        List<String> heures = reservationService.getAllHeuresForFilmAndDate(selectedFilmID, selectedDate);
        heureResf.setItems(FXCollections.observableArrayList(heures));
    }

    private void updateSiegeNumbersWithReservationStatus() {
        try {
            String selectedFilmName = nomFilmCombo.getValue();
            int selectedFilmID = reservationService.getFilmIDByName(selectedFilmName);
            String selectedDate = dateresf.getValue();
            String selectedHeure = heureResf.getValue();

            List<String> siegeNumbers = reservationService.getSiegeNumbersForFilmDateAndTime(selectedFilmID, selectedDate, selectedHeure);

            ObservableList<String> updatedSiegeNumbers = FXCollections.observableArrayList();
            for (String siegeNumber : siegeNumbers) {
                if (reservationService.isSiegeIndisponible(selectedFilmID, selectedDate, selectedHeure, siegeNumber)) {
                    updatedSiegeNumbers.add(siegeNumber + " (Réservé)");
                } else {
                    updatedSiegeNumbers.add(siegeNumber);
                }
            }
            NumsSieges.setItems(updatedSiegeNumbers);
        } catch (SQLException e) {
            showErrorAlert(ERROR_TITLE, "Erreur lors de la mise à jour des sièges réservés.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void afficherImage(MouseEvent mouseEvent) {
        try {
            // Charger l'image à afficher
            Image image = new Image(getClass().getResourceAsStream("/images/salle.png"));

            // Créer une nouvelle fenêtre pour afficher l'image
            Stage stage = new Stage();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
            Scene scene = new Scene(new Group(imageView));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateQRCode(String data, int width, int height, String filePath) {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);
            File qrFile = new File(filePath);
            MatrixToImageWriter.writeToFile(matrix, "PNG", qrFile);
            System.out.println("Code QR généré avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de la génération du code QR : " + e.getMessage());
        }
    }

    public void genererQRCodeReservation(Reservations reservation, List<String> selectedSiegeNumbers) {
        String selectedFilmName = nomFilmCombo.getValue();
        String selectedDate = dateresf.getValue();
        String selectedHeure = heureResf.getValue();
        String nombrePlaces = nbreplacef.getText();

        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append("Film: ").append(selectedFilmName).append("\n")
                .append("Date: ").append(selectedDate).append("\n")
                .append("Heure: ").append(selectedHeure).append("\n")
                .append("Nombre de places: ").append(nombrePlaces).append("\n");

        if (!selectedSiegeNumbers.isEmpty()) {
            dataBuilder.append("Sièges: ");
            for (String siege : selectedSiegeNumbers) {
                dataBuilder.append(siege).append(", ");
            }
            dataBuilder.delete(dataBuilder.length() - 2, dataBuilder.length()); // Supprimer la virgule finale
            dataBuilder.append("\n");
        } else {
            dataBuilder.append("Sièges: Aucun\n"); // Ajouter un message indiquant qu'aucun siège n'est sélectionné
        }

        String data = dataBuilder.toString();
        String filePath = "reservation_qr_code.png";

        generateQRCode(data, 300, 300, filePath);
        afficherQRCode(filePath);
    }

    private void afficherQRCode(String filePath) {
        try {
            // Charger l'image à afficher
            Image image = new Image(new File(filePath).toURI().toString());

            // Créer une nouvelle fenêtre pour afficher le code QR
            Stage stage = new Stage();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            Scene scene = new Scene(new Group(imageView));
            stage.setScene(scene);
            stage.setTitle("Code QR de réservation");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genererPDF(List<String> selectedSiegeNumbers) {
        try {
            // Création du document PDF
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter("reservation.pdf"));
            Document document = new Document(pdfDocument);

            SolidLine solidLine = new SolidLine();
            solidLine.setColor(ColorConstants.BLACK);
            solidLine.setLineWidth(1);

            // Ajouter "TICKET" en gras et la référence de réservation
            Paragraph ticketParagraph = new Paragraph("TICKET             ").setBold().setFontSize(18);
            ticketParagraph.add(" Référence de réservation: " + reservationService.getLatestReservationId()).setFontSize(14);
            document.add(ticketParagraph);
            document.add(new LineSeparator(solidLine));

            document.add(new Paragraph("\n")); // Ajouter un paragraphe vide pour l'espace

            // Ajouter Titre de Film en gras et la valeur de nomFilmCombo
            document.add(new Paragraph("Titre de Film:").setBold().setFontSize(18));
            document.add(new Paragraph(nomFilmCombo.getValue()).setFontSize(18));
            document.add(new Paragraph("\n")); // Ajouter un paragraphe vide pour l'espace

            // Ajouter une bande grise avec les détails de la réservation
            document.add(new Paragraph("Date:").setBold());
            document.add(new Paragraph(dateresf.getValue()).setFontSize(18));
            document.add(new Paragraph("Heure:").setBold());
            document.add(new Paragraph(heureResf.getValue()).setFontSize(18));
            document.add(new Paragraph("Place:").setBold());
            document.add(new Paragraph(nbreplacef.getText()).setFontSize(18));
            document.add(new Paragraph("Place(s) réservé(s):").setBold());

            for (String siege : selectedSiegeNumbers) {
                document.add(new Paragraph(siege).setFontSize(18));
            }

            document.add(new LineSeparator(solidLine));

            document.add(new Paragraph("\nMerci pour votre achat !").setBold()).setFontSize(15);
            document.add(new Paragraph("Voici la confirmation électronique de votre réservation et votre billet.")).setFontSize(15);
            document.add(new Paragraph("Veuillez apporter une copie de ce mail au cinéma pour validation. Veuillez soit")).setFontSize(15);
            document.add(new Paragraph("l'imprimer soit nous la montrer sur place depuis votre smartphone ! N'oubliez")).setFontSize(15);
            document.add(new Paragraph("pas de vous rendre au cinéma en avance. Merci et bon film !")).setFontSize(15);
            document.add(new Paragraph("\n")); // Ajouter un paragraphe vide pour l'espace

            // Ajouter des conditions générales en gras
            document.add(new Paragraph("Conditions générales : ").setBold());
            document.add(new Paragraph("Merci de vous référer à notre application  pour les conditions de vente.")).setFontSize(15);
            document.add(new Paragraph("\nDate de délivrance : " + new Date())).setFontSize(9);

            // Fermeture du document
            document.close();

            // Ouvrir le PDF après la génération
            ouvrirPDF("reservation.pdf");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showErrorAlert(ERROR_TITLE, "Erreur lors de la génération du PDF.");
        }
    }

    private void ouvrirPDF(String filePath) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                File file = new File(filePath);
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.example.ges_reservation.utils;

import com.example.ges_reservation.models.evenement;
import com.example.ges_reservation.test.MainFX;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingFXUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class card2 {
    @FXML
    private Text date;

    @FXML
    private Text description;

    @FXML
    private Text id;


    @FXML
    private Button btn_consulter;

    @FXML
    private Button btn_rev;
    @FXML
    private Text id_cinema;

    @FXML
    private Text nom_ev;

    @FXML
    private Text periode;
    @FXML
    private HBox qrCode;

    public Button getBtn_consulter() {
        return btn_consulter;
    }

    public void setProductData2(evenement evenement) {
        // Instantiate the produitService


        nom_ev.setText(" " + evenement.getNom_ev());
        description.setText(" " + evenement.getDescription());
        date.setText(" " + evenement.getDate());
        periode.setText("" + evenement.getPeriode());


        qrCode.setOnMouseClicked(event -> {
            System.out.println("ID du achat à générer qr Code : " + evenement.getId());

            String text = " evenement id: " + evenement.getId()
                            + "\nID cinema: " + evenement.getId_cinema()
                            + "\n Nom evenement: " + evenement.getNom_ev()
                            + "\nDescription: " + evenement.getDescription()
                            + "\nDate: " +evenement.getDate()
                            + "\nPeriode: " + evenement.getPeriode();

            // Créer un objet QRCodeWriter pour générer le QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            // Générer la matrice de bits du QR code à partir du texte saisi
            BitMatrix bitMatrix;
            try {
                bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                // Convertir la matrice de bits en image BufferedImage
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                ImageView qrCodeImg = (ImageView) ((Node) event.getSource()).getScene().lookup("#qrCodeImg");


                qrCodeImg.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

                HBox qrCodeImgModel = (HBox) ((Node) event.getSource()).getScene().lookup("#qrCodeImgModel");
                qrCodeImgModel.setVisible(true);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
        btn_rev.setOnAction(actionEvent ->
        {
            MainFX.data.id_evenement = evenement.getId();
            System.out.println("EVENEMENT " + MainFX.data.id_evenement);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FormAvis.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btn_consulter.setOnAction(actionEvent ->
        {
            MainFX.data.id_evenement = evenement.getId();
            System.out.println("EVENEMENT " + MainFX.data.id_evenement);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ListReview.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

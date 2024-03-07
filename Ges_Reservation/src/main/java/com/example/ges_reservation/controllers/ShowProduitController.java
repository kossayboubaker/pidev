/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;



import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import com.example.ges_reservation.models.Produit;
import com.example.ges_reservation.services.ProduitService;
import com.example.ges_reservation.services.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ShowProduitController implements Initializable {


    private static final String CHARSET = "UTF-8";
    private static final String FILE_FORMAT = "png";
    @FXML
    private ListView<Produit> listView;
   
    ObservableList<Produit> data;
    
    public static int idE ;
    
    Service ds = new Service();
    ProduitService p = new ProduitService();
    
    @FXML
    private Label label;
    public  static int idP ;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            data = (ObservableList<Produit>)p.getAllProuduits();
            listView.setItems(data);
            listView.setCellFactory((ListView<Produit> param) -> new ListViewProduit());
            
            
            // TODO
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    void ajout(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AddProduit.fxml")));
            Stage myWindow = (Stage) listView.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void modifier(ActionEvent event) {

        ObservableList<Produit> e = listView.getSelectionModel().getSelectedItems();
        for (Produit m : e) {
           idP = m.getIdProduit();
        }

        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModifierProduit.fxml")));
            Stage myWindow = (Stage) listView.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @FXML
    void supprimer(ActionEvent event) throws SQLDataException {

        ObservableList<Produit> e = listView.getSelectionModel().getSelectedItems();
        for (Produit m : e) {
            p.delete(m.getIdProduit());
        }

        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ShowProduit.fxml")));
            Stage myWindow = (Stage) listView.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @FXML
    void generer(ActionEvent event) {
        ObservableList<Produit> m = listView.getSelectionModel().getSelectedItems();

        for (Produit e : m) {

        try {
            String nom = e.getNom();
            String description = e.getDescription();
            float prix = e.getPrix();

// Affichage des données sous forme de tableau
            System.out.println("+----------------+------------------+----------------+");
            System.out.println("|     Nom        |   Description    |     Prix       |");
            System.out.println("+----------------+------------------+----------------+");
            System.out.println(String.format("| %-14s | %-16s | %-14s |", nom, description, prix));
            System.out.println("+----------------+------------------+----------------+");
            String data = "Nom :"+e.getNom()+" description : "+e.getDescription()+"Prix :"+e.getPrix(); // Votre texte ou données à encoder dans le code QR
            String filePath = ""+e.getNom()+".png";
            int width = 300; // Largeur du code QR en pixels
            int height = 300; // Hauteur du code QR en pixels

            generateQRCode(data, filePath, width, height);
            System.out.println("Code QR généré avec succès !");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
    }


    public static void generateQRCode(String data, String filePath, int width, int height) throws IOException, WriterException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, CHARSET);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hintMap);

        File outputFile = new File(filePath);
        MatrixToImageWriter.writeToFile(bitMatrix, FILE_FORMAT, outputFile);
    }
    }

    


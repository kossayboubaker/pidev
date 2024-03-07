/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.example.ges_reservation.test.MainFX;
import com.example.ges_reservation.models.Commande;
import com.example.ges_reservation.models.commande_panier;
import com.example.ges_reservation.models.panier;
import com.example.ges_reservation.services.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PasserCommandeController implements Initializable {
    private static final String STRIPE_SECRET_KEY = "sk_test_51Or3LkBz8ipXK0OuWIHF3fbzTwLEVNYP4vhNPvlrsUSTr24zyAw6PE3YpRoNsnbhqG4urs0cIySk8p6OcBi9BBdt00m1W6EdWD";

    @FXML
    private ChoiceBox<String> checbox;
    @FXML
    private Label username;
    @FXML
    private Label prix;
    
    private Float somme = 0f ;
    
    Service s = new Service ();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            username.setText("Koussay");
            List<panier> lpanier = s.getAllPanier();
            
            for(panier p : lpanier){
            
            somme = ((s.get_produit(p.getId_produit()).getPrix())*p.getQuantité())+somme ;
            }
            prix.setText(String.valueOf(somme));
           checbox.getItems().addAll("en ligne","Espéce");


            
            
        } catch (SQLDataException ex) {
            Logger.getLogger(PasserCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void PasserCommande(ActionEvent event) throws SQLException {


        Commande c = new Commande();
        c.setPrix(somme);
        c.setId_user(1);
        c.setType(checbox.getValue());
        c.setEtat("En attent");
        s.addCommande(c);
        List<panier> lpanier = s.getAllPanier();
        
        for(panier p : lpanier){
        
            commande_panier cp = new commande_panier();
            cp.setId_produit(p.getId_produit());
            cp.setId_commande(s.getIdCommande());
            s.addCommandePanier(cp);
            s.deletePanier();
        }


        Stripe.apiKey = STRIPE_SECRET_KEY;

        if (Objects.equals(checbox.getValue(), "en ligne")) {

            try {
                processPayment((long) (somme*100)); // Montant en cents (1000 = 10.00 USD)
            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
            sendSMS("+21650393158",somme.longValue());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Paiement Avec succès", ButtonType.OK);
            alert.showAndWait();

            Parent root;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Front.fxml")));
                Stage myWindow = (Stage) prix.getScene().getWindow();
                Scene sc = new Scene(root);
                myWindow.setScene(sc);
                myWindow.setTitle("page name");
                //myWindow.setFullScreen(true);
                myWindow.show();
            } catch (IOException ex) {
                Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
        
        
        
        
    }



    public void processPayment(Long amount) throws StripeException {
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(amount) // Montant en centimes
                .setCurrency("eur")
                .setDescription("Example Payment")
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
    }

    void sendSMS(String recipientNumber,long amount) {
        String ACCOUNT_SID = "AC7ff49d1703dff90b40c663ff5582143b";
        String AUTH_TOKEN = "1341d5547cb377602f680438de95b7d8";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        // recipientNumber = "+21629082917";
        String message = "Votre Paiement du montant "+amount+" a été effectué avec succès";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("14153199952"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());

    }

    public void pageaccueille(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/Front.fxml"));
        try {
            checbox.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    }


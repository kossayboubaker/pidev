/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.ges_reservation.models.Produit;
import com.example.ges_reservation.models.panier;
import com.example.ges_reservation.services.ProduitService;
import com.example.ges_reservation.services.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientHomeController implements Initializable {


    ProduitService produitService = new ProduitService();

    @FXML
    private GridPane testContainer;
    
    private List<Produit> produits ;


    @FXML
    private Label label;




    @FXML
    void goPanier(ActionEvent event) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/ShowPanier.fxml"));
            Stage myWindow = (Stage) label.getScene().getWindow();
            Scene sc = new Scene(root);
            myWindow.setScene(sc);
            myWindow.setTitle("page name");
            //myWindow.setFullScreen(true);
            myWindow.show();
        } catch (IOException ex) {
            Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setData(List<Produit> listQ){
         
        int column = 0;
        int row = 1 ;
         try {
               for(Produit q : listQ){
                    FXMLLoader fxmlloader = new FXMLLoader() ;
                    fxmlloader.setLocation(getClass().getResource("/cardProduit.fxml"));
                    VBox testBox = fxmlloader.load() ;
                    CardProduitController cardTestController = fxmlloader.getController() ;
                    cardTestController.setData(q) ;
                             
                    if(column == 3){
                    column = 0 ;
                    ++row ;
                   }
                    
                    testContainer.add(testBox, column++, row);
                    GridPane.setMargin(testBox, new Insets(10));

                }             
               
        } catch (IOException ex) {
                Logger.getLogger(ClientHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          label.setText(String.valueOf(CardProduitController.nbr));
          try {
              setData(getHome()) ;
          } catch (SQLDataException e) {
              throw new RuntimeException(e);
          }

      }

    private List<Produit> getHome() throws SQLDataException {
        produits = new ArrayList<>(produitService.getAllProuduits()) ;
        return produits ;
    }



}

    


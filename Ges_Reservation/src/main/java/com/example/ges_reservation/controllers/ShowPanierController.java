/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.example.ges_reservation.test.MainFX;
import com.example.ges_reservation.models.Produit;
import com.example.ges_reservation.models.panier;
import com.example.ges_reservation.services.ProduitService;
import com.example.ges_reservation.services.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class ShowPanierController implements Initializable {



    @FXML
    private ListView<Produit> listView;
   
    ObservableList<Produit> data;
    
    public static int idE ;
    
    Service ds = new Service();
    ProduitService ps = new ProduitService();

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        List<Produit> listProduit = new ArrayList<>();
        

        try {
            for (panier p : ds.getAllPanier()){
                listProduit.add(ps.getProduitById(p.getId_produit()));
            }
            
         ObservableList<Produit> observableList = FXCollections.observableList(listProduit);
        data = (ObservableList<Produit>) observableList;
        listView.setItems(data);
        listView.setCellFactory((ListView<Produit> param) -> new ListViewPanier());
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowPanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
        
        // TODO
    }    




    @FXML
    private void PasserCommande(ActionEvent event) throws SQLDataException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/PasserCommande.fxml"));
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
    private void AnnulerCommande(ActionEvent event) throws SQLDataException {

        CardProduitController.nbr = 0 ;
             ds.deletePanier();
               Parent root;
                       try {
              root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Front.fxml")));
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
    private void SuprimerItem(ActionEvent event) throws SQLDataException {


                ObservableList<Produit> e = listView.getSelectionModel().getSelectedItems();
         for (Produit m : e) {
             ds.deleteFromPanier(m.getIdProduit());
         }
         
                  Parent root;
                       try {
              root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ShowPanier.fxml")));
               Stage myWindow = (Stage) listView.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page panier");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(ShowProduitController.class.getName()).log(Level.SEVERE, null, ex);
               }


        
    }


    public void homepage(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("/Front.fxml"));
        try {
            listView.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    }

    


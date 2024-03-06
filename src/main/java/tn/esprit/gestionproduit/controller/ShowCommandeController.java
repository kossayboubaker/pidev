/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import tn.esprit.gestionproduit.entity.Produit;
import tn.esprit.gestionproduit.entity.commande_panier;
import tn.esprit.gestionproduit.service.Service;

import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ShowCommandeController implements Initializable {

    @FXML
    private ListView<Produit> listView;
   
    ObservableList<Produit> data;
    
    
    Service ds = new Service();
    @FXML
    private Label prix;
    @FXML
    private Label username;
    

   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        List<Produit> listProduit = new ArrayList<>();
        
        username.setText("koussay");
        prix.setText(String.valueOf(ds.get_Commande(ListeCommandeController.ide).getPrix()));
        
        try {
            for (commande_panier p : ds.getAllCommandePanier()){
                
                
                listProduit.add(ds.get_produit(p.getId_produit()));
            }
            
         ObservableList<Produit> observableList = FXCollections.observableList(listProduit);
        data = (ObservableList<Produit>) observableList;
        listView.setItems(data);
        listView.setCellFactory((ListView<Produit> param) -> new ListViewProduit());
        } catch (SQLDataException ex) {
            Logger.getLogger(ShowCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
        
        // TODO
        

        
        
        // TODO
        

        
        
        // TODO
        

        
        
        // TODO
    }    




    }

    


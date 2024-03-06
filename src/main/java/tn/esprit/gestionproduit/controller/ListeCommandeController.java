/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.gestionproduit.entity.Commande;
import tn.esprit.gestionproduit.service.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ListeCommandeController implements Initializable {

    @FXML
    private TableView<Commande> table;
    @FXML
    private TableColumn<Commande, Integer> ref;
    @FXML
    private TableColumn<Commande, Float> prix;
    
    private ObservableList<Commande> UserData = FXCollections.observableArrayList();
    
    Service s = new Service();
    
    public static int ide ;
    
   
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
                try {
            List<Commande> listb= new ArrayList<Commande>();
            
            listb = s.getAllCommande();
     
            System.out.println("hello"+listb);
            UserData.clear();
            UserData.addAll(listb);
            table.setItems(UserData);
            
            ref.setCellValueFactory
                      (
                              new PropertyValueFactory<>("id_commande")
                      );
            prix.setCellValueFactory
                      (
                              new PropertyValueFactory<>("prix")
                      );
            
        } catch (SQLDataException ex) {
            Logger.getLogger(ListeCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }    

    @FXML
    private void consulter(ActionEvent event) {
        
                      ide =  table.getSelectionModel().getSelectedItem().getId_commande();     

             Parent root;
           try {
              root = FXMLLoader.load(getClass().getResource("/ShowCommande.fxml"));
               Stage myWindow = (Stage) table.getScene().getWindow();
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

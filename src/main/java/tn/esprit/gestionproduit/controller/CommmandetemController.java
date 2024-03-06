/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import tn.esprit.gestionproduit.entity.Produit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommmandetemController implements Initializable {

    @FXML
    private HBox Hbox;
    @FXML
    private Text nomuser;
    @FXML
    private Text type;

    
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
 
   
    }  
    
    public CommmandetemController(){
    
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CommandeItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        
    }

    public HBox getBox() {
        return Hbox;
    }


    
    
    
    
        public void setInfo(Produit p)  {
            
        nomuser.setText(p.getNom());
        type.setText(String.valueOf(p.getPrix()));
     
     
}
        
}

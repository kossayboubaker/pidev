/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import com.example.ges_reservation.models.Produit;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class ProduitItemController implements Initializable {

    @FXML
    private HBox Hbox;
    @FXML
    private Text nomuser;
    @FXML
    private Text type;
    @FXML
    private ImageView imv;


    @FXML
    private HBox hbox2 ;

    @FXML
    private Text desc ;
    
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
 
   
    }  
    
    public ProduitItemController(){
    
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProduitItem.fxml"));
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
        desc.setText("Description :"+p.getDescription());
        nomuser.setText(p.getNom());
        type.setText(String.valueOf(p.getPrix()));

}
        
}

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import com.example.ges_reservation.models.Produit;
import com.example.ges_reservation.models.panier;
import com.example.ges_reservation.services.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaniertemController implements Initializable {

    @FXML
    private HBox Hbox;
    @FXML
    private Text nomuser;
    @FXML
    private Text qt;
    @FXML
    private Text type;
    @FXML
    private ImageView imv;


    @FXML
    private HBox hbox2 ;

    Service s = new Service();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
 
   
    }  
    
    public PaniertemController(){
    
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PanierItem.fxml"));
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

        panier panner = s.get_PanierByProduit(p.getIdProduit());
        System.out.println("Quantite"+panner.getQuantité());
        qt.setText("Quantite"+panner.getQuantité());

        nomuser.setText(p.getNom());
        type.setText(String.valueOf(p.getPrix()));
            String imagePath = "/img/" + p.getImage();
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            imv.setImage(image);
     
     
}
        
}

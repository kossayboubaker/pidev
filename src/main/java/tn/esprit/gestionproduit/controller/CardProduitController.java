/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.gestionproduit.entity.Produit;
import tn.esprit.gestionproduit.entity.panier;
import tn.esprit.gestionproduit.service.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author damos
 */
public class CardProduitController implements Initializable {

    @FXML
    private Label nom;

    @FXML
    private Label prix;

    public static int nbr = 0;

    @FXML
    private ImageView imv;

    @FXML
    private VBox testBox;
    @FXML
    private Label idproduit;

    public static  int idprod ;

    Service ds = new Service();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    public void setData(Produit h){

        nom.setText("Nom :"+h.getNom());
        prix.setText(String.valueOf(h.getPrix()));
        String imagePath = "/img/" + h.getImage();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        imv.setImage(image);
        idproduit.setText(String.valueOf(h.getIdProduit()));
        idproduit.setVisible(false);
    }

    @FXML
    private void AddPanier(MouseEvent event) throws SQLException {

        panier p = new panier();

     int  idpr =Integer.parseInt(idproduit.getText());

        List<panier> listpanier =  ds.getAllPanier();

        if(listpanier.isEmpty()) {
            p.setId_produit(idpr);
            p.setQuantité(1);
            ds.addPanier(p);
        }


            else if(ds.get_PanierByProduit(idpr).getId_panier()!=0){
                p.setId_produit(idpr);
                p.setQuantité(p.getQuantité()+1);
                System.out.println("id"+p.getId_produit()+"quantité"+p.getQuantité());
                ds.ModifierQuantite(idpr,p.getQuantité()+1);

            }
            else{
                p.setId_produit(idpr);
                p.setQuantité(1);
                ds.addPanier(p);
            }
        nbr = nbr + 1;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/ClientHome.fxml"));
            Stage myWindow = (Stage) idproduit.getScene().getWindow();
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

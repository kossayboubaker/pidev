/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.gestionproduit.HelloApplication;
import tn.esprit.gestionproduit.entity.Categorie;
import tn.esprit.gestionproduit.service.CategorieService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModefierController implements Initializable {

    @FXML
    private TextField des;

    CategorieService categorieService = new CategorieService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.err.println("id"+categorieService.get_CatById(AfficherCategorieController.id).getDescription());
        des.setText(categorieService.get_CatById(AfficherCategorieController.id).getDescription());
  
        
    }    

    @FXML
    private void Ajouter(ActionEvent event) throws SQLDataException {
        
        Categorie c = new Categorie();
        c.setId_categori(AfficherCategorieController.id);
        c.setDescription(des.getText());
        categorieService.ModifierCategorie(c);
        
                   Parent root;
               try {
              root = FXMLLoader.load(getClass().getResource("/test (1).fxml"));
               Stage myWindow = (Stage) des.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("Afficher Categories");
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
    }

    public void returndashb(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/test (1).fxml"));
        try {
            des.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    }

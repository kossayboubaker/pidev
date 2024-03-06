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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.gestionproduit.entity.Categorie;
import tn.esprit.gestionproduit.service.CategorieService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCategorieController implements Initializable {




    @FXML
    private TextField des; // Référence au champ de texte (TextField) dans le fichier FXML correspondant
    
    CategorieService categorieService = new CategorieService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException {
        
        Categorie c = new Categorie();
        
        c.setDescription(des.getText());
        
        
        if (des.getText().equals("") ){

                 Alert alert = new Alert(Alert.AlertType.ERROR, "vérifier votre champs", ButtonType.OK);
           alert.showAndWait();
        
       
        }
        else{
        
                            categorieService.addCategorie(c);  // houni on Ajoute la catégorie à la base de données en utilisant CategorieService

        
        
        Parent root;    // Navigue vers la page "AfficherCategorie.fxml" après l'ajout réussi
               try {
              root = FXMLLoader.load(getClass().getResource("/AfficherCategorie.fxml"));
               Stage myWindow = (Stage) des.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {  // Journalise toute IOException qui se produit pendant la navigation
               Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
    }
        }
    
}

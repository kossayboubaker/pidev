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
import com.example.ges_reservation.models.Categorie;
import com.example.ges_reservation.models.Commande;
import com.example.ges_reservation.services.CategorieService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AfficherCategorieController implements Initializable {

    @FXML
    private ListView<Categorie> listcategorie;


    private ObservableList<Categorie> UserData = FXCollections.observableArrayList();
    
    CategorieService cs = new CategorieService();
    
    static int id ;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Commande commandeService = new Commande();
        try {
            ObservableList<Categorie> commandes = cs.getAllCategories();

            // Trier la liste des commandes par ID avant de les ajouter à l'ObservableList
            //  commandes.sort(Comparator.comparingInt(Commande::getEtat));

            // Créer une ObservableList pour stocker les données des commandes
            ObservableList<String> CommandeDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-35s %-22s  ", "ID","Description");
            CommandeDataList.add(columnTitles);

            // Itérer à travers la liste des commandes et ajouter leurs détails à la CommandeDataList
            for (Categorie categorie : commandes) {
                String commandeData = String.format("%-30s %-20s %-20s ",
                        categorie.getId_categori(),
                        categorie.getDescription()

                );
                CommandeDataList.add(commandeData);
            }

            // Définir les éléments pour la ListView


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }



    @FXML
    private void Ajouter(ActionEvent event) {
     
            Parent root;
               try {
              root = FXMLLoader.load(getClass().getResource("/AddCategorie.fxml"));
               Stage myWindow = (Stage) listcategorie.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
        
        
    }

    @FXML
    private void Modif(ActionEvent event) {

              id =  listcategorie.getSelectionModel().getSelectedItem().getId_categori();
              Parent root;
               try {
              root = FXMLLoader.load(getClass().getResource("/ModefierCategorie.fxml"));
               Stage myWindow = (Stage) listcategorie.getScene().getWindow();
               Scene sc = new Scene(root);
               myWindow.setScene(sc);
               myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
               myWindow.show();
               } catch (IOException ex) {
               Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
               }
        
        
    }

    @FXML
    private void Delete(ActionEvent event) throws SQLDataException {

        int id =  listcategorie.getSelectionModel().getSelectedItem().getId_categori();
        cs.deleteCategori(id);
        resetTableData();
        
        
    }
    
    
        public void resetTableData() throws SQLDataException
    {
        List<Categorie> lisre = new ArrayList<>();
        lisre = cs.getAllCategories();
        ObservableList<Categorie> data = FXCollections.observableArrayList(lisre);
        listcategorie.setItems(data);
    }
    
    
    
    
    
}

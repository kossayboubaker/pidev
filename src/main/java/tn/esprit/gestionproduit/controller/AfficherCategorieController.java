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
import tn.esprit.gestionproduit.entity.Categorie;
import tn.esprit.gestionproduit.service.CategorieService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AfficherCategorieController implements Initializable {

    @FXML
    private TableView<Categorie> table;
    @FXML
    private TableColumn<Categorie, String> des;
    
    
    private ObservableList<Categorie> UserData = FXCollections.observableArrayList();
    
    CategorieService cs = new CategorieService();
    
    static int id ;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            List<Categorie> listb= new ArrayList<Categorie>();
            
            listb = cs.getAllCategories();
     
            UserData.clear();
            UserData.addAll(listb);
            table.setItems(UserData);
            
            des.setCellValueFactory
                      (
                              new PropertyValueFactory<>("description")
                      );
        } catch (SQLDataException ex) {
            Logger.getLogger(AfficherCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void Ajouter(ActionEvent event) {
     
            Parent root;
               try {
              root = FXMLLoader.load(getClass().getResource("/AddCategorie.fxml"));
               Stage myWindow = (Stage) table.getScene().getWindow();
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
        
              id =  table.getSelectionModel().getSelectedItem().getId_categori();     
              Parent root;
               try {
              root = FXMLLoader.load(getClass().getResource("/ModefierCategorie.fxml"));
               Stage myWindow = (Stage) table.getScene().getWindow();
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
        
        int id =  table.getSelectionModel().getSelectedItem().getId_categori();     
        cs.deleteCategori(id);
        resetTableData();
        
        
    }
    
    
        public void resetTableData() throws SQLDataException
    {
        List<Categorie> lisre = new ArrayList<>();
        lisre = cs.getAllCategories();
        ObservableList<Categorie> data = FXCollections.observableArrayList(lisre);
        table.setItems(data);
    }
    
    
    
    
    
}

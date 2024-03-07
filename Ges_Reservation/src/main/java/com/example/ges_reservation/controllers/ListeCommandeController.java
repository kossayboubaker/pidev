/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.example.ges_reservation.models.Commande;
import com.example.ges_reservation.services.Service;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;


public class ListeCommandeController implements Initializable {

    @FXML
    private ListView<String> listcmd;
    
    private ObservableList<Commande> UserData = FXCollections.observableArrayList();
    
    Service s = new Service();
    
    public static int ide ;
    
   
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
                try {
            List<Commande> listb= new ArrayList<Commande>();
            
            listb = s.getAllCommande();
     
            System.out.println("hello"+listb);
            UserData.clear();
            UserData.addAll(listb);
            listcmd.setItems(UserData);
            

        } catch (SQLDataException ex) {
            Logger.getLogger(ListeCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
*/

        Commande commandeService = new Commande();
        try {
            List<Commande> commandes = s.getAllCommande();

            // Trier la liste des commandes par ID avant de les ajouter à l'ObservableList
          //  commandes.sort(Comparator.comparingInt(Commande::getEtat));

            // Créer une ObservableList pour stocker les données des commandes
            ObservableList<String> CommandeDataList = FXCollections.observableArrayList();

            // Ajouter les titres des colonnes
            String columnTitles = String.format("%-35s %-22s %-22s ", "etat", "prix", "type");
            CommandeDataList.add(columnTitles);

            // Itérer à travers la liste des commandes et ajouter leurs détails à la CommandeDataList
            for (Commande commande : commandes) {
                String commandeData = String.format("%-30s %-20s %-20s ",
                        commande.getEtat(),
                        commande.getPrix(),
                        commande.getType()

                );
                CommandeDataList.add(commandeData);
            }

            // Définir les éléments pour la ListView
            listcmd.setItems(CommandeDataList);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        
    }    



}

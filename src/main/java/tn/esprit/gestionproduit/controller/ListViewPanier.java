/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.controller;



import javafx.scene.control.ListCell;
import tn.esprit.gestionproduit.entity.Produit;

/**
 *
 * @author dell
 */
public class ListViewPanier extends ListCell<Produit> {
    
    
     @Override
     public void updateItem(Produit e, boolean empty)
    {
        super.updateItem(e,empty);
        if(e != null)
        {
            
            PaniertemController data = new PaniertemController();
            data.setInfo(e);
            setGraphic(data.getBox());
        }
    }
    
}

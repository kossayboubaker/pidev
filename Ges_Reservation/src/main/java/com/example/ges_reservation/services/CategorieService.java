/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ges_reservation.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.ges_reservation.models.Categorie;
import com.example.ges_reservation.utils.MyDatabase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategorieService {
    
    private final Connection cnx;

    private static CategorieService instance;
    
        public CategorieService() {
        cnx = MyDatabase.getInstance().getConnection();
    }
    
    public static CategorieService getInstance()
    {
        if (instance == null) {
            instance = new CategorieService();
        }
        return instance; 
    }
    
   public void addCategorie(Categorie q)throws SQLDataException, SQLException{
        
         
         
         String query ="INSERT INTO `categorie`(`description`) VALUES (?)";
 
         PreparedStatement st;
        
        try {
            st = cnx.prepareStatement(query);
                st.setString(1,q.getDescription());
                st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
        }
                

    }

    public boolean ModifierCategorie(Categorie e) throws SQLDataException {

               
                String query = "UPDATE `categorie` SET `description`=? WHERE `id_categori` = ?";
		PreparedStatement st;
        try {
                st = cnx.prepareStatement(query);
                st.setString(1,e.getDescription());
                st.setInt(2,e.getId_categori());
                st.executeUpdate();
                return true;
                
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
                
               
    }

    public ObservableList<Categorie> getAllCategories() throws SQLDataException {

        
        List<Categorie> list =new ArrayList<Categorie>();
        int count =0;
        
        String requete="select * from categorie";
         try{
             Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(requete);
            
            while (rs.next()){
                
                Categorie e = new Categorie();
                e.setId_categori(rs.getInt("id_categori"));
                e.setDescription(rs.getString("description"));
                
                list.add(e);
                
                count++;
            }
            if(count == 0){
                return null;
           }else{
             ObservableList lc_final = FXCollections.observableArrayList(list);

               return lc_final;
            
           
        }
         }
        catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
   
           
}
       public Categorie get_CatById(int i) {
        Categorie e = new Categorie();
        int nombre = 0;
        String requete = "select * from categorie where id_categori="+i;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                e.setId_categori(rs.getInt("id_categori"));
                e.setDescription(rs.getString("description"));
                
                nombre++;

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;

    }
       
       
       
     public Categorie getCategorieByDescription(String i) {
        Categorie e = new Categorie();
        int nombre = 0;
        String requete =  "SELECT * FROM `categorie` WHERE description =\""+i+"\"";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                e.setId_categori(rs.getInt("id_categori"));
                e.setDescription(rs.getString("description"));
                
                nombre++;

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;

    }
    
    
    

    public boolean deleteCategori(int idCat) throws SQLDataException {
        
        try {
            
            Statement st=cnx.createStatement();
            String req= "DELETE FROM categorie WHERE id_categori="+idCat;
            st.executeUpdate(req);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CategorieService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }


}
package com.example.ges_reservation.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.ges_reservation.models.Produit;
import com.example.ges_reservation.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduitService {


    private final Connection cnx;

    private static ProduitService instance;

    public ProduitService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    public static ProduitService getInstance()
    {
        if (instance == null) {
            instance = new ProduitService();
        }
        return instance;
    }

    public void addProdui(Produit q)throws SQLDataException, SQLException {



        String query ="INSERT INTO `produit`(`nom`,`description`,`prix`,`quantite`,`image`,`id_cat`,`certif`) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement st;

        try {
            st = cnx.prepareStatement(query);
            st.setString(1,q.getNom());
            st.setString(2,q.getDescription());
            st.setDouble(3,q.getPrix());
            st.setInt(4,q.getQuantite());
            st.setString(5,q.getImage());
            st.setInt(6,q.getId_cat());
            st.setString(7,q.getCertif());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void ModifierProduit(Produit q) throws SQLDataException {


        String query = "UPDATE `produit` SET `nom`=?,`description`=?,`prix`=?,`quantite`=?,`image`=?,`id_cat`=? ,`certif`=? WHERE `idp` = ?";
        PreparedStatement st;
        try {
            st = cnx.prepareStatement(query);
            st.setString(1,q.getNom());
            st.setString(2,q.getDescription());
            st.setFloat(3,q.getPrix());
            st.setInt(4,q.getQuantite());
            st.setString(5,q.getImage());
            st.setInt(6,q.getId_cat());
            st.setString(7,q.getCertif());
            st.setInt(8,q.getIdProduit());
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }



    public void ModifierStock(int q,int id) throws SQLDataException {


        String query = "UPDATE `produit` SET `nom`=?,`description`=?,`prix`=?,`quantite`=?,`image`=?,`id_cat`=? ,`certif`=? WHERE `idp` = ?";
        PreparedStatement st;
        try {
            st = cnx.prepareStatement(query);


            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public ObservableList<Produit> getAllProuduits() throws SQLDataException {


        List<Produit> list =new ArrayList<Produit>();
        int count =1;

        String requete="select * from produit";
        try{
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()){

                Produit e = new Produit();
                e.setNom(rs.getString("nom"));
                e.setDescription(rs.getString("description"));
                e.setPrix(rs.getFloat("prix"));
                e.setQuantite(rs.getInt("quantite"));
                e.setImage(rs.getString("image"));
                e.setId_cat(rs.getInt("id_cat"));
                e.setIdProduit(rs.getInt("idp"));
                e.setCertif(rs.getString("certif"));
                list.add(e);

                count++;
                System.out.printf("Error"+count);
            }
            if(count == 0){
                return null;
            }else{

                return FXCollections.observableArrayList(list);


            }
        }
        catch (SQLException ex) {

            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }


    }

    public Produit getProduitById(int i) {
        Produit e = new Produit();
        int nombre = 0;
        String requete = "select * from produit where idp="+i;
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                e.setNom(rs.getString("nom"));
                e.setDescription(rs.getString("description"));
                e.setPrix(rs.getFloat("prix"));
                e.setQuantite(rs.getInt("quantite"));
                e.setImage(rs.getString("image"));
                e.setId_cat(rs.getInt("id_cat"));
                e.setIdProduit(rs.getInt("idp"));
                e.setCertif(rs.getString("certif"));
                nombre++;

            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return e;

    }





    public void delete(int idCat) throws SQLDataException {

        try {

            Statement st=cnx.createStatement();
            String req= "DELETE FROM produit WHERE idp="+idCat;
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

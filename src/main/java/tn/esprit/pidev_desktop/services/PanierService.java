package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Panier;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PanierService implements ProService<Panier> {
    private Connection connection;

    public PanierService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Panier panier) throws SQLException {
        String req = "INSERT INTO panier (prod_id,stockp,nomp,prixp,imagep,descriptionp) VALUES(" + panier.getProd_id() + "," + panier.getStockp() + ",'" + panier.getNomp() + "'," + panier.getPrixp() + ",'" + panier.getImagep() + "','" + panier.getDescriptionp() + "')";

        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    @Override
    public void modifier(Panier panier) throws SQLException {

        String req = "UPDATE panier SET prod_id = ?,nomp = ?,prixp = ?,stockp = ?,imagep = ?,descriptionp = ? WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, panier.getProd_id());
        ps.setString(2, panier.getNomp());
        ps.setFloat(3, panier.getPrixp());
        ps.setInt(4, panier.getStockp());
        ps.setString(5, panier.getImagep());
        ps.setString(6, panier.getDescriptionp());
        ps.executeUpdate();

    }

    @Override
    public void supprimer(int idp) throws SQLException {

        String req = "DELETE FROM panier WHERE idp=?";

        PreparedStatement cs = connection.prepareStatement(req);
        cs.setInt(1, idp);
        cs.executeUpdate();
    }

    @Override
    public List<Panier> recuperer() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String req = "SELECT * FROM panier ";
        Statement cs = connection.createStatement();
        ResultSet rs = cs.executeQuery(req);
        while (rs.next()) {
            Panier p = new Panier();
            p.setIdp(rs.getInt(1));
            p.setNomp(rs.getString(2));
            p.setPrixp(rs.getFloat(3));
            p.setDescriptionp(rs.getString(4));
            p.setStockp(rs.getInt(5));
            p.setImagep(rs.getString(6));
            p.setProd_id(rs.getInt(7));

            paniers.add(p);
        } return paniers;
    }


    @Override
    public List<Panier> recupererById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Panier> recupererByNom(String nom) throws SQLException {
        return null;
    }

    @Override
    public List<Panier> recupererByPrix(float prix) throws SQLException {
        return null;
    }

    @Override
    public List<Panier> trie_par_nom() throws SQLException {
        return null;
    }

    @Override
    public List<Panier> trie_par_nom2() throws SQLException {
        return null;
    }

    @Override
    public List<Panier> recherche() throws SQLException {
        return null;
    }

    @Override
    public int countProduits() throws SQLException {
        return 0;

    }


/*
    @Override
    public List<Panier> recuperer() throws SQLException {
        List<Panier> paniers = new ArrayList<>();
        String req = "SELECT * FROM panier ";
        Statement cs = connection.createStatement();
        ResultSet rs = cs.executeQuery(req);
        while (rs.next()) {
            Panier p = new Panier();
            p.setIdp(rs.getInt(1));
            p.setNomp(rs.getString(2));
            p.setPrixp(rs.getFloat(3));
            p.setDescriptionp(rs.getString(4));
            p.setStockp(rs.getInt(5));
            p.setImagep(rs.getString(6));
            p.setProd_id(rs.getInt(7));

            paniers.add(p);
        }
        return paniers;
    }

*/





}
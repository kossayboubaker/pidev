package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitService implements ProService<Produit> {

    // variable de type Connection
    private Connection connection;

// initialisation de la variable et elabore a la basse de donne

    public ProduitService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String req = "INSERT INTO produit (nom, description, prix) VALUES ('" + produit.getNom() + "', '" + produit.getDescription() + "', " + produit.getPrix() + ")";
        //objet de type statement pour execution de la requete
        Statement st = connection.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void modifier(Produit produit) throws SQLException {

        String req = "UPDATE produit SET nom = ?, description = ?, prix = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, produit.getNom());
        ps.setString(2, produit.getDescription());
        ps.setFloat(3, produit.getPrix());
        ps.setInt(4, produit.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM produit WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    @Override
    public List<Produit> recuperer() throws SQLException {
        List<Produit> produits = new ArrayList<>();
        String req = "SELECT * FROM produit";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Produit produit = new Produit();
            produit.setId(rs.getInt("id"));
            produit.setNom(rs.getString("nom"));
            produit.setPrix(rs.getFloat("prix"));
            produit.setDescription(rs.getString("description"));

            produits.add(produit);
        }
        return produits;
    }

    @Override
    public List<Produit> recupererById(int id) throws SQLException {

            List<Produit> produits = new ArrayList<>();
            String req = "SELECT * FROM produit where id="+id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Produit produit = new Produit();
            produit.setId(rs.getInt("id"));
            produit.setNom(rs.getString("nom"));
            produit.setPrix(rs.getFloat("prix"));
            produit.setDescription(rs.getString("description"));

            produits.add(produit);
            }
            return produits;
        }
    }



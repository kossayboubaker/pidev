package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void supprimer(int id) {

    }

    @Override
    public List<Produit> recuperer() {
        return null;
    }
}

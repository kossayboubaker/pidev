package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Panier;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PanierService implements PaService<Panier> {
    private Connection connection;

    public PanierService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public Panier getPanierById(int id_panier) throws SQLException {
        String req = "SELECT * FROM panier where idp="+id_panier;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        Panier panier = new Panier();

        while (rs.next()) {
            panier.setIdp(rs.getInt("panier_id"));
        }
        return panier;
    }

    @Override
    public List<Panier> getAllPaniers() {
        List<Panier> paniers = new ArrayList<>();
        String req = "SELECT * FROM panier";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Panier panier = new Panier();
                panier.setIdp(rs.getInt("panier_id"));
                // Set other properties of the Panier object if needed
                paniers.add(panier);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
        return paniers;
    }

    @Override
    public void deletePanier(int id_panier) {
        String req = "DELETE FROM panier WHERE idp=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, id_panier);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }

    @Override
    public void updatePanier(Panier panier) {
        String req = "UPDATE panier SET ... WHERE idp=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            // Set the new values for the Panier object in the prepared statement
            pst.setInt(1, panier.getIdp());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }

}
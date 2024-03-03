package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Panier;
import tn.esprit.pidev_desktop.models.PanierItem;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PanierItemService implements IPanierItem {


    private Connection connection;

// initialisation de la variable et elabore a la basse de donne

    public PanierItemService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public PanierItem getPanierItemById(int id_panier_item) throws SQLException {
        String req = "SELECT * FROM panierItem where id="+id_panier_item;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        PanierItem panierItem = new PanierItem();

        while (rs.next()) {
            panierItem.setId_cartItem(rs.getInt("id"));
        }
        return panierItem;
    }

    @Override
    public void createPanierItem(Panier panier, Produit product, int quantite) {
        String req = "INSERT INTO panierItem (panier_id, product_id, quantite) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, panier.getIdp());
            pst.setInt(2, product.getId());
            pst.setInt(3, quantite);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }

    @Override
    public void updatePanierItem(PanierItem panier_item) {
        String req = "UPDATE panierItem SET quantite=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, panier_item.getQuantite());
            pst.setInt(2, panier_item.getId_panierItem());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }


    @Override
    public void deletePanierItem(int id_panier_item) {
        String req = "DELETE FROM panierItem WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, id_panier_item);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }

    @Override
    public List<PanierItem> getAllPanierItems() {
        List<PanierItem> panierItems = new ArrayList<>();
        String req = "SELECT * FROM panierItem";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                PanierItem panierItem = new PanierItem();
                panierItem.setId_cartItem(rs.getInt("id"));
                panierItem.setPanier(new PanierService().getPanierById(rs.getInt("panier_id")));
                panierItem.setProduit(new ProduitService().recupererByIdY(rs.getInt("product_id")));
                panierItem.setQuantite(rs.getInt("quantite"));
                panierItems.add(panierItem);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
        return panierItems;
    }


    @Override
    public List<PanierItem> getPanierItemByPanierId(int id_panier) {
        List<PanierItem> panierItems = new ArrayList<>();
        String req = "SELECT * FROM panierItem WHERE panier_id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, id_panier);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    PanierItem panierItem = new PanierItem();
                    panierItem.setId_cartItem(rs.getInt("id"));
                    panierItem.setPanier(new PanierService().getPanierById(rs.getInt("panier_id")));
                    panierItem.setProduit(new ProduitService().recupererByIdY(rs.getInt("product_id")));
                    panierItem.setQuantite(rs.getInt("quantite"));
                    panierItems.add(panierItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's requirements
        }
        return panierItems;
    }



}

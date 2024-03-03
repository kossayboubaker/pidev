package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Panier;
import tn.esprit.pidev_desktop.models.PanierItem;
import tn.esprit.pidev_desktop.models.Produit;

import java.sql.SQLException;
import java.util.List;

public interface IPanierItem {
    public void createPanierItem(Panier panier, Produit product, int quantite);
    public void updatePanierItem(PanierItem panier_item);
    public void deletePanierItem(int id_panier_item);
    public PanierItem getPanierItemById(int id_panier_item) throws SQLException;
    public List<PanierItem> getAllPanierItems();
    public List<PanierItem> getPanierItemByPanierId(int id_panier);
}

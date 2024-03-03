package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Panier;

import java.sql.SQLException;
import java.util.List;
public interface PaService<P> {
    //public void createPanier(User user);
    public Panier getPanierById(int id_panier) throws SQLException;
    public List<Panier> getAllPaniers();
    public void deletePanier(int id_panier);
    public void updatePanier(Panier panier);


}

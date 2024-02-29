package tn.esprit.pidev_desktop.services;

import java.sql.SQLException;
import java.util.List;

public interface ProService <T> {

    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;
    List<T> recupererById(int id) throws SQLException;
    List<T> recupererByNom(String nom) throws SQLException;
    List<T> recupererByPrix(float prix) throws SQLException;

    List<T> trie_par_nom() throws SQLException;

    List<T> trie_par_nom2() throws SQLException;

    List<T> recherche() throws SQLException;

    public int countProduits() throws SQLException;

}

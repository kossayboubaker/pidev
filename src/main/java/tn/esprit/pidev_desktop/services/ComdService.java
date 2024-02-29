package tn.esprit.pidev_desktop.services;

import java.sql.SQLException;
import java.util.List;

public interface ComdService <T>{

    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;
    void supprimer(int quantite) throws SQLException;

    List<T> recuperer() throws SQLException;
    List<T> joiner() throws SQLException;




}

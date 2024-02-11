package tn.esprit.pidev_desktop.services;

import java.sql.SQLException;
import java.util.List;

public interface ProService <T> {

    void ajouter(T t) throws SQLException;
    void modifier(T t);
    void supprimer(int id);

    List<T> recuperer();
}

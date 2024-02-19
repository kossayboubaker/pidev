package services;

import models.cinema;

import java.sql.SQLException;
import java.util.List;

public interface Iservice <c>{
    void ajouter(cinema cinema) throws SQLException;
    void modifer(cinema cinema ) throws SQLException;



    void supprimer(int id) throws SQLException;
    List <c> recuperer() throws SQLException;
}
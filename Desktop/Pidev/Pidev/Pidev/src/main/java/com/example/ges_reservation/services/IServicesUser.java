package com.example.ges_reservation.services;

import com.example.ges_reservation.models.CodePromo;

import java.sql.SQLException;
import java.util.List;

public interface IServicesUser<T> {
    void ajouter (T t) throws SQLException;

    void modifier (T t) throws SQLException;



    void supprimer (T t);

    void supprimer(int id) throws SQLException;

    List<T> recupperer() throws SQLException;


}

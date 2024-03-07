package com.example.ges_reservation.services;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;

import java.sql.SQLException;
import java.util.List;

public interface IsiegeServ<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;

    void supprimer(int SiegeID) throws SQLException;

    List<T> recuperer() throws SQLException;
    public Sieges afficherSibyID(int SiegeID) ;

}

package com.example.ges_reservation.services;

import com.example.ges_reservation.models.Reservations;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;

    void supprimer(int ReservationID) throws SQLException;

    List<T>recuperer() throws SQLException;


    public Reservations afficherResbyID(int ReservationID) ;
}

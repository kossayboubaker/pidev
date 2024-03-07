package com.example.ges_reservation.services;

import com.example.ges_reservation.models.cinema;
import com.example.ges_reservation.models.cinema;

import java.sql.SQLException;
import java.util.List;

public interface Iservicecinema<c>{
    void ajouter(cinema cinema) throws SQLException;
    void modifer(cinema cinema ) throws SQLException;


    void supprimer(int id) throws SQLException;
    List <c> recuperer() throws SQLException;
}
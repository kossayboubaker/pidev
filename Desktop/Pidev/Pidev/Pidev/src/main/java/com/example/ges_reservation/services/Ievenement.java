package com.example.ges_reservation.services;

import com.example.ges_reservation.models.evenement;
import com.example.ges_reservation.models.evenement;

import java.sql.SQLException;
import java.util.List;

public interface Ievenement<E> {
    void ajouter(evenement evenement) throws SQLException;

    void modifer(evenement evenement) throws SQLException;

    void supprimer(int id) throws SQLException;


    List<evenement> recuperer() throws SQLException;


}

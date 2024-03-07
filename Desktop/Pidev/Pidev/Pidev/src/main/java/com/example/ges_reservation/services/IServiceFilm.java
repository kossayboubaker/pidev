package com.example.ges_reservation.services;
import com.example.ges_reservation.models.Categories;
import com.example.ges_reservation.models.Films;

import java.sql.SQLException;
import java.util.List;

public interface IServiceFilm <T> {
    void ajouter (T t) throws SQLException;

    List<Films> recuperer() throws SQLException;

}

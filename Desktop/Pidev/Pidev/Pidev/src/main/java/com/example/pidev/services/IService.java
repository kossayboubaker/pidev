package com.example.pidev.services;
import com.example.pidev.models.Films;
import java.sql.SQLException;
import java.util.List;

public interface IService <T> {
    void ajouter (T t) throws SQLException;

    List<Films> recuperer() throws SQLException;
}

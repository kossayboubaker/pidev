package com.example.pidev.services;

import com.example.pidev.models.Categories;
import java.sql.SQLException;
import java.util.List;

public interface CService <T> {
    void ajouter (T t) throws SQLException;

    List<Categories> recuperer() throws SQLException;
}

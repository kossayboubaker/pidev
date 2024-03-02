package com.example.pidev.services;

import java.sql.Connection;
import com.example.pidev.models.Categories;
import com.example.pidev.utils.MyDatabase;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriesService implements CService<Categories> {

    private Connection connection;

    public CategoriesService() {
        connection = MyDatabase.getInstance().getConnection();
    }


    public void ajouter(Categories categorie) throws SQLException {

        String req = "INSERT INTO Categories (CategorieID, NomCategorie, DescriptionCategorie) VALUES(" +
                categorie.getCategorieID() + ", '" +
                categorie.getNomCategorie() + "', '" +
                categorie.getDescriptionCategorie() + "')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

    public void modifier(Categories categorie) throws SQLException {
        String req = "UPDATE Categories SET NomCategorie = ?, DescriptionCategorie = ? WHERE CategorieID = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, categorie.getNomCategorie());
        ps.setString(2, categorie.getDescriptionCategorie());
        ps.setInt(3, categorie.getCategorieID());
        ps.executeUpdate();
    }

    public void supprimer(int categorieID) throws SQLException {
        String req = "DELETE FROM Categories WHERE CategorieID = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, categorieID);
        ps.executeUpdate();
    }

    @Override
    public List<Categories> recuperer() throws SQLException {
        List<Categories> categories = new ArrayList<>();
        String req = "SELECT * FROM Categories";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Categories categorie = new Categories(
                    rs.getInt("CategorieID"),
                    rs.getString("NomCategorie"),
                    rs.getString("DescriptionCategorie")
            );
            categories.add(categorie);
        }
        return categories;
    }
}

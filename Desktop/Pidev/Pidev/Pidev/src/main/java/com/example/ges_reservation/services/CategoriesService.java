package com.example.ges_reservation.services;
import java.sql.Connection;
import com.example.ges_reservation.models.Categories;
import com.example.ges_reservation.models.Films;
import com.example.ges_reservation.utils.MyDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        String deleteFilmsReq = "DELETE FROM films WHERE CategorieID = ?";
        PreparedStatement ps1 = connection.prepareStatement(deleteFilmsReq);
        ps1.setInt(1, categorieID);
        ps1.executeUpdate();

        // Ensuite, supprimer la catégorie elle-même
        String deleteCategorieReq = "DELETE FROM categories WHERE CategorieID = ?";
        PreparedStatement ps2 = connection.prepareStatement(deleteCategorieReq);
        ps2.setInt(1, categorieID);
        ps2.executeUpdate();
    }



    public List<Categories> recuperer() throws SQLException {
        List<Categories> categories = new ArrayList<>();
        String req = "SELECT * FROM Categories";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Categories categorie = new Categories();
            categorie.setCategorieID(rs.getInt("CategorieID"));
            categorie.setNomCategorie(rs.getString("NomCategorie"));
            categorie.setDescriptionCategorie(rs.getString("DescriptionCategorie"));

            categories.add(categorie);
        }
        return categories;



    }



   /* public List<Films> recuperere() throws SQLException {
        List<Films> films = new ArrayList<>();

        String req = "SELECT * FROM films";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Films film = new Films();

                film.setFilmID(rs.getInt("FilmID"));
                film.setTitre(rs.getString("titre"));
                film.setRealisateur(rs.getString("realisateur"));
                film.setAnneeSortie(rs.getInt("anneeSortie"));
                film.setDuree(rs.getInt("duree"));
                film.setSynopsis(rs.getString("synopsis"));
                film.setCategorieID(rs.getInt("categorieID"));
                film.setImage(rs.getString("image"));

                films.add(film);
            }
            return films;
        }
    } */




}




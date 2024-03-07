package com.example.ges_reservation.services;

import com.example.ges_reservation.models.Review;
import com.example.ges_reservation.models.evenement;
import com.example.ges_reservation.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class evenementservice implements Ievenement<evenement> {
    private Connection connection;
    public evenementservice(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(evenement evenement) throws SQLException {
        String sql = "insert into evenement (id_cinema,nom_ev,description,date,periode) " +
                "values('" + evenement.getId_cinema() + "','" + evenement.getNom_ev()
                + "' ,'" + evenement.getDescription() +"','" +evenement.getDate() +"','"+evenement.getPeriode() + "')";

        Statement statement =  connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifer(evenement evenement) throws SQLException {
        String sql = "UPDATE evenement SET id_cinema = ?, nom_ev = ?, description = ?, date = ?, periode = ? WHERE id = ?";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, evenement.getId_cinema());
        preparedStatement.setString(2, evenement.getNom_ev());
        preparedStatement.setString(3,evenement.getDescription());
        preparedStatement.setDate(4, evenement.getDate());
        preparedStatement.setString(5, evenement.getPeriode());
        preparedStatement.setInt(6, evenement.getId());
        preparedStatement.executeUpdate();

    }


    @Override
    public List<evenement> recuperer() throws SQLException {
        String sql = "select * from evenement";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<evenement> list = new ArrayList<>();
        while (rs.next()){
            evenement p = new evenement();
            p.setId(rs.getInt("id"));
            p.setId_cinema(rs.getInt("id_cinema"));
            p.setNom_ev(rs.getString("nom_ev"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setPeriode(rs.getString("periode"));
            list.add(p);

        }
        return list;
    }



    public evenement recuperere(int id_cinema) throws SQLException {
        evenement p = new evenement();
        String sql = "select * from evenement where id_cinema=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id_cinema);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){

            p.setId(rs.getInt("id"));
            p.setId_cinema(rs.getInt("id_cinema"));
            p.setNom_ev(rs.getString("nom_ev"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setPeriode(rs.getString("periode"));


        }
        preparedStatement.close();
        return p;
    }

    public evenement getEvenementByID(int id) throws SQLException {
        evenement p = new evenement();
        String sql = "select * from evenement where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){

            p.setId(rs.getInt("id"));
            p.setId_cinema(rs.getInt("id_cinema"));
            p.setNom_ev(rs.getString("nom_ev"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setPeriode(rs.getString("periode"));


        }
        preparedStatement.close();
        return p;
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM evenement WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }




    public List<evenement> joiner(int id_cinema) throws SQLException {
        List<evenement> list = new ArrayList<>();
        String req = "SELECT * FROM evenement e INNER JOIN cinema c ON e.id_cinema = c.id where id_cinema=?;";
        PreparedStatement st = connection.prepareStatement(req);
        st.setInt(1,id_cinema);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            evenement p = new evenement();

            p.setId(rs.getInt("id"));
            p.setId_cinema(rs.getInt("id_cinema"));


            p.setNom_ev(rs.getString("nom_ev"));
            p.setDescription(rs.getString("description"));
            p.setDate(rs.getDate("date"));
            p.setPeriode(rs.getString("periode"));


            list.add(p);
        }
        return list;
    }


    public Map<String, Long> getevenementCountByStationWithLieu() {
        Map<String, Long> evenementCount = new HashMap<>();
        String req = "SELECT c.nom, COUNT(e.id_cinema) AS evenement_count " +
                "FROM evenement e " +
                "INNER JOIN cinema c ON e.id_cinema = c.id " +
                "GROUP BY c.nom";

        try (Statement stmt = connection.createStatement(); ResultSet res = stmt.executeQuery(req)) {
            while (res.next()) {
                String nom = res.getString("nom");
                long evenementCounts = res.getLong("evenement_count");
                evenementCount.put(nom, evenementCounts);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return evenementCount;
    }

    public List<evenement> search(String search) {
        List<evenement> evenementList = new ArrayList<>();
        try {
            String query = "SELECT * FROM evenement WHERE nom_ev LIKE ? OR description LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcours du résultat de la requête
            while (resultSet.next()) {
                evenement evenement = new evenement();
                evenement.setId(resultSet.getInt("id"));
                evenement.setId_cinema(resultSet.getInt("id_cinema"));
                evenement.setNom_ev(resultSet.getString("nom_ev"));
                evenement.setDescription(resultSet.getString("description"));
                evenement.setDate(resultSet.getDate("date"));
                evenement.setPeriode(resultSet.getString("periode"));

                evenementList.add(evenement);
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenementList;
    }

    public int getTotalEventsReviews(int id_evenement) {
        int total = 0;
        try {
            // Ensure you have a valid and open database connection (the 'connection' variable)
            if (connection != null && !connection.isClosed()) {
                String query = "SELECT count(*) FROM review WHERE id_evenement = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, id_evenement);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            total = resultSet.getInt(1);
                        }
                    }
                }
            } else {
                // Handle the case when the database connection is not valid or closed
                System.err.println("Database connection is not valid or closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public int getTotalEventsReviewsByStar(int id_evenement, int value) {
        int total = 0;
        try {
            String query = "SELECT count(*) FROM review WHERE id_evenement = ? and value = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_evenement);
            preparedStatement.setInt(2, value);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Review> getAllComments(int id_evenement) {
        List<Review> reviewList = new ArrayList<>();
        try {
            String query = "SELECT * FROM review WHERE id_evenement = ? ORDER BY id_review DESC ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_evenement);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Parcours du résultat de la requête
            while (resultSet.next()) {
                Review review = new Review();
                review.setId_review(resultSet.getInt("id_review"));
                review.setTitle(resultSet.getString("title"));
                review.setComments(resultSet.getString("comment"));
                review.setValue(resultSet.getInt("value"));
                review.setId_evenement(resultSet.getInt("id_evenement"));
                reviewList.add(review);
            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    public void addReview(Review review) {
        try {
            String req = "INSERT INTO review(title, comment,value, id_evenement) VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, review.getTitle());
            ps.setString(2, review.getComments());
            ps.setInt(3, review.getValue());
            ps.setInt(4, review.getId_evenement());
            ps.executeUpdate();
            System.out.println("reviews added successfully");
            ps.close();
        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite lors de la récupération du review : " + e.getMessage());
        }
    }
}
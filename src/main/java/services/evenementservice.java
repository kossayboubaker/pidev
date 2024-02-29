package services;

import models.cinema;
import models.evenement;
import utils.mydb;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class evenementservice implements Ievenement<evenement> {
    private Connection connection;
    public evenementservice(){
        connection = mydb.getInstance().getConnection();
    }
    cinema cs = new cinema();

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
}
package services;

import models.evenement;
import utils.mydb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class evenementservice implements Ievenement<evenement> {
    private Connection connection;
    public evenementservice(){
        connection = mydb.getInstance().getConnection();
    }


    @Override
    public void ajouter(evenement evenement) throws SQLException {
        String sql = "insert into evenement (idc,nom,description,date,periode) " +
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
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM evenement WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
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


    public List<evenement> joiner() throws SQLException {
        List<evenement> evenement = new ArrayList<>();
        String req = "SELECT * FROM evenement e INNER JOIN cinema c ON e.idc = c.id ;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            evenement ev = new evenement();

            ev.setId(rs.getInt("id"));
            ev.setId_cinema(rs.getInt("id_cinema"));


            ev.setNom_ev(rs.getString("nom_ev"));
            ev.setDescription(rs.getString("description"));
            ev.setDate(rs.getDate("date"));
            ev.setPeriode(rs.getString("periode"));


            evenement.add(ev);;
        }
        return evenement;
    }
}
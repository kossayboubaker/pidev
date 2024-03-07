package com.example.ges_reservation.services;

import com.example.ges_reservation.models.cinema;
import com.example.ges_reservation.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class cinemaservice implements Iservicecinema<cinema> {
    private Connection connection;
    public cinemaservice(){
        connection = MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(cinema cinema) throws SQLException {
        String sql = "insert into cinema (nom,etat,place,nb_salle) " +
                "values('" + cinema.getNom() + "','" + cinema.getEtat()
                + "' ,'" + cinema.getPlace() +"','" +cinema.getNb_salle() + "')";

        Statement statement =  connection.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public void modifer(cinema cinema) throws SQLException {
        String sql = "UPDATE cinema SET place = ?, nb_salle = ?, etat = ?, nom = ? WHERE id = ?";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cinema.getPlace());
        preparedStatement.setInt(2, cinema.getNb_salle());
        preparedStatement.setString(3,cinema.getEtat());
        preparedStatement.setString(4, cinema.getNom());
        preparedStatement.setInt(5, cinema.getId());
        preparedStatement.executeUpdate();

    }





    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM cinema WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<cinema> recuperer() throws SQLException {
        String sql = "select * from cinema";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<cinema> list = new ArrayList<>();
        while (rs.next()){
            cinema p = new cinema();
            p.setId(rs.getInt("id"));
            p.setPlace(rs.getString("place"));
            p.setNb_salle(rs.getInt("nb_salle"));
            p.setEtat(rs.getString("etat"));
            p.setNom(rs.getString("nom"));

            list.add(p);

        }
        return list;
    }


}
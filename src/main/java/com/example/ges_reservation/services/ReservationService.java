package com.example.ges_reservation.services;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservations> {
    private Connection connexion;

    public ReservationService() {
        connexion = MyDatabase.getInstance().getConnection();

    }


    @Override
    public void ajouter(Reservations reservations) throws SQLException {

        String req = "INSERT INTO reservations (IDUtilisateur,FilmID,DateReservation,HeureReservation,NombrePlacesReservees) VALUES(" + reservations.getUtilisateurID() + "," + reservations.getFilmID() + ",'" + reservations.getDateReservation() + "','" + reservations.getHeureReservation() + "'," + reservations.getNombrePlacesReservees() + ")";
        Statement st = connexion.createStatement();
        st.executeUpdate(req);

    }

    @Override
    public void modifier(Reservations reservations) throws SQLException {
        String req = "UPDATE reservations " +
        "JOIN utilisateurs ON reservations.IDUtilisateur = utilisateurs.IDUtilisateur " +
                "JOIN films ON reservations.FilmID = films.FilmID " +
                "SET reservations.FilmID = ?, reservations.DateReservation = ?, " +
                "reservations.HeureReservation = ?, reservations.NombrePlacesReservees = ? " +
                "WHERE reservations.ReservationID = ?";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1, reservations.getFilmID());
        ps.setDate(2, reservations.getDateReservation());
        ps.setTime(3, reservations.getHeureReservation());
        ps.setInt(4, reservations.getNombrePlacesReservees());
        ps.setInt(5, reservations.getReservationID());
        ps.executeUpdate();

    }

    @Override
    public void supprimer(int ReservationID) throws SQLException {
        try {
            String deleteSiegesReq = "DELETE FROM sieges WHERE ReservationID = ?";
            PreparedStatement deleteSiegesPs = connexion.prepareStatement(deleteSiegesReq);
            deleteSiegesPs.setInt(1, ReservationID);
            deleteSiegesPs.executeUpdate();

            String deleteReservationReq = "DELETE FROM reservations WHERE ReservationID = ?";
            PreparedStatement deleteReservationPs = connexion.prepareStatement(deleteReservationReq);
            deleteReservationPs.setInt(1, ReservationID);
            deleteReservationPs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }


    @Override
    public List<Reservations> recuperer() throws SQLException {
        List<Reservations> reservationsList = new ArrayList<>();

        String req = "SELECT * FROM reservations";
        Statement st = connexion.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Reservations reservation = new Reservations();
            reservation.setReservationID(rs.getInt("ReservationID"));
            reservation.setUtilisateurID(rs.getInt("IDUtilisateur"));
            reservation.setFilmID(rs.getInt("FilmID"));
            reservation.setDateReservation(rs.getDate("DateReservation"));
            reservation.setHeureReservation(rs.getTime("HeureReservation"));
            reservation.setNombrePlacesReservees(rs.getInt("NombrePlacesReservees"));

            reservationsList.add(reservation);
        }

        return reservationsList;
    }


    public Reservations afficherResbyID(int ReservationID) {
        Reservations reservation = new Reservations();
        String request = "SELECT * FROM reservations WHERE ReservationID LIKE '%" + ReservationID + "%'";
        try {
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(request);
            while (rs.next()) {
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setUtilisateurID(rs.getInt("IDUtilisateur"));
                reservation.setFilmID(rs.getInt("FilmID"));
                reservation.setDateReservation(rs.getDate("DateReservation"));
                reservation.setHeureReservation(rs.getTime("HeureReservation"));
                reservation.setNombrePlacesReservees(rs.getInt("NombrePlacesReservees"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reservation;

    }

}
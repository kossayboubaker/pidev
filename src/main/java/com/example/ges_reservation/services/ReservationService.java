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
        String req = "INSERT INTO reservations (FilmID,DateReservation,HeureReservation,NombrePlacesReservees) VALUES(?,?,?,?)";
        PreparedStatement st = connexion.prepareStatement(req);
        st.setInt(1, reservations.getFilmID());
        st.setDate(2, reservations.getDateReservation());
        st.setTime(3, reservations.getHeureReservation());
        st.setInt(4, reservations.getNombrePlacesReservees());
        st.executeUpdate();
    }

    @Override
    public void ajouterResBack(Reservations reservations) throws SQLException {
        String req = "INSERT INTO reservations (FilmID,DateReservation,HeureReservation,NombrePlacesDisponibles) VALUES(?,?,?,?)";
        PreparedStatement st = connexion.prepareStatement(req);
        st.setInt(1, reservations.getFilmID());
        st.setDate(2, reservations.getDateReservation());
        st.setTime(3, reservations.getHeureReservation());
        st.setInt(4, reservations.getNombrePlacesDisponibles());
        st.executeUpdate();
    }

    @Override
    public void modifier(Reservations reservations) throws SQLException {
        String req = "UPDATE reservations " +
                "SET FilmID = ?, DateReservation = ?, HeureReservation = ?, NombrePlacesDisponibles = ? " +
                "WHERE ReservationID = ?";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1, reservations.getFilmID());
        ps.setDate(2, reservations.getDateReservation());
        ps.setTime(3, reservations.getHeureReservation());
        ps.setInt(4, reservations.getNombrePlacesDisponibles());
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
            reservation.setNombrePlacesDisponibles(rs.getInt("NombrePlacesDisponibles"));
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
                reservation.setNombrePlacesDisponibles(rs.getInt("NombrePlacesDisponibles"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    public List<Integer> getAllFilmIDs() throws SQLException {
        List<Integer> filmIDs = new ArrayList<>();
        String query = "SELECT FilmID FROM films";
        try (PreparedStatement statement = connexion.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int filmID = resultSet.getInt("FilmID");
                filmIDs.add(filmID);
            }
        }
        return filmIDs;
    }

    public List<String> getAllDatesForFilm(int filmID) throws SQLException {
        List<String> dates = new ArrayList<>();
        String query = "SELECT DISTINCT DateReservation FROM reservations WHERE FilmID = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, filmID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getString("DateReservation");
                dates.add(date);
            }
        }
        return dates;
    }

    public List<String> getAllHeuresForFilmAndDate(int filmID, String date) throws SQLException {
        List<String> heures = new ArrayList<>();
        String query = "SELECT DISTINCT HeureReservation FROM reservations WHERE FilmID = ? AND DateReservation = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, filmID);
            statement.setString(2, date);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String heure = resultSet.getString("HeureReservation");
                heures.add(heure);
            }
        }
        return heures;
    }

    public int getNombrePlacesDisponibles(int filmID, String date, String heure) throws SQLException {
        String query = "SELECT NombrePlacesDisponibles FROM reservations WHERE FilmID = ? AND DateReservation = ? AND HeureReservation = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, filmID);
            statement.setString(2, date);
            statement.setString(3, heure);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("NombrePlacesDisponibles");
            } else {
                return 0;
            }
        }
    }

    public void decrementerNombrePlaces(int filmID, String date, String heure, int nombrePlaces) throws SQLException {
        String query = "UPDATE reservations SET NombrePlacesDisponibles = NombrePlacesDisponibles - ? " +
                "WHERE FilmID = ? AND DateReservation = ? AND HeureReservation = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, nombrePlaces);
            statement.setInt(2, filmID);
            statement.setString(3, date);
            statement.setString(4, heure);
            statement.executeUpdate();
        }
    }

    public List<String> getAllFilmNames() throws SQLException {
        List<String> filmNames = new ArrayList<>();
        String query = "SELECT Titre FROM films";
        try (PreparedStatement statement = connexion.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String filmName = resultSet.getString("Titre");
                filmNames.add(filmName);
            }
        }
        return filmNames;
    }

    public int getFilmIDByName(String filmName) throws SQLException {
        int filmID = -1; // Valeur par défaut si le film n'est pas trouvé
        String query = "SELECT FilmID FROM films WHERE Titre = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, filmName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    filmID = resultSet.getInt("FilmID");
                }
            }
        }
        return filmID;
    }

    public void mettreAJourStatutSiege(String numeroSiege, String nouveauStatut) throws SQLException {
        String query = "UPDATE sieges SET Statut = ? WHERE NumeroSiege = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, nouveauStatut);
            statement.setString(2, numeroSiege);
            statement.executeUpdate();
        }
    }
}

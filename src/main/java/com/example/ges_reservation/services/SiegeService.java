package com.example.ges_reservation.services;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiegeService implements IsiegeServ<Sieges> {
    private Connection connexion;

    public SiegeService() {
        connexion = MyDatabase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Sieges sieges) throws SQLException {
        String req = "INSERT INTO sieges (ReservationID, NumeroSiege, Statut) " +
                "SELECT r.ReservationID, ?, ? " +
                "FROM reservations r " +
                "WHERE r.ReservationID = ?";

        try (PreparedStatement ps = connexion.prepareStatement(req)) {
            // Paramètres de la requête
            ps.setString(1, sieges.getNumeroSiege());
            ps.setString(2, sieges.getStatut());
            ps.setInt(3, sieges.getReservationID());

            // Exécution de la requête
            ps.executeUpdate();
        }
    }


    @Override
    public void modifier(Sieges sieges) throws SQLException {
        String req = "UPDATE sieges SET ReservationID=?, NumeroSiege=?, Statut=? WHERE ReservationID=?";
        try (PreparedStatement ps = connexion.prepareStatement(req)) {
            ps.setInt(1, sieges.getReservationID());
            ps.setString(2, sieges.getNumeroSiege());
            ps.setString(3, sieges.getStatut());
            ps.setInt(4, sieges.getReservationID());

            ps.executeUpdate();
        }
    }


    @Override
    public void supprimer(int SiegeID) throws SQLException {
        String req = "DELETE FROM sieges WHERE SiegeID = ?";
        PreparedStatement ps = connexion.prepareStatement(req);
        ps.setInt(1, SiegeID);
        ps.executeUpdate();
    }


    @Override
    public List<Sieges> recuperer() throws SQLException {
        List<Sieges> siegesList = new ArrayList<>();
        String req = "SELECT * FROM sieges";

        Statement st = connexion.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Sieges siege = new Sieges();
            siege.setSiegeID(rs.getInt("SiegeID"));
            siege.setReservationID(rs.getInt("ReservationID"));
            siege.setNumeroSiege(rs.getString("NumeroSiege"));
            siege.setStatut(rs.getString("Statut"));
            siegesList.add(siege);
        }
        return siegesList;

    }

    public Sieges afficherSibyID(int SiegeID) {
        Sieges siege = new Sieges();
        String request = "SELECT * FROM sieges WHERE SiegeID=" + SiegeID;
        try {
            Statement st = connexion.createStatement();
            ResultSet rs = st.executeQuery(request);
            if (rs.next()) {
                siege.setSiegeID(rs.getInt("SiegeID"));
                siege.setReservationID(rs.getInt("ReservationID"));
                siege.setNumeroSiege(rs.getString("NumeroSiege"));
                siege.setStatut(rs.getString("Statut"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return siege;
    }


}


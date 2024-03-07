package com.example.ges_reservation.test;

import com.example.ges_reservation.models.Reservations;
import com.example.ges_reservation.models.Sieges;
import com.example.ges_reservation.services.ReservationService;
import com.example.ges_reservation.services.SiegeService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {
        ReservationService rs = new ReservationService();
        SiegeService ps = new SiegeService();

// CRUD Reservation

        //ajout
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse("2039-12-15");
            Date Datevoy = new Date(utilDate.getTime());

            // Parse the time string to a java.sql.Time object
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            java.util.Date utilTime = timeFormat.parse("14:30");
            Time sqlTime = new Time(utilTime.getTime());

            // Create a new Reservations object with the parsed date and time values
            rs.ajouterResBack(new Reservations(1,Datevoy, sqlTime,0, 5));
            System.out.println("la reservation est ajouter avec succée : !");

        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
        //modifier
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse("2035-01-07");
            Date Datevoy = new Date(utilDate.getTime());

            // Parse the time string to a java.sql.Time object
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            java.util.Date utilTime = timeFormat.parse("07:30");
            Time sqlTime = new Time(utilTime.getTime());

            // Create a new Reservations object with the parsed date and time values
            rs.modifier(new Reservations(1,Datevoy, sqlTime,0 ,8));

        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }

//supprimer et afficher
        try {
     //     rs.supprimer(11);
            System.out.println(rs.recuperer());

        } catch ( SQLException e) {
            System.err.println(e.getMessage());
        }


        System.out.println("la reservation rechercher par id : !");

        System.out.println( rs.afficherResbyID(12));


        //CRUD Sieges
//ajouter
        try {
            ps.ajouter(new Sieges(25,11,"A1","Disponible"));
            System.out.println("Siège ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du siège : " + e.getMessage());
        }
//modifier
        try {
            ps.modifier(new Sieges(1,6,"A1","non disponible"));
            System.out.println("Siège modifié avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du siège : " + e.getMessage());
        }

        try {
             // ps.supprimer(3);
            System.out.println("Siège supprimer avec succès !");

            System.out.println(ps.recuperer());

        } catch ( SQLException e) {
            System.err.println(e.getMessage());
        }




    }



}

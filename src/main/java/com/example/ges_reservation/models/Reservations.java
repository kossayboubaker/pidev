package com.example.ges_reservation.models;

import java.sql.Date;
import java.sql.Time;

public class Reservations {
    private int reservationID;
    private int utilisateurID;
    private int filmID;
    private Date dateReservation;
    private Time heureReservation;
    private int nombrePlacesReservees;


  /*  public Reservations(int reservationID, int utilisateurID, int filmID, Date dateReservation, Time heureReservation, int nombrePlacesReservees) {
        this.reservationID = reservationID;
        this.utilisateurID = utilisateurID;
        this.filmID = filmID;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
        this.nombrePlacesReservees = nombrePlacesReservees;
    }
   */


    public Reservations(int utilisateurID, int filmID, Date dateReservation, Time heureReservation, int nombrePlacesReservees) {
        this.utilisateurID = utilisateurID;
        this.filmID = filmID;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
        this.nombrePlacesReservees = nombrePlacesReservees;
    }

    public Reservations() {

    }



    public int getReservationID() {
        return reservationID;
    }

    public int getUtilisateurID() {
        return utilisateurID;
    }

    public int getFilmID() {
        return filmID;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public Time getHeureReservation() {
        return heureReservation;
    }

    public int getNombrePlacesReservees() {
        return nombrePlacesReservees;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setUtilisateurID(int utilisateurID) {
        this.utilisateurID = utilisateurID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public void setHeureReservation(Time heureReservation) {
        this.heureReservation = heureReservation;
    }

    public void setNombrePlacesReservees(int nombrePlacesReservees) {
        this.nombrePlacesReservees = nombrePlacesReservees;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "reservationID=" + reservationID +
                ", utilisateurID=" + utilisateurID +
                ", filmID=" + filmID +
                ", dateReservation=" + dateReservation +
                ", heureReservation=" + heureReservation +
                ", nombrePlacesReservees=" + nombrePlacesReservees +
                '}';
    }



}


package com.example.ges_reservation.models;

public class Sieges {

    private int siegeID;
    private int reservationID;
    private String numeroSiege;
    private String statut;
public  Sieges(){}
    public Sieges(int siegeID, int reservationID, String numeroSiege, String statut) {
        this.siegeID = siegeID;
        this.reservationID = reservationID;
        this.numeroSiege = numeroSiege;
        this.statut = statut;
    }

    public int getSiegeID() {
        return siegeID;
    }

    public void setSiegeID(int siegeID) {
        this.siegeID = siegeID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getNumeroSiege() {
        return numeroSiege;
    }

    public void setNumeroSiege(String numeroSiege) {
        this.numeroSiege = numeroSiege;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Sieges{" +
                "siegeID=" + siegeID +
                ", reservationID=" + reservationID +
                ", numeroSiege='" + numeroSiege + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

}

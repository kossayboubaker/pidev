package com.example.ges_reservation.models;

import java.util.Date;

public class evenement {

    int id, id_cinema;
    String nom_ev,description;
    Date date;
    String periode;

    public evenement() {}
    public evenement(int id_cinema, String nom_ev, String description, Date date, String periode) {
        this.id_cinema = id_cinema;
        this.nom_ev = nom_ev;
        this.description = description;
        this.date = date;
        this.periode = periode;
       // this.image = image;
    }

    public evenement(int id, int id_cinema, String nom_ev, String description, Date date, String periode) {
        this.id = id;
        this.id_cinema = id_cinema;
        this.nom_ev = nom_ev;
        this.description = description;
        this.date = date;
        this.periode = periode;
       // this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cinema() {
        return id_cinema;
    }

    public void setId_cinema(int id_cinema) {
        this.id_cinema = id_cinema;
    }

    public String getNom_ev() {
        return nom_ev;
    }

    public void setNom_ev(String nom_ev) {
        this.nom_ev = nom_ev;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }


    @Override
    public String toString() {
        return "evenement{" +
                "id=" + id +
                ", id_cinema=" + id_cinema +
                ", nom_ev='" + nom_ev + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", periode='" + periode + '\'' +

                '}';
    }
}

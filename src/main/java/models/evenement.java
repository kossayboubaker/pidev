package models;

public class evenement {

    int id, id_cinema;
    String nom_ev,description,date,periode;

    public evenement() {}
    public evenement(int id_cinema, String nom_ev, String description, String date, String periode) {
        this.id_cinema = id_cinema;
        this.nom_ev = nom_ev;
        this.description = description;
        this.date = date;
        this.periode = periode;
    }

    public evenement(int id, int id_cinema, String nom_ev, String description, String date, String periode) {
        this.id = id;
        this.id_cinema = id_cinema;
        this.nom_ev = nom_ev;
        this.description = description;
        this.date = date;
        this.periode = periode;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

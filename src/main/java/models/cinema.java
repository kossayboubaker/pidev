package models;

public class cinema {
    private int id, nb_salle;
    private String place, etat, nom ;

    public cinema() {}
    public cinema(int nb_salle, String place, String etat, String nom) {
        this.nb_salle = nb_salle;
        this.place = place;
        this.etat = etat;
        this.nom = nom;
    }
    public cinema(int id, int nb_salle, String place, String etat, String nom) {
        this.id = id;
        this.nb_salle = nb_salle;
        this.place = place;
        this.etat = etat;
        this.nom = nom;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNb_salle() {
        return nb_salle;
    }

    public void setNb_salle(int nb_salle) {
        this.nb_salle = nb_salle;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "cinema{" +
                "id=" + id +
                ", nb_salle=" + nb_salle +
                ", place='" + place + '\'' +
                ", etat='" + etat + '\'' +
                ", nom='" + nom + '\'' +
                '}';
    }
}

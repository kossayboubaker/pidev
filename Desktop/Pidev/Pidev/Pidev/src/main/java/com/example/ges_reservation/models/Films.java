package com.example.ges_reservation.models;

public class Films {
    private int filmID;
    private String titre;
    private String realisateur;
    private int anneeSortie;
    private int duree;
    private String synopsis;
    private int categorieID;

    private String image;

    // Constructor

    public Films( String titre, String realisateur, int anneeSortie,  int duree, String synopsis, int categorieID,String image) {
        this.titre = titre;
        this.realisateur = realisateur;
        this.anneeSortie = anneeSortie;
        this.duree = duree;
        this.synopsis = synopsis;
        this.categorieID = categorieID;
        this.image = image ;
    }

    public Films() {

    }

    // Getters and setters

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }


    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getCategorieID() {
        return categorieID;
    }

    public void setCategorieID(int categorieID) {
        this.categorieID = categorieID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Films{" +
                "filmID=" + filmID +
                ", titre='" + titre + '\'' +
                ", realisateur='" + realisateur + '\'' +
                ", anneeSortie=" + anneeSortie +
                ", duree=" + duree +
                ", synopsis='" + synopsis + '\'' +
                ", categorieID=" + categorieID +
                ", image='" + image + '\'' +
                '}';
    }
}

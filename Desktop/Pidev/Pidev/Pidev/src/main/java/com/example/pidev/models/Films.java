package com.example.pidev.models;

public class Films {
    private int filmID;
    private String titre;
    private String realisateur;
    private int anneeSortie;
    private String genre;
    private int duree;
    private String synopsis;
    private int categorieID;

    // Constructor

    public Films(int filmID, String titre, String realisateur, int anneeSortie, String genre, int duree, String synopsis, int categorieID) {
        this.filmID = filmID;
        this.titre = titre;
        this.realisateur = realisateur;
        this.anneeSortie = anneeSortie;
        this.genre = genre;
        this.duree = duree;
        this.synopsis = synopsis;
        this.categorieID = categorieID;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    @Override
    public String toString() {
        return "Films{" +
                "filmID=" + filmID +
                ", titre='" + titre + '\'' +
                ", realisateur='" + realisateur + '\'' +
                ", anneeSortie=" + anneeSortie +
                ", genre='" + genre + '\'' +
                ", duree=" + duree +
                ", synopsis='" + synopsis + '\'' +
                ", categorieID=" + categorieID +
                '}';
    }
}

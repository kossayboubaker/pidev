package com.example.ges_reservation.models;

public class Categories {

    private int categorieID;
    private String nomCategorie;
    private String descriptionCategorie;

    // Constructor
    public Categories(int categorieID, String nomCategorie, String descriptionCategorie) {
          this.categorieID = categorieID;
        this.nomCategorie = nomCategorie;
        this.descriptionCategorie = descriptionCategorie;
    }
    public Categories(String nomCategorie, String descriptionCategorie) {
     //   this.categorieID = categorieID;
        this.nomCategorie = nomCategorie;
        this.descriptionCategorie = descriptionCategorie;
    }

    public Categories() {

    }


    // Getters and setters
    public int getCategorieID() {
        return categorieID;
    }

    public void setCategorieID(int categorieID) {
        this.categorieID = categorieID;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public String getDescriptionCategorie() {
        return descriptionCategorie;
    }

    public void setDescriptionCategorie(String descriptionCategorie) {
        this.descriptionCategorie = descriptionCategorie;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "categorieID=" + categorieID +
                ", nomCategorie='" + nomCategorie + '\'' +
                ", descriptionCategorie='" + descriptionCategorie + '\'' +
                '}';
    }


}

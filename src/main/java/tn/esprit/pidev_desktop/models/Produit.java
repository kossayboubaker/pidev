package tn.esprit.pidev_desktop.models;

public class Produit {
    private int id;
    private String nom;
    private String description;
    private float prix;

    // constructeur par defaut
    public Produit() {

    }
    // constructeur parametrique

    public Produit(int id, String nom, String description, float prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // constructeur parametrique sauf id pour l

    public Produit(String nom, String description, float prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    // methode toString


    @Override
    public String toString() {
        return "Produits{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                '}';
    }


}

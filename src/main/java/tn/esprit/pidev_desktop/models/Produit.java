package tn.esprit.pidev_desktop.models;

public class Produit {
    private int id;
    private String nom;
    private String description;
    private float prix;

    private int stock;

    private String image;

    // constructeur par defaut
    public Produit() {

    }
    // constructeur parametrique

    public Produit(int id, String nom, String description, float prix, int stock, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
    }

    // constructeur parametrique sauf id l'insertion

    public Produit(String nom, String description, float prix, int stock, String image) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    // methode toString


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                '}';
    }
}

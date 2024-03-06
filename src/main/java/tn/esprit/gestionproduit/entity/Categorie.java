
package tn.esprit.gestionproduit.entity;

public class Categorie {
    
    private int id_categori ;
    private String description ;

    public int getId_categori() {
        return id_categori;
    }

    public void setId_categori(int id_categori) {
        this.id_categori = id_categori;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    // methode toString
    @Override
    public String toString() {
        return "Categorie{" +

                ", description='" + description + '\'' +
                '}';
    }
    
}

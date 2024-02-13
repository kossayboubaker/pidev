package tn.esprit.pidev_desktop.models;

public class Commande {
    private int id;
    private int quantite;
    private String date_comd;
    private int user_id;
    private int pro_id;
    private float montantTotal;

    // constructeur par defaut
    public Commande() {

    }

    // constructeur parametrique

    public Commande(int id, int quantite, String date_comd, int user_id, int pro_id, float montantTotal) {
        this.id = id;
        this.quantite = quantite;
        this.date_comd = date_comd;
        this.user_id = user_id;
        this.pro_id = pro_id;
        this.montantTotal = montantTotal;
    }


    // constructeur parametrique sauf id pour l'insertion

    public Commande(int quantite, String date_comd, int user_id, int pro_id, float montantTotal) {
        this.quantite = quantite;
        this.date_comd = date_comd;
        this.user_id = user_id;
        this.pro_id = pro_id;
        this.montantTotal = montantTotal;
    }


    // getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQauntite() {
        return quantite;
    }

    public void setQauntite(int qauntite) {
        this.quantite = qauntite;
    }

    public String getDate_comd() {
        this.date_comd = date_comd;
        return null;
    }

    public void setDate_comd(String date_comd) {
        this.date_comd = date_comd;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public float getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(float montantTotal) {
        this.montantTotal = montantTotal;
    }
// methode toString


    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", quantite=" + quantite +
                ", date_comd=" + date_comd +
                ", user_id=" + user_id +
                ", pro_id=" + pro_id +
                ", montantTotal=" + montantTotal +
                '}';
    }
}

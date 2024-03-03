package tn.esprit.pidev_desktop.models;

public class Panier {

    private int idp;
    private int user_id;


    public Panier() {
    }

    public Panier(int idp) {
        this.idp = idp;
        //this.user = user;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idp=" + idp +
                ", user_id=" + user_id +
                '}';
    }
}



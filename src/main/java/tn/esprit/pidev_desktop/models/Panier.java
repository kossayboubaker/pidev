package tn.esprit.pidev_desktop.models;

public class Panier {

    private int idp,stockp;
    private String nomp,descriptionp;
    private float prixp;
    private String imagep;
    private int prod_id;
    public Panier() {
    }

    public Panier(int idp, int stockp,int prod_id, String nomp, float prixp, String imagep,String descriptionp) {
        this.idp = idp;
        this.stockp = stockp;
        this.prod_id = prod_id;
        this.nomp = nomp;
        this.prixp = prixp;
        this.imagep = imagep;
        this.descriptionp = descriptionp;

    }

    public Panier(int stockp,int prod_id, String nomp, float prixp, String imagep,String descriptionp) {
        this.stockp = stockp;
        this.prod_id = prod_id;
        this.nomp = nomp;
        this.prixp = prixp;
        this.imagep = imagep;
        this.descriptionp = descriptionp;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public int getStockp() {
        return stockp;
    }

    public void setStockp(int stockp) {
        this.stockp = stockp;
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public String getDescriptionp() {
        return descriptionp;
    }

    public void setDescriptionp(String descriptionp) {
        this.descriptionp = descriptionp;
    }

    public float getPrixp() {
        return prixp;
    }

    public void setPrixp(float prixp) {
        this.prixp = prixp;
    }

    public String getImagep() {
        return imagep;
    }

    public void setImagep(String imagep) {
        this.imagep = imagep;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idp=" + idp +
                ", stockp=" + stockp +
                ", nomp='" + nomp + '\'' +
                ", descriptionp='" + descriptionp + '\'' +
                ", prixp=" + prixp +
                ", imagep='" + imagep + '\'' +
                ", prod_id=" + prod_id +
                '}';
    }
}



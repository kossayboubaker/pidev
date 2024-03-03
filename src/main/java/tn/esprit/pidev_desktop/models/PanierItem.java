package tn.esprit.pidev_desktop.models;

public class PanierItem {
    private int id_panierItem;
    private Panier panier;
    private Produit produit;
    private int quantite;

    public PanierItem () {};

    public PanierItem(int id_panierItem, Panier panier, Produit produit, int quantite) {
        this.id_panierItem = id_panierItem;
        this.panier = panier;
        this.produit = produit;
        this.quantite = quantite;
    }

    public int getId_panierItem() {
        return id_panierItem;
    }

    public void setId_cartItem(int id_panierItem) {
        this.id_panierItem = id_panierItem;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }



    @Override
    public String toString() {
        return "PanierItem{" +
                "id_cartItem=" + id_panierItem +
                ", panier=" + panier +
                ", produit=" + produit +
                ", quantite=" + quantite +
                '}';
    }
}

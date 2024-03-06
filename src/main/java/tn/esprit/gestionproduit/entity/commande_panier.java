/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gestionproduit.entity;


public class commande_panier {
    
    
    private int id_commande , id_produit ,id_pannierCommande ;

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_pannierCommande() {
        return id_pannierCommande;
    }

    public void setId_pannierCommande(int id_pannierCommande) {
        this.id_pannierCommande = id_pannierCommande;
    }

    public commande_panier() {
    }

    @Override
    public String toString() {
        return "commande_panier{" +
                "id_commande=" + id_commande +
                ", id_produit=" + id_produit +
                ", id_pannierCommande=" + id_pannierCommande +
                '}';
    }
}

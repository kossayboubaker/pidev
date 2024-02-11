package tn.esprit.pidev_desktop.test;

import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        // instance de base de données
        MyDatabase db =  MyDatabase.getInstance();
        System.out.println(db);

/*
       ProduitService ps = new ProduitService();
        try {
            ps.ajouter(new Produit("cafe", "express", 2.0f));
            System.out.println("produit ajouté avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
/*
        ProduitService ps = new ProduitService();
        try {
            System.out.println("le liste de produits");
            System.out.println(ps.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

*/

        /*
        ProduitService ps = new ProduitService();
        try {
            ps.modifier(new Produit( 4,"chicken wings", "chicken wings", 10.0f));
            System.out.println("produit modifie avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        */
        /*
        ProduitService ps = new ProduitService();
        try {
            ps.supprimer(4);
            System.out.println("produit supprime avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
    }


}

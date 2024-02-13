package tn.esprit.pidev_desktop.test;

import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.models.Produit;
import tn.esprit.pidev_desktop.services.CommandeService;
import tn.esprit.pidev_desktop.services.ProduitService;
import tn.esprit.pidev_desktop.utils.MyDatabase;

import java.sql.SQLException;
import java.sql.Date;

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
        CommandeService cs = new CommandeService();
        try {
            cs.ajouter(new Commande( 1,"2023-01-01" , 4, 3,2.5f));
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
        CommandeService Cs = new CommandeService();
        try {
            System.out.println("le liste de Commande");
            System.out.println(Cs.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

*/


        ProduitService Cs = new ProduitService();
        try {
            System.out.println("le liste de Commande");
            System.out.println(Cs.recupererById(5));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }



/*
        CommandeService cs = new CommandeService();
        try {
            cs.modifier(new Commande( 5,"2023-01-01" , 4, 5,5.5f));
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
        /*
         CommandeService cs = new CommandeService();
        try {
            cs.supprimer(4);
            System.out.println("produit supprime avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }*/

    }


}

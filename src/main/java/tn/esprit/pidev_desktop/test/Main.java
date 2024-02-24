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
            ps.ajouter(new Produit("express", "express jgkjkjgkjkgkgk", 6.0f, 5, "express.jpg"));
            System.out.println("produit ajouté avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
/*
        CommandeService cs = new CommandeService();
        try {
            cs.ajouter(new Commande(9, "", 4, 3, 5.0f, "kossay", "boubaker"));
            System.out.println("commande ajouté avec succès");
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
/*
        CommandeService Cs = new CommandeService();
        try {
            System.out.println("le recherche de Commande");
            System.out.println(Cs.joiner());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


/*
*/
        /*
        ProduitService Cs = new ProduitService();
        try {
            System.out.println("le liste de Commande par id");
            System.out.println(Cs.recupererById(3));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/


/*
        ProduitService Cs = new ProduitService();
        try {
            System.out.println("le liste de produits par prix");
            System.out.println(Cs.recupererByPrix(7));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

*/
/*

        ProduitService Cs = new ProduitService();
        try {
            System.out.println("le liste de Commande par nom");
            System.out.println(Cs.recupererByNom("chips"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

*/

/*
        CommandeService cs = new CommandeService();
        try {
            cs.modifier(new Commande(38,12, "2024-05-12", 4, 6, 8.0f, "ali", "bougbaker"));
            System.out.println("produit modifie avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
/*
        ProduitService ps = new ProduitService();
        try {
            ps.supprimer(6);
            System.out.println("produit supprime avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
        /*

         CommandeService cs = new CommandeService();
        try {
            cs.supprimer(31);
            System.out.println("commande supprime avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
/*
        ProduitService cs = new ProduitService();
        try {
            cs.modifier(new Produit(5, "chips", "chhhhh", 2.0f, 5, "chips.jpg"));
            System.out.println("produit modifie avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/


    }


}

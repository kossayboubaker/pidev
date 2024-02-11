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


        ProduitService ps = new ProduitService();
        try {
            ps.ajouter(new Produit("chicken", "chicken poule", 20.0f));
            System.out.println("produit ajouté avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }


}

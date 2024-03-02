package com.example.pidev.test;
import com.example.pidev.services.CategoriesService;
import com.example.pidev.services.FilmsService;
import java.sql.SQLException;


public class main {

    public static void main(String[] args) {
        FilmsService fs = new FilmsService();
        CategoriesService cs = new CategoriesService();

        try {
            //fs.ajouter(new Films(1, "ABAB", "B",2,"C",4,"D", 1 ));
            //fs.modifier(new Films(1, "ABAB", "B",2,"C",4,"D", 1 ));
            //fs.supprimer(1);
            System.out.println(fs.recuperer());
            System.out.println(cs.recuperer());
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}


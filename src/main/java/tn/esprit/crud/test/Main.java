package tn.esprit.crud.test;

import tn.esprit.crud.models.CodePromo;
import tn.esprit.crud.models.User;
import tn.esprit.crud.services.PromoService;
import tn.esprit.crud.services.UserService;
import tn.esprit.crud.utils.MyDatabase;
import tn.esprit.crud.models.User;
import tn.esprit.crud.models.CodePromo;
import tn.esprit.crud.services.UserService;


import java.sql.SQLOutput;
import java.text.ParseException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException, RuntimeException {
        UserService us = new UserService();


        //AJOUTER USER

 /* try{
            us.ajouter(new User(1,"Maram", "Test", "Ariana", "maramtest@gmail.com", "12345"));
            System.out.println("ID ajouter avec succes");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/




     //AFFICHAGE A7LA :)  choix2
/*
        System.out.println("Voici la liste des utilisateurs :");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-20s | %-25s | %-20s |\n", "ID", "Nom", "Prenom", "Email", "Password");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        List<User> usersList = us.recupperer();
        for (User user : usersList) {
            System.out.printf("| %-4d | %-20s | %-20s | %-25s | %-20s |\n", user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getMdp());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");


*/


        ///MODIFIER USER

  /*  try {
            us.modifier(new User(51,"Halaand",  "Cancelo" ,"Benzarte",  "jackjack@gmail.com" ,  "999"));
            System.out.println("ID modifier avec succes");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/



        ////SUPRIMER USER

   /*   try {
           us.supprimer(52);
            System.out.println("ID supprimer avec succes");
           System.out.println("**************************");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }



*/
        PromoService ps = new PromoService();


        //AJOUTER CODE PROMO
/*
       try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse("2028-12-07");
            Date Datevoy = new Date(utilDate.getTime());

            // Créer un nouvel objet CodePromo
            CodePromo nouveauCode = new CodePromo( 565656, Datevoy, 0,52);

            // Ajouter le code promo
            ps.ajouter(nouveauCode);

            // Récupérer les détails du code promo
            int userId = nouveauCode.getUser_id();
            int code = nouveauCode.getCode();

            // Afficher les détails du code promo
            System.out.println("Un code promo est ajouté pour le user_id : " + userId);
            System.out.println("Le code promo est : " + code);
            System.out.println("Date d'expiration du code est : " + Datevoy);

        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }

*/

        //AFFICHER LA LISTE DES CODE PROMO

/*
        try {
            System.out.println("Voici la liste des codes promo :");
            System.out.println("---------------------------------------------------------------------------");

            System.out.printf("| %-4s | %-10s | %-20s | %-10s | %-15s |\n", "ID", "Code", "Date d'expiration", "Utilisé", "ID utilisateur");
            System.out.println("---------------------------------------------------------------------------");
            List<CodePromo> codesPromoList = ps.recupperer();
            for (CodePromo codePromo : codesPromoList) {
                System.out.printf("| %-4d | %-10d | %-20s | %-10d | %-15d |\n", codePromo.getId(), codePromo.getCode(), codePromo.getDate_exp(), codePromo.getUtilise(), codePromo.getUser_id());
            }
            System.out.println("---------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


*/

        //MODIFIER CODE PROMO
/*
        try {
            CodePromo codePromo = new CodePromo( 123233, Date.valueOf("2032-09-01"), 1, 46);
            ps.modifier(codePromo);
            System.out.println("Modification du code promo effectuée avec succès:");
            System.out.println("--------------------------------------------------");
            System.out.println("Nouveau code : " + codePromo.getCode());
            System.out.println("Nouvelle date d'expiration : " + codePromo.getDate_exp());
            System.out.println("Cas d'utilisation : " + codePromo.getUtilise());
            System.out.println("ID de l'utilisateur associé : " + codePromo.getUser_id());
            System.out.println("--------------------------------------------------");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du code promo : " + e.getMessage());
        }
*/






        //SUPRIMER CODEPROMO
/*
     try {
          int idCodePromoSupprime = 37; // ID du code promo a supprimé (id du code promo et pas le user_id)

            // Supprimer le code promo avec l'ID spécifié
            ps.supprimer(idCodePromoSupprime);

            // Afficher le message avec l'ID du code promo supprimé
            System.out.println("CodePromo avec l'ID = " + idCodePromoSupprime + " supprimé avec succès");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

*/


    }
}








package tests;
import models.cinema;
import models.evenement;
import services.cinemaservice ;
import services.evenementservice;

import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
         cinemaservice cs = new cinemaservice();
        evenementservice ps = new evenementservice();

     //cs.ajouter(new cinema(5,"tunis","complet","pathe"));
       // cs.modifer(new cinema(1,1144,"fs","fds","eskf"));
      // cs.supprimer(4);
        //System.out.println(cs.recuperer());

      //ps.ajouter(new evenement(4,"fire","good","12/12/2024","4H"));




        // ps.modifer(new evenement(2,2,"44","fs","fds33","5HEURES"));



      //  ps.supprimer(2);
      // System.out.println(ps.recuperer());
       // System.out.println(ps.joiner());
    }
}

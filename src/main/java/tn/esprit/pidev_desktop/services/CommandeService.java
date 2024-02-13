package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.utils.MyDatabase;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements ComdService<Commande> {

    // variable de type Connection
    private Connection connection;


// initialisation de la variable et elabore a la basse de donne

    public CommandeService() {connection = MyDatabase.getInstance().getConnection();}

    @Override
    public void ajouter(Commande commande) throws SQLException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());

    String req = "INSERT INTO commande (quantite, date_comd, user_id, pro_id,montantTotal) VALUES ("+commande.getQauntite()+","+sqlDate.toString()+","+commande.getUser_id()+","+commande.getPro_id()+","+commande.getMontantTotal()+")";
        //objet de type statement pour execution de la requete
        System.out.println(timestamp);
        Statement st = connection.createStatement();
        st.executeUpdate(req);


    }

    @Override
    public void modifier(Commande commande) throws SQLException {
        String req = "UPDATE commande SET quantite = ?, date_comd = ?, user_id = ?, pro_id = ?, montantTotal = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, commande.getQauntite());
        ps.setString(2, commande.getDate_comd());
        ps.setInt(3, commande.getUser_id());
        ps.setInt(4, commande.getPro_id());
        ps.setFloat(5, commande.getMontantTotal());
        ps.setInt(6, commande.getId());
        ps.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
    String req = "DELETE FROM commande WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();


    }

    @Override
    public List<Commande> recuperer() throws SQLException {
    List<Commande> commandes = new ArrayList<>();
    String req = "SELECT * FROM commande";
    Statement st = connection.createStatement();
    ResultSet rs = st.executeQuery(req);
    while (rs.next()) {
        Commande commande = new Commande();
        commande.setId(rs.getInt("id"));
        commande.setQauntite(rs.getInt("quantite"));
        commande.setDate_comd(rs.getString("date_comd"));
        commande.setUser_id(rs.getInt("user_id"));
        commande.setPro_id(rs.getInt("pro_id"));
        commande.setMontantTotal(rs.getFloat("montantTotal"));

        commandes.add(commande);
    }
        return commandes;
}


    }


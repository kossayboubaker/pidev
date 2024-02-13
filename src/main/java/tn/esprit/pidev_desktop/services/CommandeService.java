package tn.esprit.pidev_desktop.services;

import tn.esprit.pidev_desktop.models.Commande;
import tn.esprit.pidev_desktop.utils.MyDatabase;

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


    String req = "INSERT INTO commande (quantite, user_id, pro_id,montantTotal,nom_user,prenom_user) VALUES ("+commande.getQauntite()+","+commande.getUser_id()+","+commande.getPro_id()+","+commande.getMontantTotal()+",'"+commande.getNom_user()+"','"+commande.getPrenom_user()+"')";
        //objet de type statement pour execution de la requete
        req = "INSERT INTO commande (user_id,pro_id,quantite,montantTotal,nom_user,prenom_user) VALUES ("+"(SELECT id from user WHERE id="+commande.getUser_id()+")"+","+"(SELECT id from produit WHERE id="+commande.getPro_id()+")"+","+commande.getQauntite()+","+commande.getMontantTotal()+",'"+commande.getNom_user()+"','"+commande.getPrenom_user()+"')";

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
        commande.setNom_user(rs.getString("nom_user"));
        commande.setPrenom_user(rs.getString("prenom_user"));

        commandes.add(commande);
    }
        return commandes;
}

    @Override
    public List<Commande> joiner() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String req = "SELECT u.nom, u.prenom, c.id AS id, c.date_comd, p.nom AS product_name, p.prix AS product_price, c.quantite AS quantite, c.montantTotal AS montantTotal FROM commande c INNER JOIN user u ON c.user_id = u.id INNER JOIN produit p ON c.nom_user = u.nom AND c.prenom_user = u.prenom;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Commande commande = new Commande();

            commande.setNom_user(rs.getString("nom"));
            commande.setPrenom_user(rs.getString("prenom"));


            commande.setDate_comd(rs.getString("date_comd"));

            commande.setQauntite(rs.getInt("quantite"));
            commande.setMontantTotal(rs.getFloat("montantTotal"));






            commandes.add(commande);
        }
        return commandes;
    }



}


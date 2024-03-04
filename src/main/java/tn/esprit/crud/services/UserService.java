package tn.esprit.crud.services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.crud.models.User;
import tn.esprit.crud.utils.MyDatabase;
import javax.mail.*;
import javax.mail.internet.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class UserService implements IServices<User> {

    private Connection connection;


    public UserService() throws SQLException {
        connection = MyDatabase.getInstance().getConnection();
    }





    public void supprimer(String email) throws SQLException {
        String req = "DELETE FROM user WHERE email = ?";
        try (PreparedStatement us = connection.prepareStatement(req)) {
            us.setString(1, email);
            us.executeUpdate();
        }
    }


    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO user(nom , prenom , adresse , email , mdp) VALUES( '" + user.getNom() + "' , '" + user.getPrenom() + "' , '" + user.getAdresse() + "' , '" + user.getEmail() + "' , '" + user.getMdp() + "')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
    }

        public void modifier(User user) throws SQLException {
            String req = "UPDATE user SET nom = ?, prenom = ?, adresse = ?, mdp = ? WHERE id = ?";
            try (PreparedStatement us = connection.prepareStatement(req)) {
                us.setString(1, user.getNom());
                us.setString(2, user.getPrenom());
                us.setString(3, user.getAdresse());
                us.setString(4, user.getMdp());
                us.setInt(5, user.getId());
                us.executeUpdate();
            }
        }


    @Override
    public void supprimer(User user) {

    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req = "DELETE FROM user WHERE id = ? ";
        PreparedStatement us = connection.prepareStatement(req);
        us.setInt(1, id);
        us.executeUpdate();
    }


    @Override
    public List<User> recupperer() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user";
        Statement us = connection.createStatement();
        ResultSet rs = us.executeQuery(req);


        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setAdresse(rs.getString("adresse"));
            user.setEmail(rs.getString("email"));
            user.setMdp(rs.getString("mdp"));


            users.add(user);

        }
        return users;


    }



    public User getByEmail(String email) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setNom(resultSet.getString("nom"));
            user.setPrenom(resultSet.getString("prenom"));
            user.setAdresse(resultSet.getString("adresse"));
            user.setEmail(resultSet.getString("email"));
            user.setMdp(resultSet.getString("mdp"));
        }

        return user;
    }


    // Autres méthodes de la classe UserService...

    // Méthode pour mettre à jour les hachages de mot de passe
    public void updatePasswordHashes() throws SQLException {
        String query = "SELECT id, mdp FROM user";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String oldHash = resultSet.getString("mdp");
                // Vérifier si le hachage utilise une version de BCrypt incompatible
                if (!oldHash.startsWith("$2y$")) {
                    // Regénérer le hachage avec la version actuelle de BCrypt
                    String newHash = BCrypt.hashpw(oldHash, BCrypt.gensalt());
                    // Mettre à jour le mot de passe haché dans la base de données
                    String updateQuery = "UPDATE user SET mdp = ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, newHash);
                        updateStatement.setInt(2, userId);
                        updateStatement.executeUpdate();
                    }
                }
            }
        }
    }

    public boolean emailExiste(String email) throws SQLException {
        // Requête SQL pour vérifier si l'email existe déjà dans la base de données
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Utilisation de la connexion existante
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            // Vérifier si des résultats sont retournés
            if (resultSet.next()) {
                // Si des résultats sont retournés, l'email existe déjà
                return true;
            }
        } finally {
            // Fermeture des ressources
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        // Aucun résultat trouvé, donc l'email n'existe pas encore
        return false;
    }





    public User authentifier(String email, String mdp) throws SQLException {
        // Appeler le service UserService pour récupérer l'utilisateur en fonction de l'email
        UserService userService = new UserService();
        User user = userService.getByEmail(email);

        // Vérifier si un utilisateur correspondant à l'email existe dans la base de données
        if (user != null) {
            // Si l'utilisateur existe, vérifier si le mot de passe correspond
            if (BCrypt.checkpw(mdp, user.getMdp())) {
                // Le mot de passe est correct, retourner l'utilisateur
                return user;
            } else {
                // Le mot de passe est incorrect, retourner null
                return null;
            }
        } else {
            // Aucun utilisateur trouvé avec cet email, retourner null
            return null;
        }
    }


    public void envoyerEmailRecuperation(String email, String codeRecuperation) {
        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP Gmail
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); // Port SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du chiffrement TLS

        // Création d'une session avec l'authentification
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("manideliro@gmail.com", "qjkw tpnc sryq dzku");
                    }
                });

        try {
            // Création d'un message MIME
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manideliro@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Adresse e-mail de destination
            message.setSubject("Récupération de mot de passe"); // Sujet de l'e-mail
            message.setText("Votre code de récupération de mot de passe est : " + codeRecuperation); // Corps de l'e-mail avec le code de récupération

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail de récupération envoyé avec succès à " + email);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getCodeRecuperation() {
    return codeRecuperation;
    }
    private static String codeRecuperation; // Variable pour stocker le code de récupération

    public String generateRandomCode() {
        // Générer un code aléatoire de 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return codeRecuperation = String.valueOf(code);
    }
    public void envoyerEmailRecuperation2(String email, String codeRecuperation) {
        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP Gmail
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); // Port SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du chiffrement TLS

        // Création d'une session avec l'authentification
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("manideliro@gmail.com", "qjkw tpnc sryq dzku");
                    }
                });

        try {
            // Création d'un message MIME
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manideliro@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Adresse e-mail de destination
            message.setSubject("Félicitations ! Vous avez gagné un code promo"); // Sujet de l'e-mail
            message.setText("Félicitations ! Vous êtes le gagnant d'un super code promo ! Utilisez le code suivant lors de votre prochaine commande pour bénéficier d'une réduction exclusive :  " + codeRecuperation); // Corps de l'e-mail avec le code de récupération

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail de récupération envoyé avec succès à " + email);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public void modifierMotDePasse(String email, String nouveauMdp) throws SQLException {
        // Construire la requête SQL pour mettre à jour le mot de passe de l'utilisateur avec l'e-mail donné
        String query = "UPDATE user SET mdp = ? WHERE email = ?";
        // Préparer la requête SQL
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Remplacer les paramètres de la requête par les valeurs correspondantes
            statement.setString(1, nouveauMdp);
            statement.setString(2, email);
            // Exécuter la requête SQL
            statement.executeUpdate();
        }
    }
    public void supprimerParEmail(String email) throws SQLException {
        // Préparez la requête SQL pour supprimer l'utilisateur avec l'e-mail spécifié
        String query = "DELETE FROM user WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Remplacez le paramètre de la requête par l'e-mail spécifié
            preparedStatement.setString(1, email);
            // Exécutez la requête de suppression
            preparedStatement.executeUpdate();
        }
    }



    //fonction hedhi bch nzidou l affichage mta3 l ID
    public List<User> recuppererSansChampsSensibles() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT nom, prenom, adresse, email FROM user"; // Sélectionnez uniquement les colonnes nécessaires
        Statement us = connection.createStatement();
        ResultSet rs = us.executeQuery(req);

        while (rs.next()) {
            User user = new User();
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setAdresse(rs.getString("adresse"));
            user.setEmail(rs.getString("email"));
            users.add(user);
        }
        return users;
    }


    //fonction hedhi bch nzidou l affichage mta3 l ID bch tban fl ajout code promo
    public List<User> recuppererSansChampsSensibles2() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT id, nom, prenom, adresse, email FROM user"; // Ajout de l'ID à la requête SQL
        Statement us = connection.createStatement();
        ResultSet rs = us.executeQuery(req);

        while (rs.next()) {
            User user = new User();
            // Affecter les valeurs de chaque colonne de la base de données à l'objet User
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setAdresse(rs.getString("adresse"));
            user.setEmail(rs.getString("email"));
            users.add(user);
        }
        return users;
    }

    public void modifierParEmail(User user) throws SQLException {
        String req = "UPDATE user SET nom = ?, prenom = ?, adresse = ?, mdp = ? WHERE email = ?";
        PreparedStatement us = connection.prepareStatement(req);
        us.setString(1, user.getNom());
        us.setString(2, user.getPrenom());
        us.setString(3, user.getAdresse());
        us.setString(4, user.getMdp());
        us.setString(5, user.getEmail());
        us.executeUpdate();
    }




        // Méthode pour récupérer les utilisateurs avec leurs noms, prénoms et adresses
        public ObservableList<String> recuppererNomsPrenomsAdresses() throws SQLException {
            ObservableList<String> utilisateurs = FXCollections.observableArrayList();
            String req = "SELECT nom, prenom, adresse FROM user";
            Statement us = connection.createStatement();
            ResultSet rs = us.executeQuery(req);

            while (rs.next()) {
                String utilisateur = rs.getString("nom") + " " + rs.getString("prenom") + " - " + rs.getString("adresse");
                utilisateurs.add(utilisateur);
            }
            return utilisateurs;
        }

    public User getById(int userId) throws SQLException {
        // Préparer la requête SQL pour récupérer l'utilisateur par son ID
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);

        // Exécuter la requête SQL et récupérer le résultat
        ResultSet resultSet = preparedStatement.executeQuery();

        // Vérifier si un utilisateur avec cet ID existe
        if (resultSet.next()) {
            // Créer un objet User à partir des données de la base de données
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setNom(resultSet.getString("nom"));
            user.setPrenom(resultSet.getString("prenom"));
            user.setAdresse(resultSet.getString("adresse"));
            user.setEmail(resultSet.getString("email"));
            user.setMdp(resultSet.getString("mdp"));
            // Ajoutez d'autres champs selon votre structure de données

            return user;
        } else {
            // Si aucun utilisateur n'est trouvé avec cet ID, renvoyer null
            return null;
        }
    }
    public void sendEmail(String recipientEmail, String subject, String body) {
        // Configuration des propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Serveur SMTP Gmail
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587"); // Port SMTP
        props.put("mail.smtp.starttls.enable", "true"); // Activation du chiffrement TLS

        // Création d'une session avec l'authentification
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("manideliro@gmail.com", "qjkw tpnc sryq dzku");
                        // Remplacez "votre_adresse_email@gmail.com"
                        // et "votre_mot_de_passe" par vos informations de connexion Gmail
                    }
                });

        try {
            // Création d'un message MIME
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manideliro@gmail.com")); // Remplacez par votre adresse e-mail
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Adresse e-mail du destinataire
            message.setSubject(subject); // Sujet de l'e-mail
            message.setText(body); // Corps de l'e-mail

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès à " + recipientEmail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] getNomPrenomFromEmail(String email) {
        return new String[]{"", ""};
    }
    }






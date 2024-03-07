    package com.example.ges_reservation.services;
    import java.sql.Connection;
    import javax.mail.internet.*;

    import com.example.ges_reservation.models.Categories;
    import com.example.ges_reservation.models.Films;
    import com.example.ges_reservation.utils.MyDatabase;
    import javax.mail.Session;
    import javax.mail.*;
    import javax.mail.MessagingException;
    import javax.mail.internet.InternetAddress;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.sql.ResultSet;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Properties;
    import javax.mail.PasswordAuthentication;
    import javax.mail.Authenticator;




    public class FilmsService implements IServiceFilm<Films> {

        private Connection connection;

        public FilmsService() {
            connection = MyDatabase.getInstance().getConnection();
        }


        public void ajouter(Films films) throws SQLException {

            String req = "INSERT INTO Films ( titre, realisateur, anneeSortie, duree, synopsis, categorieID,image) VALUES('" +
                    films.getTitre() + "', '" +
                    films.getRealisateur() + "', '" +
                    films.getAnneeSortie() + "', '" +
                    films.getDuree() + "', '" +
                    films.getSynopsis() + "',' " +
                    films.getCategorieID() + " ',' " +
                    films.getImage() + " ') ";
            Statement st = connection.createStatement();
            st.executeUpdate(req);
        }

        public void modifier(Films films) throws SQLException {
            String req = "UPDATE films SET filmID = ?, titre = ?, realisateur = ?, anneeSortie = ?, duree = ?, synopsis = ?, categorieID = ? WHERE  filmID = ?";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, films.getFilmID());
            ps.setString(2, films.getTitre());
            ps.setString(3, films.getRealisateur());
            ps.setInt(4, films.getAnneeSortie());
            ps.setInt(5, films.getDuree());
            ps.setString(6, films.getSynopsis());
            ps.setInt(7, films.getCategorieID());
            ps.setInt(8, films.getFilmID());

            ps.executeUpdate();

        }

        public void supprimer(int filmID) throws SQLException {
            String req1 = "DELETE s FROM sieges s INNER JOIN reservations r ON s.ReservationID = r.ReservationID WHERE r.FilmID = ?";
            PreparedStatement ps1 = connection.prepareStatement(req1);
            ps1.setInt(1, filmID);
            ps1.executeUpdate();

            String req2 = "DELETE FROM films WHERE filmID = ?";
            PreparedStatement ps2 = connection.prepareStatement(req2);
            ps2.setInt(1, filmID);
            ps2.executeUpdate();
        }

        @Override
        public List<Films> recuperer() throws SQLException {
            List<Films> films = new ArrayList<>();

            String req = "SELECT f.*, c.NomCategorie " +
                    "FROM films f " +
                    "JOIN categories c ON f.CategorieID = c.CategorieID";

            try (Statement st = connection.createStatement();
                 ResultSet rs = st.executeQuery(req)) {

                while (rs.next()) {
                    Films film = new Films();

                    film.setFilmID(rs.getInt("FilmID"));
                    film.setTitre(rs.getString("Titre"));
                    film.setRealisateur(rs.getString("Realisateur"));
                    film.setAnneeSortie(rs.getInt("AnneeSortie"));
                    film.setDuree(rs.getInt("Duree"));
                    film.setSynopsis(rs.getString("Synopsis"));
                    film.setCategorieID(rs.getInt("CategorieID"));
                    film.setImage(rs.getString("image"));


                    films.add(film);
                }
            }
            return films;
        }


        public Films recuperere(int categorieID) throws SQLException {
            Films p = new Films();
            String sql = "select * from films where CategorieID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categorieID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {


                p.setFilmID(rs.getInt("FilmID"));
                p.setTitre(rs.getString("titre"));
                p.setRealisateur(rs.getString("realisateur"));
                p.setAnneeSortie(rs.getInt("anneeSortie"));
                p.setDuree(rs.getInt("duree"));
                p.setSynopsis(rs.getString("synopsis"));
                p.setCategorieID(rs.getInt("categorieID"));
                p.setImage(rs.getString("image"));


            }
            preparedStatement.close();
            return p;
        }


     /*   public List<Films> joiner(int CategorieID) throws SQLException {
            List<Films> list = new ArrayList<>();
            String req = "SELECT * FROM films f INNER JOIN categories c ON f.CategorieID = c.CategorieID where CategorieID=?;";
            PreparedStatement st = connection.prepareStatement(req);
            st.setInt(1, CategorieID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Films p = new Films();

                p.setFilmID(rs.getInt("FilmID"));

                p.setTitre(rs.getString("Titre"));
                p.setRealisateur(rs.getString("Realisateur"));
                p.setAnneeSortie(rs.getInt("AnneeSortie"));

                p.setDuree(rs.getInt("Duree"));
                p.setSynopsis(rs.getString("Synopsis"));
                p.setCategorieID(rs.getInt("CategorieID"));

                p.setImage(rs.getString("image"));



                list.add(p);
            }
            return list;
        }  */


        public void sendEmail(String recipientEmail, String subject, String body) {
            // Configuration des propriétés SMTP
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");

            // Création d'une session avec l'authentification
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("bargouguiamir2000@gmail.com", "qwfm twzp cred jvyg");
                }
            });

            try {
                // Création d'un message MIME
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("bargouguiamir2000@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(body);

                // Envoi du message
                Transport.send(message);

                System.out.println("Email envoyé avec succès à " + recipientEmail);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }



        public List<Films> recupererByCategorie(int categorieID) throws SQLException {
            List<Films> films = new ArrayList<>();
            String req = "SELECT * FROM Films WHERE CategorieID = ?";
            try (PreparedStatement st = connection.prepareStatement(req)) {
                st.setInt(1, categorieID);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        Films film = new Films();
                        film.setFilmID(rs.getInt("FilmID"));
                        film.setTitre(rs.getString("Titre"));
                        film.setRealisateur(rs.getString("Realisateur"));
                        film.setAnneeSortie(rs.getInt("AnneeSortie"));
                        film.setDuree(rs.getInt("Duree"));
                        film.setSynopsis(rs.getString("Synopsis"));
                        film.setCategorieID(rs.getInt("CategorieID"));
                        film.setImage(rs.getString("image"));
                        films.add(film);
                    }
                }
            }
            return films;
        }
    }





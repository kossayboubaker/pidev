    package com.example.pidev.services;
    import java.sql.Connection;
    import com.example.pidev.models.Films;
    import com.example.pidev.utils.MyDatabase;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.sql.ResultSet;
    import java.util.ArrayList;
    import java.util.List;
    public class FilmsService implements IService<Films> {

        private Connection connection;

        public FilmsService() {
            connection = MyDatabase.getInstance().getConnection();
        }


        public void ajouter(Films films) throws SQLException {

            String req = "INSERT INTO Films (filmID, titre, realisateur, anneeSortie, genre, duree, synopsis, categorieID) VALUES(" +
                    films.getFilmID() + ", '" +
                    films.getTitre() + "', '" +
                    films.getRealisateur() + "', " +
                    films.getAnneeSortie() + ", '" +
                    films.getGenre() + "', " +
                    films.getDuree() + ", '" +
                    films.getSynopsis() + "', " +
                    films.getCategorieID() + ")";
            Statement st = connection.createStatement();
            st.executeUpdate(req);
        }

        public void modifier(Films films) throws SQLException {
            String req = "UPDATE films SET filmID = ?, titre = ?, realisateur = ?, anneeSortie = ?, genre = ?, duree = ?, synopsis = ?, categorieID = ? ";
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, films.getFilmID());
            ps.setString(2, films.getTitre());
            ps.setString(3, films.getRealisateur());
            ps.setInt(4, films.getAnneeSortie());
            ps.setString(5, films.getGenre());
            ps.setInt(6, films.getDuree());
            ps.setString(7, films.getSynopsis());
            ps.setInt(8, films.getCategorieID());
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
            String req = "SELECT * FROM films";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                Films film = new Films(
                        rs.getInt("filmID"),
                        rs.getString("titre"),
                        rs.getString("realisateur"),
                        rs.getInt("anneeSortie"),
                        rs.getString("genre"),
                        rs.getInt("duree"),
                        rs.getString("synopsis"),
                        rs.getInt("categorieID")
                );
                films.add(film);
            }
            return films;
        }







    }

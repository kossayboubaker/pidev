package tn.esprit.crud.services;

import tn.esprit.crud.models.CodePromo;
import tn.esprit.crud.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PromoService implements IServices<CodePromo> {
    private Connection connection;

    public PromoService() throws SQLException {
        connection = MyDatabase.getInstance().getConnection();
    }

/*    @Override
    public void ajouter(CodePromo codePromo) throws SQLException {
        String req = "INSERT INTO code_promo (code, date_exp, utilise, user_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, codePromo.getCode());
        ps.setTimestamp(2,new java.sql.Date(codePromo.getDate_exp().getTime())); // Utiliser directement getDate_exp()
        ps.setInt(3, codePromo.getUtilise());
        ps.setInt(4, codePromo.getUser_id());
        ps.executeUpdate();
        ps.close();
    }
*/
@Override
public void ajouter(CodePromo codePromo) throws SQLException {
    String req = "INSERT INTO code_promo (code, date_exp, utilise, user_id) VALUES ('" +
            codePromo.getCode() + "', '" +
            codePromo.getDate_exp() + "', '" +
            codePromo.getUtilise() + "', '" +
            codePromo.getUser_id() + "')" ; // Corrected concatenation
    Statement st = connection.createStatement();
    st.executeUpdate(req);
}

    public void modifierParCodePromo(CodePromo codePromo) throws SQLException {
        String req = "UPDATE code_promo SET code = ?, date_exp = ?, utilise = ?, user_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, codePromo.getCode());
            ps.setDate(2, codePromo.getDate_exp());
            ps.setInt(3, codePromo.getUtilise());
            ps.setInt(4, codePromo.getUser_id());
            ps.setInt(5, codePromo.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(CodePromo codePromo) throws SQLException {
        String req = "UPDATE code_promo SET code = ?, date_exp = ?, utilise = ? WHERE user_id = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, codePromo.getCode());
        ps.setTimestamp(2, new Timestamp(codePromo.getDate_exp().getTime())); // Utiliser directement getDate_exp()
        ps.setInt(3, codePromo.getUtilise());
        ps.setInt(4, codePromo.getUser_id());
        ps.executeUpdate();
    }


    @Override
    public void supprimer(CodePromo codePromo) {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM code_promo WHERE id = ? ";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    public Map<LocalDate, Integer> nombreCodesAjoutesParJour() throws SQLException {
        Map<LocalDate, Integer> codesParJour = new HashMap<>();
        String query = "SELECT DATE(date_creation) AS date, COUNT(*) AS count FROM code_promo GROUP BY DATE(date_creation)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    int count = resultSet.getInt("count");
                    codesParJour.put(date, count);
                }
            }
        }

        return codesParJour;
    }


    @Override
    public List<CodePromo> recupperer() throws SQLException {
        List<CodePromo> codesPromo = new ArrayList<>();
        String req = "SELECT * FROM code_promo";
        Statement ps = connection.createStatement();
        ResultSet rs = ps.executeQuery(req);

        while (rs.next()) {
            CodePromo code_promo = new CodePromo();
            code_promo.setId(rs.getInt("id"));
            code_promo.setCode(rs.getInt("code"));
            code_promo.setDate_exp(rs.getDate("date_exp"));
            code_promo.setUtilise(rs.getInt("utilise"));
            code_promo.setUser_id(rs.getInt("user_id"));

            codesPromo.add(code_promo);
        }

        return codesPromo;
    }


}


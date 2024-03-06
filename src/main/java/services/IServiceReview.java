package Services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceReview <T>{

    public void ajouterReview(T t) throws SQLException;
    public List<T> displayReview() throws SQLException ;

    public int ReviewCount() throws SQLException;



}

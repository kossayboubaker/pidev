package utils;
import java.sql.*;
public class mydb {
    final String url = "jdbc:mysql://localhost:3306/gestcinema";
    final String user = "root";
    final String pwd = "";
    Statement statement;
    Connection connection;
    private  static mydb instance;
    private mydb(){
        try {
            connection = DriverManager.getConnection(url,user,pwd);
            System.out.println("connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static  mydb getInstance(){
        if(instance == null)
            instance=new mydb();
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }
}
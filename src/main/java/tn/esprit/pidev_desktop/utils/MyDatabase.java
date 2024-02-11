package tn.esprit.pidev_desktop.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    // 2eme etape de création d'une instance de base de données
    private static MyDatabase instance;
    private final String url = "jdbc:mysql://localhost:3306/pidev";
    private final String username = "root";
    private final String password = "";
    private Connection connection;

    //constructor par defaut

    // 1er etape de création d'une instance de base de données et de transfere le public en private
    private MyDatabase() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    // 3eme etape de création d'une instance de base de données

        public static MyDatabase getInstance() {
            if (instance == null) {
                instance = new MyDatabase();
            }
            return instance;
        }
    // methode getconnection qui permet de donner l'accès à la base de données.
    public Connection getConnection() {
        return connection;
    }

    }



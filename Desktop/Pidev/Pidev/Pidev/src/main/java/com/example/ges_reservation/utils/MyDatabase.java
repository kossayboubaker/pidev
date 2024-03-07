package com.example.ges_reservation.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB PARAM

public class MyDatabase {
    private static  MyDatabase instance  ;

    private final String URL ="jdbc:mysql://localhost:3306/pidev";
    private  final String USER="root";
 private final String PASSWORD="";

//var
private Connection connexion;



private MyDatabase(){
    try {
        connexion = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("connexion established")   ;
    } catch (SQLException ex) {
          System.err.println(ex.getMessage());
    }                  
}

public static MyDatabase getInstance(){
    if(instance == null)
        instance=new MyDatabase();
    return instance;
}
public  Connection getConnection(){
    return  connexion;
}
}

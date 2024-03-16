package com.example.vitalpharma;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

     public Connection connection;
     final String JDBC_URL = "jdbc:mysql://localhost:3306/vitalpharma";
     final String USERNAME = "root";
     final String PASSWORD = "root";

    public  Connection getConnection() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;

    }
}

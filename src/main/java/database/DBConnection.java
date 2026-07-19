package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    //DA MODIFICARE IN BASE A CHI SI CONNETTE (DATABASE ATTUALE ANTONIO)
    private static final String URL = "jdbc:postgresql://localhost:28481/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2143";

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al DB avvenuta con successo!");
            }
        } catch (SQLException e) {
            System.err.println("Errore critico di connessione al database: " + e.getMessage());
        }

        return connection;
    }
}
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static final String DB_URL = "jdbc:sqlite:db/flight_management.db"; // Path to .db file

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static Connection getInMemoryConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite::memory:");
    }
}

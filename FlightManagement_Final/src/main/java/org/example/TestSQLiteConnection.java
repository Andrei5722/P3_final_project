package org.example;

import java.sql.Connection;

public class TestSQLiteConnection {
    public static void main(String[] args) {
        try (Connection connection = SQLiteConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

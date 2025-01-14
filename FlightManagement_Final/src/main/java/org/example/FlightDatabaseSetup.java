package org.example;

import java.sql.Connection;
import java.sql.Statement;

public class FlightDatabaseSetup {
    public static void setupDatabase() {
        String createFlightsTableSQL = """
            CREATE TABLE IF NOT EXISTS flights (
                flight_number TEXT PRIMARY KEY,
                origin TEXT NOT NULL,
                destination TEXT NOT NULL,
                departure_time TEXT NOT NULL,
                arrival_time TEXT NOT NULL,
                aircraft_model TEXT DEFAULT 'Unknown Model'
            );
        """;

        String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                role TEXT NOT NULL CHECK (role IN ('Admin', 'User'))
            );
        """;

        try (Connection connection = SQLiteConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createFlightsTableSQL);
            statement.execute(createUserTableSQL);
            System.out.println("Database tables created or already exist.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void insertSampleUsers() {
//        String checkUsersSQL = "SELECT COUNT(*) FROM users";
//        String insertUserSQL = """
//        INSERT INTO users (username, password, role) VALUES
//        ('admin', 'admin123', 'Admin'),
//        ('user1', 'password1', 'User');
//    """;
//
//        try (Connection connection = SQLiteConnection.getConnection();
//             Statement statement = connection.createStatement();
//             var rs = statement.executeQuery(checkUsersSQL)) {
//
//            if (rs.next() && rs.getInt(1) == 0) { // No users found
//                statement.execute(insertUserSQL);
//                System.out.println("Sample users added to the database.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

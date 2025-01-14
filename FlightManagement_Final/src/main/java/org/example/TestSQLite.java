package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestSQLite {
    public static void main(String[] args) {
        try (Connection connection = SQLiteConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Insert a sample flight
            String insertSQL = """
                INSERT INTO flights (flight_number, origin, destination, departure_time, arrival_time, aircraft_model)
                VALUES ('AB123', 'New York', 'Los Angeles', '10:00 AM', '1:00 PM', 'Boeing 737');
            """;
            statement.executeUpdate(insertSQL);

            // Query and print flights
            String querySQL = "SELECT * FROM flights";
            ResultSet rs = statement.executeQuery(querySQL);
            while (rs.next()) {
                System.out.println("Flight Number: " + rs.getString("flight_number"));
                System.out.println("Origin: " + rs.getString("origin"));
                System.out.println("Destination: " + rs.getString("destination"));
                System.out.println("Departure Time: " + rs.getString("departure_time"));
                System.out.println("Arrival Time: " + rs.getString("arrival_time"));
                System.out.println("Aircraft Model: " + rs.getString("aircraft_model"));
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

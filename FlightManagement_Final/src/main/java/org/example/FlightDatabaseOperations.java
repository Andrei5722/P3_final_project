package org.example;

import java.sql.*;

public class FlightDatabaseOperations {

    public static void addFlight(String flightNumber, String origin, String destination,
                                 String departureTime, String arrivalTime, String aircraftModel) {
        String sql = "INSERT INTO flights (flight_number, origin, destination, departure_time, arrival_time, aircraft_model) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, flightNumber);
            preparedStatement.setString(2, origin);
            preparedStatement.setString(3, destination);
            preparedStatement.setString(4, departureTime);
            preparedStatement.setString(5, arrivalTime);
            preparedStatement.setString(6, aircraftModel);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("SQLITE_CONSTRAINT_PRIMARYKEY")) {
                System.out.println("Error: Flight number already exists. Please use a unique flight number.");
            } else {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    public static boolean isFlightNumberExists(String flightNumber) {
        String query = "SELECT COUNT(*) FROM flights WHERE flight_number = ?";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, flightNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if the count is greater than 0
            }
        } catch (SQLException e) {
            System.out.println("Error checking flight number: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteFlight(String flightNumber) {
        String sql = "DELETE FROM flights WHERE flight_number = ?";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, flightNumber);
            int rowsAffected = preparedStatement.executeUpdate(); // Returns the number of rows affected
            return rowsAffected > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            System.out.println("Error deleting flight: " + e.getMessage());
        }
        return false; // Return false if an error occurred
    }

    public static void listAllFlights() {
        String query = "SELECT * FROM flights";
        try (Connection connection = SQLiteConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            if (!rs.isBeforeFirst()) { // Check if ResultSet is empty
                System.out.println("No flights found in the database.");
                return;
            }

            while (rs.next()) {
                System.out.println("Flight Number: " + rs.getString("flight_number"));
                System.out.println("Origin: " + rs.getString("origin"));
                System.out.println("Destination: " + rs.getString("destination"));
                System.out.println("Departure Time: " + rs.getString("departure_time"));
                System.out.println("Arrival Time: " + rs.getString("arrival_time"));
                System.out.println("Aircraft Model: " + rs.getString("aircraft_model"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error listing flights: " + e.getMessage());
        }
    }

    public static boolean updateFlight(String flightNumber, String origin, String destination,
                                       String departureTime, String arrivalTime, String aircraftModel) {
        String sql = """
        UPDATE flights
        SET origin = ?, destination = ?, departure_time = ?, arrival_time = ?, aircraft_model = ?
        WHERE flight_number = ?
    """;

        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, origin);
            preparedStatement.setString(2, destination);
            preparedStatement.setString(3, departureTime);
            preparedStatement.setString(4, arrivalTime);
            preparedStatement.setString(5, aircraftModel);
            preparedStatement.setString(6, flightNumber);

            int rowsAffected = preparedStatement.executeUpdate(); // Returns the number of rows updated
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating flight: " + e.getMessage());
        }
        return false;
    }

    public static String login(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection connection = SQLiteConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString("role"); // Return the role of the user
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null; // Return null if login fails
    }



    // Other database methods (listAllFlights, deleteFlight, etc.)
}

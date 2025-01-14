package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.Statement;

class FlightDatabaseOperationsTest {

    @BeforeEach
    void setUp() throws Exception {
        try (Connection connection = SQLiteConnection.getInMemoryConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS flights (" +
                    "flight_number TEXT PRIMARY KEY, " +
                    "origin TEXT, " +
                    "destination TEXT, " +
                    "departure_time TEXT, " +
                    "arrival_time TEXT, " +
                    "aircraft_model TEXT)");

            statement.execute("INSERT INTO flights (flight_number, origin, destination, departure_time, arrival_time, aircraft_model) " +
                    "VALUES ('AB123', 'New York', 'London', '10:00 AM', '8:00 PM', 'Boeing 747')");
        }
    }

    @Test
    void testAddFlight() {
        FlightDatabaseOperations.addFlight("XY789", "Paris", "Tokyo", "3:00 PM", "11:00 AM", "Airbus A380");
        assertTrue(FlightDatabaseOperations.isFlightNumberExists("XY789"));
    }

    @Test
    void testDeleteFlight() {
        FlightDatabaseOperations.deleteFlight("AB123");
        assertFalse(FlightDatabaseOperations.isFlightNumberExists("AB123"));
    }
}

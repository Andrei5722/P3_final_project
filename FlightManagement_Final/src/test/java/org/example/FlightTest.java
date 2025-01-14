package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    @Test
    void testFlightConstructor() {
        // Arrange & Act
        Flight flight = new Flight("AB123", "New York", "London", "10:00 AM", "8:00 PM", "Boeing 747");

        // Assert
        assertEquals("AB123", flight.getFlightNumber());
        assertEquals("New York", flight.getOrigin());
        assertEquals("London", flight.getDestination());
        assertEquals("10:00 AM", flight.getDepartureTime());
        assertEquals("8:00 PM", flight.getArrivalTime());
        assertEquals("Boeing 747", flight.getAircraftModel());
    }
}

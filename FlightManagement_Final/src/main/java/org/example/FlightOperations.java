package org.example;

import java.util.List;

public interface FlightOperations {
    void addFlight(); // Method to add a new flight
    void listAllFlights(); // Method to list all flights
    void deleteFlightByNumber(); // Method to delete a flight by its flight number
    void updateFlight(); // New method to update a flight
    List<Flight> getFlights(); // Method to get the list of flights
}

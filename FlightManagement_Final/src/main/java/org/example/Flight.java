package org.example;

import java.io.Serializable;

public class Flight implements Serializable {
    private String flightNumber;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String aircraftModel;

    public Flight(String flightNumber, String origin, String destination, String departureTime, String arrivalTime, String aircraftModel) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.aircraftModel = aircraftModel;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    @Override
    public String toString() {
        return "Flight [Number: " + flightNumber + ", Origin: " + origin +
                ", Destination: " + destination + ", Departure Time: " + departureTime +
                ", Arrival Time: " + arrivalTime + ", Aircraft Model: " + aircraftModel + "]";
    }
}

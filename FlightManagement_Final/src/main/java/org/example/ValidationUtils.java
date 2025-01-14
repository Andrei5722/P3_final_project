package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidationUtils {

    public static boolean isValidFlightNumber(String flightNumber) {
        return flightNumber.matches("^[A-Z]{2}\\d{1,4}$");
    }

    public static boolean isValidTimeFormat(String time) {
        return time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]( (AM|PM))?$");
    }

    public static boolean isDepartureBeforeArrival(String departureTime, String arrivalTime) {
        try {
            // Try parsing as 12-hour format (with AM/PM)
            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
            Date departure = parseTime(departureTime, sdf12);
            Date arrival = parseTime(arrivalTime, sdf12);

            return departure.before(arrival); // Ensure departure is earlier
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid time format for departure or arrival.");
        }
    }

    private static Date parseTime(String time, SimpleDateFormat sdf12) throws ParseException {
        // Try parsing as 12-hour format (with AM/PM)
        try {
            return sdf12.parse(time);
        } catch (ParseException e) {
            // If parsing as 12-hour fails, try 24-hour format
            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
            return sdf24.parse(time);
        }
    }


    public static boolean isValidLocation(String location) {
        return location.matches("^[a-zA-Z ]+$") && location.length() <= 50;
    }

    public static boolean isValidAircraftModel(String aircraftModel) {
        return aircraftModel.length() <= 100;
    }
}

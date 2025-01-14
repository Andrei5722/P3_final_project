package org.example;

import java.util.Scanner;

public class Main implements FlightOperations {
    private static final String ROLE_ADMIN = "Admin";
    private static final String ROLE_USER = "User";
    private static String currentUserRole;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Initialize the database
            FlightDatabaseSetup.setupDatabase();

            // Login
            while (currentUserRole == null) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                currentUserRole = FlightDatabaseOperations.login(username, password);
                if (currentUserRole == null) {
                    System.out.println("Invalid credentials. Please try again.");
                }
            }
            System.out.println("Login successful. Role: " + currentUserRole);

            boolean running = true;

            // Display menu based on user role
            while (running) {
                displayMenuForRole();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (ROLE_ADMIN.equals(currentUserRole)) {
                    handleAdminChoice(choice);
                } else if (ROLE_USER.equals(currentUserRole)) {
                    handleUserChoice(choice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayMenuForRole() {
        System.out.println("\nChoose an option:");
        System.out.println("1 - List all flights");
        if (ROLE_ADMIN.equals(currentUserRole)) {
            System.out.println("2 - Add a new flight");
            System.out.println("3 - Delete a flight");
            System.out.println("4 - Update a flight");
        }
        System.out.println("5 - Exit");
        System.out.print("Your choice: ");
    }

    private static void handleAdminChoice(int choice) {
        Main app = new Main();
        switch (choice) {
            case 1 -> app.listAllFlights();
            case 2 -> app.addFlight();
            case 3 -> app.deleteFlightByNumber();
            case 4 -> app.updateFlight();
            case 5 -> exitApplication();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleUserChoice(int choice) {
        Main app = new Main();
        switch (choice) {
            case 1 -> app.listAllFlights();
            case 5 -> exitApplication();
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void exitApplication() {
        System.out.println("Exiting the application...");
        System.exit(0);
    }



@Override
    public void addFlight() {
        Scanner scanner = new Scanner(System.in);
        try {
            // Validate Flight Number
            String flightNumber;
            while (true) {
                System.out.print("Enter flight number (e.g., AB123): ");
                flightNumber = scanner.nextLine().trim();
                if (!ValidationUtils.isValidFlightNumber(flightNumber)) {
                    System.out.println("Invalid flight number format. Please use a format like AB123.");
                    continue;
                }
                if (FlightDatabaseOperations.isFlightNumberExists(flightNumber)) {
                    System.out.println("Flight number already exists. Please enter a unique flight number.");
                    continue;
                }
                break;
            }

            // Validate Origin and Destination
            String origin, destination;
            while (true) {
                System.out.print("Enter origin: ");
                origin = scanner.nextLine().trim();
                if (!ValidationUtils.isValidLocation(origin)) {
                    System.out.println("Origin must only contain alphabetic characters and spaces (max 50 characters).");
                    continue;
                }
                System.out.print("Enter destination: ");
                destination = scanner.nextLine().trim();
                if (!ValidationUtils.isValidLocation(destination)) {
                    System.out.println("Destination must only contain alphabetic characters and spaces (max 50 characters).");
                    continue;
                }
                if (origin.equalsIgnoreCase(destination)) {
                    System.out.println("Origin and destination cannot be the same.");
                    continue;
                }
                break;
            }

            // Validate Departure and Arrival Times
            String departureTime, arrivalTime;
            while (true) {
                System.out.print("Enter departure time (e.g., 10:00 AM or 17:30): ");
                departureTime = scanner.nextLine().trim();
                if (!ValidationUtils.isValidTimeFormat(departureTime)) {
                    System.out.println("Invalid departure time format. Use formats like '10:00 AM' or '17:30'.");
                    continue;
                }
                System.out.print("Enter arrival time (e.g., 10:00 AM or 17:30): ");
                arrivalTime = scanner.nextLine().trim();
                if (!ValidationUtils.isValidTimeFormat(arrivalTime)) {
                    System.out.println("Invalid arrival time format. Use formats like '10:00 AM' or '17:30'.");
                    continue;
                }
                if (!ValidationUtils.isDepartureBeforeArrival(departureTime, arrivalTime)) {
                    System.out.println("Departure time must be earlier than arrival time.");
                    continue;
                }
                break;
            }

            // Validate Aircraft Model
            System.out.print("Enter aircraft model: ");
            String aircraftModel = scanner.nextLine().trim();
            if (!ValidationUtils.isValidAircraftModel(aircraftModel)) {
                System.out.println("Aircraft model must not exceed 100 characters.");
                return;
            }
            if (aircraftModel.isEmpty()) aircraftModel = "Unknown Model";

            // Add the flight to the database
            FlightDatabaseOperations.addFlight(flightNumber, origin, destination, departureTime, arrivalTime, aircraftModel);
            System.out.println("Flight added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding flight: " + e.getMessage());
        }
    }

    @Override
    public void listAllFlights() {
        System.out.println("List of all flights:");
        FlightDatabaseOperations.listAllFlights();
    }

    @Override
    public void deleteFlightByNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the flight number to delete: ");
        String flightNumber = scanner.nextLine().trim();

        try {
            boolean success = FlightDatabaseOperations.deleteFlight(flightNumber);
            if (success) {
                System.out.println("Flight deleted successfully.");
            } else {
                System.out.println("No flight found with number " + flightNumber + ".");
            }
        } catch (Exception e) {
            System.out.println("Error deleting flight: " + e.getMessage());
        }
    }

    @Override
    public void updateFlight() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the flight number to update: ");
            String flightNumber = scanner.nextLine().trim();

            // Check if the flight exists
            if (!FlightDatabaseOperations.isFlightNumberExists(flightNumber)) {
                System.out.println("No flight found with number " + flightNumber + ".");
                return;
            }

            // Prompt the user for new details
            System.out.print("Enter new origin: ");
            String origin = scanner.nextLine().trim();
            if (!ValidationUtils.isValidLocation(origin)) {
                throw new IllegalArgumentException("Invalid origin. Must only contain alphabetic characters and spaces (max 50 characters).");
            }

            System.out.print("Enter new destination: ");
            String destination = scanner.nextLine().trim();
            if (!ValidationUtils.isValidLocation(destination)) {
                throw new IllegalArgumentException("Invalid destination. Must only contain alphabetic characters and spaces (max 50 characters).");
            }
            if (origin.equalsIgnoreCase(destination)) {
                throw new IllegalArgumentException("Origin and destination cannot be the same.");
            }

            String departureTime, arrivalTime;
            while (true) {
                System.out.print("Enter new departure time (e.g., 10:00 AM or 17:30): ");
                departureTime = scanner.nextLine().trim();
                if (!ValidationUtils.isValidTimeFormat(departureTime)) {
                    System.out.println("Invalid departure time format. Use formats like '10:00 AM' or '17:30'.");
                    continue;
                }

                System.out.print("Enter new arrival time (e.g., 10:00 AM or 17:30): ");
                arrivalTime = scanner.nextLine().trim();
                if (!ValidationUtils.isValidTimeFormat(arrivalTime)) {
                    System.out.println("Invalid arrival time format. Use formats like '10:00 AM' or '17:30'.");
                    continue;
                }

                if (!ValidationUtils.isDepartureBeforeArrival(departureTime, arrivalTime)) {
                    System.out.println("Departure time must be earlier than arrival time.");
                    continue;
                }
                break;
            }

            System.out.print("Enter new aircraft model: ");
            String aircraftModel = scanner.nextLine().trim();
            if (!ValidationUtils.isValidAircraftModel(aircraftModel)) {
                throw new IllegalArgumentException("Aircraft model must not exceed 100 characters.");
            }
            if (aircraftModel.isEmpty()) aircraftModel = "Unknown Model";

            // Update the flight in the database
            boolean updated = FlightDatabaseOperations.updateFlight(flightNumber, origin, destination, departureTime, arrivalTime, aircraftModel);
            if (updated) {
                System.out.println("Flight updated successfully.");
            } else {
                System.out.println("Failed to update flight.");
            }
        } catch (Exception e) {
            System.out.println("Error updating flight: " + e.getMessage());
        }
    }


    @Override
    public java.util.List<Flight> getFlights() {
        throw new UnsupportedOperationException("Method not used in SQLite integration.");
    }
}

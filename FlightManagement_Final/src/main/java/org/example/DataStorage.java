package org.example;

import java.io.IOException;
import java.util.List;

public interface DataStorage {
    void saveData(List<Flight> flights, String filePath) throws IOException; // Method to save flight data
    List<Flight> loadData(String filePath) throws IOException, ClassNotFoundException; // Method to load flight data
}

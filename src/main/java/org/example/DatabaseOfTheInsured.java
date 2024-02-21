package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseOfTheInsured {
    private Connection connection;

    public DatabaseOfTheInsured() {
        try {
            // Připojení k MySQL databázi
            String url = "jdbc:mysql://localhost:3306/wowatrustcorporation";
            String username = "wowatattoo22";
            String password = "Bigger/279";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Připojení k databázi úspěšné.");
        } catch (SQLException e) {
            System.out.println("Chyba při připojení k databázi: " + e.getMessage());
        }
        databaseFilePath = null;
    }


    private final List<Insured> database = new ArrayList<>();
    private final String databaseFilePath;

    public DatabaseOfTheInsured(String databaseFilePath) {
        this.databaseFilePath = databaseFilePath;
        loadDatabase();
    }

    private void loadDatabase() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(databaseFilePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String surname = data[2].trim();
                String birthDate = data[3].trim();
                String address = data[4].trim();
                String bankAccountNumber = data[5].trim();
                Insured insured = new Insured(id, name, surname, birthDate, address, bankAccountNumber);
                database.add(insured);
            }
        } catch (IOException e) {
            System.out.println("Chyba při načítání databáze: " + e.getMessage());
        }
    }

    public List<Insured> findInsuredByPartialSurname(String partialSurname) {
        List<Insured> results = new ArrayList<>();
        for (Insured insured : database) {
            if (insured.getSurname().toLowerCase().contains(partialSurname.toLowerCase())) {
                results.add(insured);
            }
        }
        return results;
    }

    public List<Insured> findInsuredByPartialName(String partialName) {
        List<Insured> results = new ArrayList<>();
        for (Insured insured : database) {
            if (insured.getName().toLowerCase().contains(partialName.toLowerCase())) {
                results.add(insured);
            }
        }
        return results;
    }

    public void addInsured(int id, String name, String surname, String birthDate, String address, String bankAccountNumber) {
        Insured insured = new Insured(id, name, surname, birthDate, address, bankAccountNumber);
        database.add(insured);
        saveDatabase();
    }

    public List<Insured> findInsuredByName(String name) {
        List<Insured> results = new ArrayList<>();
        for (Insured insured : database) {
            if (insured.getName().equalsIgnoreCase(name)) {
                results.add(insured);
            }
        }
        return results;
    }

    public Insured findInsuredByID(int id) {
        for (Insured insured : database) {
            if (insured.getId() == id) {
                return insured;
            }
        }
        return null;
    }

    public void idToRemove(int id) {
        for (Insured insured : database) {
            if (insured.getId() == id) {
                database.remove(insured);
                saveDatabase();
                return;
            }
        }
        System.out.println("Pojištěnec s ID " + id + " nebyl nalezen.");
    }


    private void saveDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(databaseFilePath), StandardCharsets.UTF_8))) {
            for (Insured insured : database) {
                writer.write(insured.getId() + ", " + insured.getName() + ", " + insured.getSurname() +
                        ", " + insured.getBirthDate() + ", " + insured.getAddress() + ", " + insured.getBankAccountNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba při ukládání databáze: " + e.getMessage());
        }
    }
    public List<Insured> getDatabase() {
        return database;
    }
}


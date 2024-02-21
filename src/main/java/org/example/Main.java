package org.example;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        String url = "jdbc:mysql://localhost:3306/wowatrustcorporation";
        String username = "wowatattoo22";
        String password = "Bigger/279";
        String filePath = "insureds.txt"; // Cesta k textovému souboru

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Připojení k databázi úspěšné.");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM insureds");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

                System.out.println("ID: " + id + ", Name: " + name + ", Surname: " + surname);
            }
        } catch (SQLException e) {
            System.out.println("Chyba při připojení k databázi: " + e.getMessage());
        }

        DatabaseOfTheInsured database = new DatabaseOfTheInsured("insureds.txt");
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Insurance Company");
            System.out.println("Wowa Trust Forever");
            System.out.println("Vítejte v databázi pojištěnců!");
            System.out.println();
            System.out.println("Co si přejete?");
            System.out.println();
            System.out.println("1 - Přidat pojištěnce?");
            System.out.println("2 - Odebrat pojištěnce?");
            System.out.println("3 - Najít pojištěnce podle ID, \n"
                    +"podle jména anebo jeho části anebo přijmení anebo jeho časti?");
            System.out.println("4 - Najít pojištěnce jmena?");
            System.out.println("5 - Vypsat všechny pojištěnce?");
            System.out.println("6 - Ukončit program?");
            System.out.println("Pro návrat do hlavní nabídky klikněte dvakrát Enter");

            int volba;
            try {
                volba = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Chybný vstup. Zadejte prosím číslo mezi 1 a 6.");
                continue;
            }
            switch (volba) {
                case 1 -> {
                    // Přidání nového pojištěnce do databáze.
                    // Uživatel je vyzván k zadání ID, jména, příjmení, data narození, adresy a čísla bankovního účtu.

                    System.out.println("Zadejte ID pojištěnce:");
                    int id; // Výchozí hodnota pro ID

                    while (true) {
                        String inputId = scanner.nextLine();
                        if (inputId.matches("\\d+")) { // Kontrola, zda řetězec obsahuje pouze číslice
                            id = Integer.parseInt(inputId);
                            break; // Pokud je vstup správný, opustíme smyčku while
                        } else {
                            System.out.println("Neplatný vstup. Zadejte pouze čísla.");
                        }
                    }

                    System.out.println("Zadejte jméno pojištěnce:");
                    String name;

                    while (true) {
                        String inputName = scanner.nextLine();
                        if (inputName.matches("\\p{L}+")) { // Kontrola, zda řetězec obsahuje pouze písmena
                            name = inputName;
                            break; // Pokud je vstup správný, opustíme smyčku while
                        } else {
                            System.out.println("Neplatný vstup. Zadejte pouze písmena.");
                        }
                    }

                    System.out.println("Zadejte příjmení pojištěnce:");
                    String surname;

                    while (true) {
                        String inputSurname = scanner.nextLine();
                        if (inputSurname.matches("\\p{L}+([-\\s]\\p{L}+)?")) { // Kontrola, zda řetězec obsahuje pouze písmena
                            surname = inputSurname;
                            break; // Pokud je vstup správný, opustíme smyčku while
                        } else {
                            System.out.println("Neplatný vstup. Zadejte pouze písmena.");
                        }
                    }

                    System.out.println("Zadejte datum narození pojištěnce (ve formátu dd.MM.yyyy):");
                    String birthDate = scanner.nextLine();


                    while (true) {
                        String inputbirthDate = scanner.nextLine();

                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                            dateFormat.setLenient(false);
                            // Zkontroluje, zda je vstup ve správném formátu
                            dateFormat.parse(inputbirthDate);

                            // Pokud vstup splňuje požadovaný birthDate
                            // Pokud je vstup správný, opustíme smyčku while
                            break;
                        } catch (ParseException e) {
                            System.out.println("Neplatný formát data. Zadejte datum ve formátu dd.MM.yyyy.");
                        }
                    }
                    System.out.println("Zadejte adresu pojištěnce:");
                    String address = scanner.nextLine();

                    System.out.println("Zadejte číslo bankovního účtu pojištěnce ve formátu xxxxxxx/xxxx:");
                    String bankAccountNumber;

                    while (true) {
                        String inputBankAccountNumber = scanner.nextLine(); // Přesunutí načítání vstupu dovnitř smyčky
                        // Kontrola, zda řetězec obsahuje správný formát čísla účtu
                        if (inputBankAccountNumber.matches("\\d+/\\d{4}")) { // Opravení regulárního výrazu
                            bankAccountNumber = inputBankAccountNumber;
                            break; // Pokud je vstup správný, opustíme smyčku while
                        } else {
                            System.out.println("Neplatný vstup. Zadejte číslo účtu ve formátu xxxxxxx/xxxx");
                        }
                    }

                    database.addInsured(id, name, surname, birthDate, address, bankAccountNumber);
                    System.out.println("Pojištěnec byl přidán do databáze.");
                }
                // Odebrání pojištěnce z databáze.
                // Uživatel je vyzván k zadání ID pojištěnce, kterého chce odebrat.
                case 2 -> {
                    System.out.println("Zadejte ID pojištěnce, kterého chcete odebrat:");
                    int idToRemove = scanner.nextInt();
                    scanner.nextLine();
                    database.idToRemove(idToRemove);
                    System.out.println("Pojištěnec byl odebrán z databáze.");

                }
                // Hledání pojištěnce podle zadaného ID.
                // Uživatel je vyzván k zadání ID pojištěnce, kterého chce najít.
                case 3 -> {
                    System.out.println("Zadejte ID pojištěnce, část jména nebo část příjmení, kterého chcete najít:");
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("Neplatný vstup.");
                        break;
                    }
                    if (input.matches("\\d+")) { // Pokud vstup je číslo, vyhledej podle ID
                        int idToFind = Integer.parseInt(input);
                        Insured foundInsuredById = database.findInsuredByID(idToFind);
                        if (foundInsuredById != null) {
                            System.out.printf("Pojištěnec nalezen: ID: %d, Jméno: %s, Příjmení: %s, Datum narození: %s%nAdresa: %s, Číslo bankovního účtu: %s%n",
                                    foundInsuredById.getId(), foundInsuredById.getName(), foundInsuredById.getSurname(),
                                    foundInsuredById.getBirthDate(), foundInsuredById.getAddress(),
                                    foundInsuredById.getBankAccountNumber());
                        } else {
                            System.out.println("Pojištěnec s ID " + idToFind + " nebyl nalezen.");
                        }
                    } else { // Pokud vstup není číslo, vyhledej podle části jména nebo části příjmení
                        List<Insured> foundInsuredByPartialName = database.findInsuredByPartialName(input);
                        List<Insured> foundInsuredByPartialSurname = database.findInsuredByPartialSurname(input);

                        if (!foundInsuredByPartialName.isEmpty()) {
                            System.out.println("Nalezení pojištěnci podle jména:");
                            for (Insured insured : foundInsuredByPartialName) {
                                System.out.println(insured);
                            }
                        }
                        if (!foundInsuredByPartialSurname.isEmpty()) {
                            System.out.println("Nalezení pojištěnci podle příjmení:");
                            for (Insured insured : foundInsuredByPartialSurname) {
                                System.out.println(insured);
                            }
                        }
                        if (foundInsuredByPartialName.isEmpty() && foundInsuredByPartialSurname.isEmpty()) {
                            System.out.println("Pojištěnci s částí jména nebo částí příjmení " + input + " nebyli nalezeni.");
                        }
                    }
                }
                // Hledání pojištěnce podle jména nebo jeho části.
                // Uživatel je vyzván k zadání jména pojištěnce, kterého chce najít.
                case 4 -> {
                    System.out.println("Zadejte jméno pojištěnce, kterého chcete najít:");
                    String nameToFind = scanner.nextLine();
                    List<Insured> foundInsuredByName = database.findInsuredByName(nameToFind);
                    if (!foundInsuredByName.isEmpty()) {
                        for (Insured insured : foundInsuredByName) {
                            System.out.println(insured);
                        }
                    } else {
                        System.out.println("Pojištěnci s jménem " + nameToFind + " nebyli nalezeni.");
                    }
                }
                // Vypsání všech pojištěnců v databázi.
                // Program vypíše všechny pojištěnce uložené v databázi.
                case 5 -> {
                    List<Insured> allInsured = database.getDatabase();
                    if (!allInsured.isEmpty()) {
                        System.out.println("Všichni pojištěnci:");
                        for (Insured insured : allInsured) {
                            System.out.println(insured);
                        }
                    } else {
                        System.out.println("Databáze neobsahuje žádné pojištěnce.");
                    }

                }
                // Ukončení programu.
                // Proměnná running je nastavena na false, což způsobí ukončení hlavní smyčky programu.
                case 6 -> running = false;
                default -> System.out.println("Neplatná volba. Zvolte prosím znovu.");

            }
        }
    }
}
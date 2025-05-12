package repository;

import objects.User;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {

    public static List<User> getUsersFromFile() {
        List<User> users = new ArrayList<>();
        try {
            File usersFile = new File("resources/users.csv");
            Scanner reader = new Scanner(usersFile);

            reader.nextLine(); // Skip first line

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(";");

                if (data.length >= 9) {
                    try {
                        int userId = Integer.parseInt(data[0].trim());
                        String fullName = data[1].trim();
                        String email = data[2].trim();
                        LocalDate birthDate = LocalDate.parse(data[3].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        double initialCashDKK = Double.parseDouble(data[4].trim());
                        LocalDate createdDate = LocalDate.parse(data[5].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        LocalDate lastUpdated = LocalDate.parse(data[6].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        boolean admin = Boolean.parseBoolean(data[7]);
                        String password = data[8].trim();

                        User user = new User(userId, fullName, email, birthDate, initialCashDKK, createdDate, lastUpdated, admin, password);
                        users.add(user);

                    } catch (NumberFormatException e) {
                        System.out.println("Kunne ikke parse linje: " + line);
                    }
                } else {
                    System.out.println("Ugyldig linje (for få felter): " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return users;
    }

    public static void addUserToFile(User user) {
        List<User> users = getUsersFromFile();
        users.add(user);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/users.csv", true))) {
            writer.newLine();
            writer.write(user.getUserId() + ";" + user.getFullName() + ";" + user.getEmail()
                    + ";" + user.getBirthDate().format(dateFormatter) + ";" + user.getInitialCashDKK() + ";" + user.getCreatedDate().format(dateFormatter)
                    + ";" + user.getLastUpdated().format(dateFormatter) + ";" + user.isAdmin() + ";" + user.getPassword());
        } catch (IOException e) {
            System.out.println("Failed to add user: " + e.getMessage());
        }
    }

    // TODO: Tilføj en funktion der redigerer en users oplysninger
    public static void removeUserFromFile(int userId) {
        List<User> users = getUsersFromFile();
        for (User user : users) {
            if (userId == user.getUserId()){
                users.remove(user);
                break;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/users.csv"))) {
            writer.write("userId;fullName;email;birthDate;initialCashDKK;createdDate;lastUpdated;admin;password");
            writer.newLine();

            // Skriv alle brugere til filen
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for (User user : users) {
                writer.write(
                        user.getUserId() + ";" +
                                user.getFullName() + ";" +
                                user.getEmail() + ";" +
                                user.getBirthDate().format(dateFormatter) + ";" +
                                user.getInitialCashDKK() + ";" +
                                user.getCreatedDate().format(dateFormatter) + ";" +
                                user.getLastUpdated().format(dateFormatter) + ";" +
                                user.isAdmin() + ";" +
                                user.getPassword()
                );
                writer.newLine();
            }

            System.out.println("Bruger med ID " + userId + " er slettet.");
        } catch (IOException e) {
            System.out.println("Fejl under sletning: " + e.getMessage());
        }
    }

    public static User findUserById(int userId) {
        for (User user : UserRepository.getUsersFromFile()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public static User findUserByFullName(String fullName) {
        for (User user : UserRepository.getUsersFromFile()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }
}
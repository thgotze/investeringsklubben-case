package repository;

import objects.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {

    public static List<User> readUsersFile() {
        List<User> users = new ArrayList<>();
        try {
            File usersFile = new File("resources/users.csv");
            Scanner reader = new Scanner(usersFile);

            boolean isFirstLine = true;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(";");

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
                    System.out.println("Could not read line " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return users;
    }

    public static void addUserToFile(User user) {
        List<User> users = readUsersFile();
        users.add(user);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/users.csv", true))) {
            writer.newLine();
            writer.write(user.toString());
        } catch (IOException e) {
            System.out.println("Failed to add user: " + e.getMessage());
        }
    }
}
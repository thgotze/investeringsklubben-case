package repository;

import Objects.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {
    public static void readUserFile() {
        List<User> users = new ArrayList<>();

        try {
            File userFile = new File("src/files/users.csv");
            Scanner reader = new Scanner(userFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(";");

                try {
                    int userId = Integer.parseInt(data[0].trim());
                    String fullName = data[1].trim();
                    String email = data[2].trim();
                    LocalDate birthDate = LocalDate.parse(data[3].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    double initialCashDKK = Double.parseDouble(data[4].trim());
                    LocalDate createdDate = LocalDate.parse(data[5].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate lastUpdated = LocalDate.parse(data[6].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                    // Since `admin` and `password` fields are not in the file, use defaults
                    boolean admin = false; // Default value
                    String password = ""; // Default empty password

                    //                int userId = Integer.parseInt(data[0].trim());
                    //                String fullName = data[1].trim();
                    //                String email = data[2].trim();
                    //                LocalDate birthDate = LocalDate.parse(data[3].trim());
                    //                double initialCashDKK = Double.parseDouble(data[4].trim());
                    //                LocalDate createdDate = LocalDate.parse(data[5].trim());
                    //                LocalDate lastUpdated = LocalDate.parse(data[6].trim());
                    //                boolean admin = Boolean.parseBoolean(data[7].trim());
                    //                String password = data[8].trim();

                    User user = new User(userId, fullName, email, birthDate, initialCashDKK, createdDate, lastUpdated, admin, password);
                    users.add(user);


                    // TODO: Temp debug besked
                    System.out.println("User: " + fullName + " added to list!");
                }
                catch (NumberFormatException e) {
                    System.out.println("Could not read line " + line);
                }
            }
            System.out.println(users.size() + " total users read!");

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
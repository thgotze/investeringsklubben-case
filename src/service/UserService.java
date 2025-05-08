package service;

import objects.User;
import repository.UserRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class UserService {
    public static User logIn(Scanner scanner) {

        User user;
        while (true) {
            System.out.println("Indtast fulde navn:");
            String name = scanner.nextLine();
            user = UserService.findByFullName(name);
            if (user == null) {
                System.out.println("Bruger ikke fundet. Prøv igen");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.println("Indtast adgangskode: ");
            password = scanner.nextLine();

            if (password.equals(user.getPassword())) {
                break;
            } else {
                System.out.println("Forkert adgangskode. Prøv igen");
            }
        }
        return user;
    }

    public static void updateUserBalance(int userId, double amount, String transactionType) {
        List<User> users = UserRepository.getUsersFromFile();

        for (User user : users) {
            if (user.getUserId() == userId) {
                if ("BUY".equalsIgnoreCase(transactionType)) {
                    user.setInitialCashDKK(user.getInitialCashDKK() - amount);
                } else if ("SELL".equalsIgnoreCase(transactionType)) {
                    user.setInitialCashDKK(user.getInitialCashDKK() + amount);
                }

                UserRepository.addUserToFile(user);
                return;
            }
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }


    public static User findUserById(int userId) {
        for (User user : UserRepository.getUsersFromFile()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public static User findByFullName(String fullName) {
        for (User user : UserRepository.getUsersFromFile()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }
}

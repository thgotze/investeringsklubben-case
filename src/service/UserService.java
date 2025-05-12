package service;

import objects.Transaction;
import objects.User;
import repository.TransactionRepository;
import repository.UserRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserService {
    public static User findUserById(int userId) {
        return UserRepository.findUserById(userId);
    }

    public static User findUserByFullName(String fullName) {
        return UserRepository.findUserByFullName(fullName);
    }

    public static boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    public static void addUser(Scanner scanner) {
        System.out.println("Hvad er brugerens fulde navn?");
        System.out.println("> 0. Returner til hovedmenu");

        String fullName = scanner.nextLine();
        if (fullName.equals("0")) return;

        System.out.println("Hvad er brugerens e-mail adresse?");
        System.out.println("> 0. Returner til hovedmenu");

        String email = scanner.nextLine();
        if (email.equals("0")) return;

        LocalDate birthDay;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            try {
                // År
                System.out.println("Angiv fødselsår for brugeren:");
                System.out.println("> 0. Returner til hovedmenu");

                String inputYear = scanner.nextLine();
                if (inputYear.equals("0")) return;
                int birthyear = Integer.parseInt(inputYear);

                if (birthyear > LocalDate.now().getYear() || birthyear < 1900) {
                    System.out.println("Ugyldigt fødselsår! Prøv igen");
                    continue;
                }

                // Måned
                System.out.println("Angiv fødselsmåned for brugeren (1-12):");
                System.out.println("> 0. Returner til hovedmenu");

                String inputMonth = scanner.nextLine();
                if (inputMonth.equals("0")) return;
                int birthMonth = Integer.parseInt(inputMonth);

                if (birthMonth < 1 || birthMonth > 12) {
                    System.out.println("Ugyldig måned! Prøv igen");
                    continue;
                }

                // Dag
                int maxDaysInMonth = YearMonth.of(birthyear, birthMonth).lengthOfMonth();

                System.out.println("Angiv fødselsdato for brugeren:");
                System.out.println("> 0. Returner til hovedmenu");

                String inputDate = scanner.nextLine();
                if (inputDate.equals("0")) return;
                int birthDate = Integer.parseInt(inputDate);

                if (birthDate < 1 || birthDate > maxDaysInMonth) {
                    System.out.println("Ugyldig dato! Prøv igen");
                    continue;
                }

                // Gem Dato
                birthDay = LocalDate.of(birthyear, birthMonth, birthDate);
                String formattedBirthDay = birthDay.format(dateTimeFormatter);
                birthDay = LocalDate.parse(formattedBirthDay, dateTimeFormatter);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt input! prøv igen");
            }
        }


        boolean admin = false;
        while (true) {
            try {
                System.out.println("Angiv om brugeren er admin, eller normal bruger:");
                System.out.println("> 1. Bruger");
                System.out.println("> 2. Admin");
                System.out.println("> 0. Returner til hovedmenu");
                String inputStr = scanner.nextLine();

                if (inputStr.equals("0")) {
                    return;
                }

                int input = Integer.parseInt(inputStr);

                switch (input) {
                    case 1:
                        admin = false;
                        break;

                        case 2:
                            admin = true;
                            break;

                            default:
                                System.out.println("Ugyldigt. input! prøv igen");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt input! prøv igen");
            }
        }

        System.out.println("Angiv adgangskode hos brugren");
        System.out.println("> 0. Returner til hovedmenu");

        String password = scanner.nextLine();
        if (password.equals("0")) {
            return;
        }

        int userId = UserRepository.getUsersFromFile().getLast().getUserId() + 1;
        double initialSaldo = 100000.0;

        LocalDate createdDate = LocalDate.now();

        LocalDate lastUpdated = LocalDate.now();

        User user = new User(userId, fullName, email, birthDay , initialSaldo, createdDate, lastUpdated, admin, password);
        UserRepository.addUserToFile(user);
        System.out.println(user.getFullName() + " er blevet tilføjet til programmet");
    }

    public static void deleteUser(Scanner scanner, User user) {
        System.out.println("Indtast navnet på den bruger du vil fjerne fra systemet:");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();

        if (input.equals("0")) return;
        User userToDelete = UserRepository.findUserByFullName(input);

        if (userToDelete == null) return;

        int idOfUserToDelete = userToDelete.getUserId();

        if (idOfUserToDelete == user.getUserId()) {
            System.out.println("Kan ikke slette den bruger du er logget ind med!");
        } else {
            UserRepository.removeUserFromFile(idOfUserToDelete);
        }
    }

    public static double findUserBalance(User user) {
        List<Transaction> userTransactions = TransactionRepository.findTransactionsForUser(user);

        double userBalance = user.getInitialCashDKK();
        for (Transaction transaction : userTransactions) {
            if (transaction.getOrderType().equals("buy")) {
                userBalance -= transaction.getPrice() * transaction.getQuantity();
            } else {
                userBalance += transaction.getPrice() * transaction.getQuantity();
            }
        }
        return userBalance;
    }
}
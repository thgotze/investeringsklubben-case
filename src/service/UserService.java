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


    public static User findUserById(int userId) {
        for (User user : UserRepository.getUsersFromFile()) {
            if (user.getUserId() == userId) {
                return user;
            }
        } return null;
    }

    public static User findByFullName(String fullName) {
        for (User user : UserRepository.getUsersFromFile()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(Scanner scanner) {
        System.out.println("Hvad er brugerens fulde navn?");
        String fullName = scanner.nextLine();

        System.out.println("Hvad er brugerens e-mail adresse?");
        String email = scanner.nextLine();

        LocalDate birthDay;

        while (true) {
            try {
                System.out.println("angiv fødselsår for brugeren:");
                int birthyear = Integer.parseInt(scanner.nextLine());

                if (birthyear > LocalDate.now().getYear() || birthyear < 1900) {
                    System.out.println("Ugyldigt fødselsår! prøv igen");
                    continue;
                }

                System.out.println("Angiv fødselsmåned for brugeren (1-12):");
                int birthMonth = Integer.parseInt(scanner.nextLine());

                if (birthMonth < 1 || birthMonth > 12) {
                    System.out.println("Ugyldigt fødselsmåned! prøv igen");
                    continue;
                }

                int maxDaysInMonth = YearMonth.of(birthyear, birthMonth).lengthOfMonth();

                System.out.println("Angiv fødselsdato for brugeren:");
                int birthDate = Integer.parseInt(scanner.nextLine());

                if (birthDate < 1 || birthDate > maxDaysInMonth) {
                    System.out.println("Ugyldig fødselsdato! prøv igen");
                    continue;
                }

                birthDay = LocalDate.of(birthyear, birthMonth, birthDate);
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
                int input = Integer.parseInt(scanner.nextLine());

                switch (input) {
                    case 1:
                        admin = false;
                        break;

                        case 2: admin = true;
                            break;
                            
                            default:
                }
            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt input! prøv igen");
            }
            break;
        }

        System.out.println("Angiv adgangskode hos brugren");
        String password = scanner.nextLine();

        int userId = UserRepository.getUsersFromFile().getLast().getUserId() + 1;
        double initialSaldo = 100000.0;
        LocalDate createdDate = LocalDate.now();
        LocalDate lastUpdated = LocalDate.now();

        User user = new User(userId, fullName, email, birthDay, initialSaldo, createdDate,lastUpdated, admin, password);
        UserRepository.addUserToFile(user);
        System.out.println(userId + " " + fullName + " " + email + " " + birthDay + " " + initialSaldo + " " + createdDate + " " + lastUpdated + " " + admin + " " + password);
        System.out.println(user.getFullName() + " er blevet tilføjet til programmet");
    }

    public static double findUserBalance(User user) {
        List<Transaction> userTransactions = TransactionService.findAllTransactionsForUser(user);

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

package service;

import objects.Transaction;
import objects.User;
import repository.UserRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserService {

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

        birthDay = chooseBirthDay(scanner);
        if (birthDay == null) return;

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

        System.out.println("Angiv adgangskode hos brugeren");
        System.out.println("> 0. Returner til hovedmenu");

        String password = scanner.nextLine();
        if (password.equals("0")) {
            return;
        }

        int userId = UserRepository.getUsersFromFile().getLast().getUserId() + 1;
        double initialSaldo = 100000.0;

        LocalDate createdDate = LocalDate.now();

        LocalDate lastUpdated = LocalDate.now();

        User user = new User(userId, fullName, email, birthDay, initialSaldo, createdDate, lastUpdated, admin, password);
        UserRepository.addUserToFile(user);
        System.out.println(user.getFullName() + " er blevet tilføjet til programmet");
    }

    public static void deleteUser(Scanner scanner, User user) {
        System.out.println("Indtast navnet på den bruger du vil fjerne fra systemet:");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();

        if (input.equals("0")) return;
        User userToDelete = UserService.findUserByFullName(input);

        if (userToDelete == null) return;

        int idOfUserToDelete = userToDelete.getUserId();

        if (idOfUserToDelete == user.getUserId()) {
            System.out.println("Kan ikke slette den bruger du er logget ind med!");
        } else {
            UserRepository.removeUserFromFile(idOfUserToDelete);
        }
    }

    public static void editUser(Scanner scanner, User user) {
        boolean editing = true;
        while (editing) {
            System.out.println("Hvad vil du redigere:");
            System.out.println("> 1. Navn");
            System.out.println("> 2. Email");
            System.out.println("> 3. Fødselsdato");
            System.out.println("> 4. Adgangskode");
            System.out.println("> 0. Gem og afslut");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Indtast nyt navn:");
                    String newName = scanner.nextLine();
                    if (newName.equals(user.getFullName())) {
                        System.out.println("Du kan ikke bruge det samme navn");
                    } else {
                        user.setFullName(scanner.nextLine());
                    }
                    break;
                case 2:
                    System.out.println("Indtast ny email:");
                    String newEmail = scanner.nextLine();
                    if (newEmail.equals(user.getEmail())) {
                        System.out.println("Du kan ikke bruge den samme email");
                    } else {
                        user.setEmail(newEmail);
                    }
                    break;
                case 3:
                    System.out.println("Indtast ny fødselsdag:");
                    LocalDate newBirthDay = chooseBirthDay(scanner);
                    if (newBirthDay == null) break;

                    user.setBirthDate(newBirthDay);
                    break;
                case 4:
                    System.out.println("Indtast ny adgangskode:");
                    String newPassword = scanner.nextLine();
                    if (newPassword.equals(user.getPassword())) {
                        System.out.println("Du kan ikke bruge samme adgangskode");
                    } else {
                        user.setPassword(newPassword);
                    }
                    break;
                case 5:
                    editing = false;
                    user.setLastUpdated(LocalDate.now());
                    UserRepository.updateUserInFile(user);
                    System.out.println("Bruger opdateret");
                    break;
                default:
                    System.out.println("Ugyldigt input! Prøv igen");
            }
        }
    }

    public static LocalDate chooseBirthDay(Scanner scanner) {
        LocalDate birthDay;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            try {
                // År
                System.out.println("Angiv fødselsår for brugeren:");
                System.out.println("> 0. Returner til hovedmenu");

                String inputYear = scanner.nextLine();
                if (inputYear.equals("0")) return null;
                int birthyear = Integer.parseInt(inputYear);

                if (birthyear > LocalDate.now().getYear() || birthyear < 1900) {
                    System.out.println("Ugyldigt fødselsår! Prøv igen");
                    continue;
                }

                // Måned
                System.out.println("Angiv fødselsmåned for brugeren (1-12):");
                System.out.println("> 0. Returner til hovedmenu");

                String inputMonth = scanner.nextLine();
                if (inputMonth.equals("0")) return null;
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
                if (inputDate.equals("0")) return null;
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
        return birthDay;
    }

    public static double findUserBalance(User user) {
        List<Transaction> userTransactions = TransactionService.findTransactionsForUser(user);

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

    public static void userRanking(User user) {
        System.out.println("Top 5 brugere i programmet");
        List<Transaction> userTransactions = TransactionService.findTransactionsForUser(user);
        Map<String, Double> latestPrices = new HashMap<>();
        Map<String, LocalDate> latestDates = new HashMap<>();

        for (Transaction transaction : userTransactions) {
            if (!latestPrices.containsKey(transaction.getTicker()) || transaction.getDate().isAfter(latestDates.get(transaction.getTicker()))) {
                latestPrices.put(transaction.getTicker(), transaction.getPrice());
                latestDates.put(transaction.getTicker(), transaction.getDate());
            }
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
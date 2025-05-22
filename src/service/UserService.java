package service;

import models.Transaction;
import models.User;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

public final class UserService {
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    public UserService(UserRepository userRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    public void displayPortfolioValueOfAllUsers() {
        System.out.println("-*- Porteføljeværdi oversigt -*-");
        System.out.printf("%-6s %-30s %-30s %19s\n", "Plads", "Navn", "Email", "Porteføljeværdi");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        Map<User, Double> portfolioValueMap = new HashMap<>();

        for (User user : userRepository.getUsersFromFile()) {
            double userPortfolioValue = transactionService.findPortfolioValueOfUser(user);
            portfolioValueMap.put(user, userPortfolioValue);
        }

        List<Map.Entry<User, Double>> sortedPortfolioValues = new ArrayList<>(portfolioValueMap.entrySet());
        sortedPortfolioValues.sort(Map.Entry.<User, Double>comparingByValue().reversed());

        for (int i = 0; i < sortedPortfolioValues.size(); i++) {
            Map.Entry<User, Double> entry = sortedPortfolioValues.get(i);
            User user = entry.getKey();
            Double portfolioValue = entry.getValue();

            System.out.printf("%-6d. %-30s %-30s %15.2f DKK\n", i + 1, user.getFullName(), user.getEmail(), portfolioValue);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-*- Porteføljeværdi oversigt -*-");
    }

    public void displayLeaderboard() {
        System.out.println("-*- Rangliste for Afkast -*-");
        System.out.printf("%-6s %-30s %-30s %19s %14s\n",
                "Plads", "Navn", "Email", "Gevinst", "Afkast");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        // Create a map to store User objects and their returns
        Map<User, Double> userReturnsMap = new HashMap<>();

        // Populate the map with all users and their returns
        for (User user : userRepository.getUsersFromFile()) {
            double userReturn = getReturnOfUser(user);
            userReturnsMap.put(user, userReturn);
        }

        // Sort users by return value (descending order)
        List<Map.Entry<User, Double>> sortedReturns = new ArrayList<>(userReturnsMap.entrySet());
        sortedReturns.sort(Map.Entry.<User, Double>comparingByValue().reversed());

        for (int i = 0; i < sortedReturns.size(); i++) {
            Map.Entry<User, Double> entry = sortedReturns.get(i);
            User user = entry.getKey();
            Double returnValue = entry.getValue() ;

            System.out.printf("%-6d. %-30s %-30s %15.2f DKK %14.2f%%\n",
                    i + 1,
                    user.getFullName(),
                    user.getEmail(),
                    transactionService.findUserBalance(user) - user.getInitialCashDKK(),
                    returnValue
            );
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-*- Rangliste -*-");
    }

    public void displayPortfolioOfUser(User user) {
        transactionService.displayPortfolioOfUser(user);
    }

    public double getReturnOfUser(User user) {
        return transactionService.findReturnOfUser(user);
    }

    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    public void updateUserName(User user, String newName) {
        user.setFullName(newName);
    }

    public void updateUserEmail(User user, String newEmail) {
        user.setEmail(newEmail);
    }

    public void updateUserBirthDate(User user, LocalDate newBirthDate) {
        user.setBirthDate(newBirthDate);
    }

    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    public void makeAdmin(User user) {
        user.setAdmin(true);
    }

    public void removeAdmin(User user) {
        user.setAdmin(false);
    }

    public void saveUser(User user) {
        userRepository.updateUserInFile(user);
    }

    public void createUser(String fullName, String email, LocalDate birthDay, boolean admin, String password) {
        int userId = userRepository.getUsersFromFile().getLast().getUserId() + 1;
        double initialSaldo = 100000.0;
        LocalDate createdDate = LocalDate.now();
        LocalDate lastUpdated = LocalDate.now();

        User user = new User(userId, fullName, email, birthDay, initialSaldo, createdDate, lastUpdated, admin, password);
        userRepository.addUserToFile(user);
    }

    public void deleteUser(int userIdToDelete, int currentUserId) {
        if (userIdToDelete == currentUserId) {
            System.out.println("Kan ikke slette den bruger du er logget ind på");
        }

        User userToDelete = findUserById(userIdToDelete);
        if (userToDelete == null) {
            System.out.println("Bruger ikke fundet");
        }

        userRepository.removeUserFromFile(userIdToDelete);
    }

    public List<Transaction> getAllTransactions(User user) {
        return transactionService.findTransactionsForUser(user);
    }

    public double userBalance(User user) {
        return transactionService.findUserBalance(user);
    }

    public boolean emailChecker(String email) {
        for (User user : userRepository.getUsersFromFile()) {
            if (email.equals(user.getEmail())) {
                return false;
            }
        }
        return true;
    }
    
    public User findUserById(int userId) {
        for (User user : userRepository.getUsersFromFile()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User findUserByFullName(String fullName) {
        for (User user : userRepository.getUsersFromFile()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }

    public User findUserByEmail(String email) {
        for (User user : userRepository.getUsersFromFile()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

}
package service;

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

    public void displayTop5UserReturns() {
        System.out.println("-*- Top 5 afkast -*-");
        System.out.printf("%15s %15s %15s %15s %15s\n", "Plads", "Navn", "Email", "Gevinst", "Afkastprocent");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        // 1. Maria Jensen          mariajensen@gmail.com        10000 DKK          75%

        Map<User, Double> userReturns = new HashMap<>();

        List<Double> userReturns = new ArrayList<>();


        // Map<String, Integer> userPortfolio = new HashMap<>();
        //
        //        List<Transaction> userTransactions = findTransactionsForUser(user);
        //
        //        if (userTransactions.isEmpty()) {
        //            return userPortfolio;
        //        }
        //
        //        for (Transaction transaction : userTransactions) {
        //            String ticker = transaction.getTicker();
        //            int quantity = transaction.getQuantity();
        //
        //            String orderType = transaction.getOrderType();
        //            if (orderType.equals("buy")) {
        //                userPortfolio.put(ticker, userPortfolio.getOrDefault(ticker, 0) + quantity);
        //            } else if (orderType.equals("sell")) {
        //                userPortfolio.put(ticker, userPortfolio.getOrDefault(ticker, 0) - quantity);
        //            }
        //
        //            if (userPortfolio.get(ticker) == 0) {
        //                userPortfolio.remove(ticker);
        //            }
        //        }
        //        return userPortfolio;
        //    }

        for (User user : userRepository.getUsersFromFile()) {
            userReturns.add(getReturnOfUser(user));
        }

        userReturns.sort(Comparator.reverseOrder());

        for (int i = 0; i < Math.min(5, userReturns.size()); i++) {
            System.out.println((i + 1) + ":" + userReturns.get(i) + userReturns);
        }



        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("-*- Top 5 afkast -*-");
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
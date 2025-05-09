package service;

import objects.Stock;
import objects.Transaction;
import objects.User;
import repository.StockRepository;
import repository.TransactionRepository;

import java.util.*;

public class TransactionService {

    public static void createTransaction(Scanner scanner, User user) {
        System.out.println("> 1. Køb Aktier ");
        System.out.println("> 2. Sælg Aktier");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();
        switch (input) {
            case "1": // Køb aktie
                StockService.showAllStocks();
                buyStock(scanner, user);
                break;
            case "2": // Sælg aktie
                showPortfolio(user);
                sellStock(scanner, user);
                break;

            case "0": // Gå tilbage til hovedmenu
                return;

            default:
                System.out.println("Ugyldigt input! Prøv igen");
                break;
        }
        createTransaction(scanner, user);
    }

    // Køb eller sælg?

    // Hvilken aktie?

    // Hvor mange


    // Transaction transaction = new Transaction()
    // List<Transaction> transactions = TransactionRepository.readTransactionFile();

    // Genererer nyt ID
    //  int newId = TransactionRepository.readTransactionFile().isEmpty() ? 1 :
    //  transactions.getLast().getId() + 1;
    // transaction.setId(newId);

    // transactions.add(transaction);
    // TransactionRepository.addTransactionToFile(transaction);

    // Opdater brugerbalance
    // double amount = transaction.getPrice() * transaction.getQuantity();
    // UserService.updateUserBalance(transaction.getUserId(), amount, transaction.getOrderType());

    public static void buyStock(Scanner scanner, User user) {
        System.out.println("Hvilken stock vil du købe");
        System.out.println("Indtast ticker på stock");

        String tickerInput = scanner.nextLine();
        Stock stock = StockService.findByTicker(tickerInput);
        if (stock == null) {
            System.out.println("Denne aktie findes ikke");
        }
        System.out.println("Hvor mange af " + stock.getName() + " vil du købe?");
        int amountInput = Integer.parseInt(scanner.nextLine());



    }


    public static void sellStock(Scanner scanner, User user) {
        String sellInput = scanner.nextLine();

        switch (sellInput) {
            case "1":

                break;
            case "2":

                break;

            case "0":
                break;
        }

    }

    public static Transaction findTransactionById(int id) {
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public static List<Transaction> findAllTransactionsForUser(User user) {
        int userId = user.getUserId();

        List<Transaction> transactionsForUser = new ArrayList<>();
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getUserId() == userId) {
                transactionsForUser.add(transaction);
            }
        }
        return transactionsForUser;
    }

    public static List<Transaction> findTransactionsWithSameTicker(List<Transaction> transactions, String ticker) {
        List<Transaction> sameTickerTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getTicker().equals(ticker)) {
                sameTickerTransactions.add(transaction);
            }
        }
        return sameTickerTransactions;
    }


    public static void showPortfolio(User user) {
        // Retrieve all transactions for the user
        List<Transaction> userTransactions = findAllTransactionsForUser(user);

        // If there are no transactions, return early
        if (userTransactions.isEmpty()) {
            System.out.println("No transactions found for the user.");
            return;
        }

        // Map to hold aggregated portfolio data
        // Key: Ticker, Value: Net Quantity
        Map<String, Integer> portfolio = new HashMap<>();

        // Iterate over user transactions
        for (Transaction transaction : userTransactions) {
            String ticker = transaction.getTicker(); // e.g., NOVO-B
            int quantity = transaction.getQuantity(); // Quantity of the transaction

            // Get the order type: buy or sell
            String orderType = transaction.getOrderType();

            // Update portfolio based on order type
            if (orderType.equals("buy")) {
                portfolio.put(ticker, portfolio.getOrDefault(ticker, 0) + quantity);
            } else if (orderType.equals("sell")) {
                portfolio.put(ticker, portfolio.getOrDefault(ticker, 0) - quantity);
            }
        }

        // Display the portfolio
        System.out.println("Portfolio for " + user.getFullName() + ":");
        double totalValue = 0.0;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String ticker = entry.getKey();
            int netQuantity = entry.getValue();

            // Only display stocks with a positive quantity
            if (netQuantity > 0) {
                double value = StockService.findByTicker(ticker).getPrice() * netQuantity;
                totalValue += value;
                System.out.println("Ticker: " + ticker + ", Quantity: " + netQuantity);
            }
        }
        System.out.println("Total Værdi: " + totalValue);
    }
}
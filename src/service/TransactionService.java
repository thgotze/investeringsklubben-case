package service;

import objects.Stock;
import objects.Transaction;
import objects.User;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionService {

    public static void createTransaction(Scanner scanner, User user, Stock stock) {
        
        Transaction transaction = new Transaction()
        List<Transaction> transactions = TransactionRepository.readTransactionFile();

        // Genererer nyt ID
        int newId = TransactionRepository.readTransactionFile().isEmpty() ? 1 :
                transactions.getLast().getId() + 1;
        transaction.setId(newId);

        transactions.add(transaction);
        TransactionRepository.addTransactionToFile(transaction);

        // Opdater brugerbalance
        double amount = transaction.getPrice() * transaction.getQuantity();
        UserService.updateUserBalance(transaction.getUserId(), amount, transaction.getOrderType());
    }

    public static Transaction findById(int id) {
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public static List<Transaction> findAllByUserId(int userId) {
        List<Transaction> transactionsByUserId = new ArrayList<>();
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getUserId() == userId) {
                transactionsByUserId.add(transaction);
            }
        }
        return transactionsByUserId;
    }
}

    //public static void showPortfolio(User user) {
    //        List<Transaction> userTransactions = findAllByUserId(user.getUserId());
    //        double totalvalue = 0.0;
    //
    //        System.out.println("\n--- DIN PORTEFØLJE ---");
    //        System.out.printf("%-10s %-10s %-10s %-10s%n", "Aktie", "Antal", "Pris", "Værdi");
    //
    //        List<String> uniqueTickers = new ArrayList<>();
    //        for (Transaction transaction : userTransactions) {
    //            Stock stock = StockService.findByTicker(transaction.getTicker());
    //            double currentvalue = stock.getPrice() * transaction.getQuantity();
    //            totalvalue += currentvalue;
    //
    //
    //        }
    //
    //    }





    // // Valider input
    //    if (stock == null || user == null || currency == null ||
    //        !(orderType.equalsIgnoreCase("BUY") && !(orderType.equalsIgnoreCase("SELL"))) {
    //        throw new IllegalArgumentException("Ugyldigt transaktionsinput");
    //    }
    //
    //    // Beregn transaktionsværdi
    //    double transactionAmount = stock.getPrice() * quantity;
    //    double exchangeRate = currency.getRate(); // Antager at rate er fra currency til basisvaluta
    //
    //    // Konverter til brugerens valuta hvis nødvendigt
    //    if (!stock.getCurrency().equals(currency.getBaseCurrency())) {
    //        transactionAmount *= exchangeRate;
    //    }
    //
    //    // Tjek brugerens saldo ved køb
    //    if (orderType.equalsIgnoreCase("BUY") && user.getBalance() < transactionAmount) {
    //        throw new IllegalStateException("Utilstrækkelig saldo");
    //    }
    //
    //    // Opret transaktion
    //    int transactionId = TransactionRepository.getNextId();
    //    Transaction transaction = new Transaction(
    //        transactionId,
    //        user.getUserId(),
    //        LocalDateTime.now(),
    //        stock.getTickerName(),
    //        stock.getPrice(),
    //        currency.getBaseCurrency(), // Gem basisvalutaen
    //        orderType.toUpperCase(),
    //        quantity
    //    );
    //
    //    // Opdater brugerens saldo
    //    if (orderType.equalsIgnoreCase("BUY")) {
    //        user.setBalance(user.getBalance() - transactionAmount);
    //    } else { // SELL
    //        user.setBalance(user.getBalance() + transactionAmount);
    //    }
    //
    //    // Gem transaktion
    //    TransactionRepository.saveTransaction(transaction);
    //    UserRepository.updateUser(user); // Antager at du har en måde at gemme brugeren på
    //
    //    return transaction;
    //

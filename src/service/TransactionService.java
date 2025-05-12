package service;

import objects.Currency;
import objects.Stock;
import objects.Transaction;
import objects.User;
import repository.CurrencyRepository;
import repository.StockRepository;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.util.*;

public class TransactionService {

    public static void buyStock(Scanner scanner, User user) {
        System.out.println("Hvilken stock vil du købe?");
        System.out.println("Indtast ticker på stock");

        String tickerInput = scanner.nextLine();
        Stock stock = StockRepository.findStockByTicker(tickerInput);
        if (stock == null) {
            System.out.println("Denne aktie findes ikke");
            return;
        }
        List<Transaction> transactions = TransactionRepository.readTransactionFile();

        int transactionId = transactions.getLast().getId() + 1;
        int userId = user.getUserId();
        String tickerName = stock.getTickerName();
        double price = stock.getPrice();
        String orderType = "buy";
        Currency currency = CurrencyRepository.findByBaseCurrency("DKK"); // TODO: THOR ER I GANG MED DET HER SHIT

        System.out.println("Hvor mange af " + stock.getName() + " vil du købe?");
        int amountInput = Integer.parseInt(scanner.nextLine());

        if (stock.getPrice() * amountInput > user.getInitialCashDKK()) {
            System.out.println("Du har ikke råd til at købe " + amountInput + " x " + stock.getName() + " fordi din balance er " + user.getInitialCashDKK() + " og det  ville koste " + amountInput * stock.getPrice());
        }

        Transaction transaction = new Transaction(transactionId, userId, LocalDate.now(), tickerName, price, currency, orderType, amountInput);
        TransactionRepository.addTransactionToFile(transaction);

        System.out.println("Du har nu købt: " + amountInput + " x " + stock.getName());

        // UserRepository.updateUser TODO: THOR Opdater brugerbalance
    }

    public static void sellStock(Scanner scanner, User user) {
        System.out.println("Hvilken stock vil du sælge?");
        System.out.println("Indtast ticker på stock");

        List<Transaction> transactionsForUser = TransactionRepository.findTransactionsForUser(user);

        String tickerInput = scanner.nextLine();

        // TODO: THOR OG SEBASTIAN LAVER DET HER
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

    public static Map<String, Integer> getPortfolioForUser(User user) {
        Map<String, Integer> userPortfolio = new HashMap<>();

        List<Transaction> userTransactions = TransactionRepository.findTransactionsForUser(user);

        if (userTransactions.isEmpty()) {
            return userPortfolio;
        }

        for (Transaction transaction : userTransactions) {
            String ticker = transaction.getTicker();
            int quantity = transaction.getQuantity();

            String orderType = transaction.getOrderType();
            if (orderType.equals("buy")) {
                userPortfolio.put(ticker, userPortfolio.getOrDefault(ticker, 0) + quantity);
            } else if (orderType.equals("sell")) {
                userPortfolio.put(ticker, userPortfolio.getOrDefault(ticker, 0) - quantity);
            }

            if (userPortfolio.get(ticker) == 0) {
                userPortfolio.remove(ticker);
            }
        }
        return userPortfolio;
    }

    public static void displayPortfolioOfUser(Map<String, Integer> portfolio, User user) {
        System.out.println(" -*- " + user.getFullName() + "'s  Portefølje -*-");
        System.out.printf("%-9s %-21s %10s %10s \n", "Ticker", "Navn", "Antal", "Værdi");
        System.out.println("--------------------------------------------------------");

        double totalValue = 0.0;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String ticker = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = StockRepository.findStockByTicker(ticker); // TODO FIX

            double subtotal = stock.getPrice() * quantity;
            totalValue += subtotal;
            System.out.printf("%-9s %-21s %10d %10.2f\n", ticker, stock.getName(), quantity, subtotal);
        }
        System.out.println("Total Værdi: " + totalValue);
        System.out.println(" -*- " + user.getFullName() + "'s  Portefølje -*-");
    }
}
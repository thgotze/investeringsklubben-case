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
        Stock stock = StockService.findStockByTicker(tickerInput);
        if (stock == null) {
            System.out.println("Denne aktie findes ikke");
            return;
        }

        System.out.println("Hvor mange " + stock.getName() + " aktier vil du købe?");
        int amountToBuy = Integer.parseInt(scanner.nextLine());

        if (stock.getPrice() * amountToBuy > user.getInitialCashDKK()) {
            System.out.println("Du har ikke råd til at købe " + amountToBuy + " x " + stock.getName() + "("+ (amountToBuy * stock.getPrice())
                    + " " + stock.getCurrency() + " samlet)" + " - din saldo er kun " + user.getInitialCashDKK());
            return;
        }

        addTransaction(user, stock, "buy", amountToBuy);
    }

    public static void sellStock(Scanner scanner, User user) {
        Map<String, Integer> portfolio = TransactionService.getPortfolioForUser(user);

        System.out.println("Hvilken stock vil du sælge?");
        System.out.println("Indtast ticker på stock");

        String selectedTicker = null;
        while (true) {
            String tickerInput = scanner.nextLine();
            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(tickerInput)) {
                    selectedTicker = tickerInput.toUpperCase();
                    break;
                }
            }

            if (selectedTicker != null) {
                break;
            } else {
                System.out.println("Du ejer ikke denne aktie blablabla debug besked");
            }
        }

        Stock stock = StockService.findStockByTicker(selectedTicker);
        System.out.println("Hvor mange " + stock.getName() + " vil du sælge?");
        int ownedAmount = portfolio.get(selectedTicker);
        int amountToSell = 0;
        while (true) {
            String sellAmount = scanner.nextLine();

            try {
                amountToSell = Integer.parseInt(sellAmount);

                if (amountToSell <= 0) {
                    System.out.println("Du skal sælge mindst 1 aktie.");
                } else if (amountToSell > ownedAmount) {
                    System.out.println("Du ejer ikke så mange. Prøv igen.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt tal. Prøv igen.");
            }
        }
        addTransaction(user, stock, "sell", amountToSell);
    }

    private static void addTransaction(User user, Stock stock, String orderType, int quantity) {
        List<Transaction> transactions = TransactionRepository.readTransactionFile();

        int transactionId = transactions.getLast().getId() + 1;
        int userId = user.getUserId();
        LocalDate date = LocalDate.now();
        String tickerName = stock.getTickerName();
        double price = stock.getPrice();
        Currency currency = CurrencyService.findByBaseCurrency("DKK");

        Transaction transaction = new Transaction(transactionId, userId, date, tickerName, price, currency, orderType, quantity);
        TransactionRepository.addTransactionToFile(transaction);
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

        List<Transaction> userTransactions = TransactionService.findTransactionsForUser(user);

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

    public static void displayPortfolioOfUser(User user) {
        Map<String, Integer> portfolio = getPortfolioForUser(user);
        if (portfolio.isEmpty()) {
            System.out.println("Du har ingen aktier!");
            return;
        }
        System.out.println(" -*- " + user.getFullName() + "'s Portefølje -*-");
        System.out.printf("%-9s %-21s %10s %10s \n", "Ticker", "Navn", "Antal", "Værdi");
        System.out.println("---------------------------------------------------------");

        double totalValue = 0.0;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String ticker = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = StockService.findStockByTicker(ticker); // TODO FIX

            double subtotal = stock.getPrice() * quantity;
            totalValue += subtotal;
            System.out.printf("%-9s %-21s %10d %10.2f\n", ticker, stock.getName(), quantity, subtotal, "DKK");
        }
        System.out.println("Total Værdi: " + totalValue);
        System.out.println(" -*- " + user.getFullName() + "'s  Portefølje -*-");
    }

    public static Transaction findTransactionById(int id) {
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    public static List<Transaction> findTransactionsForUser(User user) {
        int userId = user.getUserId();

        List<Transaction> transactionsForUser = new ArrayList<>();
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            if (transaction.getUserId() == userId) {
                transactionsForUser.add(transaction);
            }
        }
        return transactionsForUser;
    }
}
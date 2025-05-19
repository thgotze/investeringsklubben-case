package service;

import objects.Currency;
import objects.Stock;
import objects.Transaction;
import objects.User;
import repository.TransactionRepository;
import util.Utilities;

import java.time.LocalDate;
import java.util.*;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CurrencyService currencyService;
    private final StockService stockService;

    public TransactionService(TransactionRepository transactionRepository, CurrencyService currencyService,
                              StockService stockService) {
        this.transactionRepository = transactionRepository;
        this.currencyService = currencyService;
        this.stockService = stockService;
    }

    public void showAllStock() {
        stockService.showAllStocks();
    }

        // HALLA DEN ER LAVET der mangler bare noget finpudsning


    public void userRanking(User user) {
        List<Transaction> userTransactions = findAllTransactionsForUsers();



        Map<String, Double> latestPrices = new HashMap<>();
        Map<String, LocalDate> latestDates = new HashMap<>();

        // nyeste pris for hver aktie med dato
        for (Transaction transaction : userTransactions) {
            if (!latestPrices.containsKey(transaction.getTicker()) || transaction.getDate().isAfter(latestDates.get(transaction.getTicker()))) {
                latestPrices.put(transaction.getTicker(), transaction.getPrice());
                latestDates.put(transaction.getTicker(), transaction.getDate());
            }
        }

        Map<Integer, Map<String, AvgPrice>> userTickerBuys = new HashMap<>();

        for (Transaction t : userTransactions) {
            if (!t.getOrderType().equalsIgnoreCase("BUY")) continue;

            userTickerBuys
                    .computeIfAbsent(t.getUserId(), k -> new HashMap<>())
                    .computeIfAbsent(t.getTicker(), k -> new AvgPrice())
                    .add(t.getPrice(), t.getQuantity());
        }
        Map<String, Double> userGains = new HashMap<>();

        for (int userId : userTickerBuys.keySet()) {
            double totalGain = 0;
            Map<String, AvgPrice> tickers = userTickerBuys.get(userId);

            for (String ticker : tickers.keySet()) {
                AvgPrice avg = tickers.get(ticker);
                double avgBuyPrice = avg.getAvg();
                Double latestPrice = latestPrices.get(ticker);

                if (latestPrice != null) {
                    double diff = latestPrice - avgBuyPrice;
                    totalGain += diff;
                }
            }

            userGains.put(String.valueOf(userId), totalGain);

        }
        // TODO: OMREGN OGSÅ TIL PROCENT
        System.out.println("Top 5 brugere med højest positiv ændring i pris:");
        userGains.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.printf("User %s: %.2f kr. afkast\n", entry.getKey(), entry.getValue()));

    }


    public void buyStock(User user, Stock stock, int quantity) {
        double totalPrice = stock.getPrice() * quantity;

        if (totalPrice > findUserBalance(user)) {
            System.out.println("Du har ikke nok på din saldo");
            return;
        }

        int transactionId = transactionRepository.readTransactionFile().size() + 1;

        System.out.println("Du har købt " + quantity + " x " + stock.getName() + ".");
        Transaction transaction = new Transaction(transactionId, user.getUserId(), LocalDate.now(), stock.getTickerName(), stock.getPrice(), stock.getCurrency(), "buy", quantity);
        transactionRepository.addTransactionToFile(transaction);
        displayUserBalance(user);
    }

    public void sellStock(User user, Stock stock, int quantity) {
        int transactionId = transactionRepository.readTransactionFile().size() + 1;

        System.out.println("Du har solgt " + quantity + " x " + stock.getName() + ".");
        Transaction transaction = new Transaction(transactionId, user.getUserId(), LocalDate.now(), stock.getTickerName(), stock.getPrice(), stock.getCurrency(), "sell", quantity);
        transactionRepository.addTransactionToFile(transaction);
        displayUserBalance(user);
    }

    public List<Transaction> findTransactionsWithSameTicker(List<Transaction> transactions, String ticker) {
        List<Transaction> sameTickerTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getTicker().equals(ticker)) {
                sameTickerTransactions.add(transaction);
            }
        }
        return sameTickerTransactions;
    }

    public Map<String, Integer> getPortfolioForUser(User user) {
        Map<String, Integer> userPortfolio = new HashMap<>();

        List<Transaction> userTransactions = findTransactionsForUser(user);

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

    public void displayPortfolioOfUser(User user) {
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
            Stock stock = stockService.findStockByTicker(ticker);

            double subtotal = stock.getPrice() * quantity;
            totalValue += subtotal;

            System.out.printf("%-9s %-21s %10d %10.2f %10.2f\n",
                    ticker, stock.getName(), quantity, stock.getPrice(), subtotal);
        }

        System.out.println("Total Værdi: " + totalValue + " DKK");
        System.out.println(" -*- " + user.getFullName() + "'s  Portefølje -*-");
    }

    public Transaction findTransactionById(int id) {
        for (Transaction transaction : transactionRepository.readTransactionFile()) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        return null;
    }

    // LAV EN FIND ALL TRANSACTION UDEN AT CHECKE MED SPECIFIKT USER
    public List<Transaction> findAllTransactionsForUsers() {

        List<Transaction> transactionsForUser = new ArrayList<>();
        for (Transaction transaction : transactionRepository.readTransactionFile()) {

            transactionsForUser.add(transaction);

        }
        return transactionsForUser;
    }

    public List<Transaction> findTransactionsForUser(User user) {
        int userId = user.getUserId();

        List<Transaction> transactionsForUser = new ArrayList<>();
        for (Transaction transaction : transactionRepository.readTransactionFile()) {
            if (transaction.getUserId() == userId) {
                transactionsForUser.add(transaction);
            }
        }
        return transactionsForUser;
    }

    public void displayUserBalance(User user) {
        double balance = findUserBalance(user);
        System.out.printf("Saldo for %s: %.2f DKK\n", user.getFullName(), balance);
    }

    public double findUserBalance(User user) {
        List<Transaction> userTransactions = findTransactionsForUser(user);

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
package service;

import models.Stock;
import models.Transaction;
import models.User;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final StockService stockService;

    public TransactionService(TransactionRepository transactionRepository, StockService stockService) {
        this.transactionRepository = transactionRepository;
        this.stockService = stockService;
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

    public void getAllStocks() {
        stockService.showAllStocks();
    }

    public Stock getStockByTicker(String tickerInput) {
        Stock stock = stockService.findStockByTicker(tickerInput);
        if (stock == null) {
            System.out.println("Denne aktie findes ikke");
            return null;
        }
        return stock;
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

    public List<Stock> getStocksBySectors(String sector) {
        List<Stock> stockBySector = stockService.findAllStocksBySector(sector);

        return stockBySector;
    }

    public double getReturnOfUser(User user) {
        return (findUserBalance(user) / user.getInitialCashDKK() - 1) * 100;
    }
}
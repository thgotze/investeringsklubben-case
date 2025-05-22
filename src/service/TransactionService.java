package service;

import models.Currency;
import models.Stock;
import models.Transaction;
import models.User;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TransactionService {
    private final TransactionRepository transactionRepository;
    private final StockService stockService;
    private final CurrencyService currencyService;

    public TransactionService(TransactionRepository transactionRepository, StockService stockService, CurrencyService currencyService) {
        this.transactionRepository = transactionRepository;
        this.stockService = stockService;
        this.currencyService = currencyService;
    }

    public Currency findByBaseCurrency(String string) {
        return currencyService.findByBaseCurrency(string);
    }

    public void processStockPurchase(User user, Stock stock, int quantity) {
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

    public double findPortfolioValueOfUser(User user) {
        Map<String, Integer> portfolio = findPortfolioForUser(user);
        double totalValue = 0.0;

        if (portfolio.isEmpty()) return totalValue;

        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String ticker = entry.getKey();
            int quantity = entry.getValue();
            Stock stock = stockService.findStockByTicker(ticker);
            if (stock == null) break;

            double subtotal = stock.getPrice() * quantity;
            totalValue += subtotal;
        }
        return totalValue;
    }

    public Map<String, Integer> findPortfolioForUser(User user) {
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
        Map<String, Integer> portfolio = findPortfolioForUser(user);
        if (portfolio.isEmpty()) {
            System.out.println("Du har ingen aktier!");
            return;
        }
        System.out.println(" -*- " + user.getFullName() + "'s Portefølje -*-");
        System.out.printf("%-8s %-25s %-8s %-10s %-8s %-18s\n", "Ticker", "Navn", "Antal", "Pris", "Valuta", "Samlet Værdi");
        System.out.println("---------------------------------------------------------------------------");

        double totalValue = 0.0;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String ticker = entry.getKey();
            int quantity = entry.getValue();

            Stock stock = stockService.findStockByTicker(ticker);
            if (stock == null) return;
            Currency currency = currencyService.findByBaseCurrency(stock.getCurrency());
            if (currency == null) return;

            double subtotal = currency.getRate() * stock.getPrice() * quantity;
            totalValue += subtotal;

            System.out.printf("%-8s %-25s %-8d %-10.2f %-8s %-18.2f\n",
                    ticker, stock.getName(), quantity, stock.getPrice(), stock.getCurrency(), quantity * currency.getRate() * stock.getPrice());
        }

        System.out.println("Total Værdi: " + totalValue + " DKK");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println(" -*- " + user.getFullName() + "'s  Portefølje -*-");
    }

    public void findAllStocks() {
        stockService.displayAllStocks();
    }

    public Stock findStockByTicker(String tickerInput) {
        return stockService.findStockByTicker(tickerInput);
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

    public List<Stock> findStocksBySectors(String sector) {
        return stockService.findAllStocksBySector(sector);
    }

    public double findReturnOfUser(User user) {
        return ((findUserBalance(user) + findPortfolioValueOfUser(user)) / user.getInitialCashDKK() - 1) * 100;
    }
}
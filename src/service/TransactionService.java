package service;

import objects.Currency;
import objects.Stock;
import objects.Transaction;
import objects.User;
import repository.CurrencyRepository;
import repository.StockRepository;
import repository.TransactionRepository;
import util.Utilities;

import java.time.LocalDate;
import java.util.*;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CurrencyService currencyService;
    private final StockService stockService;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, CurrencyService currencyService,
                              StockService stockService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.currencyService = currencyService;
        this.stockService = stockService;
        this.userService = userService;
    }

    public void userRanking(User user) {
        // Brugeren skal bruge sin procentvise stigning på deres aktier både når de har solgt en aktie og købt holdt en aktie
        // altså så den viser hvor meget brugeren har tjent og den procentvise stigning eller faldning
        List<Transaction> userTransactions = findTransactionsForUser(user);

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
        // VED IK OM DEN HER SEKTION NEDEUNDER VIRKER
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

//            userGains.put(userId, totalGain);
        }
        // TODO: OMREGN OGSÅ TIL PROCENT
        System.out.println("Top 5 brugere med højest positiv ændring i pris:");
        userGains.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.printf("User %s: %.2f kr. gevinst\n", entry.getKey(), entry.getValue()));
    }

    public void buyStock(Scanner scanner, User user) {
        System.out.println("Hvilken aktie vil du købe?");
        System.out.println("Indtast navnet på den ønskede aktie ");
        String tickerInput = scanner.nextLine();

        Stock stock = stockService.findStockByTicker(tickerInput);
        if (stock == null) {
            System.out.println("Denne aktie findes ikke");
            return;
        }

        System.out.println("Hvor mange " + stock.getName() + " aktier vil du købe?");
        int amountToBuy = Integer.parseInt(scanner.nextLine());

        double totalPrice = stock.getPrice() * amountToBuy;

        if (stock.getPrice() * amountToBuy > user.getInitialCashDKK()) {
            System.out.println("Du har ikke råd til at købe " + amountToBuy + " x " + stock.getName() + "(" + (amountToBuy * stock.getPrice())
                    + " " + stock.getCurrency() + " samlet)" + " - din saldo er kun " + user.getInitialCashDKK());
            return;
        }

        System.out.println("Bekræft køb af: " + amountToBuy + "x " + stock.getName() + "for " + totalPrice + " " + stock.getCurrency() + ".");
        System.out.println("Er du sikker? (Ja/nej): ");
        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("ja")) {
            System.out.println("Handlen blev annulleret.");
            return;
        }
        addTransaction(user, stock, "buy", amountToBuy);
        System.out.println("Du har købt " + amountToBuy + " x " + stock.getName() + ".");
        double updatedUserBalance = findUserBalance(user);
        System.out.println("Din saldo er nu: %.2f DKK\n" + updatedUserBalance);
    }

    public void sellStock(Scanner scanner, User user) {
        Map<String, Integer> portfolio = getPortfolioForUser(user);

        System.out.println("Hvilken aktie vil du sælge?");
        System.out.println("Indtast navnet på den ønskede aktie");

        String selectedTicker = null;
        while (true) {
            String tickerInput = scanner.nextLine();
            boolean stockFound = false;

            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(tickerInput)) {
                    selectedTicker = tickerInput.toUpperCase();
                    System.out.println("Du har valgt at sælge " + selectedTicker);
                    System.out.println("Er du sikker (ja/nej)? ");

                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("ja")) {
                        stockFound = true;
                        break;
                    } else if (confirmation.equalsIgnoreCase("nej")) {
                        selectedTicker = null;
                        System.out.println("Vælg en anden aktie...");
                        break;
                    } else {
                        Utilities.displayInvalidInputMessage();
                        selectedTicker = null;
                        break;
                    }
                }
            }

            if (!stockFound && selectedTicker == null) {
                System.out.println("Du ejer ikke denne aktie. Prøv igen eller tryk enter for at afbryde");
            }
            if (stockFound) {
                break;
            }
        }

        Stock stock = stockService.findStockByTicker(selectedTicker);
        System.out.println("Hvor mange " + stock.getName() + " vil du sælge?");

        int ownedAmount = portfolio.get(selectedTicker);
        int amountToSell = 0;
        double totalPrice = 0.0;

        while (true) {
            String sellAmount = scanner.nextLine();

            try {
                amountToSell = Integer.parseInt(sellAmount);

                if (amountToSell <= 0) {
                    System.out.println("Du skal sælge mindst 1 aktie.");
                } else if (amountToSell > ownedAmount) {
                    System.out.println("Du ejer ikke så mange. Prøv igen.");
                } else {
                    totalPrice = stock.getPrice() * amountToSell;
                    System.out.println("Bekræft salg af: " + amountToSell + " x " + stock.getName() + " til " + totalPrice + stock.getCurrency() + "? (ja/nej)");

                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("ja")) {
                        break;
                    } else if (confirmation.equalsIgnoreCase("nej")) {
                        System.out.println("Handlen blev afbrudt.");
                    } else {
                        Utilities.displayInvalidInputMessage();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt tal. Prøv igen.");
            }
        }
        addTransaction(user, stock, "sell", amountToSell);
        System.out.println("Du har solgt " + amountToSell + " x " + stock + " for " + totalPrice + stock.getCurrency());
        double updatedUserBalance = findUserBalance(user);
        System.out.println("Din saldo er nu: %.2f DKK\n" + updatedUserBalance);
    }

    private void addTransaction(User user, Stock stock, String orderType, int quantity) {
        List<Transaction> transactions = transactionRepository.readTransactionFile();

        int transactionId = transactions.getLast().getId() + 1;
        int userId = user.getUserId();
        LocalDate date = LocalDate.now();
        String tickerName = stock.getTickerName();
        double price = stock.getPrice();
        Currency currency = currencyService.findByBaseCurrency("DKK");

        Transaction transaction = new Transaction(transactionId, userId, date, tickerName, price, currency, orderType, quantity);
        transactionRepository.addTransactionToFile(transaction);
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
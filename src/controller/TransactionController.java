package controller;

import models.Stock;
import models.User;
import service.StockService;
import service.TransactionService;
import util.MessagePrinter;

import java.util.Map;
import java.util.Scanner;

public class TransactionController {
    private final TransactionService transactionService;
    private final StockService stockService;
    private final Scanner scanner;

    public TransactionController(TransactionService transactionService, StockService stockService, Scanner scanner) {
        this.transactionService = transactionService;
        this.stockService = stockService;
        this.scanner = scanner;
    }

    public void showTransactionMenu(User user) {
        System.out.println("> 1. Køb aktier");
        System.out.println("> 2. Sælg aktier");
        System.out.println("> 3. Vis alle aktier");
        System.out.println("> 4. Søg efter aktier");
        System.out.println("> 0. Returner til hovedmenu");

        while (true) {
            String input = scanner.nextLine();
            switch (input) {
                case "1": // Køb aktier
                    buyStock(user);
                    break;

                case "2": // Sælg aktier
                    sellStock(user);
                    break;

                case "3": // Vis alle aktier
                    stockService.showAllStocks();
                    break;

                case "4": // Søg efter aktier
                    // TODO
                    break;

                case "0": // Returner til hovedmenu
                    return;

                default:
                    MessagePrinter.printInvalidInputMessage();
                    break;
            }
        }
    }

    private void buyStock(User user) {
        System.out.println("Hvilken aktie vil du købe?");
        System.out.println("Indtast navnet på den ønskede aktie ");
        String tickerInput = scanner.nextLine();

            Stock stock = stockService.findStockByTicker(input);
            if (stock == null) {
                System.out.println("Denne aktie findes ikke");
                return;
            }

        System.out.println("Hvor mange " + stock.getName() + " aktier vil du købe?");
        int amountToBuy = Integer.parseInt(scanner.nextLine());

        double totalPrice = stock.getPrice() * amountToBuy;

            if (stock.getPrice() * amountToBuy > user.getInitialCashDKK()) {
                System.out.println("Du har ikke råd til at købe " + amountToBuy + " x " + stock.getName() + "(" + (amountToBuy * stock.getPrice()) + " " + stock.getCurrency() + " samlet)" + " - din saldo er kun " + user.getInitialCashDKK());
                return;
            }

            System.out.println("Er du sikker på at du vil købe: " + amountToBuy + "x " + stock.getName() + "for " + totalPrice + " " + stock.getCurrency());
            System.out.println("> 1. Ja");
            System.out.println("> 2. Nej");
            String confirmation = scanner.nextLine();

            if (confirmation.equals("2")) {
                System.out.println("Handlen blev annulleret.");
                return;
            }

            try {
                transactionService.buyStock(user, stock, amountToBuy);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void sellStock(User user) {
        Map<String, Integer> portfolio = transactionService.getPortfolioForUser(user);

        System.out.println("Hvilken aktie vil du sælge?");
        System.out.println("Indtast navnet på den ønskede aktie");
        System.out.println("> 0. Returner til menu");

        String selectedTicker = null;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("0")) return;

            boolean stockFound = false;

            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(input)) {
                    selectedTicker = input.toUpperCase();
                    System.out.println("Vil du gerne sælge " + selectedTicker);
                    System.out.println("> 1. Ja");
                    System.out.println("> 2. Nej");

                    String confirmation = scanner.nextLine();
                    if (confirmation.equalsIgnoreCase("1")) {
                        stockFound = true;
                        break;
                    } else if (confirmation.equalsIgnoreCase("2")) {
                        selectedTicker = null;
                        System.out.println("Vælg en anden aktie...");
                        break;
                    } else {
                        MessagePrinter.printInvalidInputMessage();
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
                        MessagePrinter.printInvalidInputMessage();
                    }
                }
            } catch (NumberFormatException e) {
                MessagePrinter.printInvalidInputMessage();
            }
        }
        transactionService.sellStock(user, stock, amountToSell);
    }
}
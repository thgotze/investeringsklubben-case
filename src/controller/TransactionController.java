package controller;

import objects.Stock;
import objects.User;
import service.CurrencyService;
import service.StockService;
import service.TransactionService;

import java.util.Scanner;

public class TransactionController {
    private final TransactionService transactionService;
    private final StockService stockService;
    private final CurrencyService currencyService;
    private final Scanner scanner;

    public TransactionController(TransactionService transactionService, StockService stockService, CurrencyService currencyService, Scanner scanner) {
        this.transactionService = transactionService;
        this.stockService = stockService;
        this.currencyService = currencyService;
        this.scanner = scanner;
    }

    public void showTransactionMenu(User user) {
        System.out.println("> 1. Køb Aktier ");
        System.out.println("> 2. Sælg Aktier");
        System.out.println("> 3. Vis alle Aktier");
        System.out.println("> 4. Søg efter Aktier");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();
        switch (input) {
            case "1": // Køb aktie
                // stockService.showAllStocks();
                // transactionService.buyStock(scanner, user);
                break;

            case "2": // Sælg aktie
                if (transactionService.getPortfolioForUser(user).isEmpty()) {
                    System.out.println("Kan ikke sælge aktier da dit portefølje er tomt!");
                } else {
                    transactionService.displayPortfolioOfUser(user);
                    // transactionService.sellStock(scanner, user);
                    return;
                }
                break;

            case "0": // Gå tilbage til hovedmenu
                return;

            default:
                System.out.println("Ugyldigt input! Prøv igen");
                break;
        }
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

        System.out.println("Er du sikker på at du vil købe: " + amountToBuy + "x " + stock.getName() + "for " + totalPrice + " " + stock.getCurrency());
        System.out.println("> 1. Ja");
        System.out.println("> 2. Nej");
        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("ja")) {
            System.out.println("Handlen blev annulleret.");
            return;
        }
    }
}
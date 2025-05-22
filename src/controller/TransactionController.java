package controller;

import models.Currency;
import models.Stock;
import models.User;
import service.TransactionService;
import util.MessagePrinter;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class TransactionController {
    private final TransactionService transactionService;
    private final Scanner scanner;

    public TransactionController(TransactionService transactionService, Scanner scanner) {
        this.transactionService = transactionService;
        this.scanner = scanner;
    }

    public void openTransactionMenu(User user) {
        System.out.println("> 1. Køb aktier");
        System.out.println("> 2. Sælg aktier");
        System.out.println("> 3. Vis alle aktier");
        System.out.println("> 4. Søg efter aktier");
        System.out.println("> 0. Returner til hovedmenu");

        while (true) {
            String input = scanner.nextLine();
            switch (input) {
                case "1": // Køb aktier
                    openBuyStockMenu(user);
                    return;

                case "2": // Sælg aktier
                    openSellStockMenu(user);
                    return;

                case "3": // Vis alle aktier
                    transactionService.findAllStocks();
                    return;

                case "4": // Søg efter aktier
                    openSearchStockBySectorMenu();
                    return;

                case "0": // Returner til hovedmenu
                    return;

                default:
                    MessagePrinter.printInvalidInputMessage();
                    break;
            }
        }
    }

    private void openBuyStockMenu(User user) {
        System.out.println("Hvilken aktie vil du købe?");
        System.out.println("Indtast navnet på den ønskede aktie");
        System.out.println("> 0. Returner til hovedmenu");

        Stock stock;
        while (true) {
            String tickerInput = scanner.nextLine();

            if (tickerInput.equals("0"))
                return;

            stock = transactionService.findStockByTicker(tickerInput);
            if (stock == null) {
                System.out.println("Denne aktie findes ikke");
            } else {
                break;
            }
        }
        System.out.println("Hvor mange " + stock.getName() + " aktier vil du købe?");
        System.out.println("> 0. Returner til hovedmenu");

        int amountToBuy;
        while (true) {
            String amountToBuyInput = scanner.nextLine();

            if (amountToBuyInput.equals("0"))
                return;

            try {
                amountToBuy = Integer.parseInt(amountToBuyInput);
                if (amountToBuy <= 0) {
                    System.out.println("Du skal købe mindst 1 aktie.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                MessagePrinter.printInvalidInputMessage();
            }
        }

        Currency currency = transactionService.findByBaseCurrency(stock.getCurrency());
        System.out.println("Er du sikker på at du vil købe: " + amountToBuy + "x " + stock.getName() + " for " + stock.getPrice() * currency.getRate() * amountToBuy + " DKK?");
        System.out.println("> 1. Ja");
        System.out.println("> 0. Nej");

        String confirmation = scanner.nextLine();
        if (confirmation.equals("0")) {
            System.out.println("Handlen blev annulleret.");
            return;
        }
        transactionService.processStockPurchase(user, stock, amountToBuy);
    }


    //        if (transactionService.findUserBalance()) {
    //            System.out.println("Du har ikke råd til at købe " + amountToBuy + " x " + stock.getName() + "(" + (amountToBuy * stock.getPrice()) + " " + stock.getCurrency() + " samlet)" + " - din saldo er kun " + user.getInitialCashDKK());
    //            return;
    //        }
    //        try {
    //            transactionService.buyStock(user, stock, amountToBuy);
    //            return;
    //        } catch (IllegalArgumentException e) {
    //            System.out.println(e.getMessage());
    //        }

    private void openSellStockMenu(User user) {
        Map<String, Integer> portfolio = transactionService.findPortfolioForUser(user);

        System.out.println("Hvilken aktie vil du sælge?");
        System.out.println("Indtast navnet på den ønskede aktie");
        System.out.println("> 0. Returner til hovedmenu");

        String selectedTicker = null;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("0"))
                return;

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

            if (!stockFound) {
                System.out.println("Du ejer ikke denne aktie. Prøv igen eller tryk enter for at afbryde");
            }
            if (stockFound) {
                break;
            }
        }

        Stock stock = transactionService.findStockByTicker(selectedTicker);
        System.out.println("Hvor mange " + stock.getName() + " vil du sælge?");

        int ownedAmount = portfolio.get(selectedTicker);
        int amountToSell;
        double totalPrice;

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

    public void openSearchStockBySectorMenu() {
        System.out.println("Vælg en sektor blandt følgende: ");
        System.out.println("> 1. Health Care ");
        System.out.println("> 2. Industrials ");
        System.out.println("> 3. Consumer Goods ");
        System.out.println("> 4. Financials ");
        System.out.println("> 5. Energy ");
        System.out.println("> 6. Utilities");
        System.out.println("> 7. Technology");

        int choice = Integer.parseInt(scanner.nextLine());

        String sector = switch (choice) {
            case 1 -> "Health Care";
            case 2 -> "Industrials";
            case 3 -> "Consumer Goods";
            case 4 -> "Financials";
            case 5 -> "Energy";
            case 6 -> "Utilities";
            case 7 -> "Technology";
            default -> null;
        };

        if (sector == null) {
            MessagePrinter.printInvalidInputMessage();
            return;
        }

        List<Stock> stocks = transactionService.findStocksBySectors(sector);
        if (stocks.isEmpty()) {
            System.out.println("Ingen aktier fundet i sektoren: " + sector);
        } else {
            System.out.println("Aktier i sektoren '" + sector + "':");
            for (Stock stock : stocks) {
                System.out.println("- " + stock.getName() + " (" + stock.getTickerName() + ")");
            }
        }
    }
}
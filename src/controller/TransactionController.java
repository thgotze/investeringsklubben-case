package controller;

import objects.User;
import service.StockService;
import service.TransactionService;

import java.util.Map;
import java.util.Scanner;

public class TransactionController {

    public static void showTransactionMenu(Scanner scanner, User user) {
        System.out.println("> 1. Køb Aktier ");
        System.out.println("> 2. Sælg Aktier");
        System.out.println("> 3. Vis alle Aktier");
        System.out.println("> 4. Søg efter Aktier");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();
        switch (input) {
            case "1": // Køb aktie
                StockService.showAllStocks();
                TransactionService.buyStock(scanner, user);
                break;

            case "2": // Sælg aktie
                if (TransactionService.getPortfolioForUser(user).isEmpty()) {
                    System.out.println("Kan ikke sælge aktier da dit portefølje er tomt!");
                } else {
                    TransactionService.displayPortfolioOfUser(user);
                    TransactionService.sellStock(scanner, user);
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
}

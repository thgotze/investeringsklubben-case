package controller;

import models.User;
import service.CurrencyService;
import service.StockService;
import service.TransactionService;
import util.MessagePrinter;

import java.util.Scanner;

public class AppManager {
    private final UserController userController;
    private final TransactionController transactionController;
    private final CurrencyService currencyService;
    private final StockService stockService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final Scanner scanner;


    public AppManager(UserController userController, TransactionController transactionController, CurrencyService currencyService, StockService stockService, TransactionService transactionService, UserService userService, Scanner scanner) {
        this.userController = userController;
        this.transactionController = transactionController;
        this.currencyService = currencyService;
        this.stockService = stockService;
        this.transactionService = transactionService;
        this.userService = userService;
        this.scanner = scanner;
    }

    public void startProgram() {
        while (true) {
            User user = userController.logIn();
            if (user == null) return;

            System.out.println("\n-*-*- ThorNet -*-*-");
            System.out.println("> 1. Aktiemarked");
            System.out.println("> 2. Aktiehandel");
            System.out.println("> 3. Valutakurser");
            System.out.println("> 4. Min konto");

            if (user.isAdmin()) {
                System.out.println("> 5. Statistik");
                System.out.println("> 6. Rediger medlemmer");
            }
            System.out.println("> 0. Log ud");

            while (true) {
                String input = scanner.nextLine();
                switch (input) {
                    case "1": // Aktiemarked
                        stockService.showAllStocks();
                        break;

                    case "2": // Aktiehandel
                        transactionController.showTransactionMenu(user);
                        break;

                    case "3": // Valutakurser
                        currencyService.showAllCurrencies();
                        break;

                    case "4": // Min konto
                        printMyAccountMenu(user);
                        break;

                    case "5": // Statistik
                        printStatisticsMenu();
                        break;

                    case "6": // Rediger medlemmer
                        userController.adminEditUserMenu(user);
                        break;

                    case "0": // Log ud
                        System.out.println("Bruger " + user.getFullName() + " er nu logget ud\n");
                        break;

                    default:
                        MessagePrinter.printInvalidInputMessage();
                }
                if (input.equals("0")) {
                    break;
                }
            }
        }
    }

    private void printMyAccountMenu(User user) {
        System.out.println("\n-*-*- " + user.getFullName() + " -*-*-");
        if (user.isAdmin()) {
            System.out.println("Admin");
        }
        System.out.println("BrugerID: " + user.getUserId());
        System.out.println("Email: " + user.getEmail());
        System.out.printf("Saldo: %.2f DKK\n", transactionService.findUserBalance(user));

        System.out.println("> 1. Mit portefølje");
        System.out.println("> 2. Rediger oplysninger");
        System.out.println("> 3. Transaktionshistorik");
    }

    private void printStatisticsMenu() {
        System.out.println("\n-*-*- Statistik Menu -*-*-");
        System.out.println("> 1. Se brugernes porteføljeværdi");
        System.out.println("> 2. Se rangliste");
        System.out.println("> 3. Se fordeling af aktier & sektorer");
    }
}
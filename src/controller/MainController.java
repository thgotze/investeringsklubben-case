package controller;

import objects.User;
import service.CurrencyService;
import service.StockService;
import service.TransactionService;
import service.UserService;
import util.Utilities;

import java.util.Scanner;

public class MainController {

    public static void startProgram() {
        Scanner scanner = new Scanner(System.in);
        User user = UserController.logIn(scanner);
        showMainMenu(scanner, user);
    }

    private static void showMainMenu(Scanner scanner, User user) {
        while (true) {
            printMainMenu(user);
            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Afslutter program...");
                    System.exit(0);

                case "1":
                    StockService.showAllStocks();
                    break;

                case "2":
                    CurrencyService.showAllCurrencies();
                    break;

                case "3":
                    TransactionService.displayPortfolioOfUser(user);
                    break;

                case "4":
                    TransactionController.showTransactionMenu(scanner, user);
                    break;

                case "5":
                    System.out.println("Ikke implementeret endnu!");
                    break;

                case "99":
                    Utilities.showUserBalance(user);
                    break;

                case "98":
                    // UserService.editUser(scanner, user);
                    break;

                case "6": // Adminfunktion: se brugernes porteføljeværdi
                    if (!Utilities.hasAdminAccess(user)) {
                        Utilities.displayInvalidInputMessage();
                        break;
                    }
                    System.out.println("Ikke implementeret endnu!");
                    break;

                case "7": // Adminfunktion: se rangliste
                    if (!Utilities.hasAdminAccess(user)) {
                        Utilities.displayInvalidInputMessage();
                        break;
                    }
                    System.out.println("Ikke implementeret endnu!");
                    break;

                case "8": // vis sektor bs
                    if (user.isAdmin()) {
                        StockService.showSectorDistribution();
                    } else {
                        Utilities.displayInvalidInputMessage();
                    }
                    break;

                case "9":
                    if (user.isAdmin()) {
                        UserController.adminEditUserMenu(scanner, user);
                    } else {
                        Utilities.displayInvalidInputMessage();
                    }
                    break;
            }
        }
    }

    private static void printMainMenu(User user) {
        System.out.println("\n    -*-*- ThorNet -*-*-");
        System.out.println("> 1. Se aktiemarkedet");
        System.out.println("> 2. Se valutakurser");
        System.out.println("> 3. Se min portefølje");
        System.out.println("> 4. Køb/Sælg aktier");
        System.out.println("> 5. Se tidligere handler");
        System.out.println("> 99. Se min saldo");
        System.out.println("> 98. Rediger kontooplysninger");

        if (user.isAdmin()) {
            System.out.println("> 6. Se brugernes porteføljeværdi");
            System.out.println("> 7. Se rangliste");
            System.out.println("> 8. Se fordeling af aktier & sektorer");
            System.out.println("> 9. Rediger bruger");
        }

        System.out.println("> 0. Afslut program");
    }
}
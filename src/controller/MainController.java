package controller;

import objects.User;
import service.CurrencyService;
import service.StockService;
import service.TransactionService;
import service.UserService;

import java.util.Map;
import java.util.Scanner;

public class MainController {

    public static void startProgram() {
        Scanner scanner = new Scanner(System.in);
        User user = UserController.logIn(scanner);
        showMainMenu(scanner, user);
    }

    private static void showMainMenu(Scanner scanner, User user) {
        System.out.println("    -*-*- ThorNet -*-*-      ");
        System.out.println("> 1. Se aktiemarkedet");
        System.out.println("> 2. Se kurs for valutaer");
        System.out.println("> 3. Se min portefølje");
        System.out.println("> 4. Køb/Sælg aktier");
        System.out.println("> 5. Se tidligere handler");
        System.out.println("> 99. Se min saldo");
        System.out.println("> 98. Rediger kontooplysninger");


        if (user.isAdmin()) {
            System.out.println("> 6. Se brugernes porteføljeværdi");
            System.out.println("> 7. Se rangliste");
            System.out.println("> 8. Se fordeling af aktier & sektorer");
            System.out.println("> 9. Rediger brugere");
        }
        System.out.println("> 0. Afslut program");

        String input = scanner.nextLine();
        switch (input) {
            case "0": // Afslut program
                System.out.println("Afslutter program...");
                System.exit(0);
                return;

            case "1": // Se aktiemarkedet & Kurs
                StockService.showAllStocks();
                break;

            case "2": // Se Valutakurser
                CurrencyService.showAllCurrencies();
                break;

            case "3": // Se min portefølje
                Map<String, Integer> portfolio = TransactionService.getPortfolioForUser(user);
                if (portfolio.isEmpty()) {
                    System.out.println(user.getFullName() + "'s portefølje er tom");
                } else {
                    TransactionService.displayPortfolioOfUser(portfolio, user);
                }
                break;

            case "4": // Køb/Sælg aktier
                TransactionController.showTransactionMenu(scanner, user);
                break;

            case "5": // Se tidligere handler
                System.out.println("Ikke implementeret endnu!");
                break;

            case "99": // Se saldo
                double userBalance = UserService.findUserBalance(user);
                System.out.println(userBalance);
                break;

            case "98":
                UserService.editUser(scanner, user);
                break;

            case "6": // Se brugernes porteføljeværdi
                System.out.println("Ikke implementeret endnu!");
                if (!user.isAdmin()) {
                    break;
                }
                break;

            case "7": // Se rangliste
                System.out.println("Ikke implementeret endnu!");
                if (!user.isAdmin()) {
                    break;
                }
                break;
                // TODO: OLIVER COOKER RANGLISTE

            case "8": // Se fordeling af aktier & sektorer
                System.out.println("Ikke implementeret endnu!");
                if (!user.isAdmin()) {
                    break;
                    // TODO OLIVER PRØVER AT COOK FORDELING
                }
                break;

            case "9": // Tilføj bruger
                if (user.isAdmin()) {
                    UserController.adminEditUserMenu(scanner, user);
                    break;
                }
                break;

            default:
                System.out.println("Ugyldigt input! Prøv igen");
                break;
        }
        showMainMenu(scanner, user);
    }
}
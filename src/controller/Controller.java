package controller;

import objects.Stock;
import objects.Transaction;
import objects.User;
import service.StockService;
import service.TransactionService;
import service.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class Controller {

    public static void startProgram() {
        Scanner scanner = new Scanner(System.in);

        User user = UserService.logIn(scanner);
        showMainMenu(scanner, user);
    }

    private static void showMainMenu(Scanner scanner, User user) {
        System.out.println("    -*-*- THORNET -*-*-      ");
        System.out.println("> 1. Se aktiemarkedet & Kurs");
        System.out.println("> 2. Se min portefølje");
        System.out.println("> 3. Køb/Sælg aktier");
        System.out.println("> 4. Se tidligere handler");
        System.out.println("> 99. Se min saldo");

        if (user.isAdmin()) {
            System.out.println("> 5. Se brugernes porteføljeværdi");
            System.out.println("> 6. Se rangliste");
            System.out.println("> 7. Se fordeling af aktier & sektorer");
            System.out.println("> 8. Tilføj bruger");
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

            case "2": // Se min portefølje
                TransactionService.showPortfolio(user);
                break;

            case "3": // Køb/Sælg aktier
                TransactionService.createTransaction(scanner, user);
                break;

            case "4": // Se tidligere handler
                System.out.println("Ikke implementeret endnu!");

                break;

            case "99": // TODO: TESTING Se min saldo
                double userBalance = UserService.findUserBalance(user);
                System.out.println(userBalance);
                break;

            case "5": // Se brugernes porteføljeværdi
                if (!user.isAdmin()) {
                    break;
                }
                System.out.println("Ugyldigt input! Prøv igen");
                break;

            case "6": // Se rangliste
                if (!user.isAdmin()) {
                    break;
                }

            case "7": // Se foredeling af aktier & sektorer
                if (!user.isAdmin()) {
                    break;
                }
                System.out.println("Ugyldigt input! Prøv igen");
                break;

            case "8":
                if (user.isAdmin()) {
                    UserService.addUser(scanner);
                    break;
                }
                System.out.println("Ugyldigt input! Prøv igen");
                break;

            default:
                System.out.println("Ugyldigt input! Prøv igen");
                break;
        }
        showMainMenu(scanner, user);
    }
}
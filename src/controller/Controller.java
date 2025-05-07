package controller;

import objects.User;
import org.w3c.dom.ls.LSOutput;
import service.UserService;

import java.util.Scanner;

public class Controller {

    public static void logIn(Scanner scanner) {
        while (true) {
            System.out.println("Indtast fulde navn:");
            String name = scanner.nextLine();
            User user = UserService.findByFullName(name);
            if (user == null) {
                System.out.println("Bruger ikke fundet, prøv igen! ");
            } else {
                System.out.println("Indtast adgangskode: ");
                String password = scanner.nextLine();

                if (password.equals(user.getPassword())) {
                    showMenu(user, scanner);
                    break;
                } else {
                    System.out.println("Forkert adgangskode! Prøv igen.");
                }
            }
        }
    }

    public static void showMenu(User user, Scanner scanner) {
        while (true) {
            System.out.println("> 1. køb aktier");
            if (user.isAdmin()) {
                System.out.println("> 2. Sælg aktier");
            }

            userChoice(scanner);
        }
    }

    public static void userChoice(Scanner scanner) {
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                System.out.println("Nu køber du aktier");

                break;

            case 2:
                System.out.println("Nu sælger du aktier");
        }
    }
}
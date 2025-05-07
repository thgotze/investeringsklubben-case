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

    public static void showMenu() {
        System.out.println("           -*-*- THORNET -*-*-");
        System.out.println("> 1. Se aktiemarkedet & Kurs");
        System.out.println("> 2. Se min portefølje");
        System.out.println("> 3. Køb/Sælg aktier ");
        System.out.println("> 4. Se tidligere handler ");
        System.out.println("> 0. Afslut program");


//        For admin:
//        System.out.println("> 5. Se brugernes porteføljeværdi");
//        System.out.println("> 6. Se rangliste");
//        System.out.println("> 7. Se fordeling af aktier & sektorer");
//        System.out.println("> 0. Afslut program");



            userChoice(scanner, user);
        }
    }

    public static void userChoice(Scanner scanner, User user) {
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                System.out.println("Nu køber du aktier");

                break;

            case 2:
                if (user.isAdmin()) {
                    System.out.println("Nu sælger du aktier");
                } else {
                    System.out.println("Ugyldigt input");
                }
                break;

            default:
                System.out.println("Ugyldigt input");
        }
    }
}
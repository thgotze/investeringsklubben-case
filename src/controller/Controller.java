package controller;

import objects.User;
import service.UserService;

import java.util.Scanner;

public class Controller {

    public static void logIn() {
        Scanner scanner = new Scanner(System.in);

        User user;
        String name;
        String password;

        while (true) {
            System.out.println("Indtast fulde navn:");
            name = scanner.nextLine();
            user = UserService.findByFullName(name);
            if (user == null) {
                System.out.println("Bruger ikke fundet, prøv igen! ");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Indtast adgangskode: ");
            password = scanner.nextLine();

            if (password.equals(user.getPassword())) {
                showMenu();
                break;
            } else {
                System.out.println("Forkert adgangskode! Prøv igen.");
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



        }

    public static void handleUserChoice(Scanner scanner, User user) {

        int input = scanner.nextInt();
        switch (input) {
            case 1:
                System.out.println("Nu køber du aktier");

                break;

            case 2:
                if (user.isAdmin()) {
                    System.out.println("Nu sælger du aktier");
                }
                break;

            default:
                System.out.println("Ugyldigt input");
        }
    }
}

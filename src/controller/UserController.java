package controller;

import objects.User;
import service.UserService;

import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final Scanner scanner;

    public UserController(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public void printMainMenu(User user) {
        System.out.println("\n    -*-*- ThorNet -*-*-");
        System.out.println("> 1. Se aktiemarkedet");
        System.out.println("> 2. Se valutakurser");
        System.out.println("> 3. Se min portefølje");
        System.out.println("> 4. Køb/Sælg aktier");
        System.out.println("> 5. Se tidligere handler");
        System.out.println("> 6. Se min saldo");
        System.out.println("> 7. Rediger kontooplysninger");
        if (user.isAdmin()) {
            System.out.println("> 8. Se brugernes porteføljeværdi");
            System.out.println("> 9. Se rangliste");
            System.out.println("> 10. Se fordeling af aktier & sektorer");
            System.out.println("> 11. Rediger bruger");
            System.out.println("> 12. Log ud");
        }
        System.out.println("> 0. Afslut program");
    }

    public User logIn() {
        System.out.println("-*-*- Login på ThorNet -*-*-");
        System.out.println("Indtast brugernavn: ");

        User user;
        while (true) {
            String name = scanner.nextLine();
            user = userService.findUserByFullName(name);

            if (user == null) {
                System.out.println("Bruger ikke fundet. Prøv igen");
            } else {
                break;
            }
        }

        System.out.println("Indtast adgangskode: ");
        while (true) {
            String password = scanner.nextLine();
            boolean correctPassword = UserService.validatePassword(user, password);

            if (correctPassword) {
                System.out.println("Velkommen tilbage " + user.getFullName() + "!");
                return user;
            } else {
                System.out.println("Forkert adgangskode. Prøv igen");
            }
        }
    }

    public void adminEditUserMenu(User user) {
        System.out.println("> 1. Tilføj bruger");
        System.out.println("> 2. Fjern bruger");
        System.out.println("> 3. Ændrer brugers admin status");
        System.out.println("> 0. Returnern til menu");

        String input = scanner.nextLine();

        switch (input) {
            case "0":
                return;
            case "1":
                userService.addUser(scanner);
                break;
            case "2":
                userService.deleteUser(scanner, user);
                break;
            case "3":

            default:
                System.out.println("Ugyldigt input! prøv igen");
        }
    }
}
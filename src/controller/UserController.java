package controller;

import objects.User;
import service.UserService;

import java.util.Scanner;

public class UserController {

    public static User logIn(Scanner scanner) {
        System.out.println("-*-*- Login på ThorNet -*-*-");
        System.out.println("Indtast brugernavn: ");

        User user;
        while (true) {
            String name = scanner.nextLine();
            user = UserService.findUserByFullName(name);

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


    public static void adminEditUserMenu (Scanner scanner, User user) {
        System.out.println("> 1. Tilføj bruger");
        System.out.println("> 2. Fjern bruger");
        System.out.println("> 3. Ændrer brugers admin status");
        System.out.println("> 0. Returnern til menu");

        String input = scanner.nextLine();

        switch (input) {
            case "0":
                return;
            case "1":
                UserService.addUser(scanner);
                break;
            case "2":
                UserService.deleteUser(scanner, user);
                break;
            case "3":

            default:
                System.out.println("Ugyldigt input! prøv igen");

        }
    }
}
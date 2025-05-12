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
}
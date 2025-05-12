package controller;

import objects.User;
import repository.UserRepository;

import java.util.Scanner;

public class UserController {

    public static User logIn(Scanner scanner) {
        User user;
        while (true) {
            System.out.println("Indtast fulde navn:");
            String name = scanner.nextLine();
            user = UserRepository.findByFullName(name);
            if (user == null) {
                System.out.println("Bruger ikke fundet. Prøv igen");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.println("Indtast adgangskode: ");
            password = scanner.nextLine();

            if (password.equals(user.getPassword())) {
                break;
            } else {
                System.out.println("Forkert adgangskode. Prøv igen");
            }
        }
        return user;
    }
}

package util;

import objects.User;
import service.UserService;

public class Utilities {

    public static void displayInvalidInputMessage() {
        System.out.println("Ugyldigt input! Prøv igen.");
    }

    public static boolean hasAdminAccess(User user) {
        if (!user.isAdmin()) {
            System.out.println("Adgang nægtet: Denne funktion kræver administratorrettigheder.");
            return false;
        }
        return true;
    }
½
    public static void showUserBalance(User user) {
        double balance = UserService.findUserBalance(user);
        System.out.printf("Saldo for %s: %.2f\n", user.getFullName(), balance);
    }
}
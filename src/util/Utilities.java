package util;

import objects.User;
import service.UserService;

public class Utilities {

    public static void displayInvalidInputMessage() {
        System.out.println("Ugyldigt input! Prøv igen.");
    }

    public static void notAdminMessage() {
        System.out.println("Adgang nægtet: Denne funktion kræver administratorrettigheder.");
    }
}
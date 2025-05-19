package controller;

import models.User;
import service.UserService;
import util.MessagePrinter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class UserController {
    private final UserService userService;
    private final Scanner scanner;

    public UserController(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public User logIn() {
        System.out.println("-*-*- Login på ThorNet -*-*-");
        System.out.println("> 0. Afslut program");
        System.out.println("Indtast email:");

        User user;
        while (true) {
            String email = scanner.nextLine();

            if (email.equals("0")) return null;

            user = userService.findUserByEmail(email);

            if (user == null) {
                System.out.println("Bruger ikke fundet. Prøv igen");
            } else {
                break;
            }
        }

        System.out.println("Indtast adgangskode: ");
        while (true) {
            String password = scanner.nextLine();
            boolean correctPassword = userService.validatePassword(user, password);

            if (correctPassword) {
                System.out.println("Velkommen " + user.getFullName() + "!");
                return user;
            } else {
                System.out.println("Forkert adgangskode. Prøv igen");
            }
        }
    }

    public void openMyAccountMenu(User user) {
        System.out.println("\n-*-*- " + user.getFullName() + " -*-*-");
        if (user.isAdmin()) {
            System.out.println("Admin: true");
        }
        System.out.println("BrugerID: " + user.getUserId());
        System.out.println("Email: " + user.getEmail());
        System.out.printf("Saldo: %.2f DKK\n", userService.userBalance(user));
        System.out.printf("Afkast: %.2f%%\n", userService.getReturnOfUser(user));

        System.out.println("> 1. Mit portefølje");
        System.out.println("> 2. Rediger oplysninger");
        System.out.println("> 3. Transaktionshistorik");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();

        switch (input) {
            case "1": // Mit portefølje
                userService.displayPortfolioOfUser(user);
                break;

            case "2": // Rediger oplysninger

                break;

            case "3": // Transaktionshistorik
                break;

            case "0": // Returner til hovedmenuen
                return;

            default:
                MessagePrinter.printInvalidInputMessage();
        }
    }

    public void openStatisticsMenu() {
        System.out.println("\n-*-*- Statistik Menu -*-*-");
        System.out.println("> 1. Se brugernes porteføljeværdi");
        System.out.println("> 2. Top 5 afkast");
        System.out.println("> 3. Se fordeling af aktier & sektorer");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();
        switch (input) {
            case "1": // Se brugernes porteføljeværdi
                break;

            case "2": // Se top 5 afkast
                userService.displayTop5UserReturns();
                break;

            case "3": // Se fordeling af aktier & sektorer
                break;

            case "0": // Returner til hovedmenuen
                return;

            default:
                MessagePrinter.printInvalidInputMessage();
        }
    }

    public void openAdminEditUserMenu(User user) {
        System.out.println("> 1. Tilføj bruger");
        System.out.println("> 2. Rediger bruger");
        System.out.println("> 3. Slet bruger");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();
        switch (input) {
            case "1": // Tilføj bruger
                addUser(scanner);
                break;

            case "2": // Rediger bruger
                editUser(scanner, user);
                break;

            case "3": // Slet bruger
                handleDeleteUser(scanner, user);
                break;

            case "0": // Returner til hovedmenuen
                return;

            default:
                MessagePrinter.printInvalidInputMessage();

        }
    }

    public void handleDeleteUser(Scanner scanner, User currentUser) {
        System.out.println("Indtast navnet på den bruger du vil fjerne:");
        System.out.println("> 0. Returner til hovedmenu");

        String input = scanner.nextLine();
        if (input.equals("0")) return;

        User userToDelete = userService.findUserByFullName(input);
        if (userToDelete != null) {
            userService.deleteUser(userToDelete.getUserId(), currentUser.getUserId());
        } else {
            System.out.println("Bruger ikke fundet");
        }

    }

    public void addUser(Scanner scanner) {
        System.out.println("Hvad er brugerens fulde navn?");
        System.out.println("> 0. Returner til hovedmenu");
        String fullName = scanner.nextLine();
        if (fullName.equals("0")) return;

        System.out.println("Hvad er brugerens e-mail adresse?");
        System.out.println("> 0. Returner til hovedmenu");
        String email = scanner.nextLine();
        if (email.equals("0")) return;

        boolean newMail = userService.emailChecker(email);
        if (!newMail) {
            return;
        }

        LocalDate birthDay = chooseBirthDay(scanner);
        if (birthDay == null) return;

        boolean admin = false;
        while (true) {
            try {
                System.out.println("Angiv om brugeren er admin, eller normal bruger:");
                System.out.println("> 1. Bruger");
                System.out.println("> 2. Admin");
                System.out.println("> 0. Returner til hovedmenu");
                String inputStr = scanner.nextLine();

                if (inputStr.equals("0")) {
                    return;
                }

                int input = Integer.parseInt(inputStr);

                switch (input) {
                    case 1:
                        break;

                    case 2:
                        admin = true;
                        break;

                    default:
                        MessagePrinter.printInvalidInputMessage();
                }
                break;
            } catch (NumberFormatException e) {
                MessagePrinter.printInvalidInputMessage();
            }
        }

        System.out.println("Angiv adgangskode hos brugeren");
        System.out.println("> 0. Returner til hovedmenu");
        String password = scanner.nextLine();
        if (password.equals("0")) return;

        userService.createUser(fullName, email, birthDay, admin, password);
        System.out.println(fullName + " er blevet tilføjet til programmet");
    }

    public void editUser(Scanner scanner, User user) {
        boolean editing = true;
        while (editing) {
            System.out.println("Hvad vil du redigere:");
            System.out.println("> 1. Navn");
            System.out.println("> 2. Email");
            System.out.println("> 3. Fødselsdato");
            System.out.println("> 4. Adgangskode");
            System.out.println("> 0. Gem og afslut");

            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                MessagePrinter.printInvalidInputMessage();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Indtast nyt navn:");
                    String newName = scanner.nextLine();
                    if (newName.equals(user.getFullName())) {
                        System.out.println("Du kan ikke bruge det samme navn");
                    } else {
                        userService.updateUserName(user, newName);
                    }
                    break;

                case 2:
                    System.out.println("Indtast ny email:");
                    String newEmail = scanner.nextLine();
                    if (newEmail.equals(user.getEmail())) {
                        System.out.println("Du kan ikke bruge den samme email");
                    } else {
                        userService.updateUserEmail(user, newEmail);
                    }
                    break;
                case 3:

                    LocalDate newBirthDate = chooseBirthDay(scanner);
                    try {
                        userService.updateUserBirthDate(user, newBirthDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Ugyldigt datoformat! Prøv igen.");
                    }
                    break;
                case 4:
                    System.out.println("Indtast ny adgangskode:");
                    String newPassword = scanner.nextLine();
                    if (newPassword.equals(user.getPassword())) {
                        System.out.println("Du kan ikke bruge samme adgangskode");
                    } else {
                        userService.updateUserPassword(user, newPassword);
                    }
                    break;
                case 0:
                    editing = false;
                    userService.saveUser(user);
                    System.out.println("Bruger opdateret");
                    break;
                default:
                    MessagePrinter.printInvalidInputMessage();
            }
        }
    }

    public void changeAdminStatus(Scanner scanner, User user) {
        System.out.println("Angiv ID på den bruger du vil ændre admin status på:");
        System.out.println("> 0. Returner til hovedmenu");

        int input = Integer.parseInt(scanner.nextLine());
        if (input == 0) return;

        User userToChange = userService.findUserById(input);
        if (user == userToChange) {
            System.out.println("Kan ikke ændre status på den bruger du er logget ind på!");
            return;
        } else if (userToChange.isAdmin() == false) {
            System.out.println("Denne bruger er ikke en admin! Kunne du tænke dig at gøre brugeren til admin?");
            System.out.println("> 1. Ja");
            System.out.println("> 2. Nej");

            int makeAdmin = Integer.parseInt(scanner.nextLine());
            switch (makeAdmin) {
                case 1:
                    userService.makeAdmin(userToChange);
                    break;
                case 2:
                    return;
                default:
                    MessagePrinter.printInvalidInputMessage();
            }

        } else if (userToChange.isAdmin()) {
            System.out.println("Denne bruger er en admin! Kunne du tænke dig at fjerne brugerens admin status?");
            System.out.println("> 1. Ja");
            System.out.println("> 2. Nej");

            int removeAdmin = Integer.parseInt(scanner.nextLine());
            switch (removeAdmin) {
                case 1:
                    userService.removeAdmin(userToChange);
                    break;
                case 2:
                    return;
                default:
                    MessagePrinter.printInvalidInputMessage();
            }
        }

        userService.saveUser(userToChange);
        System.out.println(userToChange.getFullName() + " admin status has been changed");
    }


    public LocalDate chooseBirthDay(Scanner scanner) {
        LocalDate birthDay;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            try {
                // År
                System.out.println("Angiv fødselsår for brugeren:");
                System.out.println("> 0. Returner til hovedmenu");

                String inputYear = scanner.nextLine();
                if (inputYear.equals("0")) return null;
                int birthyear = Integer.parseInt(inputYear);

                if (birthyear > LocalDate.now().getYear() || birthyear < 1900) {
                    System.out.println("Ugyldigt fødselsår! Prøv igen");
                    continue;
                }

                // Måned
                System.out.println("Angiv fødselsmåned for brugeren (1-12):");
                System.out.println("> 0. Returner til hovedmenu");

                String inputMonth = scanner.nextLine();
                if (inputMonth.equals("0")) return null;
                int birthMonth = Integer.parseInt(inputMonth);

                if (birthMonth < 1 || birthMonth > 12) {
                    System.out.println("Ugyldig måned! Prøv igen");
                    continue;
                }

                // Dag
                int maxDaysInMonth = YearMonth.of(birthyear, birthMonth).lengthOfMonth();

                System.out.println("Angiv fødselsdato for brugeren:");
                System.out.println("> 0. Returner til hovedmenu");

                String inputDate = scanner.nextLine();
                if (inputDate.equals("0")) return null;
                int birthDate = Integer.parseInt(inputDate);

                if (birthDate < 1 || birthDate > maxDaysInMonth) {
                    System.out.println("Ugyldig dato! Prøv igen");
                    continue;
                }

                // Gem Dato
                birthDay = LocalDate.of(birthyear, birthMonth, birthDate);
                String formattedBirthDay = birthDay.format(dateTimeFormatter);
                birthDay = LocalDate.parse(formattedBirthDay, dateTimeFormatter);
                break;

            } catch (NumberFormatException e) {
                MessagePrinter.printInvalidInputMessage();
            }
        }
        return birthDay;
    }

}
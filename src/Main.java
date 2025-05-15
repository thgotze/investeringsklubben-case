import controller.TransactionController;
import controller.UserController;
import objects.Transaction;
import objects.User;
import repository.CurrencyRepository;
import repository.StockRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.CurrencyService;
import service.StockService;
import service.TransactionService;
import service.UserService;
import util.Utilities;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        // TODO: Rykke MainController op i main. main skal starte rep, service og controller og uddelegere arbejde til controllere
        // TODO: (1) Main menuen er alt for overcrowded, vi skal bestemme konkret hvilke valgmuligheder der skal være i den -
        // TODO: (2) fjerne dem der ikke er nødvendige, og merge dem der skal være sammen
        // TODO: Vi skal huske at have mindst et par testmetoder
        // TODO: Vi skal fjerne metoder der ikke bruges
        // TODO: Være sikker på at systemet ikke har mulighed for at crashe - tjekke om der mangler throw exception
        // TODO: Tjekke at vi altid går fra controller -> service -> repository og ikke controller -> repository
        // TODO: Er der nogen 'magic numbers'?

        Scanner scanner = new Scanner(System.in);

        // Repositories
        StockRepository stockRepository = new StockRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        // Services
        StockService stockService = new StockService(stockRepository);
        CurrencyService currencyService = new CurrencyService(currencyRepository);
        UserService userService = new UserService(userRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, currencyService, stockService, userService);

        // Controllers
        UserController userController = new UserController(userService, scanner);
        TransactionController transactionController = new TransactionController(transactionService, stockService, currencyService, scanner);

        User user = userController.logIn();

        while (true) {
            userController.printMainMenu(user);
            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Afslutter program...");
                    System.exit(0);

                case "1":
                    stockService.showAllStocks();
                    break;

                case "2":
                    currencyService.showAllCurrencies();
                    break;

                case "3":
                    transactionService.displayPortfolioOfUser(user);
                    break;

                case "4":
                    transactionController.showTransactionMenu(scanner, user);
                    break;

                case "5":
                    for (Transaction transaction : transactionService.findTransactionsForUser(user)) {
                        System.out.println(transaction);
                    }
                    break;

                case "6":
                    transactionService.displayUserBalance(user);
                    break;

                case "7":
                    userController.editUser(scanner, user);
                    break;

                case "8": // Adminfunktion: se brugernes porteføljeværdi
                    if (!user.isAdmin()) {
                        Utilities.notAdminMessage();
                        break;
                    }
                  transactionService.displayPortfolioOfUser(user);
                    break;

                case "9": // Adminfunktion: se rangliste
                    if (user.isAdmin()) {
                        TransactionService service = new TransactionService(transactionRepository, currencyService, stockService, userService);
                        service.userRanking(user);

                        break;
                    }
                    Utilities.notAdminMessage();
                    break;

                case "10": // vis sektor fordeling
                    if (!user.isAdmin()) {
                        Utilities.notAdminMessage();
                        break;
                    }
                    stockService.showSectorDistribution();
                    break;

                case "11":
                    if (!user.isAdmin()) {
                        Utilities.notAdminMessage();
                        break;
                    }
                    userController.adminEditUserMenu(user);
                    break;

                case "12":
                    userService.logOutOfUser(user);
                    user = userController.logIn();
                    break;
            }
        }
    }
}
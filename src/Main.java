import controller.AppManager;
import controller.TransactionController;
import controller.UserController;
import repository.CurrencyRepository;
import repository.StockRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.CurrencyService;
import service.StockService;
import service.TransactionService;
import service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // TODO: (1) Main menuen er alt for overcrowded, vi skal bestemme konkret hvilke valgmuligheder der skal være i den -
        // TODO: (2) fjerne dem der ikke er nødvendige, og merge dem der skal være sammen
        // TODO: Vi skal huske at have mindst et par testmetoder
        // TODO: Vi skal fjerne metoder der ikke bruges
        // TODO: Være sikker på at systemet ikke har mulighed for at crashe - tjekke om der mangler throw exception
        // TODO: Tjekke at vi altid går fra controller -> service -> repository og ikke controller -> repository
        // TODO: Er der nogen 'magic numbers'?
        // TODO: Nyt class diagram 2.0
        // TODO: UnitTesting

        Scanner scanner = new Scanner(System.in);

        // Repositories
        StockRepository stockRepository = new StockRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        // Services
        StockService stockService = new StockService(stockRepository);
        CurrencyService currencyService = new CurrencyService(currencyRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, stockService);
        UserService userService = new UserService(userRepository);

        // Controllers
        UserController userController = new UserController(userService, scanner);
        TransactionController transactionController = new TransactionController(transactionService, scanner);

        // Manager (MainController)
        AppManager appManager = new AppManager(userController, transactionController, currencyService, stockService, transactionService, userService, scanner);
        appManager.startProgram();
    }
}
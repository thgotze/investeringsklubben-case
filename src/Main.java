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

        // TODO: Troubleshoot programmet
        // TODO: Klassediagram (OLIVER)
        // TODO: UnitTesting (SEBASTIAN)
        // TODO: Afkast (OLIVER)
        // TODO: Slet AvgPrice class

        // TODO: Maria Jensen skal lægges tilbage i .csv filen bagefter

        Scanner scanner = new Scanner(System.in);

        // Repositories
        StockRepository stockRepository = new StockRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        // Services
        StockService stockService = new StockService(stockRepository);
        CurrencyService currencyService = new CurrencyService(currencyRepository);
        TransactionService transactionService = new TransactionService(transactionRepository, stockService, currencyService);
        UserService userService = new UserService(userRepository, transactionService);

        // Controllers
        UserController userController = new UserController(userService, scanner);
        TransactionController transactionController = new TransactionController(transactionService, scanner);

        // Manager (MainController)
        AppManager appManager = new AppManager(userController, transactionController, currencyService, stockService, scanner);
        appManager.startProgram();
    }
}
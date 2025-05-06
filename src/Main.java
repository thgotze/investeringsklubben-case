import Objects.Currency;
import Objects.Stock;
import Objects.Transaction;
import Objects.User;
import repository.CurrencyRepository;
import repository.StockRepository;
import repository.TransactionRepository;
import repository.UserRepository;

public class Main {
    public static void main(String[] args) {


        double a = 1.0;
        double b = 2.0


        System.out.println("Users: ");
        System.out.println("-------------------------");
        for (User user : UserRepository.readUsersFile()) {
            System.out.println(user);
        }

        System.out.println();
        System.out.println("Transactions: ");
        System.out.println("-------------------------");
        for (Transaction transaction : TransactionRepository.readTransactionFile()) {
            System.out.println(transaction);
        }

        System.out.println();
        System.out.println("Currencies:");
        System.out.println("-------------------------");
        for (Currency currency : CurrencyRepository.readCurrencyFile()) {
            System.out.println(currency);
        }

        System.out.println();
        System.out.println("Stocks:");
        System.out.println("-------------------------");
        for (Stock stock : StockRepository.readStockFile()) {
            System.out.println(stock);
        }
    }
}
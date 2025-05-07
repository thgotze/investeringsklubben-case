import Objects.Currency;
import Objects.Stock;
import Objects.Transaction;
import Objects.User;
import repository.CurrencyRepository;
import repository.StockRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import service.StockService;
import service.TransactionService;

public class Main {
    public static void main(String[] args) {

        for (Stock stock : StockRepository.readStockFile()) {
            System.out.println(stock);
        }

    }
}
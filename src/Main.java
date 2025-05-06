import repository.StockRepository;
import repository.TransactionRepository;
import repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Osmans Disciple!");

//        UserRepository.readUserFile();
//        StockRepository.readStockFile();
        TransactionRepository.readTransactionFile();
    }
}
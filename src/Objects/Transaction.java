package Objects;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private User user;
    private LocalDate date;
    private Stock stock;
    private double price;
    private Currency currency;
    private String orderType;
    private int quantity;

    public Transaction(int id, User user, LocalDate date, Stock stock, double price, Currency currency, String orderType, int quantity) {
        
    }
}

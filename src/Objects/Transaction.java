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
        this.id = id;
        this.user = user;
        this.date = date;
        this.stock = stock;
        this.price = price;
        this.currency = currency;
        this.orderType = orderType;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Transaktions ID: " + id + ", Bruger ID: " + user.getUserId() + ", Dato: " + date + ", Ticker: " + stock.getTickerName() + ", Valuta: " + currency.getQuoteCurrency() + ", Ordre type: " + orderType + ", Antal: " + quantity;
    }
}

package objects;

import java.time.LocalDate;

public class Transaction {

    private int id;
    private int userId;
    private LocalDate date;
    private String ticker;
    private double price;
    private String currency;
    private String orderType;
    private int quantity;


    public Transaction(int id, int userId, LocalDate date, String ticker, double price, String currency, String orderType, int quantity) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.ticker = ticker;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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
        return "Transaktions ID: " + id + ", Bruger ID: " + userId + ", Dato: " + date + ", Ticker: " + ticker + ", Valuta: " + currency + ", Ordre type: " + orderType + ", Antal: " + quantity;
    }
}

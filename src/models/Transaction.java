package models;

import java.time.LocalDate;

public class Transaction {

    private final int id;
    private final int userId;
    private final LocalDate date;
    private final String ticker;
    private final double price;
    private final Currency currency;
    private final String orderType;
    private final int quantity;

    // Constructor
    public Transaction(int id, int userId, LocalDate date, String ticker, double price, Currency currency, String orderType, int quantity) {
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

    public int getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getOrderType() {
        return orderType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Transaktions ID: " + id + ", Bruger ID: " + userId + ", Dato: " + date + ", Ticker: " + ticker + ", Valuta: " + currency + ", Ordre type: " + orderType + ", Antal: " + quantity;
    }
}

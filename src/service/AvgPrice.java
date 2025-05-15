package service;

import models.Transaction;

public class AvgPrice {
    double totalPrice = 0;
    int totalQuantity = 0;

    void add(Transaction transaction) {
        totalPrice += transaction.getPrice() * transaction.getQuantity();
        totalQuantity += transaction.getQuantity();
    }

    double getAvg() {
        return totalQuantity == 0 ? 0 : totalPrice / totalQuantity;
    }

    public void add(double price, int quantity) {
    }

    public int getQuantity() {
        return totalQuantity;
    }
}

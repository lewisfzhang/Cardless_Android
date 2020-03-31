package com.example.cardless_android;

import java.util.ArrayList;
import java.util.List;

// FIXME move to separate secure class when finished!
public class TransactionInfo {
    private static TransactionInfo savedList = null;
    private List<Transaction> list = new ArrayList<>();

    static void setSavedList(TransactionInfo t) {
        savedList = t;
    }

    static TransactionInfo getSavedList() {
        return savedList;
    }

    static void clearSavedList() {
        savedList = null;
    }

    public void fetchData(String key) {
        // FIXME get transaction data from server -> load into arraylist
    }

    public void addItem(String name, String price) {
        list.add(new Transaction(name, price));
    }

    public void clearItems() {
        list.clear();
    }

    public List<Transaction> getItems() {
        return list;
    }

    public String getTotal() {
        return "$" + Transaction.sum(list);
    }

    public List<Transaction> getList() {
        return list;
    }
}

class Transaction {
    private double _price; // FIXME: assumes valid format --> ($3.1 --> 3.10)
    private String _name;

    Transaction(String name, double price) {
        _price = price;
        _name = name.toUpperCase();
    }

    Transaction(String name, String price) {
        this(name, Double.valueOf(price));
    }

    static double sum(List<Transaction> list) {
        double d = 0;
        for (Transaction t : list) {
            d += t._price;
        }
        return d;
    }

    String getName() {
        return _name;
    }

    String getPrice() {
        return "$" + _price;
    }
}

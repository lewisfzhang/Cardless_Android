package com.example.cardless_android;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

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
        return Transaction.sum(list);
    }

    public List<Transaction> getList() {
        return list;
    }
}

class Transaction {
    private Price _price; // FIXME: assumes valid format --> ($3.1 --> 3.10)
    private String _name;

    Transaction(String name, String price) {
        _price = new Price(price);
        _name = name.toUpperCase();
    }

//    Transaction(String name, String price) {
//        this(name, Double.valueOf(price));
//    }

    static String sum(List<Transaction> list) {
        Price p = new Price();
        for (Transaction t : list) {
            p = Price.add(p, t._price);
        }
        return p.toString();
    }

    String getName() {
        return _name;
    }

    String getPrice() {
        return _price.toString();
    }

    private static class Price {
        private int dollar, cent;

        Price () {
            this(0, 0);
        }

        Price (int dollar, int cent) {
            this.dollar = dollar;
            this.cent = cent;
        }

        Price (String price) {
            price = price.trim();
            if (price.charAt(0) == '$') {
                price = price.substring(1);
            }

            check(Pattern.matches(".+[.].+", price), "valid format");
            Log.d("RICE", price);
            Log.d("DANK", ""+price.indexOf('.'));
            String[] split = price.split("\\.");
            Log.d("RICE2", Arrays.toString(split));
            check(split.length == 2, "length of 2");

            dollar = Integer.parseInt(split[0]);
            cent = Integer.parseInt(split[1]);

            check(dollar >= 0 && cent >= 0 && cent < 100, "valid price");
        }

        static Price add(Price p1, Price p2) {
            int cent = p1.cent + p2.cent;
            int carry = 0;
            if (cent >= 100) {
                cent = cent % 100;
                carry = 1;
            }
            int dollar  = p1.dollar + p2.dollar + carry;

            return new Price(dollar, cent);
        }

        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "$%d.%d", dollar, cent);
        }

        static void check(boolean check, String msg) throws IllegalArgumentException {
            if (!check) {
                throw new IllegalArgumentException(msg);
            }
        }
    }
}

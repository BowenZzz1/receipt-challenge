package com.example.demo;

import java.util.List;

public class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private double total;

    public String getRetailer() {
        return retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public static class Item {
        private String shortDescription;
        private double price;

        public String getShortDescription() {
            return shortDescription;
        }

        public double getPrice() {
            return price;
        }

    }
}


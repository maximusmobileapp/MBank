package com.maximus.maximusbank.Model;


import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Transaction {
    public String name;
    public String date;

    public int amount;
    public String payment;
    public String status;

    public Transaction(String name, String date, int amount, String payment, String status) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.payment = payment;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // Method to format amount with Rupees symbol
    public String getFormattedAmount() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        formatter.setCurrency(Currency.getInstance("INR"));
        return formatter.format(amount);
    }

//    public static void main(String[] args) {
//        Transaction transaction = new Transaction("Hello", "16/07/2024", 400, "Within Bank", "Completed");
//        System.out.println("Amount: " + transaction.getFormattedAmount());
//    }
}



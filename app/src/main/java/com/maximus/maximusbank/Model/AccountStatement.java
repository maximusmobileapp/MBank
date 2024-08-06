package com.maximus.maximusbank.Model;

public class AccountStatement {
    private String date;
    private String description;
    private String transaction;
    private double amount;

    public AccountStatement(String date, int amount, String description, String transaction) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

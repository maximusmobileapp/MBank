package com.maximus.maximusbank.model;

public class RecentTransactionModel {

    public String name,  date,  amount;
    public RecentTransactionModel(String name, String date, String amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
    }
}

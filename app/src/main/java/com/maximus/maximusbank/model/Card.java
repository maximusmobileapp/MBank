package com.maximus.maximusbank.model;

public class Card {

    private String cardNumber;
    private boolean cardEnabled;


    public Card(String cardNumber, boolean cardEnabled) {
        this.cardNumber = cardNumber;
        this.cardEnabled = cardEnabled;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean isCardEnabled() {
        return cardEnabled;
    }
}

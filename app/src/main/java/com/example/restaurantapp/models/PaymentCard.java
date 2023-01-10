package com.example.restaurantapp.models;

import java.math.BigInteger;

public class PaymentCard {
    private String cardName;
    private String cardNumber;
    private String cardDate;
    private int cardCVV;
    private String uuidUser;

    public PaymentCard() {
    }

    public PaymentCard(String cardName, String cardNumber, String cardDate, int cardCVV, String uuidUser) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
        this.cardCVV = cardCVV;
        this.uuidUser = uuidUser;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public int getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(int cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getuuidUser() {
        return uuidUser;
    }

    public void setuuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }


}

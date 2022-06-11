package com.cs5520.assignments.numad22su_nisargpatel;

public class Player {
    private double totalAmount;

    Player() {
        this.totalAmount = 0.0;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void addAmount(double production) {
        totalAmount += production;
    }

    public void subtractAmount(double cost) {
        totalAmount -= cost;
    }
}

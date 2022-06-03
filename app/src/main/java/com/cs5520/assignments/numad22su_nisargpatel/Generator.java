package com.cs5520.assignments.numad22su_nisargpatel;

public class Generator {
    private final String name;
    private final double initialPrice;
    private final double coefficient;
    private final double initialTime;
    private final double initialRevenue;
    private final double initialProductivity;
    private int currentOwned;
    private double multiplier;

    private static final int MAX_ALLOWED = 1000;

    public Generator(String name, double initialPrice, double coefficient, double initialTime, double initialRevenue, double initialProductivity) {
        this.name = name;
        this.initialPrice = initialPrice;
        this.coefficient = coefficient;
        this.initialTime = initialTime;
        this.initialRevenue = initialRevenue;
        this.initialProductivity = initialProductivity;
        this.currentOwned = 0;
        this.multiplier = 1.0;
    }

    public String getName() {
        return name;
    }


    public double getInitialPrice() {
        return initialPrice;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public double getInitialTime() {
        return initialTime;
    }

    public double getInitialRevenue() {
        return initialRevenue;
    }

    public double getInitialProductivity() {
        return initialProductivity;
    }

    public int getCurrentOwned() {
        return currentOwned;
    }

    public int getNextBonusCount() {
        return Math.min(MAX_ALLOWED, ((currentOwned) / 100 + 1) * 100);
    }

    public double getProduction() {
        return initialProductivity * currentOwned * multiplier;
    }

    public double getCost(BuyType buyType) {
        if (currentOwned == MAX_ALLOWED) {
            return 0.0;
        }
        int count = getBuyCount(buyType);
        return initialPrice * Math.pow(coefficient, currentOwned) * ((Math.pow(coefficient, count) - 1) / (coefficient - 1));
    }

    public void buy(BuyType buyType) {
        currentOwned = Math.min(MAX_ALLOWED, currentOwned + getBuyCount(buyType));
    }

    public int getBuyCount(BuyType buyType) {
        if(currentOwned == MAX_ALLOWED) {
            return 0;
        }
        if (buyType == BuyType.BUY_NEXT) {
            return getNextBonusCount() - getCurrentOwned();
        } else {
            return buyType.getCount();
        }
    }
}

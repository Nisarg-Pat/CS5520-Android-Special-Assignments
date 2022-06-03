package com.cs5520.assignments.numad22su_nisargpatel;

public class Generator {
    private String name;
    private final double initialPrice;

    public Generator(String name, double initialPrice) {
        this.name = name;
        this.initialPrice = initialPrice;
    }

    public String getName() {
        return name;
    }


    public double getInitialPrice() {
        return initialPrice;
    }
}

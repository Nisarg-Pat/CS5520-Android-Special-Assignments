package com.cs5520.assignments.numad22su_nisargpatel;

public class FoodItem {
    private final String id;
    private final String name;
    private final String imageURL;

    public FoodItem(String id, String name, String imageURL) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }
}

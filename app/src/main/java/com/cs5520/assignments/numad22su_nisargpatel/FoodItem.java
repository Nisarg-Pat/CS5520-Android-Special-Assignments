package com.cs5520.assignments.numad22su_nisargpatel;

import android.graphics.Bitmap;

public class FoodItem {
    private final String id;
    private final String name;
    private final String imageURL;
    private final String description;
    private final String calories;
    private final String calorieUnit;
    private boolean isExpanded;
    private Bitmap imageIconBmp;

    public static final String VEGETARIAN = "vegetarian";
    public static final String VEGAN = "vegan";
    public static final String GLUTEN_FREE = "glutenFree";

    public FoodItem(int id, String name, String imageURL, String description, double calories, String calorieUnit) {
        this.id = String.valueOf(id);
        this.name = name;
        this.imageURL = imageURL;
        this.isExpanded = false;
        this.imageIconBmp = null;
        this.description = description;
        this.calories = String.valueOf(calories);
        this.calorieUnit = calorieUnit;
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

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public Bitmap getImageIconBmp() {
        return imageIconBmp;
    }

    public void setImageIconBmp(Bitmap imageIconBmp) {
        this.imageIconBmp = imageIconBmp;
    }

    public String getDescription() {
        return description;
    }

    public String getCalories() {
        return calories;
    }

    public String getCalorieUnit() {
        return calorieUnit;
    }
}

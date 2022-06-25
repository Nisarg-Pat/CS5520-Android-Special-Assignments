package com.cs5520.assignments.numad22su_nisargpatel;

import android.graphics.Bitmap;

public class FoodItem {
    private final String id;
    private final String name;
    private final String imageURL;
    private final String description;
    private boolean isExpanded;
    private Bitmap imageIconBmp;

    public static final String VEGETARIAN = "vegetarian";
    public static final String VEGAN = "vegan";
    public static final String GLUTEN_FREE = "gluten_free";

    public FoodItem(String id, String name, String imageURL) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.isExpanded = false;
        this.imageIconBmp = null;
        this.description = "Pasta With Tuna might be just the main course you are searching for. One serving contains <b>421 calories</b>, <b>24g of protein</b>, and <b>10g of fat</b>. For <b>$1.68 per serving</b>, this recipe <b>covers 28%</b> of your daily requirements of vitamins and minerals. 1 person were impressed by this recipe. Head to the store and pick up flour, onion, peas, and a few other things to make it today. It is a good option if you're following a <b>pescatarian</b> diet. All things considered, we decided this recipe <b>deserves a spoonacular score of 92%</b>. This score is excellent. Try <a href=\"https://spoonacular.com/recipes/pasta-and-tuna-salad-ensalada-de-pasta-y-atn-226303\">Pastan and Tuna Salad (Ensalada de Pasta y Atún)</a>, <a href=\"https://spoonacular.com/recipes/tuna-pasta-565100\">Tuna Pasta</a>, and <a href=\"https://spoonacular.com/recipes/tuna-pasta-89136\">Tuna Pasta</a> for similar recipes.";
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
}

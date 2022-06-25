package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AtYourService extends AppCompatActivity implements View.OnClickListener {
    EditText aysEditText;
    RecyclerView aysRecyclerView;
    AtYourServiceAdaptor aysAdapter;
    Button aysSearch, aysClear;
    RangeSlider aysCalorieSlider;
    Set<String> foodCategory;
    int minCalorie;
    int maxCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        aysEditText = findViewById(R.id.ays_query_et);
        aysSearch = findViewById(R.id.ays_search_btn);
        aysSearch.setOnClickListener(this);
        aysClear = findViewById(R.id.ays_clear_btn);
        aysClear.setOnClickListener(this);

        aysCalorieSlider = findViewById(R.id.calorie_slider);
        aysCalorieSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                //Empty
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                minCalorie = (int)((float)(aysCalorieSlider.getValues().get(0)));
                maxCalorie = (int)((float)(aysCalorieSlider.getValues().get(1)));
                Log.d("SLIDER", minCalorie+" "+maxCalorie);
            }
        });
        minCalorie = (int)((float)(aysCalorieSlider.getValues().get(0)));
        maxCalorie = (int)((float)(aysCalorieSlider.getValues().get(1)));
        Log.d("HASHSET", foodCategory.toString());

        foodCategory = new HashSet<>();

        aysRecyclerView = findViewById(R.id.ays_recycler_view);
        aysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aysAdapter = new AtYourServiceAdaptor(this, generateFoodItems());
        aysRecyclerView.setAdapter(aysAdapter);
    }

    public List<FoodItem> generateFoodItems() {
        //Temporary List
        List<FoodItem> foodItemList = new ArrayList<>();
        foodItemList.add(new FoodItem("654959", "Pasta With Tuna", "https://spoonacular.com/recipeImages/654959-312x231.jpg"));
        foodItemList.add(new FoodItem("511728", "Pasta Margherita", "https://spoonacular.com/recipeImages/511728-312x231.jpg"));
        foodItemList.add(new FoodItem("654812", "Pasta and Seafood", "https://spoonacular.com/recipeImages/654812-312x231.jpg"));
        foodItemList.add(new FoodItem("654857", "Pasta On The Border", "https://spoonacular.com/recipeImages/654857-312x231.jpg"));
        foodItemList.add(new FoodItem("654959", "Pasta With Tuna", "https://spoonacular.com/recipeImages/654959-312x231.jpg"));
        foodItemList.add(new FoodItem("511728", "Pasta Margherita", "https://spoonacular.com/recipeImages/511728-312x231.jpg"));
        foodItemList.add(new FoodItem("654812", "Pasta and Seafood", "https://spoonacular.com/recipeImages/654812-312x231.jpg"));
        foodItemList.add(new FoodItem("654857", "Pasta On The Border", "https://spoonacular.com/recipeImages/654857-312x231.jpg"));
        foodItemList.add(new FoodItem("654959", "Pasta With Tuna", "https://spoonacular.com/recipeImages/654959-312x231.jpg"));
        foodItemList.add(new FoodItem("511728", "Pasta Margherita", "https://spoonacular.com/recipeImages/511728-312x231.jpg"));
        foodItemList.add(new FoodItem("654812", "Pasta and Seafood", "https://spoonacular.com/recipeImages/654812-312x231.jpg"));
        foodItemList.add(new FoodItem("654857", "Pasta On The Border", "https://spoonacular.com/recipeImages/654857-312x231.jpg"));
        return foodItemList;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ays_clear_btn) {
            aysAdapter.clearFoodItems();
        } else if (view.getId() == R.id.ays_search_btn) {

        } else if (view.getId() == R.id.checkbox_vegetarian) {
            if (((CheckBox) view).isChecked()) {
                foodCategory.add(FoodItem.VEGETARIAN);
            } else {
                foodCategory.remove(FoodItem.VEGETARIAN);
            }
            Log.d("HASHSET", foodCategory.toString());
        } else if (view.getId() == R.id.checkbox_vegan) {
            if (((CheckBox) view).isChecked()) {
                foodCategory.add(FoodItem.VEGAN);
            } else {
                foodCategory.remove(FoodItem.VEGAN);
            }
            Log.d("HASHSET", foodCategory.toString());
        } else if (view.getId() == R.id.checkbox_gluten_free) {
            if (((CheckBox) view).isChecked()) {
                foodCategory.add(FoodItem.GLUTEN_FREE);
            } else {
                foodCategory.remove(FoodItem.GLUTEN_FREE);
            }
            Log.d("HASHSET", foodCategory.toString());
        }

    }
}
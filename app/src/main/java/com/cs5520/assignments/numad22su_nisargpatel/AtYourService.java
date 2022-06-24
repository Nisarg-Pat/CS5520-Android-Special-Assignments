package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AtYourService extends AppCompatActivity {
    EditText aysEditText;
    RecyclerView aysRecyclerView;
    Button aysSearch, aysClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        aysEditText = findViewById(R.id.ays_query_et);
        aysSearch = findViewById(R.id.ays_search_btn);
        aysClear = findViewById(R.id.ays_clear_btn);
        aysRecyclerView = findViewById(R.id.ays_recycler_view);

        aysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aysRecyclerView.setAdapter(new AtYourServiceAdaptor(this, generateFoodItems()));
    }

    public List<FoodItem> generateFoodItems() {
        //Temporary List
        List<FoodItem> foodItemList = new ArrayList<>();
        foodItemList.add(new FoodItem("654959","Pasta With Tuna","https://spoonacular.com/recipeImages/654959-312x231.jpg"));
        foodItemList.add(new FoodItem("511728","Pasta Margherita","https://spoonacular.com/recipeImages/511728-312x231.jpg"));
        foodItemList.add(new FoodItem("654812","Pasta and Seafood","https://spoonacular.com/recipeImages/654812-312x231.jpg"));
        foodItemList.add(new FoodItem("654857","Pasta On The Border","https://spoonacular.com/recipeImages/654857-312x231.jpg"));
        return foodItemList;
    }
}
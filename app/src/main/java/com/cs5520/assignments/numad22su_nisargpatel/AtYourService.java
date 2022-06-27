package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    private static final String TAG = "AtYourService";

    Handler adapterHander = new Handler();

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
                Log.d(TAG, minCalorie+" "+maxCalorie);
            }
        });
        minCalorie = (int)((float)(aysCalorieSlider.getValues().get(0)));
        maxCalorie = (int)((float)(aysCalorieSlider.getValues().get(1)));
        Log.d(TAG, minCalorie+" "+maxCalorie);

        foodCategory = new HashSet<>();

        aysRecyclerView = findViewById(R.id.ays_recycler_view);
        aysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aysAdapter = new AtYourServiceAdaptor(this, new ArrayList<>());
        aysRecyclerView.setAdapter(aysAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ays_clear_btn) {
            aysAdapter.clearFoodItems();
        } else if (view.getId() == R.id.ays_search_btn) {
            clickSearchButton();
        } else if (view.getId() == R.id.checkbox_vegetarian) {
            if (((CheckBox) view).isChecked()) {
                foodCategory.add(FoodItem.VEGETARIAN);
            } else {
                foodCategory.remove(FoodItem.VEGETARIAN);
            }
            Log.d(TAG, foodCategory.toString());
        } else if (view.getId() == R.id.checkbox_vegan) {
            if (((CheckBox) view).isChecked()) {
                foodCategory.add(FoodItem.VEGAN);
            } else {
                foodCategory.remove(FoodItem.VEGAN);
            }
            Log.d(TAG, foodCategory.toString());
        } else if (view.getId() == R.id.checkbox_gluten_free) {
            if (((CheckBox) view).isChecked()) {
                foodCategory.add(FoodItem.GLUTEN_FREE);
            } else {
                foodCategory.remove(FoodItem.GLUTEN_FREE);
            }
            Log.d(TAG, foodCategory.toString());
        }

    }

    private void clickSearchButton() {
        String itemName = aysEditText.getText().toString();
        if(itemName.isEmpty()) {
            Toast.makeText(this, "Food Item cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.spoonacular.com/recipes/complexSearch?apiKey=").append(Spoonacular.apiKey);
        sb.append("&query=").append(itemName);
        sb.append("&addRecipeInformation=true");
        sb.append("&number=").append(20);
        sb.append("&minCalories=").append(minCalorie);
        sb.append("&maxCalories=").append(maxCalorie);

        if(!foodCategory.isEmpty()) {
            StringBuilder foodCategoryBuilder = new StringBuilder("");
            for(String category: foodCategory) {
                foodCategoryBuilder.append(category).append(',');
            }
            foodCategoryBuilder.deleteCharAt(foodCategoryBuilder.length()-1);
            String categoryString = foodCategoryBuilder.toString();
            sb.append("&diet=").append(categoryString);
        }
        Log.d(TAG, sb.toString());
        new Thread(new SpoonacularRunnable(sb.toString())).start();
    }

    class SpoonacularRunnable implements Runnable {

        private final String urlString;

        public SpoonacularRunnable(String url) {
            this.urlString = url;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();

                BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuilder jsonSB = new StringBuilder();
                while((line =  bR.readLine()) != null){
                    jsonSB.append(line);
                }
                inputStream.close();
                Log.d(TAG, jsonSB.toString());

                JSONObject result = new JSONObject(jsonSB.toString());
                JSONArray results = result.getJSONArray("results");
                List<FoodItem> foodItemsList = new ArrayList<>();
                for(int i=0;i<results.length();i++) {
                    JSONObject item = results.getJSONObject(i);
                    JSONObject calory = item.getJSONObject("nutrition").getJSONArray("nutrients").getJSONObject(0);
                    foodItemsList.add(new FoodItem(item.getInt("id"), item.getString("title"), item.getString("image"), item.getString("summary"), calory.getDouble("amount"), calory.getString("unit")));
                }
                adapterHander.post(() -> {
                    aysAdapter.clearFoodItems();
                    aysAdapter.foodItems = foodItemsList;
                    aysAdapter.notifyItemRangeInserted(0, aysAdapter.getItemCount());
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
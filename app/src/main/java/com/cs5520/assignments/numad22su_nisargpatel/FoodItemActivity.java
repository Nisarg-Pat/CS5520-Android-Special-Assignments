package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoodItemActivity extends AppCompatActivity {

    RecyclerView aysRecyclerView;
    ProgressBar aysProgresssBar;
    AtYourServiceAdaptor aysAdapter;

    private static final String TAG = "FoodItemActivity";

    Handler adapterHander = new Handler();
    Handler visibilityHander = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item);

        aysRecyclerView = findViewById(R.id.ays_recycler_view);
        aysRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aysAdapter = new AtYourServiceAdaptor(this, new ArrayList<>());
        aysRecyclerView.setAdapter(aysAdapter);

        aysProgresssBar = findViewById(R.id.ays_progress_bar);

        aysRecyclerView.setVisibility(View.GONE);
        aysProgresssBar.setVisibility(View.VISIBLE);
        new Thread(new SpoonacularRunnable(getIntent().getStringExtra("url"))).start();
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
                while ((line = bR.readLine()) != null) {
                    jsonSB.append(line);
                }
                inputStream.close();
                Log.d(TAG, jsonSB.toString());

                JSONObject result = new JSONObject(jsonSB.toString());
                JSONArray results = result.getJSONArray("results");
                List<FoodItem> foodItemsList = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
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
            visibilityHander.post(() -> {
                aysRecyclerView.setVisibility(View.VISIBLE);
                aysProgresssBar.setVisibility(View.GONE);
            });

        }
    }
}
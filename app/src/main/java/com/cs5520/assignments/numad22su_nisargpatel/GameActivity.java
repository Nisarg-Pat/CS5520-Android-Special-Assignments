package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    Button upgradeBuy;
    RecyclerView generatorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_game);

        upgradeBuy = findViewById(R.id.upgrade_button);
        upgradeBuy.setOnClickListener((v) -> changeUpgradeBuy());

        generatorRecyclerView = findViewById(R.id.generator_rv);

        generatorRecyclerView.setHasFixedSize(true);
        generatorRecyclerView.setItemAnimator(null);
        generatorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        generatorRecyclerView.setAdapter(new GeneratorAdapter(getGeneratorList(), this));
    }

    private void changeUpgradeBuy() {
        String s = upgradeBuy.getText().toString();
        if (s.equals(getString(R.string.upgrade_buy_x1))) {
            upgradeBuy.setText(R.string.upgrade_buy_x10);
        } else if (s.equals(getString(R.string.upgrade_buy_x10))) {
            upgradeBuy.setText(R.string.upgrade_buy_x100);
        } else if (s.equals(getString(R.string.upgrade_buy_x100))) {
            upgradeBuy.setText(R.string.upgrade_buy_next);
        } else {
            upgradeBuy.setText(R.string.upgrade_buy_x1);
        }
    }

    private List<Generator> getGeneratorList() {
        List<Generator> generatorList = new ArrayList<>();
        generatorList.add(new Generator("Lemonade Stand", 3.738, 1.07, 0.6, 1, 1.67));
        generatorList.add(new Generator("Newspaper Delivery", 60, 1.15, 3, 60, 20));
        generatorList.add(new Generator("Car Wash", 720, 1.14, 6, 540, 90));
        generatorList.add(new Generator("Pizza Delivery", 8640, 1.13, 12, 4320, 360));
        generatorList.add(new Generator("Donut Stop", 103680, 1.12, 24, 51840, 2160));
        return generatorList;
    }
}
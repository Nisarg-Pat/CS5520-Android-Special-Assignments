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
        generatorList.add(new Generator("Lemon", 1.4));
        generatorList.add(new Generator("Newspaper", 60.0));
        return generatorList;
    }
}
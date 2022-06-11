package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    Button upgradeBuy;
    RecyclerView generatorRecyclerView;
    GeneratorAdapter generatorAdapter;
    BuyType buyType;
    Player player;
    TextView scoreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_game);

        player = new Player();
        scoreTV = findViewById(R.id.score_tv);
        setScoreTV();

        upgradeBuy = findViewById(R.id.upgrade_button);
        buyType = BuyType.BUY_1x;
        upgradeBuy.setText(getBuyTypeString());
        upgradeBuy.setOnClickListener((v) -> changeUpgradeBuy());

        generatorRecyclerView = findViewById(R.id.generator_rv);

        generatorRecyclerView.setHasFixedSize(true);
        generatorRecyclerView.setItemAnimator(null);
        generatorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        generatorAdapter = new GeneratorAdapter(this, getGeneratorList(),player, buyType);
        generatorRecyclerView.setAdapter(generatorAdapter);
    }

    public void setScoreTV() {
        scoreTV.setText(Long.toString((long) player.getTotalAmount()));
    }

    private String getBuyTypeString() {
        switch (buyType) {
            case BUY_10x:
                return getString(R.string.upgrade_buy_x10);
            case BUY_100x:
                return getString(R.string.upgrade_buy_x100);
            case BUY_NEXT:
                return getString(R.string.upgrade_buy_next);
            case BUY_1x:
            default:
                return getString(R.string.upgrade_buy_x1);
        }
    }

    private void changeUpgradeBuy() {
        String s = upgradeBuy.getText().toString();
        if (buyType == BuyType.BUY_1x) {
            buyType = BuyType.BUY_10x;
        } else if (buyType == BuyType.BUY_10x) {
            buyType = BuyType.BUY_100x;
        } else if (buyType == BuyType.BUY_100x) {
            buyType = BuyType.BUY_NEXT;
        } else {
            buyType = BuyType.BUY_1x;
        }
        upgradeBuy.setText(getBuyTypeString());
        generatorAdapter.updateBuyType(buyType);
    }

    private List<Generator> getGeneratorList() {
        List<Generator> generatorList = new ArrayList<>();
        generatorList.add(new Generator("Lemonade Stand", 3.738, 1.07, 0.6, 1, 1.67));
        generatorList.add(new Generator("Newspaper Delivery", 60, 1.15, 3, 60, 20));
        generatorList.add(new Generator("Car Wash", 720, 1.14, 6, 540, 90));
        generatorList.add(new Generator("Pizza Delivery", 8640, 1.13, 12, 4320, 360));
        generatorList.add(new Generator("Donut Stop", 103680, 1.12, 24, 51840, 2160));
        generatorList.get(0).buy(BuyType.BUY_1x);
        return generatorList;
    }
}
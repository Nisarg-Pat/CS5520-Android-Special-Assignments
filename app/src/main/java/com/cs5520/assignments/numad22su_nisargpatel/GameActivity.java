package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    Button upgradeBuy;

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
}
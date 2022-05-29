package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button aboutMeBtn,adCapCloneBtn;
    TextView helloWorldTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aboutMeBtn = findViewById(R.id.about_me_btn);
        aboutMeBtn.setOnClickListener((v) -> showToast());
        adCapCloneBtn = findViewById(R.id.adcap_clone_btn);
        adCapCloneBtn.setOnClickListener((v) -> openGame());
    }

    private void showToast() {
        Toast.makeText(this, "Name: Nisarg Patel\nEmail ID: patel.nisargs@northeastern.edu", Toast.LENGTH_LONG).show();
    }

    private void openGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
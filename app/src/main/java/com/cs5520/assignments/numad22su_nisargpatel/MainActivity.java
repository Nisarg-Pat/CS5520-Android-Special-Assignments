package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button aboutMeBtn, adCapCloneBtn, atyourService;
    TextView helloWorldTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aboutMeBtn = findViewById(R.id.about_me_btn);
        aboutMeBtn.setOnClickListener(this);
        adCapCloneBtn = findViewById(R.id.adcap_clone_btn);
        adCapCloneBtn.setOnClickListener(this);
        atyourService = findViewById(R.id.at_your_service_btn);
        atyourService.setOnClickListener(this);
        atyourService = findViewById(R.id.at_your_service_btn);
    }

    private void showToast() {
        Toast.makeText(this, "Name: Nisarg Patel\nEmail ID: patel.nisargs@northeastern.edu", Toast.LENGTH_LONG).show();
    }

    private void openGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void openAtYourService() {
        Intent intent = new Intent(this, AtYourService.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.about_me_btn) {
            showToast();
        } else if (view.getId() == R.id.adcap_clone_btn) {
            openGame();
        } else if (view.getId() == R.id.at_your_service_btn) {
            openAtYourService();
        }
    }
}
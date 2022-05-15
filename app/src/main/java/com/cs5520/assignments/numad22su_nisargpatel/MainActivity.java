package com.cs5520.assignments.numad22su_nisargpatel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button aboutMeBtn;
    TextView helloWorldTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aboutMeBtn = findViewById(R.id.about_me_btn);
        aboutMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast();
            }
        });
    }

    private void showToast() {
        Toast.makeText(this, "Name: Nisarg Patel\nEmail ID: patel.nisargs@northeastern.edu", Toast.LENGTH_LONG).show();
    }
}
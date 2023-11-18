package com.example.dietplandiscovery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.dietplandiscovery.Activities.HomepageActivity;

public class MainActivity extends AppCompatActivity {
    Button button_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = (Button) findViewById(R.id.button_start);

        button_start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
        });
    }
}
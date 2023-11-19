/*
    RMIT University Vietnam
    Course: COSC2657 Android Development
    Semester: 2023C
    Assessment: Assignment 1
    Author: Lai Nghiep Tri
    ID: s3799602
    Created  date: 19/11/2023
    Last modified: 19/11/2023
    Acknowledgement: Figma UI, Nutritionix, Android Developer documentation, Geeksforgeeks
 */

package com.example.dietplandiscovery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.dietplandiscovery.Activities.HomepageActivity;

public class MainActivity extends AppCompatActivity {
    // Variables and Widgets
    Button button_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = (Button) findViewById(R.id.button_start);

        // Go to homepage view
        button_start.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
        });
    }
}
package com.example.dietplandiscovery.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dietplandiscovery.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomepageActivity extends AppCompatActivity {
    TextView currentDate;
    ImageButton breakfastBtn, lunchBtn, dinnerBtn;
    AppCompatButton targetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Find all widgets
        findAllElements();

        // Get current date and show on homepage
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        currentDate.setText(formattedDate);

        breakfastBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FoodListActivity.class);
            startActivity(intent);
        });
        lunchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FoodListActivity.class);
            startActivity(intent);
        });
        dinnerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, FoodListActivity.class);
            startActivity(intent);
        });

    }

    protected void findAllElements() {
         breakfastBtn = (ImageButton) findViewById(R.id.button_breakfast);
         lunchBtn = (ImageButton) findViewById(R.id.button_lunch);
         dinnerBtn = (ImageButton) findViewById(R.id.button_dinner);
         targetBtn = (AppCompatButton) findViewById(R.id.button_target);
         currentDate = (TextView) findViewById(R.id.text_TODAY);
    }
}
package com.example.dietplandiscovery.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietplandiscovery.Model.Food;
import com.example.dietplandiscovery.R;

public class FoodDetailActivity extends AppCompatActivity {
    Food food;
    TextView foodName, foodDesc, text_calo, text_fat, text_carbs, text_protein;
    ImageView foodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        food = getIntent().getParcelableExtra("food_details");
        foodName = (TextView) findViewById(R.id.text_title_detailActivity);
        foodDesc = (TextView) findViewById(R.id.text_desc_detailActivity);
        foodImage = (ImageView) findViewById(R.id.image_header_detailActivity);

        text_calo = (TextView) findViewById(R.id.text_caloriesVal);
        text_protein = (TextView) findViewById(R.id.text_proteinVal);
        text_fat = (TextView) findViewById(R.id.text_fatVal);
        text_carbs = (TextView) findViewById(R.id.text_carbsVal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        float[] nutritionFacts = food.getNutrition();
        float calo = nutritionFacts[0];
        float carbs = nutritionFacts[1];
        float protein = nutritionFacts[2];
        float fat = nutritionFacts[3];

        foodName.setText(food.getName());
        foodDesc.setText(food.getDesc());
        foodImage.setImageResource(food.getImgUrl());

        text_calo.setText(Float.toString(calo));
        text_carbs.setText(Float.toString(carbs));
        text_protein.setText(Float.toString(protein));
        text_fat.setText(Float.toString(fat));
    }
}

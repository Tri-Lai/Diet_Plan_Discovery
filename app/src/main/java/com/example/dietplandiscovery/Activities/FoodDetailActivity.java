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

package com.example.dietplandiscovery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.dietplandiscovery.Model.Food;
import com.example.dietplandiscovery.R;

public class FoodDetailActivity extends AppCompatActivity {
    // Variables and Widgets
    Food food;
    TextView foodName, foodDesc, text_calo, text_fat, text_carbs, text_protein;
    ImageView foodImage;
    AppCompatButton button_addFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        food = getIntent().getParcelableExtra("food_details"); // Get food details passed from FoodListActivity
        findAllElements(); // Find all widgets
        setContent(); // Set xml content

        // Back to previous activity and return food has been added
        button_addFood.setOnClickListener(v -> {
            Intent goFoodListActivity = new Intent(FoodDetailActivity.this, FoodListActivity.class);
            goFoodListActivity.putExtra("added_food", food);
            setResult(RESULT_OK, goFoodListActivity);
            finish();
        });
    }

    /**
     * Find all widgets in xml files
     */
    protected void findAllElements() {
        foodName = (TextView) findViewById(R.id.text_title_detailActivity);
        foodDesc = (TextView) findViewById(R.id.text_desc_detailActivity);
        foodImage = (ImageView) findViewById(R.id.image_header_detailActivity);

        text_calo = (TextView) findViewById(R.id.text_caloriesVal);
        text_protein = (TextView) findViewById(R.id.text_proteinVal);
        text_fat = (TextView) findViewById(R.id.text_fatVal);
        text_carbs = (TextView) findViewById(R.id.text_carbsVal);

        button_addFood = (AppCompatButton) findViewById(R.id.button_addFood);
    }

    /**
     * Set content for widgets
     */
    private void setContent() {
        // Get nutrition factors from passed food
        float[] nutritionFacts = food.getNutrition();
        float calo = nutritionFacts[0];     // Get calories
        float carbs = nutritionFacts[1];    // Get carbohydrate
        float protein = nutritionFacts[2];  // Get protein
        float fat = nutritionFacts[3];      // Get fat

        foodName.setText(food.getName());
        foodDesc.setText(food.getDesc());
        foodImage.setImageResource(food.getImgUrl());

        text_calo.setText(Float.toString(calo));
        text_carbs.setText(Float.toString(carbs));
        text_protein.setText(Float.toString(protein));
        text_fat.setText(Float.toString(fat));
    }

    /**
     * Handle returned data from Detail view
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check the correct request code
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                try {
                    // Get the food details using Parcelable object
                    if (data.getParcelableExtra("food_details") != null) {
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
                } catch (NullPointerException e) {
                    Log.d("Bug", "Details of food is not passed to Detail View");
                    e.printStackTrace();
                }
            }
        }
    }
}

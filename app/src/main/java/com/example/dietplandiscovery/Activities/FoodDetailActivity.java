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
    Food food;
    TextView foodName, foodDesc, text_calo, text_fat, text_carbs, text_protein;
    ImageView foodImage;
    AppCompatButton button_addFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        food = getIntent().getParcelableExtra("food_details");

        // Find all widgets
        findAllElements();

        // Set content for page
        setContent();


        button_addFood.setOnClickListener(v -> {
            Intent goFoodListActivity = new Intent(FoodDetailActivity.this, FoodListActivity.class);
            goFoodListActivity.putExtra("added_food", food);
            setResult(RESULT_OK, goFoodListActivity);
            finish();
        });


    }

    protected void findAllElements() {
//        food = getIntent().getParcelableExtra("food_details");
        foodName = (TextView) findViewById(R.id.text_title_detailActivity);
        foodDesc = (TextView) findViewById(R.id.text_desc_detailActivity);
        foodImage = (ImageView) findViewById(R.id.image_header_detailActivity);

        text_calo = (TextView) findViewById(R.id.text_caloriesVal);
        text_protein = (TextView) findViewById(R.id.text_proteinVal);
        text_fat = (TextView) findViewById(R.id.text_fatVal);
        text_carbs = (TextView) findViewById(R.id.text_carbsVal);

        button_addFood = (AppCompatButton) findViewById(R.id.button_addFood);
    }

    private void setContent() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                try {
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
//        if (requestCode == 200) {
//            if (resultCode == RESULT_OK) {
//                String response = (String) data.getExtras().get("service");
//                TextView display = (TextView) findViewById(R.id.display);
//                display.setText("Thank you for selecting " + response + "
//                        service.");
//            }
//        }


    }
}

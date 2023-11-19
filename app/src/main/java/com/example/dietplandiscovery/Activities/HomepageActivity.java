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

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.dietplandiscovery.Model.Food;
import com.example.dietplandiscovery.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomepageActivity extends AppCompatActivity {
    // Variables and Widgets
    ArrayList<Food> selectedFood;
    private int targetCalo = 0;
    private boolean mealChooseFlag = false;
    TextView currentDate, fatProgress, proteinProgress, carbsProgress, text_caloEaten, text_caloLeft,
    text_caloStatus;
    ImageButton breakfastBtn, lunchBtn, dinnerBtn;
    AppCompatButton chooseCaloBtn, chooseBtn, cancelBtn;
    ProgressBar progressBar_carbs, progressBar_protein, progressBar_fat, progressBar_calories;
    NumberPicker numberpicker;
    float rawCarbs = 0, caloEaten = 0, rawProtein = 0, rawFat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Init added food list
        selectedFood = new ArrayList<>();

        // Find all widgets
        findAllElements();

        // Get current date and show on homepage
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        currentDate.setText(formattedDate);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // Handle breakfast click event
        breakfastBtn.setOnClickListener(v -> {

            // Disable the button function unless the target calories is defined
            if (mealChooseFlag) {
                Intent intent = new Intent(HomepageActivity.this, FoodListActivity.class);
                intent.putExtra("meal_background", "breakfast");
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(this, "Choose target calories first (at top screen).", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle lunch click event
        lunchBtn.setOnClickListener(v -> {

            // Disable the button function unless the target calories is defined
            if (mealChooseFlag) {
                Intent intent = new Intent(HomepageActivity.this, FoodListActivity.class);
                intent.putExtra("meal_background", "lunch");
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(this, "Choose target calories first (at top screen).", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle dinner click event
        dinnerBtn.setOnClickListener(v -> {

            // Disable the button function unless the target calories is defined
            if (mealChooseFlag) {
                Intent intent = new Intent(HomepageActivity.this, FoodListActivity.class);
                intent.putExtra("meal_background", "dinner");
                startActivityForResult(intent, 100);
            } else {
                Toast.makeText(this, "Choose target calories first (at top screen).", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the initial value for eaten calories
        text_caloEaten.setText(Float.toString(caloEaten));

        // Open a dialog to choose target calories
        chooseCaloBtn.setOnClickListener(v -> openDialog());

        // Update the progress of calories, carbs, protein and fat based on the total consume in
        // added food list
        updateProgressIndicator();
    }

    /**
     * Open a popup window to select for target calories
     */
    private void openDialog() {

        // Create a Dialog object
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Choose target calories");
        dialog.setContentView(R.layout.target_calories_picker);

        // Find widgets in dialog
        TextView currentSelectCalo = (TextView) dialog.findViewById(R.id.text_currentSelectedCalo);
        numberpicker = (NumberPicker) dialog.findViewById(R.id.numberPicker_calories);
        chooseBtn = (AppCompatButton) dialog.findViewById(R.id.button_pickerChoose);
        cancelBtn = (AppCompatButton) dialog.findViewById(R.id.button_pickerCancel);

        // Set min-max value for number picker
        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(14);

        // Create custom values to be displayed on picker and map them to picker
        String[] caloRange = new String[15];

        for (int i = 0; i < caloRange.length; i++) {
            String number = Integer.toString((i + caloRange.length + 1) * 100);
            caloRange[i] = number;
        }

        numberpicker.setDisplayedValues(caloRange);

        final int[] temp = new int[1]; // Store the current custom value

        currentSelectCalo.setText(String.format("%s \ncalories per day", caloRange[numberpicker.getValue()]));

        // Update label text for easy keep track with scrolled value constantly
        numberpicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            temp[0] = Integer.parseInt(caloRange[newVal]);
            currentSelectCalo.setText(String.format("%d \ncalories per day", temp[0]));
        });

        // Confirm the selection
        chooseBtn.setOnClickListener(v -> {
            targetCalo = temp[0];
            chooseCaloBtn.setText(String.format("Target: %d calories", targetCalo));
            mealChooseFlag = true;
            dialog.dismiss();
        });

        // Cancel the selection
        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        // Show a dialog
        dialog.show();
    }

    /**
     * Find all widgets in xml
     */
    private void findAllElements() {
         breakfastBtn = (ImageButton) findViewById(R.id.button_breakfast);
         lunchBtn = (ImageButton) findViewById(R.id.button_lunch);
         dinnerBtn = (ImageButton) findViewById(R.id.button_dinner);
         chooseCaloBtn = (AppCompatButton) findViewById(R.id.button_caloTargetChosen);
         currentDate = (TextView) findViewById(R.id.text_TODAY);
         text_caloEaten = (TextView) findViewById(R.id.text_caloEaten);
         text_caloLeft = (TextView) findViewById(R.id.text_caloLeft);

         carbsProgress = (TextView) findViewById(R.id.text_carbsProgress);
         proteinProgress = (TextView) findViewById(R.id.text_proteinProgress);
         fatProgress = (TextView) findViewById(R.id.text_fatProgress);
         text_caloStatus = (TextView) findViewById(R.id.text_caloStatus);

         progressBar_carbs = (ProgressBar) findViewById(R.id.progressBar_carbs);
         progressBar_protein = (ProgressBar) findViewById(R.id.progressBar_protein);
         progressBar_fat = (ProgressBar) findViewById(R.id.progressBar_fat);
         progressBar_calories = (ProgressBar) findViewById(R.id.progressBar_calories);
    }

    /**
     * Update the progress of eaten calories, carbs, protein and fat
     */
    private void updateProgressIndicator() {
        // If there are any selected food
        if (!selectedFood.isEmpty()) {

            // Accumulate the nutrition facts of all food items
            for (Food food: selectedFood) {
                float[] nutritionFacts = food.getNutrition();
                caloEaten += nutritionFacts[0];
                rawCarbs += nutritionFacts[1];
                rawProtein += nutritionFacts[2];
                rawFat += nutritionFacts[3];
            }

            // Round calories value up to 2 decimal points
            float caloLeft = (float) (Math.round((targetCalo - caloEaten) * 100.0) / 100.0);
            caloEaten = (float) (Math.round(caloEaten * 100.0) / 100.0);


            // Convert carbs, fat and protein from float to int type for display purpose
            int carbs = Math.round(rawCarbs);
            int fat = Math.round(rawFat);
            int protein = Math.round(rawProtein);


            // Display progress based on accumulated values with color
            progressBar_carbs.setProgressTintList(
                    (carbs > 100) ?
                            ColorStateList.valueOf(Color.parseColor("#9b3b3b")) :
                            ColorStateList.valueOf(Color.parseColor("#D0FD3E")));

            progressBar_protein.setProgressTintList(
                    (protein > 75) ?
                            ColorStateList.valueOf(Color.parseColor("#9b3b3b")) :
                            ColorStateList.valueOf(Color.parseColor("#D0FD3E")));

            progressBar_fat.setProgressTintList(
                    (fat > 30) ?
                            ColorStateList.valueOf(Color.parseColor("#9b3b3b")) :
                            ColorStateList.valueOf(Color.parseColor("#D0FD3E")));

            progressBar_carbs.setProgress(carbs);
            progressBar_protein.setProgress(protein);
            progressBar_fat.setProgress(fat);

            int caloLeftPercentage = (int) (100 - (caloLeft / targetCalo * 100.0));
            progressBar_calories.setProgress(caloLeftPercentage);


            // Display text-based values with color
            carbsProgress.setTextColor((carbs > 100) ? getResources().getColor(R.color.warning) : Color.WHITE);
            proteinProgress.setTextColor((protein > 75) ? getResources().getColor(R.color.warning) : Color.WHITE);
            fatProgress.setTextColor((fat > 30) ? getResources().getColor(R.color.warning) : Color.WHITE);

            carbsProgress.setText(Integer.toString(carbs));
            fatProgress.setText(Integer.toString(fat));
            proteinProgress.setText(Integer.toString(protein));
            text_caloLeft.setText((caloLeft > 0) ? Float.toString(caloLeft) : Float.toString(-caloLeft));
            text_caloEaten.setText(Float.toString(caloEaten));

            text_caloStatus.setText((caloLeft < 0) ? "cal over" : "cal left");
            text_caloLeft.setTextColor((caloLeft < 0) ?
                    getResources().getColor(R.color.warning) :
                    getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // Check request code
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                // Get list of added food from FoodListActivity
                try {
                    if (data.getParcelableArrayListExtra("selected_food_list") != null) {
                        selectedFood = data.getParcelableArrayListExtra("selected_food_list");
                        Toast.makeText(this, String.format("%d food has added to your meal.", selectedFood.size()), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No food selected", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    Log.d("Bug", "Selected food list not found");
                    e.printStackTrace();
                }
            }
        }
    }
}
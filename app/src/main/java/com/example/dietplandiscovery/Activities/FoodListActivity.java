package com.example.dietplandiscovery.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietplandiscovery.Helper.CustomAdapter;
import com.example.dietplandiscovery.Helper.ItemClickListener;
import com.example.dietplandiscovery.Model.Food;
import com.example.dietplandiscovery.R;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity implements ItemClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Food> foodList;
    private CustomAdapter customAdapter;
    private SearchView searchView;
    private TextView selectedFoodCount;
    private ImageButton backBtn;
    private ArrayList<Food> selectedFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        selectedFoodCount = (TextView) findViewById(R.id.text_currentSelectedFoodItems);
        backBtn = (ImageButton) findViewById(R.id.button_back);

        foodList = new ArrayList<>();
        selectedFood = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_foodList);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        Food grilled_chicken = new Food("Grilled chicken",
                "Chicken is the most popular poultry in the world - and for good reason! " +
                        "It’s low in calories, low in fat and has a high nutritional value. " +
                        "Grilled chicken is also a great source of protein. People who get " +
                        "enough of this nutrient are more likely to maintain muscle mass and " +
                        "support a healthy metabolism.",
                new float[]{246.0f, 12.0f, 63.56f, 19.22f},
                R.drawable.grilled_chicken_breast);

        Food pizza = new Food("Pizza",
                "Pizza is one of the most popular foods, albeit junk foods. It’s delicious, " +
                        "loaded with cheese, and you can customize the toppings based on your mood " +
                        "and taste.\n" +
                        "One slice of plain cheese pizza has approximately 400 calories. So eating " +
                        "even two or three slices of pizza will add 800 to 1,200 calories in your " +
                        "diet. This will be higher when you add processed toppings like pepperoni.\n" +
                        "It’s ok to eat pizza, only if you do so moderately. Pizza in itself is " +
                        "not as big a problem as overconsumption of pizza. It is also important " +
                        "to note that pizza is made from refined flour which can slow down your " +
                        "digestion, creating a sluggish metabolism.\n" +
                        "\n",
                new float[]{164.9f, 10.41f, 24.4f, 51.50f},
                R.drawable.pizza);

        Food pasta = new Food("Pasta",
                "Pasta is a type of noodle that’s traditionally made from durum wheat, " +
                        "water or eggs. It is formed into different noodle shapes and then " +
                        "cooked in boiling water.\n" +
                        "A few examples of commonly consumed types of pasta include:\n" +
                        "\n" +
                        "Spaghetti\n" +
                        "Tortellini\n" +
                        "Ravioli\n" +
                        "Penne\n" +
                        "Fettuccine\n" +
                        "Orzo\n" +
                        "Macaroni\n\n" +
                        "Pasta is high in carbs, which can be bad for you when consumed in large " +
                        "amounts. It also contains gluten, a type of protein that causes issues " +
                        "for those who are gluten-sensitive.\n" +
                        "\n",
                new float[]{73.4f, 80.0f, 42.0f, 25.0f},
                R.drawable.pasta);

        Food melon_avo_strawberry_salad = new Food("Melon, Avocado and Strawberry salad",
                "abc",
                new float[]{126.4f, 0.0f, 46.0f, 5.0f},
                R.drawable.melon_avo_strawberry_salad);

        Food apple_cucumber_salad = new Food("Apple Cucumber salad",
                "abc",
                new float[]{56.4f, 0.0f, 46.0f, 5.0f},
                R.drawable.apple_cucumber_salad);

        Food banh_mi = new Food("Banh mi",
                "Banh Mi is primarily a food item that has most of its calories coming from " +
                        "carbs and some fats. There are many fatty meats used in Banh Mi such as" +
                        " BBQ Pork, pate, or roast beef which can contribute to the already high " +
                        "calorie dish. ",
                new float[]{246.0f, 0.0f, 46.0f, 5.0f},
                R.drawable.banh_mi);

        Food pho = new Food("Chicken Pho Soup",
                "Pho is a Vietnamese food classic that is flavorful and comforting. " +
                        "It is a very healthy dish given that it contains ample protein content " +
                        "from the chicken, and is served in a nutrient-rich broth that is steeped " +
                        "in spices and chicken.",
                new float[]{164.9f, 0.0f, 46.0f, 5.0f},
                R.drawable.chicken_pho);

        Food egg_fried_rice = new Food("Egg fried rice",
                "abc",
                new float[]{73.4f, 20.0f, 68.9f, 35.0f},
                R.drawable.egg_fried_rice);

        Food soup = new Food("Sweet potato with black been soup",
                "abc",
                new float[]{126.4f, 61.0f, 26.3f, 21.1f},
                R.drawable.sweet_potato_black_bean_soup);

        foodList.add(grilled_chicken);
        foodList.add(pizza);
        foodList.add(pasta);
        foodList.add(melon_avo_strawberry_salad);
        foodList.add(apple_cucumber_salad);
        foodList.add(banh_mi);
        foodList.add(pho);
        foodList.add(egg_fried_rice);
        foodList.add(soup);

        customAdapter = new CustomAdapter(foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);

        customAdapter.setOnClickListener(this); // Link adapter with click listener
    }

    @Override
    protected void onResume() {
        super.onResume();

        selectedFoodCount.setText(String.format("You've selected %d food items", selectedFood.size()));

        backBtn.setOnClickListener(v -> {
            Intent goHomepage = new Intent(FoodListActivity.this, HomepageActivity.class);
            goHomepage.putParcelableArrayListExtra("selected_food_list", selectedFood);
            setResult(RESULT_OK, goHomepage);
            finish();
        });
    }

    @Override
    public void onClick(View view, int pos) {
        Intent goDetailView = new Intent(FoodListActivity.this, FoodDetailActivity.class);
        goDetailView.putExtra("food_details", foodList.get(pos));
//        launcher.launch(goDetailViewIntent);
        startActivityForResult(goDetailView, 500);
    }

    public void filterList(String text) {
        List<Food> filteredList = new ArrayList<>();

        for (Food food: foodList) {
            if (food.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(food);
            } else {
                customAdapter.setFilteredList(filteredList);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                
                if (data.getParcelableExtra("added_food") != null) {
                    Food tempFood = data.getParcelableExtra("added_food");
                    selectedFood.add(tempFood);
                    Toast.makeText(this, String.format("%s has been added. %d", tempFood.getName(), selectedFood.size()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "DEBUG: No food added", Toast.LENGTH_SHORT).show();
                }
                
            }
        }
    }
}
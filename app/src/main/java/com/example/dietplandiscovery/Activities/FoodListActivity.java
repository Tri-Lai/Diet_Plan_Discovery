package com.example.dietplandiscovery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietplandiscovery.Helper.CustomAdapter;
import com.example.dietplandiscovery.Helper.ItemClickListener;
import com.example.dietplandiscovery.Model.Food;
import com.example.dietplandiscovery.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodListActivity extends AppCompatActivity implements ItemClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Food> foodList;
    private CustomAdapter customAdapter;
    private SearchView searchView;
    private TextView selectedFoodCount;
    private ImageButton backBtn;
    private ArrayList<Food> selectedFood;
    private ImageView mealBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        selectedFoodCount = (TextView) findViewById(R.id.text_currentSelectedFoodItems);
        backBtn = (ImageButton) findViewById(R.id.button_back);
        mealBackground = (ImageView) findViewById(R.id.image_header);

        foodList = new ArrayList<>();
        selectedFood = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_foodList);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.clearFocus();


        Intent i  = getIntent();
        handleIncomingIntent(i);

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

        createSampleData();

        customAdapter = new CustomAdapter(foodList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);

        customAdapter.setOnClickListener(this); // Link adapter with click listener
    }

    private void handleIncomingIntent(Intent intent) throws NullPointerException{
        String meal = (String) Objects.requireNonNull(intent.getExtras()).get("meal_background");

        try {
            switch (meal) {
                case "breakfast":
                    mealBackground.setImageResource(R.drawable.breakfast_background);
                    break;
                case "lunch":
                    mealBackground.setImageResource(R.drawable.lunch_background);
                    break;
                case "dinner":
                    mealBackground.setImageResource(R.drawable.dinner_background);
                    break;
            }
        } catch (NullPointerException e) {
            Log.d("BUG", "Meal type is missing.");
            e.printStackTrace();
        }

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
                try {
                    if (data.getParcelableExtra("added_food") != null) {
                        Food tempFood = data.getParcelableExtra("added_food");
                        selectedFood.add(tempFood);
                        Toast.makeText(this, String.format("%s has been added. %d", tempFood.getName(), selectedFood.size()), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "DEBUG: No food added", Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    Log.d("Bug", "Added food not found.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void createSampleData() {
        Food grilled_chicken = new Food("Grilled chicken",
                "Chicken is the most popular poultry in the world - and for good reason! " +
                        "It’s low in calories, low in fat and has a high nutritional value. " +
                        "Grilled chicken is also a great source of protein. People who get " +
                        "enough of this nutrient are more likely to maintain muscle mass and " +
                        "support a healthy metabolism.",
                new float[]{284.0f, 0.0f, 57.0f, 6.5f},
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
                new float[]{285.0f, 36.0f, 12.0f, 10.0f},
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
                new float[]{196.0f, 38.0f, 7.2f, 1.2f},
                R.drawable.pasta);

        Food melon_avo_strawberry_salad = new Food("Melon, Avocado and Strawberry salad",
                "A healthy summer salad recipe, featuring watermelon, strawberries, and "+
                        "avocados, and topped with fresh mint and a sherry-orange vinaigrette.\n",
                new float[]{202.0f, 24.0f, 3.0f, 8.0f},
                R.drawable.melon_avo_strawberry_salad);

        Food apple_cucumber_salad = new Food("Apple Cucumber salad",
                "Fresh cucumbers and apples are tossed in a delicious and light dressing "+
                        "of lemon juice, apple cider vinegar, and honey, then seasoned with salt "+
                        "and pepper. The result is a refreshing and flavorful salad that is "+
                        "perfect for summertime. Scratch that, this salad is ideal for any time of year!\n" +
                        "\n" +
                        "It is so easy to make, that you'll want to keep the ingredients on hand "+
                        "at all times so you can whip it up whenever the mood strikes. Cucumbers "+
                        "and apples are a classic flavor combination, that makes a great and quick salad." +
                        "This salad is not only delicious, but it is also healthy! Cucumbers and "+
                        "apples are both low in calories and fat. They are also a good source of "+
                        "fiber and high in antioxidants which helps to protect the body from free "+
                        "radicals. This salad is a great way to get your daily dose of fruit and vegetables.\n" +
                        "\n" +
                        "The dressing for this salad is made with apple cider vinegar and honey, "+
                        "which has many health benefits. Apple cider vinegar has been shown to "+
                        "lower cholesterol, improve digestion, and boost the immune system. It is "+
                        "also a good source of vitamins and minerals.",
                new float[]{132.3f, 25.6f, 2.2f, 0.5f},
                R.drawable.apple_cucumber_salad);

        Food banh_mi = new Food("Bánh mì",
                "Bánh Mì is primarily a food item that has most of its calories coming from " +
                        "carbs and some fats. There are many fatty meats used in Banh Mi such as" +
                        " BBQ Pork, pate, or roast beef which can contribute to the already high " +
                        "calorie dish. \n" +
                        "Bánh Mì is also an excellent amount of protein and a fair number of fat grams. "+
                        "The only potentially questionable facet of Bánh Mì is the carbohydrates.",
                new float[]{600.0f, 47.0f, 50.0f, 23.0f},
                R.drawable.banh_mi);

        Food pho = new Food("Phở gà",
                "Phở is a Vietnamese soup that’s made with all the good stuff: "+
                        "broth, noodles, beef (or other protein) and lots of mix-and-match "+
                        "toppings. The best thing about Phở is that it’s such a restorative "+
                        "food—the comforting soup is savory and rich, while still feeling clean "+
                        "and refreshing at the same time. But don’t just save it for cold days: "+
                        "Phở can be enjoyed year-round—and any time of day. In Vietnam, Phở is a "+
                        "popular breakfast, and for good reason: It's super satisfying!\n" +
                        "\n" +
                        "No one really knows the exact origins of phở, but it first became "+
                        "popular in North Vietnam and migrated to the South when the country was "+
                        "divided in 1954. After the war, refugees brought Phở with them overseas.\n",
                new float[]{638.0f, 78.0f, 47.0f, 14.0f},
                R.drawable.chicken_pho);

        Food egg_fried_rice = new Food("Egg fried rice",
                "Egg fried rice is the simplest of fried rice variations that you'll find."+
                        " The basic version is nothing but cooked rice, beaten eggs, spring onions "+
                        "or scallions, oil and soy sauce cooked on high heat in a wok. You'll find"+
                        "many versions of this egg and rice recipe.\n" +
                        "Some make a quick and plain dish with no other additions other than soy"+
                        " sauce and scallions. Many versions use Chinese seasoning or specific spices" +
                        " and aromatics like garlic, celery, star anise, mace or five spice.",
                new float[]{238.0f, 45.0f, 5.5f, 4.1f},
                R.drawable.egg_fried_rice);

        Food chipotle_burrito = new Food("Chipotle Burrito Bowl",
                "Your choice of freshly grilled meat or sofritas served in a delicious bowl " +
                        "with rice, beans, or fajita veggies, and topped with guac, salsa, queso" +
                        " blanco, sour cream or cheese.",
                    new float[]{665.0f, 68.0f, 51.0f, 23.0f},
                    R.drawable.chipotle_chicken_bowls
                );

        Food soup = new Food("Sweet potato with black been soup",
                "Sweet potato black bean soup is an easy dinner recipe that's hearty and "+
                        "filling, and just so happens to be vegan friendly too! The delicate "+
                        "flavor of sweet potato is complemented by aromatic ginger, garlic,"+
                        " paprika and cumin.",
                new float[]{600.0f, 47.0f, 50.0f, 23.0f},
                R.drawable.sweet_potato_black_bean_soup);

        Food keto_beef_stew = new Food("Keto Beef Stew",
                "A rich tasting keto beef stew featuring meltingly tender beef chuck and "+
                        "perfectly cooked vegetables. You’ll never believe this is a low carb "+
                        "stew recipe. Your ketogenic diet just got better.\n\n" +
                        "This Gluten-free stew recipe is perfect for those on a low carb keto diet. "+
                        "Making beef stew without flour or thickeners reduces carbs while "+
                        "intensifying overall flavor. To make it truly low carb, choosing low "+
                        "carb vegetables is a must (see a complete list of low carb foods here). "+
                        "But properly cooking the stew is equally important.\n\n" +
                        "The ideal low carb stew should feature fork tender beef and tender "+
                        "(not mushy) vegetables in a meaty tasting broth.",
                new float[]{408.5f, 11.8f, 25.0f, 27.9f},
                        R.drawable.keto_beef_stew);

        Food baked_salmon = new Food("Baked Salmon",
                " This is the very best, Easy Healthy Baked Salmon! Made with lemon and "+
                        "garlic for incredible flavor and baked in the oven for flaky tenderness, "+
                        "this tasty salmon recipe is the answer to busy nights and special occasions alike!\n" +
                        "Salmon is rich in omega-3 fatty acids, which can help reduce inflammation, "+
                        "lower blood pressure, and decrease risk factors for diseases.\n" +
                        "It’s a great source of protein, potassium, and selenium, a mineral "+
                        "that protects bone health, improves thyroid function, and reduces the "+
                        "risk of cancer.\n" +
                        "Salmon can also help reduce the risk of heart disease, aid in weight "+
                        "control, and protect brain health.",
                new float[]{468.0f, 0.0f, 50.0f, 28.0f},
                R.drawable.baked_salmon);

         Food tofu_stir_fry = new Food("Tofu Stir Fry",
                "Every bite of this Tofu Stir Fry will transport you to your favorite Chinese"+
                        " restaurant! Packed with gourmet flavors, it’s deeply savory, a little "+
                        "spicy, and perfectly saucy. Easy to customize with your favorite vegetables!\n\n" +
                        "Tofu is good for health as it can:\n" +
                        "1. Provides protective plant compounds\n" +
                        "2. Rich in nutrients\n" +
                        "3. Alleviate peri-menopausal symptoms\n" +
                        "4. Support heart health\n" +
                        "5. Help manage cholesterol\n" +
                        "6. Support blood sugar management\n" +
                        "7. Support bone health\n" +
                        "8. Lift mood\n" +
                        "9. Reduce the risk of certain cancers",
                new float[]{162.0f, 11.0f, 12.0f, 9.5f},
                R.drawable.tofu_stir_fry);

         Food bun_cha = new Food("Bún chả",
            "Bún chả is a Vietnamese dish of grilled pork and noodles, which is thought "+
                    "to have originated from Hanoi, Vietnam. Bún chả is served with grilled "+
                    "fatty pork over a plate of white rice noodles and herbs with a side dish of dipping sauce." +
                    "To eat this dish in an authentic way, serve the meat and broth in one bowl, "+
                    "and the rice noodles on a plate next to it. Place separate bowls with the "+
                    "chopped garlic, sliced chilli, fresh herbs and salad leaves around it.\n" +
                    "\n" +
                    "Each person first seasons their broth to taste with garlic and chilli. "+
                    "Then, fill a small bowl with lettuce, herbs and some noodles and spoon "+
                    "some meat and some tasty liquid over it. If necessary, adjust the flavours "+
                    "by adding extra garlic, fresh chilli or chilli sauce. Stir gently and taste.\n" +
                    "\n" +
                    "You can also eat bun cha in a wrap. Take a lettuce leaf, and put some herbs "+
                    "and noodles in it. Scoop some pork from the broth and place on the noodles. "+
                    "Roll the lettuce leaf as a wrap and dip it in the broth before each bite. "+
                    "Hold an empty bowl under your chin to catch any drops and, to be sure, "+
                    "prepare a stack of paper napkins.",
            new float[]{810.0f, 60.0f, 38.0f, 46.0f},
            R.drawable.bun_cha);

        foodList.add(grilled_chicken);
        foodList.add(pizza);
        foodList.add(pasta);
        foodList.add(melon_avo_strawberry_salad);
        foodList.add(apple_cucumber_salad);
        foodList.add(banh_mi);
        foodList.add(pho);
        foodList.add(egg_fried_rice);
        foodList.add(soup);
        foodList.add(chipotle_burrito);
        foodList.add(keto_beef_stew);
        foodList.add(baked_salmon);
        foodList.add(tofu_stir_fry);
        foodList.add(bun_cha);
    }
}
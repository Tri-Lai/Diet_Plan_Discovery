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

package com.example.dietplandiscovery.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietplandiscovery.Model.Food;
import com.example.dietplandiscovery.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.FoodListViewHolder> {
    // Variables and Widgets
    private List<Food> foodList;
    public ItemClickListener clickListener;
    public CustomAdapter(ArrayList<Food> foods) {
       this.foodList = foods;
    }

    public void setFilteredList(List<Food> filteredList) {
        this.foodList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food_layout, parent, false);
        return new FoodListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.text_cardTitle.setText(food.getName());
        holder.text_cardNutritionVal.setText(Float.toString(food.getCalories()));
        holder.image_cardImage.setImageResource(food.getImgUrl());
//        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    /**
     * Create a view holder to xml file
     */
    public class FoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Widgets
        TextView text_cardTitle, text_cardNutritionVal;
        ImageView image_cardImage;
        CardView cardView;


        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);

            text_cardTitle = itemView.findViewById(R.id.text_cardTitle);
            text_cardNutritionVal = itemView.findViewById(R.id.text_cardNutritionVal);
            image_cardImage = itemView.findViewById(R.id.image_cardImage);
            cardView = (CardView) itemView.findViewById(R.id.layout_container_cardItem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }

    public void setOnClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}

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

package com.example.dietplandiscovery.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Food Model
 */
public class Food implements Parcelable {

    // Attributes
    private String name;
    private String desc;
    private float[] nutrition;
    private int img;

    // Methods
    public Food(String name, String desc, float[] nutrition, int img) {
        this.name = name;
        this.desc = desc;
        this.nutrition = nutrition;
        this.img = img;
    }

    protected Food(Parcel in) {
        name = in.readString();
        desc = in.readString();
        nutrition = in.createFloatArray();
        img = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float[] getNutrition() {
        return nutrition;
    }

    public float getCalories() {
        return nutrition[0];
    }

    public void setNutrition(float[] nutrition) {
        this.nutrition = nutrition;
    }

    public int getImgUrl() {
        return img;
    }

    public void setImgUrl(int imgUrl) {
        this.img = imgUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeFloatArray(nutrition);
        dest.writeInt(img);
    }
}

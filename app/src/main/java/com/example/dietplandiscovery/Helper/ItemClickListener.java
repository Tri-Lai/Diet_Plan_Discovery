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

import android.view.View;

/**
 * Handle item click in Recycler view
 */
public interface ItemClickListener {
    void onClick(View view, int pos);
}

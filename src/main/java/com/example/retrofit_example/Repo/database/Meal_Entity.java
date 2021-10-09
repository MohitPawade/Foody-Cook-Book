package com.example.retrofit_example.Repo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Meal_Entity {

    @PrimaryKey(autoGenerate =  true)
    private int id;

    @ColumnInfo(name = "Meal")
    private String mealName;

    @ColumnInfo(name = "Category")
    private String mealCategory;

    @ColumnInfo(name = "Instructions")
    private String mealInstruction;



    @ColumnInfo(name = "Meal Thumb")
    private String mealThumb;


    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getMealInstruction() {
        return mealInstruction;
    }

    public void setMealInstruction(String mealInstruction) {
        this.mealInstruction = mealInstruction;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public void setMealThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

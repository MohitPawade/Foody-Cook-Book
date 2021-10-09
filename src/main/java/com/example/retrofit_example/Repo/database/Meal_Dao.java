package com.example.retrofit_example.Repo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Meal_Dao {

    @Insert
    public void addMeal(Meal_Entity meal);

    @Delete
    public int deleteMeal(Meal_Entity meal);

    @Query("select * from meal_entity")
    public LiveData<List<Meal_Entity>> getAllMeals();

    @Query("select count(*) from meal_entity WHERE Meal = :mealName;")
    public int exists(String mealName);
}

package com.example.retrofit_example.Repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.retrofit_example.Repo.database.Meal_Dao;
import com.example.retrofit_example.Repo.database.Meal_Entity;
import com.example.retrofit_example.Repo.fromApi.Api;
import com.example.retrofit_example.Repo.fromApi.ListOfMeal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static final String TAG = "Repository";


    private Api api;
    private MutableLiveData<ListOfMeal> listOfMeals = new MutableLiveData<>();

    private Meal_Dao meal_dao;
    private LiveData<List<Meal_Entity>> meals = new MutableLiveData<>();

    public Repository(Application application) {
        api = RetrofitBuilder.getApi();
        meal_dao = Meal_Database.getInstance(application).meal_dao();
    }

    public MutableLiveData<ListOfMeal> getResultLiveData() {
        return listOfMeals;
    }

    public void getMeal() {
        Log.i(TAG, "Send API call");
        api.getRandomMeal().enqueue(new Callback<ListOfMeal>() {
            @Override
            public void onResponse(Call<ListOfMeal> call, Response<ListOfMeal> response) {
                Log.i(TAG, "Success" + response.isSuccessful());
                if (response.isSuccessful()) {
                    listOfMeals.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<ListOfMeal> call, Throwable t) {
                Log.i(TAG, "Failed");
            }
        });
    }

    /*
    Perform Database Operations
    * */

    //Add meal from database
    public void addMeal(final Meal_Entity meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Check if entry already exists
                int count = meal_dao.exists(meal.getMealName());
                Log.d(TAG, "Exists:  " + count);
                if (count > 0) {
                    return;
                }
                meal_dao.addMeal(meal);
            }
        }).start();
    }

    //Delete meal from database
    public void deleteMeal(final Meal_Entity meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Check if entry already exists
                int count = meal_dao.exists(meal.getMealName());
                Log.d(TAG, "Exists:  " + count);
                if (count == 0) {
                    return;
                }
                int delete = meal_dao.deleteMeal(meal);
                Log.d(TAG, "Delete: " + delete);

            }
        }).start();
    }

    // Get All Bookmarked meals
    public LiveData<List<Meal_Entity>> getAllMeal() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                meals = meal_dao.getAllMeals();
            }
        }).start();
        return meals;
    }
}



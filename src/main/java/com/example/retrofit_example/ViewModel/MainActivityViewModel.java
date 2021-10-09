package com.example.retrofit_example.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.retrofit_example.Repo.Repository;
import com.example.retrofit_example.Repo.database.Meal_Entity;
import com.example.retrofit_example.Repo.fromApi.ListOfMeal;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private Repository repo;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new Repository(application);
    }

    private MutableLiveData<ListOfMeal> listOfMeals = new MutableLiveData<>();

    /*
     * Initiate Api operations
     * */
    public MutableLiveData<ListOfMeal> getResult() {
        listOfMeals = repo.getResultLiveData();
        return listOfMeals;
    }

    public void sendAPICall() {
        if (listOfMeals.getValue() == null) {
            repo.getMeal();
        } else {
            listOfMeals.postValue(listOfMeals.getValue());
        }
    }

    /*
     * Initiate Database Operations
     * */
    public void addMeal(Meal_Entity meal) {
        repo.addMeal(meal);
    }

    public void deleteMeal(Meal_Entity meal) {
        repo.deleteMeal(meal);
    }

    public LiveData<List<Meal_Entity>> getAllMeals() {
        return repo.getAllMeal();
    }
}


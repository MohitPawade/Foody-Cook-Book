package com.example.retrofit_example.Repo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.retrofit_example.Repo.database.Meal_Dao;
import com.example.retrofit_example.Repo.database.Meal_Entity;

@Database(entities = Meal_Entity.class, version = 1, exportSchema = false)
public abstract class Meal_Database extends RoomDatabase {

    private static final String db_name = "Meal Database";
    private static Meal_Database instance;

    public abstract Meal_Dao meal_dao();

    public static synchronized Meal_Database getInstance(Context context) {

        if (instance == null){
            instance = Room.databaseBuilder(context,Meal_Database.class,db_name)
                    .fallbackToDestructiveMigration()
                    .build();
        }
            return instance;
    }


}

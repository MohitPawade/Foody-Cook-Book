package com.example.retrofit_example;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.retrofit_example.Repo.database.Meal_Entity;
import com.example.retrofit_example.Repo.fromApi.ListOfMeal;
import com.example.retrofit_example.Repo.fromApi.Meal;
import com.example.retrofit_example.ViewModel.MainActivityViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView receipe_name, receipe_category, receipe_instructions;
    private ImageView image, bookmark_receipe;
    private boolean flag = false;
    private Meal_Entity meal;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receipe_name = findViewById(R.id.receipe_name);
        receipe_category = findViewById(R.id.receipe_category);
        receipe_instructions = findViewById(R.id.receipe_instructions);
        image = findViewById(R.id.receipe_image);
        bookmark_receipe = findViewById(R.id.bookmark_receipe);

        meal = new Meal_Entity();
        //Add receipe to Database after bookmarked
        bookmark_receipe.setOnClickListener(this);

        //meals = new ArrayList<Meal>();
        //Create instance of MainActivityViewModel
        viewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(MainActivityViewModel.class);

        viewModel.getResult().observe(this, new Observer<ListOfMeal>() {
            @Override
            public void onChanged(ListOfMeal listOfMeal) {
                //ToDo: set HMI here
                for (Meal m : listOfMeal.getMeals()) {
                    Log.d("#m", "List Size : " + listOfMeal.getMeals().size());

                    receipe_name.setText(m.getStrMeal());
                    receipe_category.setText(m.getStrCategory());
                    receipe_instructions.setText(m.getStrInstructions());


                    //To load the image we will use Picasso library
                    Uri imageUri = Uri.parse(m.getStrMealThumb());
                    Picasso.get().load(imageUri).into(image);

                    //Tobe stored in Database if bookmarked
                    meal.setMealName(m.getStrMeal());
                    meal.setMealCategory(m.getStrCategory());
                    meal.setMealInstruction(m.getStrInstructions());
                    meal.setMealThumb(m.getStrMealThumb());

                }


            }
        });
        viewModel.sendAPICall();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.scroll_view, null);

        final TextView textview = view.findViewById(R.id.textmsg);

        viewModel.getAllMeals().observe(this, new Observer<List<Meal_Entity>>() {


            @Override
            public void onChanged(List<Meal_Entity> meal_entities) {
                String allMeals = "";
                for (Meal_Entity meal : meal_entities) {
                    String content = "";
                    content += "Meal:  " + meal.getMealName() + "\n";
                    content += "Meal Category:  " + meal.getMealCategory() + "\n";
                    content += "Meal Instructions:  " + meal.getMealInstruction() + "\n \n";
                    content += "**********" + "\n\n\n";
                    allMeals += content;
                }
                textview.setText(allMeals);
                Log.d(TAG, "onOptionsItemSelected:  All meals are: =====>" + allMeals);
            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Bookmarked Meals")
                .setView(view)
                .setNegativeButton(android.R.string.yes, null);
        AlertDialog alert = builder.create();
        alert.show();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (flag) {
            bookmark_receipe.setImageResource(R.drawable.favourite_size1_border_foreground);
            flag = false;
            //ToDo: Remove meal from Database
            viewModel.deleteMeal(meal);
        } else {
            bookmark_receipe.setImageResource(R.drawable.favourite_size1_foreground);
            flag = true;
            //ToDo: Add Meal to Database
            viewModel.addMeal(meal);
        }

    }
}

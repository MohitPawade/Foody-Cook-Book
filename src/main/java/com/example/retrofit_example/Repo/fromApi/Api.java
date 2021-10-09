package com.example.retrofit_example.Repo.fromApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    //Api call to get Random meal

    @GET("json/v1/1/random.php")
    Call<ListOfMeal> getRandomMeal();

}
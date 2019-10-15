package com.example.pakingapp.Utils;


import com.example.pakingapp.Recipe;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;


public interface JSON {
    @GET("baking.json")
    Call< ArrayList<Recipe> > getRecipe();
}

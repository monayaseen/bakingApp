package com.example.pakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.example.pakingapp.Utils.JSON;
import com.example.pakingapp.Utils.retrofitBuilder;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements rAdapter.ListItemClickListener {

    static String ALL_RECIPES="All_Recipes";
    static String SELECTED_RECIPES="Selected_Recipes";
    static String SELECTED_STEPS="Selected_Steps";
    static String SELECTED_INDEX="Selected_Index";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList< Recipe > mRecipes;

    @Nullable
    private simpleResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new simpleResource();
        }
        return mIdlingResource;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView) findViewById(R.id.rv_recipes);
        final rAdapter recipesAdapter = new rAdapter(this);
        recyclerView.setAdapter(recipesAdapter);
        int gridColumnNumber = getResources().getInteger(R.integer.grid_column_number);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), gridColumnNumber);

        getIdlingResource();




        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        if(networkAvilable()) {
            if (savedInstanceState != null) {
                mRecipes = savedInstanceState.getParcelableArrayList("recipes");
                recipesAdapter.setRecipeData(mRecipes, getApplicationContext());
            } else {

                JSON iRecipe = retrofitBuilder.Retrieve();
                Call<ArrayList<Recipe>> recipe = iRecipe.getRecipe();

                final simpleResource idlingResource = (simpleResource) ((this)).getIdlingResource();

                if (idlingResource != null) {
                    idlingResource.setIdleState(false);
                }

                recipe.enqueue(new Callback<ArrayList<Recipe>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                        Integer statusCode = response.code();
                        Log.v("status code: ", statusCode.toString());

                        mRecipes = response.body();
                        Log.d("response body", response.body().get(1).getName());
                        Log.d("the first recipe:", mRecipes.get(0).getName());
                        mRecipes.get(0).setImage("https://i0.wp.com/bakingamoment.com/wp-content/uploads/2015/03/4255featured2.jpg?w=720&ssl=1");
                        mRecipes.get(1).setImage("http://food.fnr.sndimg.com/content/dam/images/food/fullset/2011/10/26/0/CCNGK106_Everyday-Brownies-Recipe_s4x3.jpg.rend.hgtvcom.616.462.suffix/1371600958002.jpeg");
                        mRecipes.get(2).setImage("https://assets.epicurious.com/photos/57c5b45184c001120f616523/master/pass/moist-yellow-cake.jpg");
                        mRecipes.get(3).setImage("http://img.taste.com.au/O8JC4F3Q/taste/2016/11/new-york-cheesecake-40742-1.jpeg");
                        Bundle recipesBundle = new Bundle();
                        recipesBundle.putParcelableArrayList(ALL_RECIPES, mRecipes);

                        recipesAdapter.setRecipeData(mRecipes, getApplicationContext());


                        if (idlingResource != null) {
                            idlingResource.setIdleState(true);
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                        Log.v("http fail: ", t.getMessage());
                    }
                });
            }
        }else {

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }






    }

    @Override
    public void onListItemClick(Recipe selectedItemIndex) {

        Bundle selectedRecipeBundle = new Bundle();
        ArrayList<Recipe> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(selectedItemIndex);
        selectedRecipeBundle.putParcelableArrayList(SELECTED_RECIPES,selectedRecipe);

        final Intent intent = new Intent(this, Details.class);
        intent.putExtras(selectedRecipeBundle);
        startActivity(intent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("recipes", mRecipes );
    }

    public boolean networkAvilable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


}

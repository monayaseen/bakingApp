package com.example.pakingapp;

//public class Details {
//}

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;


public class  Details extends AppCompatActivity implements rsAdapter.ListItemClickListener,StepsDetailFraagment.ListItemClickListener{

    ArrayList<Recipe> recipe;
    static boolean mTwoPane;
    static String ALL_RECIPES="All_Recipes";
    static String SELECTED_RECIPES="Selected_Recipes";
    static String SELECTED_STEPS="Selected_Steps";
    static String SELECTED_INDEX="Selected_Index";
    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";
    static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";
   DetailsFragment recipeDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        recipe = new ArrayList<>();

        FrameLayout details = (FrameLayout) findViewById(R.id.fragment_container2);
        mTwoPane = (details!=null);


        if (savedInstanceState == null) {
            recipeDetailsFragment = new DetailsFragment();
        } else {
            recipeDetailsFragment = (DetailsFragment)
                    getSupportFragmentManager().getFragment(savedInstanceState, "my_fragment");
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, recipeDetailsFragment)
                .commit();





    }


    @Override
    public void onListItemClick(List<Steps> stepsOut, int clickedItemIndex) {

        final StepsDetailFraagment fragment = new StepsDetailFraagment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS, (ArrayList<Steps>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX, clickedItemIndex);

        fragment.setArguments(stepBundle);
        if (mTwoPane) {


            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment)
                    .commit();
        } else {


            final Intent intent = new Intent(this, Details.class);
            intent.putExtras(stepBundle);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "my_fragment", recipeDetailsFragment);

    }
}

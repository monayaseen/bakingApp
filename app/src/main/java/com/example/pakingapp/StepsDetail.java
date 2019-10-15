package com.example.pakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.pakingapp.Details.SELECTED_INDEX;
import static com.example.pakingapp.Details.SELECTED_STEPS;


public class StepsDetail extends AppCompatActivity implements rsAdapter.ListItemClickListener,StepsDetailFraagment.ListItemClickListener {

    StepsDetailFraagment recipeDetailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_details);
        if (savedInstanceState == null) {
            recipeDetailsFragment = new StepsDetailFraagment();
        } else {
            recipeDetailsFragment = (StepsDetailFraagment) getSupportFragmentManager().getFragment(savedInstanceState, "my_fragment");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_fragment, recipeDetailsFragment)
                .commit();




        if (recipeDetailsFragment==null || ! recipeDetailsFragment.isInLayout()) {

        }

    }

    @Override
    public void onListItemClick(List<Steps> stepsOut, int clickedItemIndex) {

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Steps>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX,clickedItemIndex);


        final Intent intent = new Intent(this,Details.class);
        intent.putExtras(stepBundle);
        startActivity(intent);
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

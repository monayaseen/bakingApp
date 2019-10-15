package com.example.pakingapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class riAdapter extends RecyclerView.Adapter<riAdapter.RecipeIngredientViewHolder>  {



    private Context mContext;
    public static int mPosition;
    private List<Ingredient> ingredient;

    public riAdapter(Context context , List<Ingredient> ingredientIn){
        mContext = context;
        ingredient = ingredientIn;
        Log.d("adapter",ingredient.get(0).getIngredient());
    }


    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForIngredients = R.layout.ingradient_detail;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutIdForIngredients , parent , attachImmediately);
        RecipeIngredientViewHolder recipeIngredientViewHolder = new RecipeIngredientViewHolder(view);

        return  recipeIngredientViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeIngredientViewHolder holder, int position) {
        holder.recipe_ingredient_name.setText(ingredient.get(position).getIngredient());
        holder.recipe_ingredient_quentity.setText(ingredient.get(position).getQuantity()+"");

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }

    class RecipeIngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipe_ingredient_name;
        TextView recipe_ingredient_quentity;


        public RecipeIngredientViewHolder(View itemView) {
            super(itemView);

            recipe_ingredient_name = (TextView) itemView.findViewById(R.id.recipe_ingredient_name);
            recipe_ingredient_quentity = (TextView) itemView.findViewById(R.id.recipe_ingredient_quantity);

            // itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }
}

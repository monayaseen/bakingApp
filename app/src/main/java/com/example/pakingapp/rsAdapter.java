package com.example.pakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class rsAdapter extends RecyclerView.Adapter<rsAdapter.RecipeStepViewHolder> {

    private Context mContext;
    private List<Steps> mSteps;
    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Steps> stepsOut,int clickedItemIndex);
    }

    public rsAdapter(ListItemClickListener listener){
        lOnClickListener = listener;
    }



    public void setRecipeData(Context context, List<Steps> stepIn){
        mContext = context;
        mSteps = stepIn;

    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForIngredients = R.layout.steps_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachImmediately = false;

        View view = inflater.inflate(layoutIdForIngredients , parent , attachImmediately);
        RecipeStepViewHolder recipeStepViewHolder = new RecipeStepViewHolder(view);

        return  recipeStepViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {
        holder.recipeStepTitle.setText(mSteps.get(position).getShortDescription());
//        Picasso.with(mContext)
//                .load(mSteps.get(position).getVideoURL())
//                .into(holder.recipeStepImage);

    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recipeStepTitle;
        //ImageView recipeStepImage;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            recipeStepTitle = (TextView) itemView.findViewById(R.id.recipe_step_title);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(mSteps , clickedPosition);

        }
    }
}


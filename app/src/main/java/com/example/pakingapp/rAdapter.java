package com.example.pakingapp;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class rAdapter extends RecyclerView.Adapter<rAdapter.RecyclerViewHolder> {

    int i=0;
    ArrayList<Recipe> lRecipes;
    Context mContext;
    final private ListItemClickListener lOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Recipe clickedItemIndex);
    }

    public rAdapter(ListItemClickListener listener) {
        lOnClickListener =listener;
    }


    public void setRecipeData(ArrayList<Recipe> recipesIn, Context context) {

        lRecipes = recipesIn;
        mContext=context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;

        Log.d("count" , i+"");

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup,  false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);

        i++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textRecyclerView.setText(lRecipes.get(position).getName());
        String imageUrl=lRecipes.get(position).getImage();

        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.get().load(builtUri).into(holder.imageRecyclerView);
        }

    }

    @Override
    public int getItemCount() {
        return lRecipes !=null ? lRecipes.size():0 ;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textRecyclerView;
        ImageView imageRecyclerView;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView.findViewById(R.id.title);
            imageRecyclerView = (ImageView) itemView.findViewById(R.id.recipeImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(lRecipes.get(clickedPosition));
        }

    }
}

package com.example.recipeazy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeViewHolder> {
    private List<Recipe> recipeList;

    public SearchRecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public SearchRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new SearchRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getRecipeTitle());
        holder.categoryTextView.setText(recipe.getCategory());
        // Load image using Picasso or Glide into holder.imageView
        Picasso.get()
                .load(recipe.getImageUrl())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click here
                Intent intent = new Intent(v.getContext(), ViewRecipes.class);
                intent.putExtra("recipeId", recipe.getRecipeId()); // Pass the clicked recipe's ID
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}

package com.example.recipeazy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SearchRecipeViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView, categoryTextView;
    public ImageView imageView;

    public SearchRecipeViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        imageView = itemView.findViewById(R.id.imageView);
        categoryTextView = itemView.findViewById(R.id.categoryTextView);
        // Add other views if needed
    }
}
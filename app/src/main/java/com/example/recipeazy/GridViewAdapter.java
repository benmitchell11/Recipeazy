package com.example.recipeazy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;
import com.squareup.picasso.Picasso;

public class GridViewAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> dataList;

    public GridViewAdapter(Context context, List<Recipe> dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
    }



// ...

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cardview_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Recipe recipe = dataList.get(position);

        // Bind data to the CardView items
        viewHolder.titleTextView.setText(recipe.getRecipeTitle());

        // Load image using Picasso
        Picasso.get()
                .load(recipe.getImageUrl()) // Assuming getImageUrl() returns the URL of the image
                //.placeholder(R.drawable.placeholder_image) // Optional placeholder image while loading
                //.error(R.drawable.error_image) // Optional error image if loading fails
                .into(viewHolder.imageView); // The ImageView where you want to load the image

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click here
                Intent intent = new Intent(v.getContext(), ViewRecipes.class);
                intent.putExtra("recipeId", recipe.getRecipeId()); // Pass the clicked recipe's ID
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }



    private static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
        // Add references to other views inside the CardView, e.g., TextView descriptionTextView;

        ViewHolder(View view) {
            titleTextView = view.findViewById(R.id.titleTextView);
            imageView = view.findViewById(R.id.imageView);
            // Initialize references to other views inside the CardView
            // descriptionTextView = view.findViewById(R.id.descriptionTextView);
        }
    }
}

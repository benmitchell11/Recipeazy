package com.example.recipeazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewRecipes extends AppCompatActivity {

    private TextView titleTextView;
    private TextView categoryTextView;
    private TextView prepTime;
    private TextView servingSize;
    private RecyclerView ingredientsRecyclerView;
    private RecyclerView instructionsRecyclerView;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        // Initialize your UI elements
        titleTextView = findViewById(R.id.titleTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        prepTime = findViewById(R.id.prepTime);
        servingSize = findViewById(R.id.servingSize);
        ingredientsRecyclerView = findViewById(R.id.ingredientsList);
        instructionsRecyclerView = findViewById(R.id.instructionsList);
        imageView = findViewById(R.id.imageView);

        // Get the recipeId passed from the previous activity
        String recipeId = getIntent().getStringExtra("recipeId");

        // Reference to your Firebase Database or Firestore
        DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference().child("recipes").child(recipeId);

        // Read data from Firebase and populate the UI
        recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Recipe recipe = dataSnapshot.getValue(Recipe.class);

                    // Set the retrieved data to your UI elements
                    titleTextView.setText(recipe.getRecipeTitle().toString());
                    categoryTextView.setText(recipe.getCategory().toString());
                    prepTime.setText(String.valueOf("Preparation Time: " + recipe.getPreparationTime() + " Minutes"));
                    servingSize.setText(String.valueOf(("Serves " + recipe.getServingSize() + " People")));
                    Picasso.get()
                            .load(recipe.getImageUrl())
                            .into(imageView);

                    // Set up RecyclerView adapters for ingredients and instructions
                    // Assuming you have Ingredient and Instruction classes for your data
                    List<Ingredient> ingredients = recipe.getIngredients();
                    List<String> instructions = recipe.getInstructions();

                    IngredientAdapter ingredientsAdapter = new IngredientAdapter(ingredients);
                    InstructionsAdapter instructionsAdapter = new InstructionsAdapter(instructions);

                    ingredientsRecyclerView.setAdapter(ingredientsAdapter);
                    instructionsRecyclerView.setAdapter(instructionsAdapter);

                    // Set layout managers for RecyclerViews if needed
                    ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(ViewRecipes.this));
                    instructionsRecyclerView.setLayoutManager(new LinearLayoutManager(ViewRecipes.this));
                } else {
                    // Handle the case where the recipe with the given ID does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}

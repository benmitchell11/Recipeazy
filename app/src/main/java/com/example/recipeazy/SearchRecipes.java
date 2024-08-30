package com.example.recipeazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchRecipes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchRecipeAdapter adapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private TextInputEditText searchBar;
    private String searchTerm;
    private Spinner categorySpinner;
    private String selectedCategory;
    private Boolean isGlutenFreeChecked = false;
    private Boolean isVeganChecked = false;
    private Boolean isVegetarianChecked = false;
    private Boolean isKetoChecked = false;
    private TextView searchReturned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);
        Log.d("RecipeListSize", String.valueOf(recipeList.size()));

        searchBar = findViewById(R.id.search_bar);
        categorySpinner = findViewById(R.id.categorySpinner);
        CheckBox isGlutenFree = findViewById(R.id.isGlutenFree);
        CheckBox isVegan = findViewById(R.id.isVegan);
        CheckBox isVegetarian = findViewById(R.id.isVegetarian);
        CheckBox isKeto = findViewById(R.id.isKeto);
        searchReturned = findViewById(R.id.searchReturned);


        isGlutenFree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGlutenFreeChecked = isChecked;
            }
        });

        isVegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVeganChecked = isChecked;
            }
        });

        isVegetarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVegetarianChecked = isChecked;
            }
        });

        isKeto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isKetoChecked = isChecked;
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            searchTerm = intent.getStringExtra("searchTerm");
            // Set the initial searchTerm to the searchBar
            searchBar.setText(searchTerm);
        }


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the selected category from the spinner
                selectedCategory = categorySpinner.getSelectedItem().toString();

                // Update the search results based on the selected category

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle when nothing is selected in the spinner (if needed)
            }
        });


        // Get the search button from the XML layout
        Button searchButton = findViewById(R.id.btn_search);

        // Set OnClickListener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text entered in the searchBar and update searchTerm
                searchTerm = searchBar.getText().toString();

                // Call the method to update the RecyclerView based on the new searchTerm
                updateSearchResults();
            }
        });


        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the list to avoid duplicates



                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    // Check if the recipe title contains the search term (case-insensitive)
                    if (recipe.getRecipeTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                        recipeList.add(recipe); // Add the recipe to the list
                    }

                }

                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                searchReturned.setText("Items Found: " + recipeList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });




        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchRecipeAdapter(recipeList);
        recyclerView.setAdapter(adapter);
    }

    private void updateSearchResults() {
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear(); // Clear the list to avoid duplicates

                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                    // Check if the recipe title contains the search term (case-insensitive),
                    // if the recipe belongs to the selected category,
                    // and if the recipe satisfies the checkbox conditions
                    if (recipe.getRecipeTitle().toLowerCase().contains(searchTerm.toLowerCase()) &&
                            (selectedCategory.equals("Any Category") || recipe.getCategory().equals(selectedCategory)) &&
                            (!isGlutenFreeChecked || recipe.isGlutenFree()) &&
                            (!isVeganChecked || recipe.isVegan()) &&
                            (!isVegetarianChecked || recipe.isVegetarian()) &&
                            (!isKetoChecked || recipe.isKeto())) {
                        recipeList.add(recipe); // Add the recipe to the list
                    }
                }

                adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
                searchReturned.setText("Items Found: " + recipeList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }



}
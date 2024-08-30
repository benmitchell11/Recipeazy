package com.example.recipeazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);

        Button searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = searchBar.getText().toString(); // Get the text from the search bar as a String
                Intent intent = new Intent(getApplicationContext(), SearchRecipes.class);
                intent.putExtra("searchTerm", searchTerm);
                startActivity(intent);
                finish();
            }
        });

        List<Recipe> dataList = new ArrayList<>();

        GridView gridView = findViewById(R.id.gridView);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, dataList); // Create your custom adapter with the data
        gridView.setAdapter(gridViewAdapter);

        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");

        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear(); // Clear the existing data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        dataList.add(recipe);
                    }
                }

                gridViewAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });


        Button addRecipeButton = findViewById(R.id.btn_add_recipe);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRecipe.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
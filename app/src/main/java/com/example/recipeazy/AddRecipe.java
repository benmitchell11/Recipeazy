package com.example.recipeazy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddRecipe extends AppCompatActivity {

    private LinearLayout ingredientsContainer, stepsContainer;
    private int ingredientCounter = 1;
    private int stepCounter = 1;
    private DatabaseReference recipesRef;

    private ImageView imageView;
    private boolean isStepButtonPressed = false;

    static final int PICK_IMAGE_REQUEST = 1;

    private String imageUrl;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ingredientsContainer = findViewById(R.id.ingredientsContainerOG);
        stepsContainer = findViewById(R.id.stepsContainer);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        recipesRef = database.getReference("recipes");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Button addImage = findViewById(R.id.btn_add_image);
        addImage.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        imageView = findViewById(R.id.imageView);


        // Override onActivityResult to handle the result of the image selection


        Button addIngredientButton = findViewById(R.id.btn_add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredientLayout();
            }
        });

        Button addStepButton = findViewById(R.id.btn_add_step);
        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewStepTextInput();
            }
        });

        Button addRecipe = findViewById(R.id.btn_add_recipe);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecipeToFirebase();
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // Handle the selected image URI (e.g., display it in an ImageView)
            imageView.setImageURI(selectedImageUri);

            uploadImageToFirebaseStorage(selectedImageUri);

            // Now, you can upload the selected image to Firebase Storage using the selectedImageUri
            // ...
        }
    }

    // Method to upload the image to Firebase Storage
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        // Create a storage reference
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        // Create a reference to the specific image file (e.g., using a random unique filename)
        String imageFileName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child("images/" + imageFileName);

        // Upload the image to Firebase Storage
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Register observers to listen for when the upload is successful or fails
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Image upload successful. You can get the download URL if needed:
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                imageUrl = uri.toString();

                // Handle the download URL (e.g., save it to the database)
            });
        }).addOnFailureListener(exception -> {
            // Image upload failed. Handle the error.
        });
    }

    private int generateViewId() {
        return View.generateViewId();
    }

    private void addIngredientLayout() {
        // Inflate the ingredient layout
        View ingredientLayout = LayoutInflater.from(this).inflate(R.layout.ingredient_text_input, null);

        // Find the Spinner and set its options
        Spinner ingredientUnitSpinner = ingredientLayout.findViewById(R.id.ingredientMeasureType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ingredient_measurements, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientUnitSpinner.setAdapter(adapter);

        // Add the inflated layout to the ingredients container
        ingredientsContainer.addView(ingredientLayout);
    }



    private void addNewStepTextInput() {

        View view = LayoutInflater.from(this).inflate(R.layout.step_text_input, null);



        view.setId(View.generateViewId());


        stepCounter++;

        // Set the hint for the new TextInputEditText
        TextInputEditText textInputEditText = view.findViewById(R.id.textInputEditText);
        textInputEditText.setHint("Step " + stepCounter);
        textInputEditText.requestFocus();


        stepsContainer.addView(view);
        isStepButtonPressed = true;
    }

    private String generateIngredientId() {

        return UUID.randomUUID().toString();
    }

    private List<Ingredient> getIngredientsList() {
        List<Ingredient> ingredientsList = new ArrayList<>();

        // Handle the original ingredient (ingredient 1)
        View originalIngredientLayout = findViewById(R.id.ingredientsContainerOG);
        TextInputEditText originalIngredientQuantEditText = originalIngredientLayout.findViewById(R.id.ingredientQuant1);
        Spinner originalIngredientMeasureSpinner = originalIngredientLayout.findViewById(R.id.ingredientMeasureType);
        TextInputEditText originalIngredientEditText = originalIngredientLayout.findViewById(R.id.ingredient);

        String originalIngredientQuant = originalIngredientQuantEditText.getText().toString();
        String originalIngredientMeasure = originalIngredientMeasureSpinner.getSelectedItem().toString();
        String originalIngredientName = originalIngredientEditText.getText().toString();

        // Create an Ingredient object with null fields if any of the fields are empty
        Ingredient originalIngredient = new Ingredient(generateIngredientId(),
                originalIngredientName.isEmpty() ? null : originalIngredientName,
                originalIngredientQuant.isEmpty() ? null : originalIngredientQuant,
                originalIngredientMeasure.isEmpty() ? null : originalIngredientMeasure);

        // Add the original ingredient to the list
        ingredientsList.add(originalIngredient);

        // Handle the dynamically added ingredients
        for (int i = 1; i < ingredientsContainer.getChildCount(); i++) {
            View ingredientLayout = ingredientsContainer.getChildAt(i);
            TextInputEditText ingredientQuantEditText = ingredientLayout.findViewById(R.id.ingredientQuant);
            Spinner ingredientMeasureSpinner = ingredientLayout.findViewById(R.id.ingredientMeasureType);
            TextInputEditText ingredientEditText = ingredientLayout.findViewById(R.id.ingredient);

            String ingredientQuant = ingredientQuantEditText.getText().toString();
            String ingredientMeasure = ingredientMeasureSpinner.getSelectedItem().toString();
            String ingredientName = ingredientEditText.getText().toString();

            // Create an Ingredient object with null fields if any of the fields are empty
            Ingredient ingredient = new Ingredient(generateIngredientId(),
                    ingredientName.isEmpty() ? null : ingredientName,
                    ingredientQuant.isEmpty() ? null : ingredientQuant,
                    ingredientMeasure.isEmpty() ? null : ingredientMeasure);

            // Add the dynamically added ingredient to the list
            ingredientsList.add(ingredient);
        }

        return ingredientsList;
    }





    private List<String> getStepsList() {
        List<String> stepsList = new ArrayList<>();

        // Handle the original step (step 1)
        View originalStepLayout = findViewById(R.id.stepsContainer);
        TextInputEditText originalStepEditText = originalStepLayout.findViewById(R.id.textInputEditText);

        String originalStep = originalStepEditText.getText().toString();

        // Add the original step to the list if it's not empty
        if (!originalStep.isEmpty()) {
            stepsList.add(originalStep);
        }

        // Handle the dynamically added steps
        for (int i = 1; i < stepsContainer.getChildCount(); i++) {
            View stepLayout = stepsContainer.getChildAt(i);
            TextInputEditText stepEditText = stepLayout.findViewById(R.id.textInputEditText);

            String step = stepEditText.getText().toString();

            // Add the dynamically added step to the list if it's not empty
            if (!step.isEmpty()) {
                stepsList.add(step);
            }
        }

        return stepsList;
    }




    private void saveRecipeToFirebase() {

        TextInputEditText addTitle = findViewById(R.id.title);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        CheckBox isGlutenFreeCB = findViewById(R.id.isGlutenFree);
        CheckBox isVeganCB = findViewById(R.id.isVegan);
        CheckBox isVegetarianCB = findViewById(R.id.isVegetarian);
        CheckBox isKetoCB = findViewById(R.id.isKeto);
        TextInputEditText prepTimeInput = findViewById(R.id.prepTime);
        TextInputEditText servingSizeInput = findViewById(R.id.servingSize);
        TextInputEditText ingredientQuant1 = findViewById(R.id.ingredientQuant1);
        Spinner ingredientMeasureType = findViewById(R.id.ingredientMeasureType);
        TextInputEditText ingredient1 = findViewById(R.id.ingredient);
        TextInputEditText step1 = findViewById(R.id.step1);
        String title = addTitle.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        boolean isGlutenFree = isGlutenFreeCB.isChecked();
        boolean isVegan = isVeganCB.isChecked();
        boolean isVegetarian = isVegetarianCB.isChecked();
        boolean isKeto = isKetoCB.isChecked();
        String prepTimeString = prepTimeInput.getText().toString();
        int prepTime = Integer.parseInt(prepTimeString);
        String servingSizeString = servingSizeInput.getText().toString();
        int servingSize = Integer.parseInt(servingSizeString);
        List<Ingredient> ingredientsList = getIngredientsList();
        List<String> instructions = getStepsList();

        String recipeId = recipesRef.push().getKey();


        Recipe recipe = new Recipe(title, recipeId, ingredientsList,instructions,prepTime,servingSize,isVegan,isGlutenFree,isKeto,isVegetarian,category,imageUrl );




        recipesRef.child(recipeId).setValue(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Recipe saved successfully, show a Toast message
                        Toast.makeText(AddRecipe.this, "Recipe added successfully!", Toast.LENGTH_SHORT).show();

                        // Return to the main activity
                        Intent intent = new Intent(AddRecipe.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


}

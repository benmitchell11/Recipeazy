package com.example.recipeazy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button signInButton = findViewById(R.id.btn_signin);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize Firebase Authentication
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        TextInputEditText emailEditText = findViewById(R.id.email);
        TextInputEditText usernameEditText = findViewById(R.id.username);
        TextInputEditText passwordEditText = findViewById(R.id.password);

        // Register button click listener
        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Register user with email and password
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration success
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    String username = usernameEditText.getText().toString().trim();
                                    // Save additional user details to the Realtime Database
                                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                    User user = new User(userId, false, false, null, null, email, username, null, 0, null, null, "");
                                    usersRef.child(userId).setValue(user);
                                    // Navigate to the next activity or perform necessary actions
                                    Log.d("Registration", "Registration successful!");
                                    Toast.makeText(Registration.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Registration failed
                                    Log.e("Registration", "Registration failed: " + task.getException().getMessage());
                                    Toast.makeText(Registration.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }
}
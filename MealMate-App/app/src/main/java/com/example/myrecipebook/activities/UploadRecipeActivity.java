package com.example.myrecipebook.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrecipebook.R;
import com.example.myrecipebook.models.Recipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadRecipeActivity extends AppCompatActivity {

    private EditText editTextName, editTextIngredients;
    private CheckBox breakfastCheckbox, lunchCheckbox, dinnerCheckbox, dessertCheckbox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload); // Ensure this matches your XML layout file name

        // Initialize UI components
        editTextName = findViewById(R.id.upload_name);
        editTextIngredients = findViewById(R.id.upload_ingredients);
        breakfastCheckbox = findViewById(R.id.breakfast_checkbox);
        lunchCheckbox = findViewById(R.id.lunch_checkbox);
        dinnerCheckbox = findViewById(R.id.dinner_checkbox);
        dessertCheckbox = findViewById(R.id.dessert_checkbox);
        saveButton = findViewById(R.id.saveButton);

        // Set up the save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });
    }

    private void saveRecipe() {
        String name = editTextName.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || ingredients.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Collect selected meal types
        StringBuilder mealTypes = new StringBuilder();
        if (breakfastCheckbox.isChecked()) {
            mealTypes.append("Breakfast ");
        }
        if (lunchCheckbox.isChecked()) {
            mealTypes.append("Lunch ");
        }
        if (dinnerCheckbox.isChecked()) {
            mealTypes.append("Dinner ");
        }
        if (dessertCheckbox.isChecked()) {
            mealTypes.append("Dessert ");
        }

        // Create a Recipe object
        Recipe recipe = new Recipe(name, ingredients, mealTypes.toString().trim());

        // Save the recipe to Firebase
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("recipes");
        String recipeId = databaseRef.push().getKey(); // Generate a unique key for the recipe

        databaseRef.child(recipeId).setValue(recipe).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(UploadRecipeActivity.this, "Recipe saved successfully!", Toast.LENGTH_SHORT).show();
                clearFields(); // Clear fields after saving
            } else {
                Toast.makeText(UploadRecipeActivity.this, "Failed to save recipe.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        editTextName.setText("");
        editTextIngredients.setText("");
        breakfastCheckbox.setChecked(false);
        lunchCheckbox.setChecked(false);
        dinnerCheckbox.setChecked(false);
        dessertCheckbox.setChecked(false);
    }
}
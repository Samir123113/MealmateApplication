package com.example.myrecipebook.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myrecipebook.R;
import com.example.myrecipebook.RecipeDatabaseHelper;
import com.example.myrecipebook.RecipeDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class UploadRecipeActivity extends AppCompatActivity {
    private EditText recipeTitle, recipeDescription, ingredientInput, stepInput, imageUrlInput;
    private NumberPicker prepTimePicker, servesPicker;
    private Button addIngredientButton, addStepButton, publishButton;
    private LinearLayout ingredientList, stepList;
    private List<String> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private RecipeDatabaseHelper dbHelper;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        dbHelper = new RecipeDatabaseHelper(this);

        // Initialize UI elements
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeDescription = findViewById(R.id.recipeDescription);
        ingredientInput = findViewById(R.id.ingredientInput);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        ingredientList = findViewById(R.id.ingredientList);
        stepInput = findViewById(R.id.stepInput);
        addStepButton = findViewById(R.id.addStepButton);
        stepList = findViewById(R.id.stepList);
        publishButton = findViewById(R.id.publishButton);
        prepTimePicker = findViewById(R.id.prepTimePicker);
        servesPicker = findViewById(R.id.servesPicker);
        rootView = findViewById(android.R.id.content);

        // Set up NumberPickers
        prepTimePicker.setMinValue(1);
        prepTimePicker.setMaxValue(100);
        servesPicker.setMinValue(1);
        servesPicker.setMaxValue(10);

        // Add ingredient button click listener
        addIngredientButton.setOnClickListener(v -> {
            String ingredient = ingredientInput.getText().toString().trim();
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
                ingredientInput.setText("");
                updateIngredientList();
            }
        });

        // Add step button click listener
        addStepButton.setOnClickListener(v -> {
            String step = stepInput.getText().toString().trim();
            if (!step.isEmpty()) {
                steps.add(step);
                stepInput.setText("");
                updateStepList();
            }
        });

        // Publish button click listener
        publishButton.setOnClickListener(v -> uploadRecipe());
    }

    private void updateIngredientList() {
        ingredientList.removeAllViews();
        for (String ingredient : ingredients) {
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            ingredientList.addView(textView);
        }
    }

    private void updateStepList() {
        stepList.removeAllViews();
        for (String step : steps) {
            TextView textView = new TextView(this);
            textView.setText(step);
            stepList.addView(textView);
        }
    }

    private void uploadRecipe() {
        String title = recipeTitle.getText().toString().trim();
        String description = recipeDescription.getText().toString().trim();
        String imageUrl = imageUrlInput.getText().toString().trim();
        int prepTime = prepTimePicker.getValue();
        int serves = servesPicker.getValue();

        if (title.isEmpty() || description.isEmpty() || ingredients.isEmpty() || steps.isEmpty() || imageUrl.isEmpty()) {
            Snackbar.make(rootView, "All fields are required!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Create a new recipe entry in the SQLite database
        ContentValues values = new ContentValues();
        values.put(RecipeDatabaseHelper.COLUMN_ID, String.valueOf(System.currentTimeMillis())); // Unique ID
        values.put(RecipeDatabaseHelper.COLUMN_TITLE, title);
        values.put(RecipeDatabaseHelper.COLUMN_DESCRIPTION, description);
        values.put(RecipeDatabaseHelper.COLUMN_DURATION, prepTime + " minutes");
        values.put(RecipeDatabaseHelper.COLUMN_SERVINGS, serves + " servings");
        values.put(RecipeDatabaseHelper.COLUMN_IMAGE_URL, imageUrl);
        values.put(RecipeDatabaseHelper.COLUMN_PURCHASED, 0); // Default value for purchased
        values.put(RecipeDatabaseHelper.COLUMN_PREP_TIME, prepTime);
        values.put(RecipeDatabaseHelper.COLUMN_INGREDIENTS, String.join(",", ingredients)); // Store as comma-separated
        values.put(RecipeDatabaseHelper.COLUMN_STEPS, String.join(",", steps)); // Store as comma-separated

        // Insert the recipe into the database
        long newRowId = dbHelper.getWritableDatabase().insert(RecipeDatabaseHelper.TABLE_RECIPES, null, values);

        if (newRowId != -1) {
            Snackbar.make(rootView, "Recipe Published!", Snackbar.LENGTH_LONG).show();
            // Redirect to All Recipes Activity
            Intent intent = new Intent(UploadRecipeActivity.this, AllRecipesActivity.class);
            startActivity(intent);
            finish(); // Optional: Call finish() if you want to close the current activity
        } else {
            Snackbar.make(rootView, "Failed to publish", Snackbar.LENGTH_LONG).show();
        }
    }
}
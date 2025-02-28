package com.example.myrecipebook.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipebook.R;
import com.example.myrecipebook.adapters.RecipeAdapter;
import com.example.myrecipebook.RecipeDatabaseHelper;
import com.example.myrecipebook.models.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        recyclerView = findViewById(R.id.recipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(recipeList, this);
        recyclerView.setAdapter(adapter);
        dbHelper = new RecipeDatabaseHelper(this);

        // Floating Action Button to add a new recipe
        FloatingActionButton fabAddRecipe = findViewById(R.id.fab_add_recipe);
        fabAddRecipe.setOnClickListener(v -> {
            Intent intent = new Intent(AllRecipesActivity.this, UploadRecipeActivity.class);
            startActivity(intent);
        });

        fetchRecipes();
    }

    private void fetchRecipes() {
        Cursor cursor = dbHelper.getReadableDatabase().query(
                RecipeDatabaseHelper.TABLE_RECIPES,
                null, // All columns
                null, // No WHERE clause
                null, // No WHERE arguments
                null, // No GROUP BY
                null, // No HAVING
                null  // No ORDER BY
        );

        if (cursor != null && cursor.moveToFirst()) {
            recipeList.clear(); // Clear previous data

            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_DESCRIPTION));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_DURATION));
                @SuppressLint("Range") String servings = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_SERVINGS));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_IMAGE_URL));
                @SuppressLint("Range") boolean purchased = cursor.getInt(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_PURCHASED)) > 0;
                @SuppressLint("Range") long prepTime = cursor.getLong(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_PREP_TIME));
                @SuppressLint("Range") String ingredients = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_INGREDIENTS));
                @SuppressLint("Range") String steps = cursor.getString(cursor.getColumnIndex(RecipeDatabaseHelper.COLUMN_STEPS));

                // Create a Recipe object and add it to the list
                Recipe recipe = new Recipe(id, title, description, duration, servings, imageUrl, purchased, prepTime,
                        List.of(ingredients.split(",")), List.of(steps.split(",")));
                recipeList.add(recipe);
            } while (cursor.moveToNext());

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged();
            cursor.close();
        } else {
            Toast.makeText(this, "No recipes found", Toast.LENGTH_SHORT).show();
        }
    }
}
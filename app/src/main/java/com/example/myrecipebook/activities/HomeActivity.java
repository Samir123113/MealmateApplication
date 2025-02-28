package com.example.myrecipebook.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipebook.R;
import com.example.myrecipebook.adapters.RecipeAdapter;
import com.example.myrecipebook.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeActionListener {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        recipeList = new ArrayList<>();

        // Set up RecyclerView with a GridLayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recipeAdapter = new RecipeAdapter(recipeList, this);
        recyclerView.setAdapter(recipeAdapter);

        // Fetch recipes from Firebase
        fetchRecipes();
    }

    private void fetchRecipes() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("recipes");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        recipeList.add(recipe);
                    }
                }
                recipeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Failed to load recipes.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // Implementing the RecipeAdapter.OnRecipeActionListener methods

    @Override
    public void onEditRecipe(Recipe recipe) {
        // Handle edit recipe action
        Toast.makeText(this, "Edit recipe: " + recipe.getTitle(), Toast.LENGTH_SHORT).show();
        // You can start a new activity to edit the recipe or open a dialog
    }

    @Override
    public void onDeleteRecipe(String recipeId) {
        // Handle delete recipe action
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("recipes").child(recipeId);
        databaseRef.removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(HomeActivity.this, "Recipe deleted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Failed to delete recipe", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onMarkAsPurchased(String recipeId) {
        // Handle mark as purchased action
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("recipes").child(recipeId).child("purchased");
        databaseRef.setValue(true)
                .addOnSuccessListener(aVoid -> Toast.makeText(HomeActivity.this, "Marked as purchased", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(HomeActivity.this, "Failed to mark as purchased", Toast.LENGTH_SHORT).show());
    }
}
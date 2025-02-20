package com.example.myrecipebook.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myrecipebook.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GroceryListActivity extends AppCompatActivity {

    private ListView listViewSelectedRecipes;
    private ListView listViewGroceryItems;
    private Button buttonGenerateGroceryList;
    private ArrayList<String> selectedRecipes;
    private ArrayList<String> groceryItems;
    private ArrayAdapter<String> groceryAdapter;

    // Sample recipe data (In a real app, this would come from a database or API)
    private HashMap<String, ArrayList<String>> recipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        listViewSelectedRecipes = findViewById(R.id.listViewSelectedRecipes);
        listViewGroceryItems = findViewById(R.id.listViewGroceryItems);
        buttonGenerateGroceryList = findViewById(R.id.buttonGenerateGroceryList);

        // Sample data for recipes and their ingredients
        recipeIngredients = new HashMap<>();
        recipeIngredients.put("Chicken Stir-Fry", new ArrayList<String>() {{
            add("Chicken");
            add("Broccoli");
            add("Soy Sauce");
            add("Rice");
        }});
        recipeIngredients.put("Vegetable Quinoa Bowl", new ArrayList<String>() {{
            add("Quinoa");
            add("Bell Peppers");
            add("Zucchini");
            add("Olive Oil");
        }});
        recipeIngredients.put("Spaghetti Carbonara", new ArrayList<String>() {{
            add("Spaghetti");
            add("Eggs");
            add("Parmesan Cheese");
            add("Bacon");
        }});

        // Sample selected recipes (In a real app, this would be dynamic)
        selectedRecipes = new ArrayList<>();
        selectedRecipes.add("Chicken Stir-Fry");
        selectedRecipes.add("Vegetable Quinoa Bowl");

        // Display selected recipes
        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedRecipes);
        listViewSelectedRecipes.setAdapter(recipeAdapter);

        // Initialize grocery items list
        groceryItems = new ArrayList<>();
        groceryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groceryItems);
        listViewGroceryItems.setAdapter(groceryAdapter);

        // Generate grocery list when button is clicked
        buttonGenerateGroceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateGroceryList();
            }
        });
    }

    private void generateGroceryList() {
        groceryItems.clear(); // Clear previous items
        for (String recipe : selectedRecipes) {
            ArrayList<String> ingredients = recipeIngredients.get(recipe);
            if (ingredients != null) {
                groceryItems.addAll(ingredients);
            }
        }
        groceryAdapter.notifyDataSetChanged(); // Update the ListView
    }
}
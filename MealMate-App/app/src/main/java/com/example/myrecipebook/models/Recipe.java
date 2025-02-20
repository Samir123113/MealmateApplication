package com.example.myrecipebook.models;

public class Recipe {
    private String name;
    private String ingredients;
    private String mealTypes;

    public Recipe() {
        // Default constructor required for calls to DataSnapshot.getValue(Recipe.class)
    }

    public Recipe(String name, String ingredients, String mealTypes) {
        this.name = name;
        this.ingredients = ingredients;
        this.mealTypes = mealTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(String mealTypes) {
        this.mealTypes = mealTypes;
    }
}
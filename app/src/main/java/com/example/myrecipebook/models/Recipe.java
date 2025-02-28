package com.example.myrecipebook.models;

import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private String duration; // e.g., "30 minutes"
    private String servings; // Change this to match Firestore field
    private String imageUrl;
    private boolean purchased;
    private long prepTime; // Change this to long to match Firestore type
    private List<String> ingredients; // List of ingredients
    private List<String> steps; // List of steps for the recipe

    // No-argument constructor required for Firestore
    public Recipe() {
    }

    // Constructor with parameters
    public Recipe(String id, String title, String description, String duration, String servings, String imageUrl, boolean purchased, long prepTime, List<String> ingredients, List<String> steps) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.servings = servings; // Update this to match Firestore field
        this.imageUrl = imageUrl;
        this.purchased = purchased;
        this.prepTime = prepTime; // Update this to match Firestore field
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getServings() { // Ensure this matches Firestore field
        return servings; // Update this to match Firestore field
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public long getPrepTime() { // Change this to long
        return prepTime; // Update this to match Firestore field
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    // Setters (if needed)
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
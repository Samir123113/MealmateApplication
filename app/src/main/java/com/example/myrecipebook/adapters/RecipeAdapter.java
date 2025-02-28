package com.example.myrecipebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipebook.R;
import com.example.myrecipebook.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.title.setText(recipe.getTitle());
        holder.description.setText(recipe.getDescription());
        holder.duration.setText(recipe.getDuration());
        holder.servings.setText(recipe.getServings());
        Picasso.get().load(recipe.getImageUrl()).into(holder.image);


        // Set up delete and purchase button actions here if needed
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public interface OnRecipeActionListener {
        void onEditRecipe(Recipe recipe);

        void onDeleteRecipe(String recipeId);

        void onMarkAsPurchased(String recipeId);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, duration, servings;
        ImageView image;
        ImageButton btnDelete, btnPurchase;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recipe_title);
            description = itemView.findViewById(R.id.recipe_description);
            duration = itemView.findViewById(R.id.recipe_duration);
            servings = itemView.findViewById(R.id.recipe_servings);
            image = itemView.findViewById(R.id.recipe_image);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnPurchase = itemView.findViewById(R.id.btn_purchase);
        }
    }
}
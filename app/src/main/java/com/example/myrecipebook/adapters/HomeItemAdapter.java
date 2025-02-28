package com.example.myrecipebook.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipebook.R;
import com.example.myrecipebook.models.HomeItemModel;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    Context context;
    List<HomeItemModel> list;

    public HomeItemAdapter(Context context, List<HomeItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views
        HomeItemModel item = list.get(position);
        holder.imageView.setImageResource(item.getImage());
        holder.name.setText(item.getName());

        // Set click listener for the card
        holder.cardView.setOnClickListener(v -> {
            // Create a bundle to pass data to the next screen
            Bundle bundle = new Bundle();
            bundle.putString("categoryName", item.getName());

            // Navigate to the RecipeDetailsFragment (or Activity)
            Navigation.findNavController(v).navigate(R.id.nav_categories, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ViewHolder class to hold references to the views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_img);
            name = itemView.findViewById(R.id.category_title);
            cardView = itemView.findViewById(R.id.homeCard);
        }
    }
}
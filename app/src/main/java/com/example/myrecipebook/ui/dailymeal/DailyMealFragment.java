package com.example.myrecipebook.ui.dailymeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myrecipebook.R;
import com.example.myrecipebook.adapters.DailyMealAdapter;
import com.example.myrecipebook.models.DailyMealItem;

import java.util.ArrayList;
import java.util.List;

public class DailyMealFragment extends Fragment {

    private RecyclerView recyclerView;
    private DailyMealAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.daily_meal_fragment, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // Initialize adapter
        adapter = new DailyMealAdapter();

        // Set click listener for the adapter
        adapter.setOnItemClickListener((item, position) -> {
            Toast.makeText(getContext(), "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            // Handle navigation or other actions here
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDailyMeals();
    }

    private void loadDailyMeals() {
        // Create sample data
        List<DailyMealItem> mealList = new ArrayList<>();

        // Add items with all required fields including discount
        mealList.add(new DailyMealItem(
                R.drawable.chicken,
                "Lunch",
                "Description Description Description",
                "20% OFF"
        ));

        mealList.add(new DailyMealItem(
                R.drawable.fries1,
                "Dinner",
                "Description Description Description",
                "50% OFF"
        ));

        mealList.add(new DailyMealItem(
                R.drawable.coffe,
                "Sweets",
                "Description Description Description",
                "30% OFF"
        ));

        // Submit the list to the adapter
        adapter.submitList(mealList);
    }
}
package com.example.myrecipebook.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipebook.R;
import com.example.myrecipebook.adapters.WeeklyPlanAdapter;
import com.example.myrecipebook.models.WeeklyPlanItem;

import java.util.ArrayList;
import java.util.List;

public class WeeklyPlanActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan);

        RecyclerView recyclerView = findViewById(R.id.weekly_plan_recycler_view);

        // Initialize the weekly plan list
        List<WeeklyPlanItem> weeklyPlanList = new ArrayList<>();
        weeklyPlanList.add(new WeeklyPlanItem("Monday", "Breakfast: Pancakes\nLunch: Salad\nDinner: Pasta"));
        weeklyPlanList.add(new WeeklyPlanItem("Tuesday", "Breakfast: Oatmeal\nLunch: Sandwich\nDinner: Soup"));
        weeklyPlanList.add(new WeeklyPlanItem("Wednesday", "Breakfast: Eggs\nLunch: Wrap\nDinner: Pizza"));
        weeklyPlanList.add(new WeeklyPlanItem("Thursday", "Breakfast: Smoothie\nLunch: Quinoa Bowl\nDinner: Stir-fry"));
        weeklyPlanList.add(new WeeklyPlanItem("Friday", "Breakfast: Toast\nLunch: Sushi\nDinner: BBQ"));
        weeklyPlanList.add(new WeeklyPlanItem("Saturday", "Breakfast: Waffles\nLunch: Grilled Cheese\nDinner: Tacos"));
        weeklyPlanList.add(new WeeklyPlanItem("Sunday", "Breakfast: Croissants\nLunch: Burger\nDinner: Roast"));

        // Set up RecyclerView
        WeeklyPlanAdapter adapter = new WeeklyPlanAdapter(weeklyPlanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

package com.example.myrecipebook.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipebook.R;
import com.example.myrecipebook.models.WeeklyPlanItem;

import java.util.List;

public class WeeklyPlanAdapter extends RecyclerView.Adapter<WeeklyPlanAdapter.WeeklyPlanViewHolder> {

    private List<WeeklyPlanItem> weeklyPlanList;

    public WeeklyPlanAdapter(List<WeeklyPlanItem> weeklyPlanList) {
        this.weeklyPlanList = weeklyPlanList;
    }

    @NonNull
    @Override
    public WeeklyPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weekly_plan, parent, false);
        return new WeeklyPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyPlanViewHolder holder, int position) {
        WeeklyPlanItem item = weeklyPlanList.get(position);
        holder.dayTextView.setText(item.getDay());
        holder.mealsTextView.setText(item.getMeals());
    }

    @Override
    public int getItemCount() {
        return weeklyPlanList.size();
    }

    public static class WeeklyPlanViewHolder extends RecyclerView.ViewHolder {

        TextView dayTextView;
        TextView mealsTextView;

        public WeeklyPlanViewHolder(View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.day_text);
            mealsTextView = itemView.findViewById(R.id.meals_text);
        }
    }
}

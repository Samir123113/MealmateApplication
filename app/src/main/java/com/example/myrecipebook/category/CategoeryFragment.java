package com.example.myrecipebook.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myrecipebook.R;

public class CategoeryFragment extends Fragment {

    private String categoryName;
    private TextView categoryTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // Initialize UI components
        categoryTitle = view.findViewById(R.id.category_title);

        // Retrieve the bundle data
        if (getArguments() != null) {
            categoryName = getArguments().getString("categoryName", "Default Category");
            categoryTitle.setText(categoryName);
        }

        return view;
    }
}

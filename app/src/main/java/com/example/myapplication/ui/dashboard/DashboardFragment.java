package com.example.myapplication.ui.dashboard;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;


    private EditText editTextFood, editTextCalories;
    private TextView textViewTotalCalories;
    private int totalCalories = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        try {
            showHomePageDesign(root);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException(e);
        }
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showHomePageDesign(View view) throws IllegalAccessException, java.lang.InstantiationException {

        editTextFood = view.findViewById(R.id.editTextFood);
        editTextCalories = view.findViewById(R.id.editTextCalories);
        textViewTotalCalories = view.findViewById(R.id.textViewTotalCalories);
        Button btn = view.findViewById(R.id.buttonAdd);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodItem = editTextFood.getText().toString();
                String caloriesStr = editTextCalories.getText().toString();

                if (!foodItem.isEmpty() && !caloriesStr.isEmpty()) {
                    int calories = Integer.parseInt(caloriesStr);
                    totalCalories += calories;

                    // Display the updated total calories
                    textViewTotalCalories.setText("Total Calories: " + totalCalories);

                    // Clear the input fields
                    editTextFood.getText().clear();
                    editTextCalories.getText().clear();
                }

            }
        });
    }


}
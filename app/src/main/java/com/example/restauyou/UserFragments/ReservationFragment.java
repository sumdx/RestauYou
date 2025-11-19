package com.example.restauyou.UserFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restauyou.R;

public class ReservationFragment extends Fragment {
    private Button selectedTimeButton;
    private String selectedTime = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        GridLayout timeGrid = view.findViewById(R.id.timeContainer);

        // Loop through all time buttons and set click listener
        for (int i = 0; i < timeGrid.getChildCount(); i++) {
            View child = timeGrid.getChildAt(i);

            if (child instanceof Button) {
                Button timeBtn = (Button) child;

                timeBtn.setOnClickListener(v -> {

                    if (selectedTimeButton != null) {
                        selectedTimeButton.setSelected(false);
                    }

                    timeBtn.setSelected(true);
                    selectedTimeButton = timeBtn;
                    selectedTime = timeBtn.getText().toString();  // store selected time
                });
            }
        }
        Button reserveBtn = view.findViewById(R.id.btnReserve);

        reserveBtn.setOnClickListener(v -> {
            if (selectedTime.isEmpty()) {
                Toast.makeText(getContext(), "Please select a time", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Selected time: " + selectedTime, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
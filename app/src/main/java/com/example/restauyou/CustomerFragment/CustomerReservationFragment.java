package com.example.restauyou.CustomerFragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restauyou.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class CustomerReservationFragment extends Fragment {
    private Button selectedTimeButton;
    EditText etName, etPhone, etDate, sRequest;
    Spinner spinnerGuests;
    private String selectedTime = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_reservation, container, false);

        // Initialize object by ids
        GridLayout timeGrid = view.findViewById(R.id.timeContainer);
        Button reserveBtn = view.findViewById(R.id.btnReserve);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        etDate = view.findViewById(R.id.etDate);
        sRequest = view.findViewById(R.id.sRequest);
        spinnerGuests = view.findViewById(R.id.spinnerGuests);


        // Time Button Grid logic
        setupTimeGrid(timeGrid);

        // Reserve button
        reserveBtn.setOnClickListener(v -> {
            if (selectedTime.isEmpty() ||
                etName.getText().toString().isEmpty() ||
                etPhone.getText().toString().isEmpty() ||
                etDate.getText().toString().isEmpty()
            )
                Toast.makeText(getContext(), "Fill in all the fields", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Done. Selected time: " + selectedTime, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void setupTimeGrid(GridLayout timeGrid) {
        if (timeGrid == null)
            return;

        // Loop through all time buttons and set click listener
        for (int i = 0; i < timeGrid.getChildCount(); i++) {
            View child = timeGrid.getChildAt(i);

            if (child instanceof MaterialButton) {
                MaterialButton timeBtn = (MaterialButton) child;

                // Select
                timeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectTimeButton(timeBtn);
                    }
                });

                // Unselect
                timeBtn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        timeBtn.setBackgroundTintList(null);
                        timeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
                        timeBtn.setSelected(false);
                        selectedTimeButton = null;
                        selectedTime = "";
                        return true;
                    }
                });
            }
        }
    }

    // Handles state & colors for time buttons
    private void selectTimeButton(MaterialButton clickedBtn) {
        final int WHITE = ContextCompat.getColor(requireContext(), R.color.white),
                  GRAY = ContextCompat.getColor(requireContext(), R.color.gray),
                  ACCENT = ContextCompat.getColor(requireContext(), R.color.accent);

        // Check prev clicked btn & reset color
        if (selectedTimeButton != null) {
            selectedTimeButton.setBackgroundTintList(null);
            selectedTimeButton.setTextColor(GRAY);
            selectedTimeButton.setSelected(false);
        }

        // Set new btn color & state
        assert clickedBtn != null;
        clickedBtn.setBackgroundTintList(ColorStateList.valueOf(ACCENT));
        clickedBtn.setTextColor(WHITE);
        clickedBtn.setSelected(true);
        selectedTimeButton = clickedBtn;
        selectedTime = clickedBtn.getText().toString();  // store selected time
    }
}
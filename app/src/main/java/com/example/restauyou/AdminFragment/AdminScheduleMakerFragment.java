package com.example.restauyou.AdminFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restauyou.AdminAdapters.AdminScheduleAdapter;
import com.example.restauyou.ModelClass.Schedule;
import com.example.restauyou.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AdminScheduleMakerFragment extends Fragment {
    RecyclerView rv;
    MaterialButton  Prevbtn;
    MaterialButton  Nextbtn;

    private TextView dateRangeTextView;
    private Calendar currentStartDate;
    private final Calendar selectedDateTime = Calendar.getInstance();
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("EEE, MMM d", Locale.getDefault());

    private AdminScheduleAdapter scheduleAdapter;
    private ArrayList<Schedule> schedules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_admin_schedule_maker, container, false);
        rv = v.findViewById(R.id.recyclerDays);
        Prevbtn = v.findViewById(R.id.Prevbtn);
        Nextbtn = v.findViewById(R.id.Nextbtn);
        dateRangeTextView = v.findViewById(R.id.tvDateRange);

        // Initialize data list and adapter
        schedules = new ArrayList<>();
        scheduleAdapter = new AdminScheduleAdapter(getContext(), schedules);


        // Set adapter & layout Manager
        rv.setAdapter(scheduleAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        initializeScheduleDates();
        updateScheduleView();
        setupButtonListeners();

        return v;
    }

    private void setupButtonListeners() {
        // Next button
        Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStartDate.add(Calendar.DAY_OF_YEAR, 7);
                updateScheduleView();
            }
        });

        // Prev Button
        Prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStartDate.add(Calendar.DAY_OF_YEAR, -7);
                updateScheduleView();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateScheduleView() {
        Calendar endDate = (Calendar) currentStartDate.clone();
        endDate.add(Calendar.DAY_OF_YEAR, 6);
        String startDateString = displayFormat.format(currentStartDate.getTime());
        String endDateString = displayFormat.format(endDate.getTime());
        dateRangeTextView.setText(startDateString + " - " + endDateString);

        // Generate and update the Schedule list
        generateWeekSchedules(currentStartDate);
        scheduleAdapter.notifyDataSetChanged();
    }

    private void initializeScheduleDates() {
        currentStartDate = Calendar.getInstance();
        int requiredDayOfWeek = Calendar.TUESDAY;
        int currentDayOfWeek = currentStartDate.get(Calendar.DAY_OF_WEEK);
        int diff = currentDayOfWeek - requiredDayOfWeek;

        if (diff < 0)
            diff += 7;

        currentStartDate.add(Calendar.DAY_OF_YEAR, -diff);
    }

    private void generateWeekSchedules(Calendar startCal) {
        schedules.clear();
        Calendar currentDay = (Calendar) startCal.clone();

        // Loop 7 days
        for (int i = 0; i < 7; i++) {
            // Get the formatted date string for the Schedule header
            String dayHeader = displayFormat.format(currentDay.getTime());

            // Add a new Schedule item - HARD_CODED
            schedules.add(new Schedule(
                    dayHeader,
                    "0 shifts scheduled",
                    "No shift scheduled"
            ));

            // Move to the next day
            currentDay.add(Calendar.DAY_OF_YEAR, 1);
        }
    }
}
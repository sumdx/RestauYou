package com.example.restauyou.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.Schedule;
import com.example.restauyou.R;

import java.util.List;

public class AdminScheduleAdapter extends RecyclerView.Adapter<AdminScheduleAdapter.ViewHolder> {

    private Context context;
    private List<Schedule> scheduleList;

    public AdminScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.editable_schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        holder.day.setText(schedule.getDay());
        holder.shiftsScheduled.setText(schedule.getshiftsScheduled());
        holder.noShifts.setText(schedule.getNoShifts());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView day, shiftsScheduled, noShifts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            shiftsScheduled = itemView.findViewById(R.id.shiftsScheduled);
            noShifts = itemView.findViewById(R.id.noShifts);
        }
    }
}

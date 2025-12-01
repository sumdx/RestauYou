package com.example.restauyou.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.Employee;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class AdminEmployeeEditAdapter extends RecyclerView.Adapter<AdminEmployeeEditAdapter.ViewHolder> {
    Context context;
    ArrayList<Employee> employeeArrayList ;


    public void setEmployeeArrayList(ArrayList<Employee> employeeArrayList) {
        this.employeeArrayList = employeeArrayList;
        notifyDataSetChanged();
    }
    public AdminEmployeeEditAdapter(Context context, ArrayList<Employee> employeeArrayList) {
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }

    @NonNull
    @Override
    public AdminEmployeeEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.editable_employee_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeArrayList.get(position);
        holder.tvEmployeeName.setText(employee.getName());
        holder.tvEmployeeStatus.setText(employee.getCurrentStatus());
        holder.tvEmployeeRole.setText(employee.getRole());
        holder.tvEmployeEmail.setText(employee.getEmail());
        holder.tvEmployeePhone.setText(employee.getPhone());
        holder.tvEmployeeHourlyRate.setText(String.valueOf(employee.getHourlyRate()));

    }


    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvEmployeeName, tvEmployeeRole, tvEmployeEmail, tvEmployeePhone, tvEmployeeHourlyRate, tvEmployeeStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeeRole = itemView.findViewById(R.id.tvEmployeeRole);
            tvEmployeEmail= itemView.findViewById(R.id.tvEmployeeEmail);
            tvEmployeePhone = itemView.findViewById(R.id.tvEmployeePhoneNumber);
            tvEmployeeHourlyRate = itemView.findViewById(R.id.tvEmployeeHourlyRate);
            tvEmployeeStatus= itemView.findViewById(R.id.tvEmployeeStatus);

        }
    }
}

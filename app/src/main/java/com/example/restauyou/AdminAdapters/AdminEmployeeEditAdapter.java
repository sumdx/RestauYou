package com.example.restauyou.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.Employee;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.ModelClass.User;
import com.example.restauyou.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;

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
        holder.tvEmployeEmail.setText(employee.getEmail());
        holder.tvEmployeeHourlyRate.setVisibility(View.GONE);
        holder.tvEmployeePhone.setText(employee.getPhone());
        holder.tvEmployeeRole.setText(employee.getPosition());
        if (employee.isEmployeePending()){
            holder.btnEditEmployee.setVisibility(View.GONE);
            holder.btnDeleteEmployee.setVisibility(View.GONE);
        }else {
            holder.btnAcceptReq.setVisibility(View.GONE);
            holder.btnDeleteReq.setVisibility(View.GONE);

            holder.tvEmployeeStatus.setText(employee.getCurrentStatus());
            holder.tvEmployeeRole.setText(employee.getRole());
            holder.tvEmployeePhone.setText(employee.getPhone());
            holder.tvEmployeeHourlyRate.setText(String.valueOf(employee.getHourlyRate()));
        }


        holder.btnAcceptReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("users").document(employee.getId()).update("role", "employee", "employeePending",false).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.btnDeleteReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();

                user.setId(employee.getId());
                user.setPhotoUrl(employee.getPhotoUrl());
                user.setName(employee.getName());
                user.setEmail(employee.getEmail());
                user.setPhone(employee.getPhone());
                user.setRole("user");

                FirebaseFirestore.getInstance().collection("users").document(employee.getId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Successfully deleted req", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvEmployeeName, tvEmployeeRole, tvEmployeEmail, tvEmployeePhone, tvEmployeeHourlyRate, tvEmployeeStatus;
        MaterialButton btnEditEmployee, btnDeleteEmployee,btnAcceptReq, btnDeleteReq;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEmployeeName = itemView.findViewById(R.id.tvEmployeeName);
            tvEmployeeRole = itemView.findViewById(R.id.tvEmployeeRole);
            tvEmployeEmail= itemView.findViewById(R.id.tvEmployeeEmail);
            tvEmployeePhone = itemView.findViewById(R.id.tvEmployeePhoneNumber);
            tvEmployeeHourlyRate = itemView.findViewById(R.id.tvEmployeeHourlyRate);
            tvEmployeeStatus= itemView.findViewById(R.id.tvEmployeeStatus);
            btnEditEmployee = itemView.findViewById(R.id.btnEditEmployee);
            btnDeleteEmployee = itemView.findViewById(R.id.btnDeleteEmployee);
            btnAcceptReq = itemView.findViewById(R.id.btnAcceptReq);
            btnDeleteReq = itemView.findViewById(R.id.btnDeleteReq);

        }
    }
}

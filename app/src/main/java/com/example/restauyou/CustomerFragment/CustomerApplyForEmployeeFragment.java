package com.example.restauyou.CustomerFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restauyou.ModelClass.Employee;
import com.example.restauyou.ModelClass.User;
import com.example.restauyou.R;
import com.example.restauyou.SignupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerApplyForEmployeeFragment extends Fragment {

    EditText etName, etEmail, etPhone, etPosition;
    LinearLayout formEmployee;
    Button btnApply;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    Employee employee;
    TextView tvApplied;
    boolean isEmployeePending;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_apply_for_employee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etPosition = view.findViewById(R.id.tvIntendedPosition);
        btnApply = view.findViewById(R.id.btnApply);
        formEmployee = view.findViewById(R.id.formEmployee);
        tvApplied = view.findViewById(R.id.tvApplied);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Boolean pending = documentSnapshot.getBoolean("employeePending");

                        if (pending != null && pending) {
                            formEmployee.setVisibility(View.GONE);
                            tvApplied.setVisibility(View.VISIBLE);
                        } else {
                            formEmployee.setVisibility(View.VISIBLE);
                            tvApplied.setVisibility(View.GONE);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });


        btnApply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String position = etPosition.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || position.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee employee = new Employee(firebaseUser.getUid(),name, email, phone,null,position,null,0.00);
                employee.setEmployeePending(true);

                try {
                    db.collection("users").document(firebaseUser.getUid()).set(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Application Submitted!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            Log.d("errorH", e.toString());
                        }
                    });

                }catch (Exception e){
                    Log.d("errorH", e.toString());
                }


            }
        });
    }
}

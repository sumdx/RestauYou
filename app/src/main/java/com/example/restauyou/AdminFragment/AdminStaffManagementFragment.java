package com.example.restauyou.AdminFragment;

import static android.widget.LinearLayout.VERTICAL;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restauyou.AdminAdapters.AdminEmployeeEditAdapter;
import com.example.restauyou.ModelClass.Employee;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Date;

public class AdminStaffManagementFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Employee> employeeArrayList;
    FirebaseFirestore firebaseFirestore;
    AdminEmployeeEditAdapter adminEmployeeEditAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_staff_management, container, false);
        recyclerView = view.findViewById(R.id.adminEmployeeManagementRecyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();
        employeeArrayList = new ArrayList<>();

        loadEmployees();
        adminEmployeeEditAdapter = new AdminEmployeeEditAdapter(getContext(), employeeArrayList);
        recyclerView.setAdapter(adminEmployeeEditAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }


    private void loadEmployees() {

        firebaseFirestore.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Log.d("FirebaseError", error.getMessage());
                    return;
                }
                if(value != null && !value.isEmpty()){
                    employeeArrayList.clear();
                    for(DocumentSnapshot doc: value.getDocuments() ){
                        Log.d("resultt", doc.toString());
                        Employee employee = doc.toObject(Employee.class);
                        employee.setId(doc.getId());
                        if(employee.getRole().equals("employee")){
                            employeeArrayList.add(employee);
                        }


                    }
                    adminEmployeeEditAdapter.setEmployeeArrayList(employeeArrayList);
                }
            }
        });
    }
}
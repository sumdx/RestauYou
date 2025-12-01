package com.example.restauyou.AdminFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restauyou.R;

import java.util.ArrayList;

public class AdminOrderManagementFragment extends Fragment {
    RecyclerView orderRV;
    //ArrayList<Order> orderList;
    TextView pendingText, preparingText, readyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_order_management, container, false);

        // Initialize objects by ids
        orderRV = view.findViewById(R.id.orderRV);
        pendingText = view.findViewById(R.id.pendingText);
        preparingText = view.findViewById(R.id.preparingText);
        readyText = view.findViewById(R.id.readyText);

//        // Hard-coding values
//        orderList = new ArrayList<>();
//
//        // Set adapter & layout manager
//        orderRV.setAdapter(new OrderAdapter(getContext(), orderList));
//        LinearLayoutManager llm = new LinearLayoutManager(getContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        orderRV.setLayoutManager(llm);

        return view;
    }
}
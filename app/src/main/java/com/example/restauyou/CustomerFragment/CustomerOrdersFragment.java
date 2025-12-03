package com.example.restauyou.CustomerFragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.restauyou.CustomerHomePageActivity;
import com.example.restauyou.R;

public class CustomerOrdersFragment extends Fragment {
    RecyclerView customerOrderRV;
    ImageView backBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_orders, container, false);
        customerOrderRV = view.findViewById(R.id.customerOrderRV);
        backBtn = view.findViewById(R.id.backBtn);


        // Back button logic
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity home = requireActivity();

                // Access home activity method
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        if (home instanceof CustomerHomePageActivity)
                            ((CustomerHomePageActivity) home).orderBackBtnClicked();
                    }
                });
            }
        });
        return view;
    }
}
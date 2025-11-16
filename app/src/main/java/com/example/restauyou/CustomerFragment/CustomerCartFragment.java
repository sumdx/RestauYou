package com.example.restauyou.CustomerFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restauyou.CustomerAdapters.CartAdapter;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class CustomerCartFragment extends Fragment {
    RecyclerView cartRv;
    Button checkoutBtn;
    TextView numItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_cart, container, false);

        // Wiring objects by ids
        cartRv = view.findViewById(R.id.cartRecyclerView);
        checkoutBtn = view.findViewById(R.id.checkoutBtn);
        numItems = view.findViewById(R.id.numOfItemText);

        // Test values
        ArrayList<MenuItem> exampleFood = new ArrayList<>();
        for (int i = 1; i <= 3; i++)
            exampleFood.add(new MenuItem("Burger " + i, "This is a standard Burger ", "Available", "$99.99", 1, R.drawable.burger_img, false));

        // Set adapter + layout manager
        cartRv.setAdapter(new CartAdapter(getContext(), exampleFood));
        cartRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
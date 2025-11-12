package com.example.restauyou.CusomterFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restauyou.CustomerAdapters.FoodAdapter;
import com.example.restauyou.ModelClass.FoodItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class CustomerHomeFragment extends Fragment {
    RecyclerView rv;

    // Test hard-coded items
    ArrayList<FoodItem> foods = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);

        // Add test items
        for (int i = 1; i <= 10; i++) {
            foods.add(new FoodItem(R.drawable.burger_img, "Burger " + i, "This is a standard Burger ", "0", "$99.99 "));
        }

        // Set adapter
        rv = view.findViewById(R.id.customerHomeRV);
        rv.setAdapter(new FoodAdapter(getContext(), foods));

        // Set layout
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        return view;
    }
}
package com.example.restauyou.CustomerFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restauyou.CustomerAdapters.CartAdapter;
import com.example.restauyou.ModelClass.CartItem;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.ModelClass.SharedCartModel;
import com.example.restauyou.R;

import java.util.ArrayList;

public class CustomerCartFragment extends Fragment {
    RecyclerView cartRv;
    Button checkoutBtn;
    TextView numItems;
    SharedCartModel sharedCartItemsList ;
    ArrayList<CartItem> exampleFood;
    CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_cart, container, false);

        // Wiring objects by ids
        cartRv = view.findViewById(R.id.cartRecyclerView);
        checkoutBtn = view.findViewById(R.id.checkoutBtn);
        numItems = view.findViewById(R.id.numOfItemText);
        sharedCartItemsList = new ViewModelProvider(requireActivity()).get(SharedCartModel.class);
        cartAdapter = new CartAdapter(getContext(), new ArrayList<>(),sharedCartItemsList);
        // Test values


        sharedCartItemsList.getCartList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CartItem>>() {
            @Override
            public void onChanged(ArrayList<CartItem> cartItems) {
                cartAdapter.setCartItems(cartItems);
                cartAdapter.notifyDataSetChanged();
                numItems.setText("Items: " + cartItems.size());
            }
        });

        // Set adapter + layout manager
        cartRv.setAdapter(cartAdapter);
        cartRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
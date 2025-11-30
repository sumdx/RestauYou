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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restauyou.CustomerAdapters.CartAdapter;
import com.example.restauyou.CustomerHomePageActivity;
import com.example.restauyou.ModelClass.CartItem;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.ModelClass.SharedCartModel;
import com.example.restauyou.R;

import java.util.ArrayList;
import java.util.Locale;

public class CustomerCartFragment extends Fragment {
    RecyclerView cartRv;
    Button checkoutBtn;
    TextView numItems, subTotalText, taxText, totalText;
    SharedCartModel sharedCartItemsList ;
    CartAdapter cartAdapter;
    ImageButton menuBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_cart, container, false);

        // Wiring objects by ids
        cartRv = view.findViewById(R.id.cartRecyclerView);
        checkoutBtn = view.findViewById(R.id.checkoutBtn);
        numItems = view.findViewById(R.id.numOfItemText);
        menuBtn = view.findViewById(R.id.menuBtn);
        subTotalText = view.findViewById(R.id.subTotalText);
        taxText = view.findViewById(R.id.taxText);
        totalText = view.findViewById(R.id.totalText);

        // Initialize selected cart items list
        sharedCartItemsList = new ViewModelProvider(requireActivity()).get(SharedCartModel.class);
        cartAdapter = new CartAdapter(getContext(), new ArrayList<>(), sharedCartItemsList);

        // Get item from Menu Fragment
        sharedCartItemsList.getCartList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CartItem>>() {
            @Override
            public void onChanged(ArrayList<CartItem> cartItems) {
                cartAdapter.setCartItems(cartItems);
                cartAdapter.notifyDataSetChanged();
                numItems.setText("Items: " + cartItems.size());
                updateCost();
            }
        });

        // Set adapter + layout manager
        cartRv.setAdapter(cartAdapter);
        cartRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Checkout button listener
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View home = requireActivity().getWindow().getDecorView().getRootView();
                home.post(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() instanceof CustomerHomePageActivity)
                            ((CustomerHomePageActivity) getActivity()).checkoutBtnClicked();
                    }
                });
            }
        });

        // Back button listener
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to previous fragment
                getParentFragmentManager().popBackStack();

                // Show the main content (if hidden)
                if (getActivity() != null) {
                    View mainContent = getActivity().findViewById(R.id.homeMenuContent);
                    if (mainContent != null) {
                        mainContent.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return view;
    }

    // Update cost texts
    public void updateCost() {
        double cost = 0;

        for (CartItem cartItem: sharedCartItemsList.getCartList().getValue())
            cost += cartItem.getTotalPrice();

        double tax = cost * 0.08, total = cost + tax;
        subTotalText.setText(String.format(Locale.CANADA, "$%.2f", cost));
        taxText.setText(String.format(Locale.CANADA, "$%.2f", tax));
        totalText.setText(String.format(Locale.CANADA, "$%.2f", total));
    }
}
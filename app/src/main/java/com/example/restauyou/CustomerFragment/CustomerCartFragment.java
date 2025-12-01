package com.example.restauyou.CustomerFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restauyou.CustomerAdapters.CartAdapter;
import com.example.restauyou.CustomerHomePageActivity;
import com.example.restauyou.LoginActivity;
import com.example.restauyou.ModelClass.CartItem;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.ModelClass.SharedCartModel;
import com.example.restauyou.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomerCartFragment extends Fragment {
    RecyclerView cartRv;
    double cost = 0;
    Button checkoutBtn;
    TextView numItems, subTotalText, taxText, totalText;
    SharedCartModel sharedCartItemsList ;
    CartAdapter cartAdapter;
    ImageButton menuBtn;

    FirebaseUser firebaseUser;
    FirebaseFirestore db;

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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        // Initialize selected cart items list
        sharedCartItemsList = new ViewModelProvider(requireActivity()).get(SharedCartModel.class);
        cartAdapter = new CartAdapter(getContext(), new ArrayList<>(), sharedCartItemsList);
        sharedCartItemsList.loadSharedPrefCart(getContext());

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
                if(firebaseUser == null){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Date now = Calendar.getInstance().getTime();
                DocumentReference docRef = db.collection("orders").document();
                Order order = new Order(sharedCartItemsList.getCartList().getValue(), docRef.getId(), firebaseUser.getUid(),"online","recieved",cost, now, now);
                docRef.set(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(getContext(), "Upload successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Upload unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                });

//                View home = requireActivity().getWindow().getDecorView().getRootView();
//                home.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (getActivity() instanceof CustomerHomePageActivity)
//                            ((CustomerHomePageActivity) getActivity()).checkoutBtnClicked();
//                    }
//                });
                //Log.d("list", sharedCartItemsList.getCartList().getValue().get(1).getQuantity()+"");
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
        ArrayList<CartItem> cart = sharedCartItemsList.getCartList().getValue();


        if (cart == null)
            return;

        for (CartItem cartItem: cart)
            cost += cartItem.getTotalPrice();

        double tax = cost * 0.08, total = cost + tax;
        subTotalText.setText(String.format(Locale.CANADA, "$%.2f", cost));
        taxText.setText(String.format(Locale.CANADA, "$%.2f", tax));
        totalText.setText(String.format(Locale.CANADA, "$%.2f", total));
    }
}
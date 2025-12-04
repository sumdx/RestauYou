package com.example.restauyou.CustomerFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.restauyou.CustomerAdapters.CustomerOrderAdapter;
import com.example.restauyou.CustomerHomePageActivity;
import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.R;
import com.example.restauyou.Services.PreparingNotification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class CustomerOrdersFragment extends Fragment {
    RecyclerView customerOrderRV;
    FirebaseFirestore db;
    FirebaseUser user;
    ArrayList<Order> orderList;
    ImageView backBtn;
    CustomerOrderAdapter customerOrderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_orders, container, false);
        customerOrderRV = view.findViewById(R.id.customerOrderRV);
        backBtn = view.findViewById(R.id.backBtn);
        db = FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        // Harding-coding values for now
        orderList = new ArrayList<>();
        loadOrder();
        customerOrderAdapter =new CustomerOrderAdapter(getContext(), orderList);

        // Set adapter & layout manager
        customerOrderRV.setAdapter(customerOrderAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        customerOrderRV.setLayoutManager(llm);

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

    private void loadOrder() {
        if (user != null)
            db.collection("orders").whereEqualTo("userId", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error!=null){
                        Log.d("FirebaseError", error.getMessage());
                        return;
                    }
                    if (value==null)
                        return;
                    orderList.clear();

                    for (DocumentChange dc: value.getDocumentChanges())
                        if(dc.getType()== DocumentChange.Type.MODIFIED) {
                            Context context = requireContext();
    //                        context.startService(new Intent(context, AdminOrderNotification.class));
                            context.startService(new Intent(context, PreparingNotification.class));
                        }

                    for(DocumentSnapshot doc : value.getDocuments()){
                        Log.d("value",value.toString());
                        Order newOrder = doc.toObject(Order.class);
                        newOrder.setOrderId(doc.getId().substring(doc.getId().length()-4, doc.getId().length()));
                        orderList.add(newOrder);
                    }
                    customerOrderAdapter.setOrderList(orderList);
                }
            });
    }
}
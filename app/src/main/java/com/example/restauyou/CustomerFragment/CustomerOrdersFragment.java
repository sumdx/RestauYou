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
import com.example.restauyou.Services.DeliveredNotification;
import com.example.restauyou.Services.PreparingNotification;
import com.example.restauyou.Services.ReadyNotification;
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
    private boolean isInitialLoad = true;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_orders, container, false);

        // Initialize objects by ids
        customerOrderRV = view.findViewById(R.id.customerOrderRV);
        backBtn = view.findViewById(R.id.backBtn);
        db = FirebaseFirestore.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();

        // Get context
        context = requireContext();

        // Load order list
        orderList = new ArrayList<>();
        loadOrder();
        customerOrderAdapter =new CustomerOrderAdapter(context, orderList);

        // Set adapter & layout manager
        customerOrderRV.setAdapter(customerOrderAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(context);
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

                    if (isInitialLoad) {
                        orderList.clear();
                        for(DocumentSnapshot doc : value.getDocuments()){
                            Log.d("value",value.toString());
                            Order newOrder = doc.toObject(Order.class);
                            newOrder.setOrderId(doc.getId().substring(doc.getId().length()-4, doc.getId().length()));
                            orderList.add(newOrder);
                        }
                        customerOrderAdapter.setOrderList(orderList);
                        isInitialLoad = false;
                        return;
                    }

                    // Update adapter
                    for (DocumentChange dc: value.getDocumentChanges()) {
                        Order updatedOrder = dc.getDocument().toObject(Order.class);
                        updatedOrder.setOrderId(dc.getDocument().getId().substring(dc.getDocument().getId().length()-4, dc.getDocument().getId().length()));
                        int oldIndex = dc.getOldIndex(),
                            newIndex = dc.getNewIndex();

                        switch (dc.getType()) {
                            case ADDED: // If change is added
                                Log.d("ADDED", updatedOrder.getOrderStatus());
                                orderList.add(newIndex, updatedOrder);
                                customerOrderAdapter.notifyItemInserted(newIndex);

                                // Notification
                                context.startService(new Intent(context, PreparingNotification.class));
                                break;

                            case MODIFIED: // If change is modified
                                orderList.set(oldIndex, updatedOrder);
                                customerOrderAdapter.notifyItemChanged(oldIndex);

                                String state = updatedOrder.getOrderStatus();
                                Intent iGo;
                                if (state.equals("preparing"))
                                    iGo = new Intent(context, PreparingNotification.class);
                                else if (state.equals("ready"))
                                    iGo = new Intent(context, ReadyNotification.class);
                                else // "delivered"
                                    iGo = new Intent(context, DeliveredNotification.class);
                                context.startService(iGo);
                                break;

                            case REMOVED: // If change is removed
                                orderList.remove(oldIndex);
                                customerOrderAdapter.notifyItemRemoved(oldIndex);
                                break;
                        }
                    }
                }
            });
    }
}
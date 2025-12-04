package com.example.restauyou.AdminFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.restauyou.AdminAdapters.OrderAdapter;
import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.R;
import com.example.restauyou.Services.AdminOrderNotification;
import com.example.restauyou.Services.PreparingNotification;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class AdminOrderManagementFragment extends Fragment {
    RecyclerView orderRV;
    ArrayList<Order> orderList;
    TextView pendingText, preparingText, readyText;
    OrderAdapter orderAdapter;
    FirebaseFirestore db;
    private int numPending, numPreparing, numReady;
    private boolean isInitialLoad = true;

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

        // Get values from firebase
        db = FirebaseFirestore.getInstance();
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext(), orderList);
        loadOrders();

        // Set adapter & layout manager
        orderRV.setAdapter(orderAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        orderRV.setLayoutManager(llm);
        return view;
    }

    private void loadOrders() {
        db.collection("orders").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null){
                    Log.d("FirebaseError", error.getMessage());
                    return;
                }
                if (value == null) return;
                orderList.clear();
                numPending = numPreparing = numReady = 0;

                for (DocumentChange dc: value.getDocumentChanges())
                    if(!isInitialLoad && dc.getType()== DocumentChange.Type.ADDED) {
                        // Get preference status
                        SharedPreferences sp = requireActivity().getSharedPreferences("UserAccount", Context.MODE_PRIVATE);
                        boolean sound = sp.getBoolean("CurrentAdminAlert", true);
                        // Start service intent
                        Context context = requireContext();
                        Intent i = new Intent(context, AdminOrderNotification.class);
                        i.putExtra("soundStatus", sound);
                        context.startService(i);
                    }

                for(DocumentSnapshot doc: value.getDocuments()) {
                    Log.d("value",value.toString());
                    Order newOrder = doc.toObject(Order.class);
                    assert newOrder != null;
                    newOrder.setOrderId(doc.getId().substring(doc.getId().length()-4, doc.getId().length()));
                    orderList.add(newOrder);
                    for (Order order: orderList)
                        switch (order.getOrderStatus()) {
                            case "received":
                                numPending++;
                                break;
                            case "preparing":
                                numPreparing++;
                                break;
                            case "ready":
                                numReady++;
                                break;
                        }

                    // Update Text
                    pendingText.setText(String.format(Locale.CANADA, "%d Pending", numPending));
                    preparingText.setText(String.format(Locale.CANADA, "%d Preparing", numPreparing));
                    readyText.setText(String.format(Locale.CANADA, "%d Ready", numReady));
                }

                // Update adapter & state
                orderAdapter.setOrderList(orderList);
                isInitialLoad = false;
            }
        });
    }
}
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

import com.example.restauyou.AdminAdapters.OrderAdapter;
import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.R;

import java.util.ArrayList;
import java.util.Locale;

public class AdminOrderManagementFragment extends Fragment {
    RecyclerView orderRV;
    ArrayList<Order> orderList;
    TextView pendingText, preparingText, readyText;

    private int numPending, numPreparing, numReady;

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

        // Hard-coding values
        orderList = new ArrayList<>();
        orderList.add(new Order());
        orderList.add(new Order());
        orderList.add(new Order());

        // Categorize orders by state (Also hard-coded)
        numPending = numPreparing = numReady = 0;
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

        // Set adapter & layout manager
        orderRV.setAdapter(new OrderAdapter(getContext(), orderList));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        orderRV.setLayoutManager(llm);

        return view;
    }
}
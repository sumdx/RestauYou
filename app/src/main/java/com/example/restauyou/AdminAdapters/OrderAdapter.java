package com.example.restauyou.AdminAdapters;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.R;
import com.example.restauyou.Services.DeliveredNotification;
import com.example.restauyou.Services.PreparingNotification;
import com.example.restauyou.Services.ReadyNotification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Order> orderList;
    private final Map<String, String> userNameCache = new HashMap<>();


    Context context;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public void setOrderList(ArrayList<Order> newOrderList){
        this.orderList = newOrderList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.editable_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Order order = orderList.get(position);
        holder.orderNumText.setText(String.format(Locale.CANADA, "Order#%s", order.getOrderId().substring(order.getOrderId().length()-4,order.getOrderId().length())));
        holder.costText.setText(String.format(Locale.CANADA, "$%.2f", order.getTotalPrice()));

        holder.dateText.setText(String.format(Locale.CANADA, "%s", order.getCreatedAt()));
        //holder.costText.setText("$0.00");

        String userId = order.getUserId();

        // Check cache first
        if (userNameCache.containsKey(userId)) {
            holder.customerText.setText("Customer: " + userNameCache.get(userId));
        } else {
            holder.customerText.setText("Customer: loading...");

            firebaseFirestore
                    .collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(doc -> {
                        if (doc.exists()) {
                            String name = doc.getString("name");
                            userNameCache.put(userId, name);

                            holder.customerText.setText("Customer: " + name);
                        }
                    });
        }

        // Set nested recycler view
        RecyclerView rv = holder.nestedOrderRV;
        rv.setAdapter(new NestedOrderAdapter(context, order.getCartItems()));
        Log.d("cartlen",String.valueOf(order.getCartItems().size()));
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        // Set various states
        String state = order.getOrderStatus();
        switch (state) {
            case "received":
                holder.pendingStateText.setVisibility(VISIBLE);
                holder.preparingStateText.setVisibility(GONE);
                holder.readyStateText.setVisibility(GONE);
                holder.deliveredStateText.setVisibility(GONE);
                holder.markBtn.setText("Mark Preparing");
                holder.markBtn.setBackgroundColor(context.getResources().getColor(R.color.gold, context.getTheme()));
                break;
            case "preparing":
                holder.pendingStateText.setVisibility(GONE);
                holder.preparingStateText.setVisibility(VISIBLE);
                holder.readyStateText.setVisibility(GONE);
                holder.deliveredStateText.setVisibility(GONE);

                holder.markBtn.setText("Mark Ready");
                holder.markBtn.setBackgroundColor(context.getResources().getColor(R.color.green, context.getTheme()));

                break;
            case "ready":
                holder.pendingStateText.setVisibility(GONE);
                holder.preparingStateText.setVisibility(GONE);
                holder.readyStateText.setVisibility(VISIBLE);
                holder.deliveredStateText.setVisibility(GONE);

                holder.markBtn.setText("Mark Delivered");
                holder.markBtn.setBackgroundColor(context.getResources().getColor(R.color.gray, context.getTheme()));
                break;
            default:  // For "delivered"
                holder.pendingStateText.setVisibility(GONE);
                holder.preparingStateText.setVisibility(GONE);
                holder.readyStateText.setVisibility(GONE);
                holder.deliveredStateText.setVisibility(VISIBLE);
                holder.markBtn.setVisibility(GONE);
                break;
        }


        // Mark button
        holder.markBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newState;
                Intent i;
                SharedPreferences sp = context.getSharedPreferences("UserAccount", Context.MODE_PRIVATE);
                boolean sound = sp.getBoolean("CurrentAdminAlert", true);

                switch (state) {
                    case "received":
                        newState = "preparing";
                        i = new Intent(context, PreparingNotification.class);
                        break;
                    case "preparing":
                        newState = "ready";
                        i = new Intent(context, ReadyNotification.class);
                        break;
                    case "ready":
                        newState = "delivered";
                        i = new Intent(context, DeliveredNotification.class);
                        break;
                    default:
                        newState = "";
                        i = new Intent(context, DeliveredNotification.class);
                }

                // Play notifications
                i.putExtra("soundStatus", sound);
                context.startService(i);

                firebaseFirestore.collection("orders").document(order.getOrderId()).update("orderStatus",newState).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Order marked as "+ newState + order.getOrderId(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FBERROR", order.getOrderId());
                        Log.d("FBERROR", e.getMessage());
                        Toast.makeText(context, "Order update failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Edit button
        holder.editOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Delete button
        holder.delOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumText, pendingStateText, preparingStateText, readyStateText, deliveredStateText, costText, customerText, dateText;
        RecyclerView nestedOrderRV;
        Button markBtn, editOrderBtn, delOrderBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumText = itemView.findViewById(R.id.orderNumText);

            // Various states
            pendingStateText = itemView.findViewById(R.id.pendingStateText);
            preparingStateText = itemView.findViewById(R.id.preparingStateText);
            readyStateText = itemView.findViewById(R.id.readyStateText);
            deliveredStateText = itemView.findViewById(R.id.deliveredStateText);

            costText = itemView.findViewById(R.id.costText);

            // Details
            customerText = itemView.findViewById(R.id.customerText);
            dateText = itemView.findViewById(R.id.dateText);

            // Nested RecyclerView
            nestedOrderRV = itemView.findViewById(R.id.nestedOrderRV);

            // Buttons
            markBtn = itemView.findViewById(R.id.markBtn);
            editOrderBtn = itemView.findViewById(R.id.editOrderBtn);
            delOrderBtn = itemView.findViewById(R.id.delOrderBtn);
        }
    }
}

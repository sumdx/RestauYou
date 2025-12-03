package com.example.restauyou.AdminAdapters;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.R;

import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<Order> orderList;
    Context context;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.editable_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderNumText.setText(String.format(Locale.CANADA, "Order#%s", order.getOrderId()));
        holder.costText.setText(String.format(Locale.CANADA, "$%.2f", order.getTotalPrice()));
        holder.dateText.setText(String.format(Locale.CANADA, "%s", order.getCreatedAt()));

        // Set nested recycler view
        RecyclerView rv = holder.nestedOrderRV;
        rv.setAdapter(new NestedOrderAdapter(context, order.getCartItems()));
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

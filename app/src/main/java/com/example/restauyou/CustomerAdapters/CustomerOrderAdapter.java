package com.example.restauyou.CustomerAdapters;

import static android.view.View.GONE;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.AdminAdapters.NestedOrderAdapter;
import com.example.restauyou.ModelClass.Order;
import com.example.restauyou.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Locale;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> {
    Context context;
    private ArrayList<Order> orderList;

    // We probably need a sharedOrderModel to update order status here

    public CustomerOrderAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        //notifyDataSetChanged();
    }

    public void setOrderList(ArrayList<Order> orderList){
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.OrderNumberText.setText(String.format("Order#%s", order.getOrderId()));
        holder.OrderPrice.setText(String.format(Locale.CANADA, "$%.2f", order.getTotalPrice()));
        holder.OrderDateTime.setText(String.format("%s", order.getCreatedAt()));
        holder.removeOrderBtn.setVisibility(GONE);

        // Set nested recycler view
        RecyclerView rv = holder.customerOrderNestedRV;
        rv.setAdapter(new NestedOrderAdapter(context, order.getCartItems())); // Reuse nested order adapter from admin
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        // Set various states
        String state = order.getOrderStatus();
        final int LIGHT_ACCENT = context.getResources().getColor(R.color.light_accent, context.getTheme()),
                  YELLOW = context.getResources().getColor(R.color.yellow, context.getTheme()),
                  LIGHT_BLUE = context.getResources().getColor(R.color.light_blue, context.getTheme()),
                  BLACK = context.getResources().getColor(R.color.black, context.getTheme()),
                  LIGHT_GREEN = context.getResources().getColor(R.color.light_green, context.getTheme());

        switch (state) {
            case "preparing":
                holder.imgCooking.setBackgroundTintList(ColorStateList.valueOf(LIGHT_ACCENT));
                holder.textCooking.setTextColor(BLACK);

                holder.EstimatedTime.setText("Estimated time: 15-20 minutes");
                holder.OrderStatus.setTextColor(Color.parseColor("#001689"));
                holder.OrderStatus.setText("Order Preparing");
                holder.OrderStatus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCEDFF")));
                break;

            case "ready":
                holder.imgCooking.setBackgroundTintList(ColorStateList.valueOf(LIGHT_ACCENT));
                holder.imgCooking.setImageTintList(ColorStateList.valueOf(LIGHT_BLUE));
                holder.imgReady.setBackgroundTintList(ColorStateList.valueOf(YELLOW));
                holder.textReady.setTextColor(BLACK);

                holder.EstimatedTime.setText("Ready for pickup now!");
                holder.OrderStatus.setTextColor(Color.parseColor("#246F00"));
                holder.OrderStatus.setText("Order Ready");
                holder.OrderStatus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DFFFCC")));
                break;

            case "delivered":
                holder.imgCooking.setBackgroundTintList(ColorStateList.valueOf(LIGHT_ACCENT));
                holder.imgCooking.setImageTintList(ColorStateList.valueOf(LIGHT_BLUE));
                holder.imgReady.setBackgroundTintList(ColorStateList.valueOf(YELLOW));
                holder.imgDone.setBackgroundTintList(ColorStateList.valueOf(LIGHT_GREEN));
                holder.textDone.setTextColor(BLACK);
 
                holder.EstimatedTime.setText("Enjoy your meal!");
                holder.OrderStatus.setText("Order Delivered");
//                holder.removeOrderBtn.setVisibility(VISIBLE);
                break;
        }

        // removeOrderBtn
        // THIS ONLY REMOVE THE LOCAL ARRAY FOR NOW. NOT THE SHARED ORDER ARRAY (WE DON'T HAVE ONE YET)
        holder.removeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAbsoluteAdapterPosition();
                orderList.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView OrderNumberText, OrderStatus, OrderPrice, OrderDateTime, textReceived, textCooking, textReady, textDone, EstimatedTime;
        ImageView imgReceived, imgCooking, imgReady, imgDone;
        RecyclerView customerOrderNestedRV;
        MaterialButton removeOrderBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderNumberText = itemView.findViewById(R.id.OrderNumberText);
            OrderStatus = itemView.findViewById(R.id.OrderStatus);
            OrderPrice = itemView.findViewById(R.id.OrderPrice);
            OrderDateTime = itemView.findViewById(R.id.OrderDateTime);
            textReceived = itemView.findViewById(R.id.textReceived);
            textCooking = itemView.findViewById(R.id.textCooking);
            textReady = itemView.findViewById(R.id.textReady);
            textDone = itemView.findViewById(R.id.textDone);
            EstimatedTime = itemView.findViewById(R.id.EstimatedTime);

            imgReceived = itemView.findViewById(R.id.imgReceived);
            imgCooking = itemView.findViewById(R.id.imgCooking);
            imgReady = itemView.findViewById(R.id.imgReady);
            imgDone = itemView.findViewById(R.id.imgDone);

            customerOrderNestedRV = itemView.findViewById(R.id.customerOrderNestedRV);
            removeOrderBtn = itemView.findViewById(R.id.removeOrderBtn);
        }
    }
}

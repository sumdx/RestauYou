package com.example.restauyou.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.CartItem;
import com.example.restauyou.R;

import java.util.ArrayList;
import java.util.Locale;

public class NestedOrderAdapter extends RecyclerView.Adapter<NestedOrderAdapter.ViewHolder> {
    Context context;
    ArrayList<CartItem> cartItems;

    public NestedOrderAdapter(Context context, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public NestedOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.editable_nested_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NestedOrderAdapter.ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.itemTitleText.setText(String.format(Locale.CANADA, "%dx %s", cartItem.getQuantity(),cartItem.getMenuItem().getItemTitle()));
        holder.itemCostText.setText(String.format(Locale.CANADA, "$%.2f", cartItem.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitleText, itemCostText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleText = itemView.findViewById(R.id.itemTitleText);
            itemCostText = itemView.findViewById(R.id.itemCostText);
        }
    }
}

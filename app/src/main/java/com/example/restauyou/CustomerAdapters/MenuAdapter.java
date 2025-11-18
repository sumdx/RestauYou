package com.example.restauyou.CustomerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    Context context;
    ArrayList<MenuItem> foods;

    public ArrayList<MenuItem> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<MenuItem> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    public MenuAdapter(Context context, ArrayList<MenuItem> foods){
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        int pos = holder.getAbsoluteAdapterPosition();
        MenuItem food = foods.get(pos);
        // Set up texts
        holder.title.setText(food.getItemTitle());
        holder.desc.setText(food.getItemDescription());
        holder.amount.setText(String.valueOf(food.getAmount()));
        holder.price.setText(String.format(Locale.CANADA,"$%.2f", Double.parseDouble(food.getItemPrice())));
        Glide.with(context).load(food.getItemImageUrl()).into(holder.img);

        // Check if added to cart
        if (food.getSelected()) {
            holder.addCartBtn.setVisibility(View.GONE);
            holder.layout.setVisibility(View.VISIBLE);
        } else {
            holder.addCartBtn.setVisibility(View.VISIBLE);
            holder.layout.setVisibility(View.GONE);
        }

        // Add cart listener
        holder.addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setSelected(true);
                food.setAmount(1);
                notifyItemChanged(pos); // To redraw the list
            }
        });

        // Count amount listeners
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setAmount(food.getAmount() + 1);
                holder.amount.setText(String.valueOf(food.getAmount()));
            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = food.getAmount();
                if (current > 1) {
                    food.setAmount(current - 1);
                    holder.amount.setText(String.valueOf(food.getAmount()));
                } else {
                    food.setSelected(false);
                    food.setAmount(0);
                    notifyItemChanged(pos); // To redraw the list
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, desc, amount, price;
        Button addCartBtn, addBtn, subBtn;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgView);
            title = itemView.findViewById(R.id.foodTitle);
            desc = itemView.findViewById(R.id.descText);
            amount = itemView.findViewById(R.id.amountText);
            addBtn = itemView.findViewById(R.id.addBtn);
            subBtn = itemView.findViewById(R.id.subBtn);
            price = itemView.findViewById(R.id.priceText);
            addCartBtn = itemView.findViewById(R.id.addCartBtn);
            layout = itemView.findViewById(R.id.countAmount);
        }
    }
}

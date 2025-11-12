package com.example.restauyou.CustomerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.FoodItem;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Context context;
    ArrayList<FoodItem> foods;

    public FoodAdapter (Context context, ArrayList<FoodItem> foods){
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_home_food_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        FoodItem food = foods.get(position);
        holder.title.setText(food.getTitle());
        holder.desc.setText(food.getDescription());
        holder.amount.setText(food.getAmount());
        holder.price.setText(food.getPrice());
        holder.img.setImageResource(food.getImg());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, desc, amount;
        Button addBtn, subBtn, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgView);
            title = itemView.findViewById(R.id.foodTitle);
            desc = itemView.findViewById(R.id.descText);
            amount = itemView.findViewById(R.id.amountText);
            addBtn = itemView.findViewById(R.id.addBtn);
            subBtn = itemView.findViewById(R.id.subBtn);
            price = itemView.findViewById(R.id.priceText);
        }
    }
}

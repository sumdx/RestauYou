package com.example.restauyou.CustomerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restauyou.ModelClass.CartItem;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.ModelClass.SharedCartModel;
import com.example.restauyou.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    ArrayList<CartItem> foods;
    SharedCartModel sharedCartModel;
    public ArrayList<CartItem> getCartItems() {
        return foods;
    }

    public void setCartItems(ArrayList<CartItem> foods) {
        this.foods = foods;
    }


    public CartAdapter (Context context, ArrayList<CartItem> foods,SharedCartModel sharedCartModel){
        this.context = context;
        this.foods = foods;
        this.sharedCartModel = sharedCartModel;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_menu_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        MenuItem food = foods.get(position).getMenuItem();
        holder.title.setText(food.getItemTitle());
        holder.amount.setText(String.valueOf(food.getAmount()));
        holder.price.setText(String.valueOf(foods.get(position).getTotalPrice()));
//        holder.img.setImageResource(food.getItemImg());
        Glide.with(context).load(food.getItemImageUrl()).into(holder.img);

        // Count amount listeners
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food.setAmount(foods.get(position).getQuantity() + 1);
                holder.amount.setText(String.valueOf(food.getAmount()));
                sharedCartModel.addToCart(food);
            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = foods.get(position).getQuantity();
                if (current > 1) {
                    food.setAmount(current - 1);
                    holder.amount.setText(String.valueOf(food.getAmount()));
                    sharedCartModel.removeFromCart(food);
                } else {
                    removeItem(holder.getAbsoluteAdapterPosition());
                    sharedCartModel.removeFromCart(food);
                }
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder.getAbsoluteAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }


    private void removeItem(int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, amount, price;
        Button addBtn, subBtn;
        ImageButton delBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgView);
            title = itemView.findViewById(R.id.foodTitle);
            amount = itemView.findViewById(R.id.amountText);
            addBtn = itemView.findViewById(R.id.addBtn);
            subBtn = itemView.findViewById(R.id.subBtn);
            price = itemView.findViewById(R.id.priceText);
            delBtn = itemView.findViewById(R.id.delBtn);
        }
    }
}

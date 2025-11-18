package com.example.restauyou.AdminAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;

import java.util.ArrayList;

public class AdminMenuEditAdapter extends RecyclerView.Adapter<AdminMenuEditAdapter.ViewHolder> {

    Context context;
    ArrayList<MenuItem> menuItemArraylist;
    public ArrayList<MenuItem> getMenuItemArraylist() {
        return menuItemArraylist;

    }

    public void setMenuItemArraylist(ArrayList<MenuItem> menuItemArraylist) {
        this.menuItemArraylist = menuItemArraylist;
        notifyDataSetChanged();
    }


    public AdminMenuEditAdapter (Context context, ArrayList<MenuItem> menuItemArraylist){
        this.context = context;
        this.menuItemArraylist = menuItemArraylist;
    }

    @NonNull
    @Override
    public AdminMenuEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.editable_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = menuItemArraylist.get(position);
        holder.itemTitle.setText(menuItem.getItemTitle());
        holder.itemPrice.setText(String.format("$ %s", menuItem.getItemPrice()));
        holder.itemDescription.setText(menuItem.getItemDescription());
        holder.itemAvailability.setText(menuItem.getItemAvailability());
        Glide.with(context).load(menuItem.getItemImageUrl()).into(holder.itemImage);

    }



    @Override
    public int getItemCount() {
        return menuItemArraylist != null ? menuItemArraylist.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitle, itemDescription, itemAvailability, itemPrice ;
        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemAvailability = itemView.findViewById(R.id.itemAvailability);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}

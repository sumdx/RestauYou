package com.example.restauyou.CustomerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauyou.ModelClass.MenuFilter;
import com.example.restauyou.R;

import java.util.ArrayList;

public class MenuFilterAdapter extends RecyclerView.Adapter<MenuFilterAdapter.ViewHolder> {
    Context context;
    ArrayList<MenuFilter> choose;

    public MenuFilterAdapter(Context context, ArrayList<MenuFilter> choose) {
        this.context = context;
        this.choose = choose;
    }

    @NonNull
    @Override
    public MenuFilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_customer_menu_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuFilterAdapter.ViewHolder holder, int position) {
        MenuFilter type = choose.get(position);
        holder.btnText.setText(type.getCategory());
        holder.btnText.setSelected(type.getSelected());
        holder.btnText.setBackgroundResource(R.drawable.food_filter_selector);

        holder.btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, type.getCategory() + " is Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return choose.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnText = itemView.findViewById(R.id.btnText);
        }
    }
}

package com.example.restauyou.AdminAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restauyou.AdminFragment.AdminMenuEditFragment;
import com.example.restauyou.ModelClass.MenuItem;
import com.example.restauyou.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdminMenuEditAdapter extends RecyclerView.Adapter<AdminMenuEditAdapter.ViewHolder> {

    Context context;
    ArrayList<MenuItem> menuItemArraylist;
    FirebaseFirestore db;
    FirebaseStorage storage;
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
        db= FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
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
        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("menu_items").document(menuItem.getItemId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StorageReference storageReference = storage.getReference().child(menuItem.getItemImagePath());
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                int removePos = holder.getAdapterPosition();
                                if (removePos != RecyclerView.NO_POSITION) {
                                    menuItemArraylist.remove(removePos);
                                    notifyItemRemoved(removePos);
                                    notifyItemRangeChanged(removePos, menuItemArraylist.size());
                                }
                                Toast.makeText(context, "Successfully Delted Menu Data", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("fb", e.toString());
                                Toast.makeText(context, "Something Went Wrong, Please try Again.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("fb", e.toString());
                        Toast.makeText(context, "Something Went Wrong item to delete the , Please try Again.", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }



    @Override
    public int getItemCount() {
        return menuItemArraylist != null ? menuItemArraylist.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitle, itemDescription, itemAvailability, itemPrice ;
        ImageView itemImage;
        MaterialButton itemDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemAvailability = itemView.findViewById(R.id.itemAvailability);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

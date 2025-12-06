package com.example.restauyou.AdminFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.restauyou.ModelClass.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.restauyou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminMenuEditCustomDialog extends DialogFragment {
    EditText editMenuTitle, editMenuItemPrice, editMenuItemDescription;
    Button btnSubmit;
    FirebaseFirestore firebaseFirestore;
    CheckBox availibilityCheckBox;
    String itemId;
//    FirebaseFirestore firebaseFirestore;
    MenuItem menuItem;

    public AdminMenuEditCustomDialog(String itemId){
        this.itemId = itemId;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog_menu_edit, null);

        firebaseFirestore = FirebaseFirestore.getInstance();

        // Initialize objects by ids
        editMenuTitle = v.findViewById(R.id.editMenuItemTitle);
        editMenuItemPrice = v.findViewById(R.id.editMenuItemPrice);
        editMenuItemDescription = v.findViewById(R.id.editMenuItemDescription);
        btnSubmit = v.findViewById(R.id.btn_submit);
        availibilityCheckBox = v.findViewById(R.id.itemAvailabilityCheckBox);
        firebaseFirestore.collection("menu_items").document(itemId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                menuItem = documentSnapshot.toObject(MenuItem.class);
                editMenuTitle.setText(menuItem.getItemTitle());
                editMenuItemDescription.setText(menuItem.getItemDescription());
                editMenuItemPrice.setText(menuItem.getItemPrice());
                if (menuItem.getItemAvailability().equals("Available")){
                    availibilityCheckBox.setChecked(true);
                }
                Toast.makeText(getContext(), menuItem.getItemTitle(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("FBMENU", e.toString());
            }
        });


        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemTitle = editMenuTitle.getText().toString(),
                       itemDescription = editMenuItemDescription.getText().toString();
                Double itemPrice = Double.parseDouble(editMenuItemPrice.getText().toString());


                // Prevent empty fields
                if (itemTitle.isEmpty() || itemDescription.isEmpty() || editMenuItemPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

//                menuItem.setItemTitle(itemTitle);
//                menuItem.setItemDescription(itemDescription);
//                menuItem.setItemPrice(String.valueOf(itemPrice));
//                if(availibilityCheckBox.isChecked()){
//                    menuItem.setItemAvailability("Available");
//                }else{
//                    menuItem.setItemAvailability("Not Available");
//                }

                firebaseFirestore.collection("menu_items").document(itemId).update("itemTitle", itemTitle,
                        "itemDescription", itemDescription,
                        "itemPrice", itemPrice.toString(),
                        "itemAvailability", availibilityCheckBox.isChecked() ? "Available" : "Not Available").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getContext(),"Menu Item Update Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FBMENU", e.toString());
                    }
                });
//                // Send data back
//                Bundle bundle = new Bundle();
//                bundle.putString("name", name);
//                bundle.putString("email", email);
//                bundle.putString("phone", phone);
//                getParentFragmentManager().setFragmentResult("customerInputs", bundle);


                dismiss();
            }
        });
        return builder.create();
    }
}
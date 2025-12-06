package com.example.restauyou.AdminFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restauyou.R;

public class DialogAdminSettingsEditFragment extends DialogFragment {
    EditText restaurantEditName, restaurantEditAddr, restaurantEditEmail, restaurantEditPhone;
    Button submitBtn;
    private final String name, addr, email, phone;

    public DialogAdminSettingsEditFragment(String n, String a, String e, String p) {
        name = n;
        addr = a;
        email = e;
        phone = p;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog_admin_settings_edit, null);

        // Initialize objects by ids
        restaurantEditName = v.findViewById(R.id.editRestaurantName);
        restaurantEditAddr = v.findViewById(R.id.editRestaurantAddr);
        restaurantEditEmail = v.findViewById(R.id.editRestaurantEmail);
        restaurantEditPhone = v.findViewById(R.id.editRestaurantPhone);
        submitBtn = v.findViewById(R.id.submitBtn);

        // Pre-fill inputs
        restaurantEditName.setText(name);
        restaurantEditAddr.setText(addr);
        restaurantEditEmail.setText(email);
        restaurantEditPhone.setText(phone);

        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = restaurantEditName.getText().toString(),
                       addr = restaurantEditAddr.getText().toString(),
                       email = restaurantEditEmail.getText().toString(),
                       phone = restaurantEditPhone.getText().toString();

                // If empty -> error
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send data back
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("address", addr);
                bundle.putString("email", email);
                bundle.putString("phone", phone);
                getParentFragmentManager().setFragmentResult("adminInputs", bundle);
                dismiss();
            }
        });
        return builder.create();
    }
}
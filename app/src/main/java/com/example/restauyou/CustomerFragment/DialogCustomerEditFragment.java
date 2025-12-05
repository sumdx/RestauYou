package com.example.restauyou.CustomerFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restauyou.R;

public class DialogCustomerEditFragment extends DialogFragment {
    EditText editProfileName, editProfileEmail, editProfilePhone;
    Button btnSubmit;
    private final String name, email, phone;

    public DialogCustomerEditFragment(String n, String e, String p) {
        name = n;
        email = e;
        phone = p;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog_customer_edit, null);

        // Initialize objects by ids
        editProfileName = v.findViewById(R.id.editProfileName);
        editProfileEmail = v.findViewById(R.id.editProfileEmail);
        editProfilePhone = v.findViewById(R.id.editProfilePhone);
        btnSubmit = v.findViewById(R.id.btn_submit);

        // Pre-fill inputs
        editProfileName.setText(name);
        editProfileEmail.setText(email);
        editProfilePhone.setText(phone);

        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(v);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editProfileName.getText().toString(),
                       email = editProfileEmail.getText().toString(),
                       phone = editProfilePhone.getText().toString();

                // If empty -> error
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send data back
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("email", email);
                bundle.putString("phone", phone);
                getParentFragmentManager().setFragmentResult("customerInputs", bundle);
                dismiss();
            }
        });
        return builder.create();
    }
}
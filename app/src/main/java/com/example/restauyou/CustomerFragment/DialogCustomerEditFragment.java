package com.example.restauyou.CustomerFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restauyou.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DialogCustomerEditFragment extends DialogFragment {
    EditText editProfileName, editProfileEmail, editProfilePhone;
    Button btnSubmit;
    FirebaseFirestore firebaseFirestore;
    FragmentManager fm;
    private final String name, email, phone, userId;

    public DialogCustomerEditFragment(String n, String e, String p, String u, FragmentManager fm) {
        name = n;
        email = e;
        phone = p;
        userId = u;
        this.fm = fm;
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
        firebaseFirestore = FirebaseFirestore.getInstance();
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

                firebaseFirestore.collection("users").document(userId).update("name", name, "email", email, "phone", phone).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", phone);
                        bundle.putString("name", name);
                        bundle.putString("email", email);

                        fm.setFragmentResult("customerInputs", bundle);
                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something went wrong please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return builder.create();
    }
}
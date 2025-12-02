package com.example.restauyou.CustomerFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restauyou.LoginActivity;
import com.example.restauyou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerSettingsFragment extends Fragment {
    LinearLayout logout, changePswdItem, getSupportItem;
    Button btnApplyForJob;
    View rootView;
    FirebaseUser firebaseUser;
    View contentLayout;
    FragmentManager fragmentManager;
    TextView addAddressText, addCardText;

    SwitchCompat notifiSwitch, orderUpdateSwitch, promotionSwitch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_customer_settings, container, false);

        // Fragment manager
        fragmentManager = getParentFragmentManager();

        // Initialize objects by ids
        contentLayout = rootView.findViewById(R.id.settingsContainer);
        logout = rootView.findViewById(R.id.logoutItem);
        btnApplyForJob = rootView.findViewById(R.id.btnApplyForJob);
        addAddressText = rootView.findViewById(R.id.addAddressText);
        addCardText = rootView.findViewById(R.id.addCardText);
        notifiSwitch = rootView.findViewById(R.id.notifiSwitch);
        orderUpdateSwitch = rootView.findViewById(R.id.orderUpdateSwitch);
        promotionSwitch = rootView.findViewById(R.id.promotionSwitch);
        changePswdItem = rootView.findViewById(R.id.changePswdItem);
        getSupportItem = rootView.findViewById(R.id.getSupportItem);

        // Connect to Firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Add address listener
        addAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Add credit card listener
        addCardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Change password listener
        changePswdItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });

        // Get support listener
        getSupportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Contact Support");
                builder.setIcon(R.drawable.outline_contact_support_24);
                builder.setMessage("Do you want to get support from our online staff?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Support Requested", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Support Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        });
//        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if (isVisible()){
//                    if (contentLayout != null)
//                        contentLayout = getView().findViewById(R.id.settingsContainer);
//                        contentLayout.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

        // Logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        // Apply button
        btnApplyForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                assert getView() != null;
                contentLayout = getView().findViewById(R.id.settingsContainer);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.settingsApplyForJobContainer, new CustomerApplyForEmployeeFragment())
                        .addToBackStack(null).commit();

                contentLayout.setVisibility(View.GONE);

            }
        });
        return rootView;
    }
}
package com.example.restauyou.AdminFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restauyou.MainActivity;
import com.example.restauyou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminSettingsFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    LinearLayout logout, notifPrefItem, backupRestoreItem, userPermItem, taxSettingsItem, receiptSettingsItem;
    SwitchCompat autoOrderSwitch, emailNotiSwitch, soundAlertSwitch;
    private static final String PREFS_NAME = "UserAccount";
    private static final String ALERT_KEY = "CurrentAdminAlert";
    private static final String NOTIFI_KEY = "CurrentAdminNotifi";
    private static final String AUTO_ORDER_KEY = "CurrentAdminAuto";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_settings, container, false);

        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Wiring object by ids
        logout = view.findViewById(R.id.logoutItem);
        notifPrefItem = view.findViewById(R.id.notiPrefItem);
        backupRestoreItem = view.findViewById(R.id.backupRestoreItem);
        userPermItem = view.findViewById(R.id.userPermItem);
        taxSettingsItem = view.findViewById(R.id.taxSettingsItem);
        receiptSettingsItem = view.findViewById(R.id.receiptSettingsItem);
        autoOrderSwitch = view.findViewById(R.id.autoOrderSwitch);
        emailNotiSwitch = view.findViewById(R.id.emailNotiSwitch);
        soundAlertSwitch = view.findViewById(R.id.soundAlertSwitch);

        // Shared preference
        SharedPreferences sp = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Set switch status (if present)
        autoOrderSwitch.setChecked(sp.getBoolean(AUTO_ORDER_KEY, true));
        emailNotiSwitch.setChecked(sp.getBoolean(NOTIFI_KEY, true));
        soundAlertSwitch.setChecked(sp.getBoolean(ALERT_KEY, true));

        // Auto order switch listener
        autoOrderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                // Update shared preference
                sp.edit().putBoolean(AUTO_ORDER_KEY, isChecked).apply();

                String message = "Auto-accept orders ";
                if (isChecked)
                    message += "enabled";
                else
                    message += "disabled";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Notification switch listener
        emailNotiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                // Update shared preference
                sp.edit().putBoolean(NOTIFI_KEY, isChecked).apply();

                String message = "Email notifications ";
                if (isChecked)
                    message += "enabled";
                else
                    message += "disabled";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Sound switch listener
        soundAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                // Update shared preference
                sp.edit().putBoolean(ALERT_KEY, isChecked).apply();

                String message = "Sound alerts ";
                if (isChecked)
                    message += "enabled";
                else
                    message += "disabled";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Tax settings listener
        taxSettingsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        // Receipt settings listener
        receiptSettingsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        // User permission listener
        userPermItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        // Backup and restore listener
        backupRestoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        // Notification switch listener
        notifPrefItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        // Logout feature
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent loginIntent = new Intent(getContext(), MainActivity.class);
                startActivity(loginIntent);
            }
        });
        return view;
    }

    private void comingSoon() {
        Toast.makeText(getContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
    }
}
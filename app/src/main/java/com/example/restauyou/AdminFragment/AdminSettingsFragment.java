package com.example.restauyou.AdminFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restauyou.MainActivity;
import com.example.restauyou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminSettingsFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView tvWelcomeText;
    Button btnSignOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_settings, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        tvWelcomeText = view.findViewById(R.id.tvWelcomeText);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        tvWelcomeText.setText("Hello, "+firebaseUser.getDisplayName().split(" ")[0]);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent loginIntent = new Intent(getContext(), MainActivity.class);
                startActivity(loginIntent);
            }
        });


        return view;
    }
}
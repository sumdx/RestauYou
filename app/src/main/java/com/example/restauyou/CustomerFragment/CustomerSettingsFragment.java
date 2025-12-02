package com.example.restauyou.CustomerFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.restauyou.LoginActivity;
import com.example.restauyou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerSettingsFragment extends Fragment {
    LinearLayout logout;
    Button btnApplyForJob;
    View rootView;
    FirebaseUser firebaseUser;
    View contentLayout;
    FragmentManager fragmentManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_customer_settings, container, false);
        contentLayout = rootView.findViewById(R.id.settingsContainer);
        logout = rootView.findViewById(R.id.logoutItem);
        btnApplyForJob = rootView.findViewById(R.id.btnApplyForJob);
        fragmentManager = getParentFragmentManager();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnApplyForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                contentLayout = getView().findViewById(R.id.settingsContainer);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.settingsApplyForJobContainer, new CustomerApplyForEmployeeFragment())
                        .addToBackStack(null).commit();

                contentLayout.setVisibility(View.GONE);

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
        return rootView;
    }


}
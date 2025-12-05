package com.example.restauyou.CustomerFragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restauyou.LoginActivity;
import com.example.restauyou.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerSettingsFragment extends Fragment {
    LinearLayout logout, changePswdItem, getSupportItem, profileLayout;
    Button btnApplyForJob;
    TextView tvName, tvEmail;
    View rootView;
    FirebaseUser firebaseUser;
    View contentLayout;
    FragmentManager fm;
    TextView addAddressText, addCardText, nameText, emailText, phoneText, addressText, cardText, CVCText;
    SwitchCompat notifiSwitch, orderUpdateSwitch, promotionSwitch;
    private static final String PREFS_NAME = "UserAccount";
    private static final String NOTIFI_KEY = "CurrentNotifi";
    private static final String ORDER_UPDATE_KEY = "CurrentUpdate";
    private static final String PROMO_KEY = "CurrentPromo";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_customer_settings, container, false);

        // Fragment manager
        fm = getParentFragmentManager();

        // Initialize objects by ids
        tvEmail = rootView.findViewById(R.id.emailText);
        tvName = rootView.findViewById(R.id.nameText);
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
        profileLayout = rootView.findViewById(R.id.profileLayout);
        nameText = rootView.findViewById(R.id.nameText);
        emailText = rootView.findViewById(R.id.emailText);
        phoneText = rootView.findViewById(R.id.phoneText);
        addressText = rootView.findViewById(R.id.addressText);
        cardText = rootView.findViewById(R.id.cardText);
        CVCText = rootView.findViewById(R.id.CVCText);

        // Connect to Firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Shared preference
        SharedPreferences sp = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Set text
        nameText.setText(sp.getString("name", "Guest User"));
        emailText.setText(sp.getString("email", "guest@example.com"));
        phoneText.setText(sp.getString("phone", "+1 (111) 111-1111"));
        addressText.setText(sp.getString("address", "123 Example St, Earth"));
        cardText.setText(sp.getString("card", "•••• •••• •••• 4242"));
        CVCText.setText(sp.getString("CVC", "Expires 12/25"));

        // Set switch status (if present)
        notifiSwitch.setChecked(sp.getBoolean(NOTIFI_KEY, true));
        orderUpdateSwitch.setChecked(sp.getBoolean(ORDER_UPDATE_KEY, true));
        promotionSwitch.setChecked(sp.getBoolean(PROMO_KEY, true));

        // Profile linear layout listener
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        // Add address listener
        addAddressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        // Add credit card listener
        addCardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
            }
        });

        //Notification Switch Listener
        notifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                // Update shared preference
                sp.edit().putBoolean(NOTIFI_KEY, isChecked).apply();

                String message = "Notifications ";
                if (isChecked)
                    message += "enabled";
                else
                    message += "disabled";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Order Update Switch Listener
        orderUpdateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                // Update shared preference
                sp.edit().putBoolean(ORDER_UPDATE_KEY, isChecked).apply();

                String message = "Order Updates ";
                if (isChecked)
                    message += "enabled";
                else
                    message += "disabled";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Promotion Switch Listener
        promotionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                // Update shared preference
                sp.edit().putBoolean(PROMO_KEY, isChecked).apply();

                String message = "Promotions ";
                if (isChecked)
                    message += "enabled";
                else
                    message += "disabled";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Change password listener
        changePswdItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comingSoon();
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

        // Logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        // Apply btn listener
        btnApplyForJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prevent guest
                if (firebaseUser == null) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                assert getView() != null;
                contentLayout = getView().findViewById(R.id.defaultSettings);
                contentLayout.setVisibility(View.GONE);

                fm.beginTransaction()
                    .replace(R.id.settingsApplyForJobContainer, new CustomerApplyForEmployeeFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });

        // Handle device's back button
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // Checks if the Home Fragment is resumed
                if (isResumed()) {
                    if (contentLayout != null)
                        contentLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // Listener for user edited inputs
        fm.setFragmentResultListener("customerInputs", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Get inputs
                String name = result.getString("name"),
                       email = result.getString("email"),
                       phone = result.getString("phone");

                // Set text
                nameText.setText(name);
                emailText.setText(email);
                phoneText.setText(phone);

                // Store inputs
                SharedPreferences.Editor e = sp.edit();
                e.putString("name", name);
                e.putString("email", email);
                e.putString("phone", phone);
                e.apply();

                // Confirm
                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private void showDialog() {
        if (firebaseUser == null) {
            Toast.makeText(getContext(), "Please log in first before editing your profile", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass data to dialog
        String name = nameText.getText().toString(),
               email = emailText.getText().toString(),
               phone = phoneText.getText().toString();
        DialogCustomerEditFragment d = new DialogCustomerEditFragment(name, email, phone);
        d.show(fm, "CustomInputTag");
    }

    private void comingSoon() {
        Toast.makeText(getContext(), "Coming Soon!", Toast.LENGTH_SHORT).show();
    }
}
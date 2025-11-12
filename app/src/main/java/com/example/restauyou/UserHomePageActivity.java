package com.example.restauyou;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.restauyou.CustomerAdapters.CustomerHomeAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHomePageActivity extends AppCompatActivity {
    FirebaseUser user;
    Button logoutBtn, cartBtn, menuBtn, reserveBtn;
    TabLayout tl;
    ViewPager2 vp2;
    private String[] tabTitles = {"All", "Pizza", "Burgers", "Pasta", "Salads"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Wiring objects by ids
        user = FirebaseAuth.getInstance().getCurrentUser();
        tl = findViewById(R.id.userTabLayout);
        vp2 = findViewById(R.id.userVP2);
        cartBtn = findViewById(R.id.cartBtn);
        menuBtn = findViewById(R.id.menuBtn);
        reserveBtn = findViewById(R.id.reserveBtn);
        logoutBtn = findViewById(R.id.demoLogout);

        // This can be useful later on
        //displayName.setText(user.getDisplayName());

        // Connect TabLayout with ViewPager2
        vp2.setAdapter(new CustomerHomeAdapter(getSupportFragmentManager(), getLifecycle()));
        new TabLayoutMediator(tl, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                tab.setText(tabTitles[i]);
            }
        }).attach();

        // Log out function
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(UserHomePageActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
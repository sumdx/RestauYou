package com.example.restauyou;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.MenuItem;
import android.window.OnBackInvokedDispatcher;

import com.example.restauyou.R;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.restauyou.AdminAdapters.AdminBottomNavigationAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminHomePageActivity extends AppCompatActivity {
    private  ViewPager2 adminViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminBottomNavigationView);
        adminViewPager = findViewById(R.id.adminViewPager);

        AdminBottomNavigationAdapter adminBottomNavigationAdapter = new AdminBottomNavigationAdapter(getSupportFragmentManager(), getLifecycle());
        adminViewPager.setAdapter(adminBottomNavigationAdapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.adminMenuItem1) {
                    adminViewPager.setCurrentItem(0);
                } else if (itemId == R.id.adminMenuItem2) {
                    adminViewPager.setCurrentItem(1);
                } else if (itemId == R.id.adminMenuItem3) {
                    adminViewPager.setCurrentItem(2);
                } else if (itemId == R.id.adminMenuItem4) {
                    adminViewPager.setCurrentItem(3);
                } else if (itemId == R.id.adminMenuItem5) {
                    adminViewPager.setCurrentItem(4);
                }

                return true;
            }
        });


        adminViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }





}
package com.example.restauyou;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.restauyou.CustomerAdapters.CustomerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerHomePageActivity extends AppCompatActivity {
    FirebaseUser user;
    BottomNavigationView bnv;
    ViewPager2 vp2;

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
        bnv = findViewById(R.id.userBottomNavigation);
        vp2 = findViewById(R.id.userVP2);

        // Set adapter
        vp2.setAdapter(new CustomerAdapter(getSupportFragmentManager(), getLifecycle()));

        // Attach viewPager with bottomNavigation
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
         @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                 bnv.getMenu().getItem(position).setChecked(true);
             }
        });

        // Attach bottomNavigation with viewPager
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.menuItem)
                    vp2.setCurrentItem(0);
                else if (id == R.id.orderItem)
                    vp2.setCurrentItem(1);
                else if (id == R.id.reserveItem)
                    vp2.setCurrentItem(2);
                else
                    vp2.setCurrentItem(3);
                return false;
            }
        });
    }

    // For backBtn from CustomerOrder Fragment
    public void orderBackBtnClicked() {
        vp2.setCurrentItem(0);
        bnv.setSelectedItemId(R.id.menuItem);
    }

    // From Cart to Order Fragment
    public void checkOutTransitToOrder() {
        vp2.setCurrentItem(1);
        bnv.setSelectedItemId(R.id.orderItem);
    }
}


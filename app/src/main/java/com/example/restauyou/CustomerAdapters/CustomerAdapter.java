package com.example.restauyou.CustomerAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.restauyou.CustomerFragment.CustomerCartFragment;
import com.example.restauyou.CustomerFragment.CustomerHomeFragment;
import com.example.restauyou.SettingsFragment;
import com.example.restauyou.UserFragments.ReservationFragment;

public class CustomerAdapter extends FragmentStateAdapter {
    public CustomerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CustomerCartFragment();
            case 2:
                return new ReservationFragment();
            case 3:
                return new SettingsFragment();
            default:
                return new CustomerHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

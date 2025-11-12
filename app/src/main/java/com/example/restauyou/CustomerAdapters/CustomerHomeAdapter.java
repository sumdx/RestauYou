package com.example.restauyou.CustomerAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.restauyou.CusomterFragment.CustomerHomeFragment;

public class CustomerHomeAdapter extends FragmentStateAdapter {
    public CustomerHomeAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new CustomerHomeFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

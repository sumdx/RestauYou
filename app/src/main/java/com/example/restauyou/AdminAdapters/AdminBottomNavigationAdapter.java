package com.example.restauyou.AdminAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.restauyou.AdminFragment.AdminMenuEditFragment;
import com.example.restauyou.AdminFragment.AdminMenuItemAddFragment;
import com.example.restauyou.AdminFragment.AdminOrderManagementFragment;
import com.example.restauyou.AdminFragment.AdminScheduleMakerFragment;
import com.example.restauyou.AdminFragment.AdminSettingsFragment;
import com.example.restauyou.AdminFragment.AdminStaffManagementFragment;


public class AdminBottomNavigationAdapter extends FragmentStateAdapter {
    public AdminBottomNavigationAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1 :
                return new AdminStaffManagementFragment();
            case 2:
                return new AdminOrderManagementFragment();
            case 3:
                return new AdminScheduleMakerFragment();
            case 4:
                return new AdminSettingsFragment();
            default: // Case 0
                return new AdminMenuEditFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

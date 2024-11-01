package com.example.coffeeappmanage.activity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.R;
import com.google.android.material.tabs.TabLayoutMediator;


public class CaPheAdapter extends FragmentStateAdapter {
    public CaPheAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TatCaCaPheFragment();
            case 1:
                return new XepHangCaPheFragment();
            case 2:
                return new GiaCaPheFragment();
            case 3:
                return new KhuyenMaiCaPheFragment();
            default:
                return new TatCaCaPheFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}

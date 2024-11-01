package com.example.coffeeappmanage.activity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SinhToAdapter extends FragmentStateAdapter {
    public SinhToAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TatCaSinhToFragment();
            case 1:
                return new XepHangSinhToFragment();
            case 2:
                return new GiaSinhToFragment();
            case 3:
                return new KhuyenMaiSinhToFragment();
            default:
                return new TatCaSinhToFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

package com.example.coffeeappmanage.activity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TraSuaAdapter extends FragmentStateAdapter {


    public TraSuaAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TatCaTraSuaFragment();
            case 1:
                return new XepHangTraSuaFragment();
            case 2:
                return new GiaTraSuaFragment();
            case 3:
                return new KhuyenMaiTraSuaFragment();
            default:
                return new TatCaTraSuaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

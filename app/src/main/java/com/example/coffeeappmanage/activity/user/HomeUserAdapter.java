package com.example.coffeeappmanage.activity.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class HomeUserAdapter extends FragmentStateAdapter {
    public HomeUserAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CaPheFragment();
            case 1:
                return new TraSuaFragment();
            case 2:
                return new SinhToFragment();
            default:
                return new CaPheFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

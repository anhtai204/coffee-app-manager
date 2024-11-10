package com.example.coffeeappmanage.activity.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.activity.user.CaPheFragment;
import com.example.coffeeappmanage.activity.user.SinhToFragment;
import com.example.coffeeappmanage.activity.user.TraSuaFragment;
import com.example.coffeeappmanage.model.User;

public class HomeAdminAdapter extends FragmentStateAdapter {
    private User user;

    public HomeAdminAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User user) {
        super(fragmentManager, lifecycle);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                fragment = new TheLoaiAdminFragment();
                break;
            case 1:
                fragment = new DoUongAdminFragment();
                break;
            case 2:
                fragment = new DonHangAdminFragment();
                break;
            case 3:
                fragment = new CaiDatAdminFragment();
                break;
            default:
                fragment = new TheLoaiAdminFragment();
        }

        // Truyền đối tượng User vào Fragment qua Bundle
        if (fragment != null) {
            bundle.putSerializable("user", user);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

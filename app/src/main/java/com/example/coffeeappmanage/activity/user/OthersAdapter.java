package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.model.User;

public class OthersAdapter extends FragmentStateAdapter {
    private User user;

    public OthersAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User user) {
        super(fragmentManager, lifecycle);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TatCaOthersFragment();
                break;
            case 1:
                fragment = new XepHangOthersFragment();
                break;
            case 2:
                fragment = new GiaOthersFragment();
                break;
            case 3:
                fragment = new KhuyenMaiOthersFragment();
                break;
            default:
                return new TatCaOthersFragment();
        }

        // Truyền đối tượng User vào mỗi Fragment qua Bundle
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);  // Truyền đối tượng User vào Bundle
            fragment.setArguments(bundle);  // Đặt Bundle vào Fragment
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.tabs.TabLayoutMediator;


public class CaPheAdapter extends FragmentStateAdapter {
    private User user;

    public CaPheAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User user) {
        super(fragmentManager, lifecycle);
        this.user = user;
    }


//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new TatCaCaPheFragment();
//            case 1:
//                return new XepHangCaPheFragment();
//            case 2:
//                return new GiaCaPheFragment();
//            case 3:
//                return new KhuyenMaiCaPheFragment();
//            default:
//                return new TatCaCaPheFragment();
//        }
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TatCaCaPheFragment();
                break;
            case 1:
                fragment = new XepHangCaPheFragment();
                break;
            case 2:
                fragment = new GiaCaPheFragment();
                break;
            case 3:
                fragment = new KhuyenMaiCaPheFragment();
                break;
            default:
                return new TatCaCaPheFragment();
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

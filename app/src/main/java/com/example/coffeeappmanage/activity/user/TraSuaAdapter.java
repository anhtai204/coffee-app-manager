package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.model.User;

public class TraSuaAdapter extends FragmentStateAdapter {

    private User user;

    public TraSuaAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User user) {
        super(fragmentManager, lifecycle);
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new TatCaTraSuaFragment();
//            case 1:
//                return new XepHangTraSuaFragment();
//            case 2:
//                return new GiaTraSuaFragment();
//            case 3:
//                return new KhuyenMaiTraSuaFragment();
//            default:
//                return new TatCaTraSuaFragment();
//        }

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TatCaTraSuaFragment();
                break;
            case 1:
                fragment = new XepHangTraSuaFragment();
                break;
            case 2:
                fragment = new GiaTraSuaFragment();
                break;
            case 3:
                fragment = new KhuyenMaiTraSuaFragment();
                break;
            default:
                return new TatCaTraSuaFragment();
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

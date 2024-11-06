package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.model.User;


public class HomeUserAdapter extends FragmentStateAdapter {
    private User user;

    public HomeUserAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User user) {
        super(fragmentManager, lifecycle);
        this.user = user;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();

//        switch (position) {
//            case 0:
//                return new CaPheFragment();
//            case 1:
//                return new TraSuaFragment();
//            case 2:
//                return new SinhToFragment();
//            default:
//                return new CaPheFragment();
//        }

        switch (position) {
            case 0:
                fragment = new CaPheFragment();
                break;
            case 1:
                fragment = new TraSuaFragment();
                break;
            case 2:
                fragment = new SinhToFragment();
                break;
            default:
                fragment = new CaPheFragment();
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
        return 3;
    }
}

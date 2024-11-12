package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coffeeappmanage.model.User;


public class ViewUserAdapter extends FragmentStateAdapter {

    private User user;

//    public ViewUserAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        switch (position) {
//            case 0:
//                return new HomeUserFragment();
//            case 1:
//                return new OrderUserFragment();
//            case 2:
//                return new AccountUserFragment();
//            default:
//                return new HomeUserFragment();
//        }
//
//    }

    public ViewUserAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User user) {
        super(fragmentManager, lifecycle);
        this.user = user; // Lưu đối tượng User được truyền vào từ Activity
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                fragment = new HomeUserFragment();
                break;
            case 1:
                fragment = new OrderUserFragment();
                break;
            case 2:
                fragment = new AccountUserFragment();
                break;
            default:
                return new HomeUserFragment();
        }

        // Truyền đối tượng User vào mỗi Fragment qua Bundle
        if (fragment != null) {
            bundle.putSerializable("user", user);  // Truyền đối tượng User vào Bundle
            fragment.setArguments(bundle);  // Đặt Bundle vào Fragment
        }

        return fragment;

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

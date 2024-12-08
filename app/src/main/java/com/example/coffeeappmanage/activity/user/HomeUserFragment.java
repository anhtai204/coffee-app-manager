package com.example.coffeeappmanage.activity.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.effect.DepthPageTransformer;
import com.example.coffeeappmanage.activity.effect.ZoomOutPageTransformer;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeUserFragment extends Fragment {

    private TabLayout tab_layout_san_pham;
    private ViewPager2 viewPager_sanPham;
    private User user;

    public HomeUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_user, container, false);



        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            // Kiểm tra xem user có được truyền qua không
            if (user != null) {
                Log.d("user_home_frag", ": " + user.toString());
            }
        }

        tab_layout_san_pham = view.findViewById(R.id.tab_layout_san_pham);
        viewPager_sanPham = view.findViewById(R.id.viewPager_sanPham);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        HomeUserAdapter homeUserAdapter = new HomeUserAdapter(fragmentManager, getLifecycle(), user);
//        HomeUserAdapter homeUserAdapter = new HomeUserAdapter(getChildFragmentManager(), getLifecycle(), user);
        HomeUserAdapter homeUserAdapter = new HomeUserAdapter(getChildFragmentManager(), requireActivity().getLifecycle(), user);
        viewPager_sanPham.setAdapter(homeUserAdapter);

        tab_layout_san_pham.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_sanPham.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager_sanPham.setPageTransformer(new ZoomOutPageTransformer());
        viewPager_sanPham.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tab_layout_san_pham.selectTab(tab_layout_san_pham.getTabAt(position));
            }
        });

        return view;
    }

}
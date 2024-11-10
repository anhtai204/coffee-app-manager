package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.effect.ZoomOutPageTransformer;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.tabs.TabLayout;

public class OthersFragment extends Fragment {

    private TabLayout tab_layout_others;
    private ViewPager2 viewPager_sanPham_loc_others;
    private User user;

    public OthersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_others, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("CaPheFragment", "User: " + user.toString());
            }
        }


        tab_layout_others = view.findViewById(R.id.tab_layout_others);
        viewPager_sanPham_loc_others = view.findViewById(R.id.viewPager_sanPham_loc_others);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        OthersAdapter othersAdapter = new OthersAdapter(fragmentManager, getLifecycle(), user);

        viewPager_sanPham_loc_others.setAdapter(othersAdapter);

        tab_layout_others.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_sanPham_loc_others.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager_sanPham_loc_others.setPageTransformer(new ZoomOutPageTransformer());
        viewPager_sanPham_loc_others.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tab_layout_others.selectTab(tab_layout_others.getTabAt(position));
            }
        });

        return view;
    }
}
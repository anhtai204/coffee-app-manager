package com.example.coffeeappmanage.activity.user;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.effect.ZoomOutPageTransformer;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.tabs.TabLayout;



public class CaPheFragment extends Fragment {

    private TabLayout tab_layout_ca_phe;
    private ViewPager2 viewPager_sanPham_loc_caphe;
    private User user;

    public CaPheFragment() {
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

        View view = inflater.inflate(R.layout.fragment_ca_phe, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("CaPheFragment", "User: " + user.toString());
            }
        }


        tab_layout_ca_phe = view.findViewById(R.id.tab_layout_ca_phe);
        viewPager_sanPham_loc_caphe = view.findViewById(R.id.viewPager_sanPham_loc_caphe);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CaPheAdapter caPheAdapter = new CaPheAdapter(fragmentManager, getLifecycle(), user);

        viewPager_sanPham_loc_caphe.setAdapter(caPheAdapter);

        tab_layout_ca_phe.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_sanPham_loc_caphe.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager_sanPham_loc_caphe.setPageTransformer(new ZoomOutPageTransformer());
        viewPager_sanPham_loc_caphe.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tab_layout_ca_phe.selectTab(tab_layout_ca_phe.getTabAt(position));
            }
        });


        return view;
    }


}


package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.effect.ZoomOutPageTransformer;
import com.google.android.material.tabs.TabLayout;


public class SinhToFragment extends Fragment {

    private TabLayout tab_layout_sinh_to;
    private ViewPager2 viewPager_sanPham_loc_sinhto;

    public SinhToFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sinh_to, container, false);

        tab_layout_sinh_to = view.findViewById(R.id.tab_layout_sinh_to);
        viewPager_sanPham_loc_sinhto = view.findViewById(R.id.viewPager_sanPham_loc_sinhto);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SinhToAdapter sinhToAdapter = new SinhToAdapter(fragmentManager, getLifecycle());

        viewPager_sanPham_loc_sinhto.setAdapter(sinhToAdapter);

        tab_layout_sinh_to.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_sanPham_loc_sinhto.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager_sanPham_loc_sinhto.setPageTransformer(new ZoomOutPageTransformer());
        viewPager_sanPham_loc_sinhto.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tab_layout_sinh_to.selectTab(tab_layout_sinh_to.getTabAt(position));
            }
        });


        return view;
    }
}
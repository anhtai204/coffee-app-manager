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

public class TraSuaFragment extends Fragment {

    private TabLayout tab_layout_tra_sua;
    private ViewPager2 viewPager_sanPham_loc_trasua;
    private User user;

    public TraSuaFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tra_sua, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("CTraSuaFragment", "User: " + user.toString());
            }
        }


        tab_layout_tra_sua = view.findViewById(R.id.tab_layout_tra_sua);
        viewPager_sanPham_loc_trasua = view.findViewById(R.id.viewPager_sanPham_loc_trasua);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        TraSuaAdapter traSuaAdapter = new TraSuaAdapter(fragmentManager, getLifecycle(), user);

        viewPager_sanPham_loc_trasua.setAdapter(traSuaAdapter);

        tab_layout_tra_sua.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_sanPham_loc_trasua.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager_sanPham_loc_trasua.setPageTransformer(new ZoomOutPageTransformer());
        viewPager_sanPham_loc_trasua.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tab_layout_tra_sua.selectTab(tab_layout_tra_sua.getTabAt(position));
            }
        });


        return view;
    }
}
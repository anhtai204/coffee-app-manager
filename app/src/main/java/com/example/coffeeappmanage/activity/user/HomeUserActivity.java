package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.effect.DepthPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeUserActivity extends AppCompatActivity {

    // Quản lý phần navigation home, order, account
    private BottomNavigationView navigationView;
    private ViewPager2 viewPager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Quản lý phần navigation home, order, account
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewUserAdapter viewUserAdapter = new ViewUserAdapter(fragmentManager, getLifecycle());

        viewPager.setAdapter(viewUserAdapter);


        navigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_home) {
                viewPager.setCurrentItem(0);
            } else if (item.getItemId() == R.id.action_order) {
                viewPager.setCurrentItem(1);
            } else if (item.getItemId() == R.id.action_account) {
                viewPager.setCurrentItem(2);
            }
            return true;
        });

        // Thêm OnPageChangeCallback
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Cập nhật giá trị focus ở BottomNavigationView
                switch (position) {
                    case 0:
                        navigationView.setSelectedItemId(R.id.action_home);
                        break;
                    case 1:
                        navigationView.setSelectedItemId(R.id.action_order);
                        break;
                    case 2:
                        navigationView.setSelectedItemId(R.id.action_account);
                        break;
                }
            }
        });
        viewPager.setPageTransformer(new DepthPageTransformer());
        // kết thúc quản lí phần navigation home, order, account


    }
}
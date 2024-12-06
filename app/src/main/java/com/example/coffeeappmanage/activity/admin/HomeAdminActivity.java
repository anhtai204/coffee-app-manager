package com.example.coffeeappmanage.activity.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.effect.DepthPageTransformer;
import com.example.coffeeappmanage.activity.user.HomeUserActivity;
import com.example.coffeeappmanage.activity.user.ViewUserAdapter;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdminActivity extends AppCompatActivity {

    private BottomNavigationView bottom_nav_admin;
    private ViewPager2 view_pager_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        User user = null;
        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().get("user");

            Log.d("user_home", user.toString());
        }

        // Quản lý phần navigation home, order, account
        bottom_nav_admin = findViewById(R.id.bottom_nav_admin);
        view_pager_admin = findViewById(R.id.view_pager_admin);

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeAdminAdapter homeAdminAdapter = new HomeAdminAdapter(fragmentManager, getLifecycle(), user);

        view_pager_admin.setAdapter(homeAdminAdapter);

        // Tắt tính năng vuốt trong ViewPager2
//        view_pager_admin.setUserInputEnabled(false);

        bottom_nav_admin.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_the_loai) {
                view_pager_admin.setCurrentItem(0);
            } else if (item.getItemId() == R.id.action_do_uong) {
                view_pager_admin.setCurrentItem(1);
            } else if (item.getItemId() == R.id.action_don_hang) {
                view_pager_admin.setCurrentItem(2);
            } else if (item.getItemId() == R.id.action_cai_dat) {
                view_pager_admin.setCurrentItem(3);
            }
            return true;
        });

        // Thêm OnPageChangeCallback
        view_pager_admin.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Cập nhật giá trị focus ở BottomNavigationView
                switch (position) {
                    case 0:
                        bottom_nav_admin.setSelectedItemId(R.id.action_the_loai);
                        break;
                    case 1:
                        bottom_nav_admin.setSelectedItemId(R.id.action_do_uong);
                        break;
                    case 2:
                        bottom_nav_admin.setSelectedItemId(R.id.action_don_hang);
                        break;
                    case 3:
                        bottom_nav_admin.setSelectedItemId(R.id.action_cai_dat);
                        break;
                }
            }
        });
        view_pager_admin.setPageTransformer(new DepthPageTransformer());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Bạn muốn thoát không?")
                .setTitle("Thoát")
                .setCancelable(false)
                .setPositiveButton("Có", (dialog, id) -> {
                    HomeAdminActivity.super.onBackPressed();
                })
                .setNegativeButton("Không", (dialog, id) -> {
                    onVisibleBehindCanceled();
                })
                .show();
    }


}
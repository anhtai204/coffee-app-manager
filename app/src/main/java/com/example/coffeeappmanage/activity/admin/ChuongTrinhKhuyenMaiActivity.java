package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCDiKemDoUongAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCKhuyenMaiAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.ResponseKhuyenMai;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuongTrinhKhuyenMaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    private User user;
    private RecyclerView rcChuongTrinhKhuyenMai;
    FloatingActionButton fabThemKhuyenMai;
    List<KhuyenMai> khuyenMaiList;
    RCKhuyenMaiAdapter rcKhuyenMaiAdapter;
    private ActivityResultLauncher<Intent> suaKhuyenMaiLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuong_trinh_khuyen_mai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().get("user");

            Log.d("ChuongTrinhKhuyenMaiActivity user: ", user.toString());

        }
        toolbar = findViewById(R.id.toolbarChuongTrinhKhuyenMai);
        setSupportActionBar(toolbar);

        rcChuongTrinhKhuyenMai = findViewById(R.id.rcChuongTrinhKhuyenMai);
        rcChuongTrinhKhuyenMai.setLayoutManager(new LinearLayoutManager(this));
        rcChuongTrinhKhuyenMai.setHasFixedSize(true);
        khuyenMaiList = new ArrayList<>();

        suaKhuyenMaiLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            KhuyenMai updatedKhuyenMai = (KhuyenMai) data.getSerializableExtra("updatedKhuyenMai");
                            Log.d("updated khuyến mại: ", updatedKhuyenMai.toString());
                            if (updatedKhuyenMai != null) {
                                loadData();
                                Toast.makeText(ChuongTrinhKhuyenMaiActivity.this, "Cập nhật khuyến mại thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        rcKhuyenMaiAdapter = new RCKhuyenMaiAdapter(this, khuyenMaiList, user, suaKhuyenMaiLauncher);
        rcChuongTrinhKhuyenMai.setAdapter(rcKhuyenMaiAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        KhuyenMai newKhuyenMai = (KhuyenMai) intent.getSerializableExtra("newKhuyenMai");

                        if (newKhuyenMai != null) {
                            khuyenMaiList.add(newKhuyenMai);
                            Log.d("newKhuyenMai: ", newKhuyenMai.toString());
                            loadData();
                        }
                    }
                }
            }
        );

        fabThemKhuyenMai = findViewById(R.id.fabThemKhuyenMai);
        fabThemKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentThemTheLoai = new Intent(ChuongTrinhKhuyenMaiActivity.this, ThemKhuyenMaiActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                intentThemTheLoai.putExtras(data);
                activityResultLauncher.launch(intentThemTheLoai);
            }
        });
    }

    private void loadData(){
        ApiService.apiService.getAllKhuyenMai().enqueue(new Callback<ResponseKhuyenMai>() {
            @Override
            public void onResponse(Call<ResponseKhuyenMai> call, Response<ResponseKhuyenMai> response) {
                ResponseKhuyenMai responseKhuyenMai = response.body();
                List<KhuyenMai> listKhuyenMaiApi = responseKhuyenMai.getData();
                int statusCode = responseKhuyenMai.getStatusCode();
                String message = responseKhuyenMai.getMessage();

                rcKhuyenMaiAdapter.setData(listKhuyenMaiApi);
                rcKhuyenMaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseKhuyenMai> call, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuBack) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
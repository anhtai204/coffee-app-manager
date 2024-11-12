package com.example.coffeeappmanage.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCKhuyenMaiAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCKhuyenMaiUserAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.ResponseKhuyenMai;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhuyenMaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    private User user;
    List<KhuyenMai> khuyenMaiList;
    private DonHang donHang;
    TextView tvLuuKhuyenMai;
    RCKhuyenMaiUserAdapter rcKhuyenMaiUserAdapter;
    RecyclerView rcKhuyenMai;
    KhuyenMai khuyenMai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khuyen_mai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            donHang = (DonHang) getIntent().getExtras().get("donhang");
            user = (User) getIntent().getExtras().get("user");

            Log.d("KhuyenMaiActivity donhang: ", donHang.toString());
            Log.d("KhuyenMaiActivity user: ", user.toString());
        }

        rcKhuyenMai = findViewById(R.id.rcKhuyenMai);
        tvLuuKhuyenMai = findViewById(R.id.tvLuuKhuyenMai);


        rcKhuyenMai = findViewById(R.id.rcKhuyenMai);
        rcKhuyenMai.setLayoutManager(new LinearLayoutManager(this));
        rcKhuyenMai.setHasFixedSize(true);
        khuyenMaiList = new ArrayList<>();

        rcKhuyenMaiUserAdapter = new RCKhuyenMaiUserAdapter(this, khuyenMaiList, user);
        rcKhuyenMai.setAdapter(rcKhuyenMaiUserAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        tvLuuKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KhuyenMai selectedKhuyenMai = rcKhuyenMaiUserAdapter.getSelectedKhuyenMai();
                if (selectedKhuyenMai != null) {
//                    Toast.makeText(KhuyenMaiActivity.this, "Khuyến mãi được chọn: " + selectedKhuyenMai.getPhanTramKhuyenMai() + "%", Toast.LENGTH_SHORT).show();
                    if(selectedKhuyenMai.getDonHangToiThieu() >= donHang.getGiaDonHang()){
                        Toast.makeText(KhuyenMaiActivity.this, "Đơn hàng chưa đủ điều kiện!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("khuyenMai", selectedKhuyenMai);
                        setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                        finish();
                    }
                    
                } else {
                    // Không có khuyến mãi nào được chọn
                    Toast.makeText(KhuyenMaiActivity.this, "Vui lòng chọn một khuyến mãi", Toast.LENGTH_SHORT).show();
                }

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

                rcKhuyenMaiUserAdapter.setData(listKhuyenMaiApi);
                rcKhuyenMaiUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseKhuyenMai> call, Throwable throwable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
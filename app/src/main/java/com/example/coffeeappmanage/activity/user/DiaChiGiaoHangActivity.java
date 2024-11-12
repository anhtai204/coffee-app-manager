package com.example.coffeeappmanage.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.DiaChi;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.PhuongThucThanhToan;
import com.example.coffeeappmanage.model.User;

public class DiaChiGiaoHangActivity extends AppCompatActivity {

    Toolbar toolbar;
    DiaChi diaChi;
    DonHang donHang;
    User user;
    RadioButton rabDiaChi1, rabDiaChi2, rabDiaChi3;
    TextView tvLuuDiaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dia_chi_giao_hang);
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

            Log.d("DiaChiGiaoHangActivity donhang: ", donHang.toString());
            Log.d("DiaChiGiaoHangActivity user: ", user.toString());
        }

        rabDiaChi1 = findViewById(R.id.rabDiaChi1);
        rabDiaChi2 = findViewById(R.id.rabDiaChi2);
        rabDiaChi3 = findViewById(R.id.rabDiaChi3);
        tvLuuDiaChi = findViewById(R.id.tvLuuDiaChi);

        tvLuuDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rabDiaChi1.isChecked() && !rabDiaChi2.isChecked() &&
                        !rabDiaChi3.isChecked()){
                    Toast.makeText(DiaChiGiaoHangActivity.this, "Chọn phương địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    if(rabDiaChi1.isChecked()){
                        diaChi = new DiaChi(1, 0, "Trường DHSP Hà Nội");
                    } else if(rabDiaChi2.isChecked()){
                        diaChi = new DiaChi(2, 0, "Đại học quốc gia HN");
                    } else if(rabDiaChi3.isChecked()){
                        diaChi = new DiaChi(3, 0, "Đại học Bách Khoa HN");
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("diaChi", diaChi);
                    setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                    finish();
                }
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
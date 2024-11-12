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
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.PhuongThucThanhToan;
import com.example.coffeeappmanage.model.User;

public class PhuongThucThanhToanActivity extends AppCompatActivity {

    Toolbar toolbar;
    DonHang donHang;
    User user;
    RadioButton rabThanhToanTienMat, rabThanhToanThe, rabThanhTooanChuyenKhoan, rabThanhToanPaypal;
    TextView tvLuuThanhToan;
    PhuongThucThanhToan phuongThucThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phuong_thuc_thanh_toan);
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

            Log.d("PhuongThucThanhToanActivity donhang: ", donHang.toString());
            Log.d("PhuongThucThanhToanActivity user: ", user.toString());
        }

        rabThanhToanTienMat = findViewById(R.id.rabThanhToanTienMat);
        rabThanhToanThe = findViewById(R.id.rabThanhToanThe);
        rabThanhTooanChuyenKhoan = findViewById(R.id.rabThanhTooanChuyenKhoan);
        rabThanhToanPaypal = findViewById(R.id.rabThanhToanPaypal);
        tvLuuThanhToan = findViewById(R.id.tvLuuThanhToan);

        tvLuuThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rabThanhToanTienMat.isChecked() && !rabThanhToanThe.isChecked() &&
                !rabThanhTooanChuyenKhoan.isChecked() && !rabThanhToanPaypal.isChecked()){
                    Toast.makeText(PhuongThucThanhToanActivity.this, "Chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                } else {
                    if(rabThanhToanTienMat.isChecked()){
                        phuongThucThanhToan = new PhuongThucThanhToan(1, "Thanh toán tiền mặt");
                    } else if(rabThanhTooanChuyenKhoan.isChecked()){
                        phuongThucThanhToan = new PhuongThucThanhToan(3, "Chuyển khoản ngân hàng");
                    } else if(rabThanhToanThe.isChecked()){
                        phuongThucThanhToan = new PhuongThucThanhToan(2, "Credit or debit card");
                    } else if(rabThanhToanPaypal.isChecked()){
                        phuongThucThanhToan = new PhuongThucThanhToan(4, "Paypal");
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("phuongThucThanhToan", phuongThucThanhToan);
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
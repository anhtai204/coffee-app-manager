package com.example.coffeeappmanage.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.AnyRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseUser;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaActivity extends AppCompatActivity {

    Toolbar toolbar;
    RatingBar rateStar;
    EditText edtDanhGia;
    TextView btnGuiDanhGia;
    User user;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_gia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getExtras() != null){
            product = (Product) getIntent().getExtras().get("product");
            user = (User) getIntent().getExtras().get("user");

            Log.d("prod_danhgia: ", product.toString());
            Log.d("user_danhgia: ", user.toString());

        }

        rateStar = findViewById(R.id.rateStar);
        edtDanhGia = findViewById(R.id.edtDanhGia);
        btnGuiDanhGia = findViewById(R.id.btnGuiDanhGia);


        btnGuiDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rate_star = rateStar.getRating();
                String danh_gia = edtDanhGia.getText().toString().trim();


                if(rate_star == 0 || danh_gia.length()==0){
                    Toast.makeText(DanhGiaActivity.this, "Hãy nhập đánh giá và số sao!", Toast.LENGTH_SHORT).show();
                } else {
                    int id_product = product.getId_product();
                    float so_luong_sao = rate_star;
                    String comment = danh_gia;
                    int id_user = user.getId_user();

                    Log.d("id_product", id_product+"");
                    Log.d("id_user", id_user+"");
                    Log.d("so_luong_sao", so_luong_sao+"");
                    Log.d("comment", comment+"");

                    ApiService.apiService.insertRate(id_product, id_user, so_luong_sao, comment).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // Xử lý phản hồi thành công
                                Toast.makeText(DanhGiaActivity.this, "Đánh giá đã được gửi!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // Xử lý phản hồi không thành công
                                Toast.makeText(DanhGiaActivity.this, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Xử lý lỗi
                            Toast.makeText(DanhGiaActivity.this, "Gọi API thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("error_insert_rate", t.toString());
                        }
                    });


                }

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
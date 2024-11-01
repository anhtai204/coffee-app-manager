package com.example.coffeeappmanage.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.coffeeappmanage.activity.LoginActivity;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCProductAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCToppingAdapter;
import com.example.coffeeappmanage.activity.SignUpActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.Topping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvCount, tvTenSanPham, tvRate, tvCountRate, tvGiaSanPham;
    ImageView imgMinus, imgPlus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvCount = findViewById(R.id.tvCount);
        tvTenSanPham = findViewById(R.id.tvTenSanPham);
        tvRate = findViewById(R.id.tvRate);
        tvCountRate = findViewById(R.id.tvCountRate);
        tvGiaSanPham = findViewById(R.id.tvGiaSanPham);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);

        if(getIntent().getExtras() != null){
            Product product = (Product) getIntent().getExtras().get("product");

            float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            tvTenSanPham.setText(product.getTenSanPham());
            tvGiaSanPham.setText(decimalFormat.format(gia_sp)+"vnd");
            tvRate.setText(product.getAverage_star()+"");

            changeClickStatusDoUong();
            changeClickStatusKichThuoc();
            changeClickStatusDuong();
            createRecyclerTopping();
        }
    }

    private void createRecyclerTopping() {
        RecyclerView rcv_topping = findViewById(R.id.rcv_topping);
        rcv_topping.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcv_topping.setHasFixedSize(true);

        List<Topping> listTopping = new ArrayList<>();
        RCToppingAdapter rcToppingAdapter = new RCToppingAdapter(this, listTopping);
        rcv_topping.setAdapter(rcToppingAdapter);

        ApiService.apiService.getAllTopping().enqueue(new Callback<ResponseTopping>() {
            @Override
            public void onResponse(Call<ResponseTopping> call, Response<ResponseTopping> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseTopping responseTopping = response.body();
                    List<Topping> listToppingCallApi = responseTopping.getData();
                    int statusCode = responseTopping.getStatusCode();
                    String message = responseTopping.getMessage();

                    Log.e("list topping", listToppingCallApi.toString());

                    listTopping.clear(); // Xóa danh sách cũ
                    listTopping.addAll(listToppingCallApi);
                    rcToppingAdapter.notifyDataSetChanged(); // Cập nhật adapter sau khi thêm dữ liệu
                }
            }

            @Override
            public void onFailure(Call<ResponseTopping> call, Throwable throwable) {
                Toast.makeText(OrderProductActivity.this, "Call API failed: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                Log.e("failed topping", throwable.toString());
            }
        });
    }




    private void changeClickStatusDuong() {
        RadioGroup rgDuong = findViewById(R.id.rgDuong);
        RadioButton rbBinhThuong = findViewById(R.id.rbBinhThuong);
        RadioButton rbGiam = findViewById(R.id.rbGiam);

        rgDuong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbBinhThuong) {
                    rbBinhThuong.setBackgroundResource(R.drawable.custom_button_selected);
                    rbBinhThuong.setTextColor(Color.WHITE);
                    rbGiam.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbGiam.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbGiam) {
                    rbGiam.setBackgroundResource(R.drawable.custom_button_selected);
                    rbGiam.setTextColor(Color.WHITE);
                    rbBinhThuong.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbBinhThuong.setTextColor(getResources().getColor(R.color.brown));
                }
            }
        });
    }

    private void changeClickStatusKichThuoc() {
        RadioGroup rgKichThuoc = findViewById(R.id.rgKichThuoc);
        RadioButton rbNho = findViewById(R.id.rbNho);
        RadioButton rbVua = findViewById(R.id.rbVua);
        RadioButton rbLon = findViewById(R.id.rbLon);

        rgKichThuoc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbNho) {
                    rbNho.setBackgroundResource(R.drawable.custom_button_selected);
                    rbNho.setTextColor(Color.WHITE);
                    rbVua.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbVua.setTextColor(getResources().getColor(R.color.brown));
                    rbLon.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbLon.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbVua) {
                    rbVua.setBackgroundResource(R.drawable.custom_button_selected);
                    rbVua.setTextColor(Color.WHITE);
                    rbNho.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbNho.setTextColor(getResources().getColor(R.color.brown));
                    rbLon.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbLon.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbLon) {
                    rbLon.setBackgroundResource(R.drawable.custom_button_selected);
                    rbLon.setTextColor(Color.WHITE);
                    rbNho.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbNho.setTextColor(getResources().getColor(R.color.brown));
                    rbVua.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbVua.setTextColor(getResources().getColor(R.color.brown));
                }
            }
        });
    }

    private void changeClickStatusDoUong() {
        RadioGroup rgDoUong = findViewById(R.id.rgDoUong);
        RadioButton rbDa = findViewById(R.id.rbDa);
        RadioButton rbNong = findViewById(R.id.rbNong);

        rgDoUong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbDa) {
                    rbDa.setBackgroundResource(R.drawable.custom_button_selected);
                    rbDa.setTextColor(Color.WHITE);
                    rbNong.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbNong.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbNong) {
                    rbNong.setBackgroundResource(R.drawable.custom_button_selected);
                    rbNong.setTextColor(Color.WHITE);
                    rbDa.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbDa.setTextColor(getResources().getColor(R.color.brown));
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
            // Xử lý sự kiện nhấn nút Back
            Intent intent = new Intent(OrderProductActivity.this, HomeUserActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
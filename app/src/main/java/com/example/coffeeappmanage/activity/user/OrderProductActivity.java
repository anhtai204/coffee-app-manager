package com.example.coffeeappmanage.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.example.coffeeappmanage.activity.RecyclerProduct.RCToppingAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseCountRate;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvCount, tvTenSanPham, tvRate, tvCountRate, tvGiaSanPham, tvGiaCuoi, tvThemGioHang, tvDanhGia;
    ImageView imgMinus, imgPlus, imgDanhGia;
    EditText edtGhiChu;
    int count = 1;
    int giaCuoi;
    String tuyChinhDoUong = "Đá";
    String tuyChinhKichThuoc = "Nhỏ";
    String tuyChinhDuong = "Bình thường";
    RCToppingAdapter rcToppingAdapter;
    List<Topping> selectedToppings;

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

        edtGhiChu = findViewById(R.id.edtGhiChu);
        tvCount = findViewById(R.id.tvCount);
        tvTenSanPham = findViewById(R.id.tvTenSanPham);
        tvRate = findViewById(R.id.tvRate);
        tvCountRate = findViewById(R.id.tvCountRate);
        tvGiaSanPham = findViewById(R.id.tvGiaSanPham);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        tvGiaCuoi = findViewById(R.id.tvGiaCuoi);

        giaCuoi = extractNumber(tvGiaSanPham.getText().toString());
        Log.d("giasanpham: ", tvGiaSanPham.getText().toString());


        if(getIntent().getExtras() != null){
            Product product = (Product) getIntent().getExtras().get("product");
            User user = (User) getIntent().getExtras().get("user");

            Log.d("user_order", user.toString());

            float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
            DecimalFormat decimalFormat = new DecimalFormat("#,###");


            ApiService.apiService.getCountRateFromId(product.getId_product()).enqueue(new Callback<ResponseCountRate>() {
                @Override
                public void onResponse(Call<ResponseCountRate> call, Response<ResponseCountRate> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseCountRate responseCountRate = response.body();
                        int countRate = responseCountRate.getData();
                        int statusCode = responseCountRate.getStatusCode();
                        String message = responseCountRate.getMessage();

                        Log.e("count rate", countRate+"");

                        tvCountRate.setText("(" + countRate + ")");
                    }
                }

                @Override
                public void onFailure(Call<ResponseCountRate> call, Throwable throwable) {

                }
            });


            tvTenSanPham.setText(product.getTenSanPham());
            tvGiaSanPham.setText(decimalFormat.format(gia_sp)+"vnd");
            tvGiaCuoi.setText(decimalFormat.format(gia_sp)+"vnd");
            tvRate.setText(product.getAverage_star()+"");

            DecimalFormat formatStar = new DecimalFormat("0.0");
            String formatted_star = formatStar.format(product.getAverage_star());
            tvRate.setText(formatted_star);

            // mặc định giá cuối lúc mới vào trang là giá sản phẩm
            giaCuoi = extractNumber(tvGiaSanPham.getText().toString());


            selectedToppings = new ArrayList<>(); // Khởi tạo danh sách topping đã chọ

            createRecyclerTopping();
            changeClickStatusDoUong();
            changeClickStatusKichThuoc();
            changeClickStatusDuong();

            changeCountDoUong();


            Log.d("count", count+"");
            int gia = extractNumber(tvGiaSanPham.getText().toString());
            Log.d("gia", gia+"");

            tvThemGioHang = findViewById(R.id.tvThemGioHang);
            tvThemGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(OrderProductActivity.this, "So luong: " + count + ", gia: " + giaCuoi , Toast.LENGTH_SHORT).show();

//                    selectedToppings = rcToppingAdapter.getSelectedToppings(); // Lấy topping đã chọn
//                    for (Topping topping : selectedToppings) {
//                        Log.d("Selected Topping", topping.getTopping_name());
//                    }
//
//                    Log.d("tùy chỉnh đồ uống: ", tuyChinhDoUong);
//                    Log.d("tùy chỉnh kích thước: ", tuyChinhKichThuoc);
//                    Log.d("tùy chỉnh đường: ", tuyChinhDuong);
//                    Log.d("Ghi chú: ", edtGhiChu.getText().toString());


                }
            });

            tvDanhGia = findViewById(R.id.tvDanhGia);
            imgDanhGia = findViewById(R.id.imgDanhGia);
            tvDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentDanhGia = new Intent(OrderProductActivity.this, DanhGiaActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("product", product);
                    data.putSerializable("user", user);
                    intentDanhGia.putExtras(data);
                    startActivity(intentDanhGia);
                }
            });
            imgDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentDanhGia = new Intent(OrderProductActivity.this, DanhGiaActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("product", product);
                    data.putSerializable("user", user);
                    intentDanhGia.putExtras(data);
                    startActivity(intentDanhGia);
                }
            });

        }
    }
    public static int extractNumber(String input) {
        String numberString = input.replaceAll("[^0-9]", "");

        return Integer.parseInt(numberString);
    }


    private void changeCountDoUong() {
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) { // Đảm bảo không giảm xuống dưới 1
                    count--;
                    tvCount.setText(String.valueOf(count));
                    updateTotalPrice();
                    Log.d("count", count+"");
                    Log.d("giaCuoi", giaCuoi+"");

                }
            }
        });

        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tvCount.setText(String.valueOf(count));
                updateTotalPrice();
                Log.d("count", count+"");
                Log.d("giaCuoi", giaCuoi+"");

            }
        });
    }

    private void updateTotalPrice() {
        int gia = extractNumber(tvGiaSanPham.getText().toString());
        giaCuoi = gia * count;

        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        tvGiaCuoi.setText(decimalFormat.format(giaCuoi)+"vnd");
    }

    private void createRecyclerTopping() {
        RecyclerView rcv_topping = findViewById(R.id.rcv_topping);
        rcv_topping.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcv_topping.setHasFixedSize(true);

        List<Topping> listTopping = new ArrayList<>();
//        rcToppingAdapter = new RCToppingAdapter(this, listTopping);
        rcToppingAdapter = new RCToppingAdapter(this, listTopping, new RCToppingAdapter.OnToppingSelectedListener() {
            @Override
            public void onToppingSelected(List<Topping> selectedToppings) {
                updateTotalPriceWithToppings(selectedToppings);
            }
        });
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

    private void updateTotalPriceWithToppings(List<Topping> selectedToppings) {
        int gia = extractNumber(tvGiaSanPham.getText().toString());
        int giaToppings = 0;

        for (Topping topping : selectedToppings) {
            giaToppings += topping.getGiaTopping(); // Cộng giá của từng topping
        }

        giaCuoi = (gia * count) + giaToppings;

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvGiaCuoi.setText(decimalFormat.format(giaCuoi) + "vnd"); // Update UI with new price
    }


    private void changeClickStatusDuong() {
        RadioGroup rgDuong = findViewById(R.id.rgDuong);
        RadioButton rbBinhThuong = findViewById(R.id.rbBinhThuong);
        RadioButton rbGiam = findViewById(R.id.rbGiam);

        rgDuong.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbBinhThuong) {
                    tuyChinhDuong = "Bình thường";
                    rbBinhThuong.setBackgroundResource(R.drawable.custom_button_selected);
                    rbBinhThuong.setTextColor(Color.WHITE);
                    rbGiam.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbGiam.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbGiam) {
                    tuyChinhDuong = "Giảm";
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
                    tuyChinhKichThuoc = "Nhỏ";
                    rbNho.setBackgroundResource(R.drawable.custom_button_selected);
                    rbNho.setTextColor(Color.WHITE);
                    rbVua.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbVua.setTextColor(getResources().getColor(R.color.brown));
                    rbLon.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbLon.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbVua) {
                    tuyChinhKichThuoc = "Vừa";
                    rbVua.setBackgroundResource(R.drawable.custom_button_selected);
                    rbVua.setTextColor(Color.WHITE);
                    rbNho.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbNho.setTextColor(getResources().getColor(R.color.brown));
                    rbLon.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbLon.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbLon) {
                    tuyChinhKichThuoc = "Lớn";
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
                    tuyChinhDoUong = "Đá";
                    rbDa.setBackgroundResource(R.drawable.custom_button_selected);
                    rbDa.setTextColor(Color.WHITE);
                    rbNong.setBackgroundResource(R.drawable.custom_button_unselected);
                    rbNong.setTextColor(getResources().getColor(R.color.brown));
                } else if (checkedId == R.id.rbNong) {
                    tuyChinhDoUong = "Nóng";
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
//            Intent intent = new Intent(OrderProductActivity.this, HomeUserActivity.class);
//            startActivity(intent);
//            return true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
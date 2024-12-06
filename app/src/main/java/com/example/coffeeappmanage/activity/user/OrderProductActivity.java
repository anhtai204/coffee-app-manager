package com.example.coffeeappmanage.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCToppingAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseCountRate;
import com.example.coffeeappmanage.model.ResponseSingleDonHang;
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
    ImageView imgMinus, imgPlus, imgDanhGia, imgOrderProduct;
    EditText edtGhiChu;
    int count = 1;
    int giaCuoi;
    static int latestId;
    String tuyChinhDoUong = "Đá";
    String tuyChinhKichThuoc = "Nhỏ";
    String tuyChinhDuong = "Bình thường";
    RCToppingAdapter rcToppingAdapter;
    List<Topping> selectedToppings;
    boolean check = true;
    User user;
    DonHang donHang;
    Product product;

    float giaTopping = 0;

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
        imgOrderProduct = findViewById(R.id.imgOrderProduct);



        giaCuoi = extractNumber(tvGiaSanPham.getText().toString());
        Log.d("giasanpham: ", tvGiaSanPham.getText().toString());


        if(getIntent().getExtras() != null){
            product = (Product) getIntent().getExtras().get("product");
            user = (User) getIntent().getExtras().get("user");

            Log.d("user_order", user.toString());
            Log.d("OrderProductActivity product: ", product.toString());

            Glide.with(this)
                    .load(getString(R.string.local_host) + product.getLogo_product())
                    .fitCenter()  // Hoặc sử dụng .centerInside() nếu bạn muốn ảnh nhỏ hơn vừa khít với ImageView
                    .into(imgOrderProduct);


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


            ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                Toast.makeText(OrderProductActivity.this, "Đánh giá đã được gửi!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

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
//                    startActivity(intentDanhGia);
                    activityResultLauncher.launch(intentDanhGia);
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
//                    startActivity(intentDanhGia);
                    activityResultLauncher.launch(intentDanhGia);
                }
            });


            tvThemGioHang = findViewById(R.id.tvThemGioHang);
            tvThemGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedToppings = rcToppingAdapter.getSelectedToppings(); // Lấy topping đã chọn

                    Log.d("id_user: ", user.getId_user()+"");
                    Log.d("id_product: ", product.getId_product()+"");
                    Log.d("status: ", edtGhiChu.getText().toString());
                    Log.d("giaDonHang: ", giaCuoi+"");
                    Log.d("soLuong:", count+"");

                    String tuyChinh = tuyChinhDoUong + " - " + tuyChinhKichThuoc + " - " + tuyChinhDuong;


                    ApiService.apiService.themVaoGioHang(user.getId_user(), product.getId_product(), count, tuyChinh,
                            "dathang", giaCuoi, edtGhiChu.getText().toString()).enqueue(new Callback<ResponseSingleDonHang>() {
                        @Override
                        public void onResponse(Call<ResponseSingleDonHang> call, Response<ResponseSingleDonHang> response) {
                            ResponseSingleDonHang responseDonHang = response.body();
                            donHang = responseDonHang.getData();
                            Log.d("don hang: ", donHang.toString());

                            if (selectedToppings.size() != 0) {
                                for (Topping topping : selectedToppings) {
                                    int toppingId = topping.getId_topping();
                                    ApiService.apiService.insertProductTopping(donHang.getId_donHang(), toppingId).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            // Check successful transaction for each topping
                                            if (callToppingsSuccess()) {
                                                // Only call goToCartActivity() after all insert operations are successful
                                                goToCartActivity();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable throwable) {
                                            check = false;
                                        }
                                    });
                                }
                            } else {
                                goToCartActivity(); // No toppings to insert, directly navigate.
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseSingleDonHang> call, Throwable throwable) {
                            check = false;
                            // Handle failure
                        }
                    });
                }
            });
        }
    }

    private void goToCartActivity() {
        if (check) {
            new Handler().post(() -> {
                Intent intentThemGioHang = new Intent(OrderProductActivity.this, DatHangActivity.class);
                Bundle data_giohang = new Bundle();
                data_giohang.putSerializable("donhang", donHang);
                data_giohang.putSerializable("user", user);
                data_giohang.putFloat("giaTopping", giaTopping);
                intentThemGioHang.putExtras(data_giohang);
                startActivity(intentThemGioHang);
            });

        }
    }


    private boolean callToppingsSuccess() {
        // Kiểm tra nếu tất cả các cuộc gọi liên quan đến topping thành công
        return check;
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

//    private void updateTotalPrice() {
//        int gia = extractNumber(tvGiaSanPham.getText().toString());
//        giaCuoi = gia * count;
//
//        DecimalFormat decimalFormat = new DecimalFormat("#,###");
//
//        tvGiaCuoi.setText(decimalFormat.format(giaCuoi)+"vnd");
//    }

    private void updateTotalPrice() {
        int gia = extractNumber(tvGiaSanPham.getText().toString());
        int giaToppings = 0;

        // Tính giá của tất cả topping đã chọn
        for (Topping topping : selectedToppings) {
            giaToppings += topping.getGiaTopping();
        }

        // Cập nhật giá cuối cùng với giá sản phẩm và giá topping
        giaCuoi = (gia * count) + (giaToppings*count);

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvGiaCuoi.setText(decimalFormat.format(giaCuoi) + "vnd"); // Cập nhật UI
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

        giaTopping = giaToppings;

        giaCuoi = (gia * count) + (giaToppings*count);

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
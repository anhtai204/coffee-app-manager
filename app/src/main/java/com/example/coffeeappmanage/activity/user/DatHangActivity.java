package com.example.coffeeappmanage.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.bumptech.glide.Glide;
import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.admin.ThemTheLoaiActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DiaChi;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.PhuongThucThanhToan;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.ResponseSingleProduct;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatHangActivity extends AppCompatActivity {

    Toolbar toolbar;
    int count;
    User user;
    DonHang donHang;
    Product_1 product;
    float giaCuoi;
    ImageView imgProductDatHang, imgDatHangChinhSua, imgDatHangXoa, imgDatHangMinus, imgDatHangPlus;
    TextView tvDatHangTenSP, tvDatHangTuyChinh, tvDatHangGiaSP, tvDatHangSoLuongSP,
            tvDatHangCount, tvDathangGiaGoc, tvDatHangGiaGiam, tvDatHangGiaCuoi, tvDatDonHang,
            tvPhuongThucThanhToan, tvPhuongThucThanhToanStatus, tvDiaChiGiaoHang, tvDiaChiGiaoHangStatus,
            tvKhuyenMai, tvKhuyenMaiStatus;

    LinearLayout viewPhuongThucThanhToan, viewDiaChiGiaoHang, viewKhuyenMai;
    float giaTopping;
    PhuongThucThanhToan phuongThucThanhToan = null;
    KhuyenMai khuyenMai = null;
    DiaChi diaChi = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dat_hang);
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
            giaTopping = getIntent().getExtras().getFloat("giaTopping");

            Log.d("DatHangActivity donhang: ", donHang.toString());
            Log.d("DatHangActivity user: ", user.toString());
            Log.d("DatHangActivity giaTopping: ", giaTopping+"");


        }

        viewPhuongThucThanhToan = findViewById(R.id.viewPhuongThucThanhToan);
        tvPhuongThucThanhToan = findViewById(R.id.tvPhuongThucThanhToan);
        tvPhuongThucThanhToanStatus = findViewById(R.id.tvPhuongThucThanhToanStatus);

        viewDiaChiGiaoHang = findViewById(R.id.viewDiaChiGiaoHang);
        tvDiaChiGiaoHang = findViewById(R.id.tvDiaChiGiaoHang);
        tvDiaChiGiaoHangStatus = findViewById(R.id.tvDiaChiGiaoHangStatus);

        viewKhuyenMai = findViewById(R.id.viewKhuyenMai);
        tvKhuyenMai = findViewById(R.id.tvKhuyenMai);
        tvKhuyenMaiStatus = findViewById(R.id.tvKhuyenMaiStatus);

        imgProductDatHang = findViewById(R.id.imgProductDatHang);
        imgDatHangChinhSua = findViewById(R.id.imgDatHangChinhSua);
        imgDatHangXoa = findViewById(R.id.imgDatHangXoa);
        tvDatHangTenSP = findViewById(R.id.tvDatHangTenSP);
        tvDatHangTuyChinh = findViewById(R.id.tvDatHangTuyChinh);
        tvDatHangGiaSP = findViewById(R.id.tvDatHangGiaSP);
        imgDatHangMinus = findViewById(R.id.imgDatHangMinus);
        imgDatHangPlus = findViewById(R.id.imgDatHangPlus);
        tvDatHangSoLuongSP = findViewById(R.id.tvDatHangSoLuongSP);
        tvDatHangCount = findViewById(R.id.tvDatHangCount);
        tvDathangGiaGoc = findViewById(R.id.tvDathangGiaGoc);
        tvDatHangGiaGiam = findViewById(R.id.tvDatHangGiaGiam);
        tvDatHangGiaCuoi = findViewById(R.id.tvDatHangGiaCuoi);
        tvDatDonHang = findViewById(R.id.tvDatDonHang);


        count = donHang.getSoLuong();

        tvDatHangTuyChinh.setText(donHang.getTuyChinh());
        tvDatHangSoLuongSP.setText(donHang.getSoLuong()+"");
        tvDatHangCount.setText(donHang.getSoLuong()+"");

        Log.d("count: ", count+"");
        Log.d("giaCuoi: ", giaCuoi+"");


        float gia_sp = donHang.getGiaDonHang();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        tvDatHangGiaCuoi.setText(decimalFormat.format(gia_sp)+"vnd");
        tvDathangGiaGoc.setText(decimalFormat.format(gia_sp)+"vnd");

        Log.d("id_product: ", donHang.getId_product()+"");
        int id_product  = donHang.getId_product();

        ApiService.apiService.getProductById(id_product).enqueue(new Callback<ResponseSingleProduct>() {
            @Override
            public void onResponse(Call<ResponseSingleProduct> call, Response<ResponseSingleProduct> response) {
                if(response.isSuccessful()){
                    ResponseSingleProduct singleProduct = response.body();
                    product = singleProduct.getData();
                    int statusCode = singleProduct.getStatusCode();
                    String message = singleProduct.getMessage();

                    Log.d("product: ", product.toString());

                    float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    String formatted_giasp = decimalFormat.format(gia_sp);

                    tvDatHangGiaSP.setText(formatted_giasp+"vnd");
                    tvDatHangTenSP.setText(product.getTenSanPham());

                    Glide.with(DatHangActivity.this)
                            .load(getString(R.string.local_host) + product.getLogo_product())
                            .fitCenter()
                            .into(imgProductDatHang);

                }
            }

            @Override
            public void onFailure(Call<ResponseSingleProduct> call, Throwable throwable) {

            }
        });

        changeCountDoUong();

        ActivityResultLauncher<Intent> activityResultLauncher_thanhtoan = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        phuongThucThanhToan = (PhuongThucThanhToan) intent.getSerializableExtra("phuongThucThanhToan");

                        if (phuongThucThanhToan != null) {
                            tvPhuongThucThanhToan.setText(phuongThucThanhToan.getHinhThucThanhToan());
                            tvPhuongThucThanhToanStatus.setText("");
                        }
                    }
                }
            }
        );

        viewPhuongThucThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPhuongThucThanhToan = new Intent(DatHangActivity.this, PhuongThucThanhToanActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                data.putSerializable("donhang", donHang);
                intentPhuongThucThanhToan.putExtras(data);
                activityResultLauncher_thanhtoan.launch(intentPhuongThucThanhToan);
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher_diachi = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        diaChi = (DiaChi) intent.getSerializableExtra("diaChi");

                        if (diaChi != null) {
                            tvDiaChiGiaoHang.setText(diaChi.getDia_chi());
                            tvDiaChiGiaoHangStatus.setText("");
                        }
                    }
                }
            }
        );
        viewDiaChiGiaoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDiaChi = new Intent(DatHangActivity.this, DiaChiGiaoHangActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                data.putSerializable("donhang", donHang);
                intentDiaChi.putExtras(data);
                activityResultLauncher_diachi.launch(intentDiaChi);
            }
        });



        ActivityResultLauncher<Intent> activityResultLauncher_khuyenmai = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        khuyenMai = (KhuyenMai) intent.getSerializableExtra("khuyenMai");

                        if (khuyenMai != null) {
                            DecimalFormat df = new DecimalFormat("0.##");
                            float phanTramKM = khuyenMai.getPhanTramKhuyenMai();
                            String formatted_phantram = df.format(phanTramKM);
                            tvKhuyenMai.setText("Giảm giá " + formatted_phantram + "%");

                            float dieuKien = khuyenMai.getDonHangToiThieu();
                            DecimalFormat decimalFormat = new DecimalFormat("#,###");

                            String formatted_dk = decimalFormat.format(dieuKien);
                            tvKhuyenMaiStatus.setText("Áp dụng cho đơn hàng tối thiểu " + formatted_dk + "vnd");

                            tvDatHangGiaGiam.setText("-"+decimalFormat.format(donHang.getGiaDonHang()*phanTramKM/100)+"vnd");

                            float lastestPrice = donHang.getGiaDonHang() - donHang.getGiaDonHang()*phanTramKM/100;
                            tvDatHangGiaCuoi.setText(decimalFormat.format(lastestPrice)+"vnd");
                            donHang.setGiaDonHang(lastestPrice);
                        }
                    }
                }
            }
        );
        viewKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentKhuyenMai = new Intent(DatHangActivity.this, KhuyenMaiActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                data.putSerializable("donhang", donHang);
                intentKhuyenMai.putExtras(data);
                activityResultLauncher_khuyenmai.launch(intentKhuyenMai);
            }
        });


        tvDatDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phuongThucThanhToan==null){
                    Toast.makeText(DatHangActivity.this, "Chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                } else {
                    if(diaChi==null){
                        Toast.makeText(DatHangActivity.this, "Nhập địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                    } else {
                        donHang.setDiaChi(diaChi.getDia_chi());
                        donHang.setStatus("dadat");
                        donHang.setId_phuong_thuc_thanh_toan(phuongThucThanhToan.getId_phuongThucThanhToan());
                        if(khuyenMai!=null){
                            donHang.setId_khuyenMai(khuyenMai.getId_khuyen_mai());
                        }
                        Log.d("dat don hang: ", donHang.toString());

                        ApiService.apiService.updateDonHang(donHang.getId_donHang(), donHang).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Intent intentHome = new Intent(DatHangActivity.this, HomeUserActivity.class);
//                                Intent intentHome = new Intent(DatHangActivity.this, OrderUserFragment.class);
                                Bundle data = new Bundle();
                                data.putSerializable("user", user);
                                intentHome.putExtras(data);
                                startActivity(intentHome);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {

                            }
                        });
                    }

                }



            }
        });


        imgDatHangChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public static int extractNumber(String input) {
        String numberString = input.replaceAll("[^0-9]", "");

        return Integer.parseInt(numberString);
    }

    private void changeCountDoUong() {

        imgDatHangMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) { // Đảm bảo không giảm xuống dưới 1
                    count--;
                    tvDatHangCount.setText(String.valueOf(count));
                    tvDatHangSoLuongSP.setText(String.valueOf(count));
                    updateTotalPrice();
                    Log.d("count", count+"");
                    Log.d("giaCuoi", giaCuoi+"");
                }
            }
        });

        imgDatHangPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tvDatHangCount.setText(String.valueOf(count));
                tvDatHangSoLuongSP.setText(String.valueOf(count));
                updateTotalPrice();
                Log.d("count", count+"");
                Log.d("giaCuoi", giaCuoi+"");
            }
        });

    }

    private void updateTotalPrice() {

        float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
        giaCuoi = gia_sp*count + giaTopping*count;
        donHang.setGiaDonHang(giaCuoi);
        donHang.setSoLuong(count);

        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        tvDatHangGiaCuoi.setText(decimalFormat.format(giaCuoi)+"vnd");
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
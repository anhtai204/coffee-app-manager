package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
import com.example.coffeeappmanage.activity.RecyclerProduct.RCProductAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCProductGioHangAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseDonHang;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {

    Toolbar toolbar;
    User user;
    private RecyclerView rcGioHang;
    List<DonHang> listDonHang;
    RCProductGioHangAdapter rcProductGioHangAdapter;


    TextView tvThemVaoGioHang, tvPhuongThucThanhToan, tvPhuongThucThanhToanStatus, tvDiaChiGiaoHang, tvDiaChiGiaoHangStatus,
            tvKhuyenMai, tvKhuyenMaiStatus, tvSoLuongDoUong, tvGiaGoc, tvGiaGiam, tvGiaCuoi, tvDatDonHang;
    ImageView imgThemVaoGioHang, imgPhuongThucThanhToan, imgKhuyenMai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listDonHang = new ArrayList<>();

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            DonHang donHang = (DonHang) getIntent().getExtras().get("donhang");
            user = (User) getIntent().getExtras().get("user");

            listDonHang.add(donHang);

            Log.d("GioHangActivity donhang: ", donHang.toString());
            Log.d("GioHangActivity user: ", user.toString());

        }

//        listDonHang.add(new DonHang(2, 1, 3, "abc", "dathang", 15000, "ngon", 4));
//        listDonHang.add(new DonHang(2, 2, 1, "cba", "dathang", 60000, "ngon", 5));
//        listDonHang.add(new DonHang(2, 3, 2, "cab", "dathang", 19000, "ngon", 6));


        tvThemVaoGioHang = findViewById(R.id.tvThemVaoGioHang);
        tvPhuongThucThanhToan = findViewById(R.id.tvPhuongThucThanhToan);
        tvPhuongThucThanhToanStatus = findViewById(R.id.tvPhuongThucThanhToanStatus);
        tvDiaChiGiaoHang = findViewById(R.id.tvDiaChiGiaoHang);
        tvDiaChiGiaoHangStatus = findViewById(R.id.tvDiaChiGiaoHangStatus);
        tvKhuyenMai = findViewById(R.id.tvKhuyenMai);
        tvKhuyenMaiStatus = findViewById(R.id.tvKhuyenMaiStatus);
        tvSoLuongDoUong = findViewById(R.id.tvSoLuongDoUong);
        tvGiaGoc = findViewById(R.id.tvGiaGoc);
        tvGiaGiam = findViewById(R.id.tvGiaGiam);
        tvGiaCuoi = findViewById(R.id.tvGiaCuoi);
        tvDatDonHang = findViewById(R.id.tvDatDonHang);
        imgThemVaoGioHang = findViewById(R.id.imgThemVaoGioHang);
        imgPhuongThucThanhToan = findViewById(R.id.imgPhuongThucThanhToan);
        imgKhuyenMai = findViewById(R.id.imgKhuyenMai);

        rcGioHang = findViewById(R.id.rcGioHang);
        rcGioHang.setLayoutManager(new LinearLayoutManager(this));
        rcGioHang.setHasFixedSize(true);

        rcProductGioHangAdapter = new RCProductGioHangAdapter(this, listDonHang, user);
        rcGioHang.setAdapter(rcProductGioHangAdapter); // Thiết lập adapter ngay lập tức
        rcProductGioHangAdapter.setData(listDonHang);

        Log.d("listDonHang: ", listDonHang.toString());



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
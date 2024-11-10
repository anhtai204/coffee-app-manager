package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.ResponseSingleKhuyenMai;
import com.example.coffeeappmanage.model.ResponseSingleTopping;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemKhuyenMaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtPhanTramKhuyenMai, edtDieuKienKhuyenMai;
    TextView btnThemKhuyenMai;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_khuyen_mai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().get("user");

            Log.d("ThemToppingActivity user: ", user.toString());
        }

        toolbar = findViewById(R.id.toolbarThemKhuyenMai);
        setSupportActionBar(toolbar);

        edtPhanTramKhuyenMai = findViewById(R.id.edtPhanTramKhuyenMai);
        edtDieuKienKhuyenMai = findViewById(R.id.edtDieuKienKhuyenMai);
        btnThemKhuyenMai = findViewById(R.id.btnThemKhuyenMai);

        btnThemKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhanTramKhuyenMai.getText().toString().trim().equals("") ||
                        edtDieuKienKhuyenMai.getText().toString().trim().equals("")){
                    Toast.makeText(ThemKhuyenMaiActivity.this, "Nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                } else {
                    float phanTramKM = Float.parseFloat(edtPhanTramKhuyenMai.getText().toString().trim());
                    float dieuKienKM = Float.parseFloat(edtDieuKienKhuyenMai.getText().toString().trim());
                    KhuyenMai khuyenMai = new KhuyenMai(0, phanTramKM, dieuKienKM);
                    ApiService.apiService.createKhuyenMai(khuyenMai).enqueue(new Callback<ResponseSingleKhuyenMai>() {
                        @Override
                        public void onResponse(Call<ResponseSingleKhuyenMai> call, Response<ResponseSingleKhuyenMai> response) {
                            if (response.isSuccessful()) {
                                ResponseSingleKhuyenMai responseSingleKhuyenMai = response.body();
                                KhuyenMai newKhuyenMai = (KhuyenMai) responseSingleKhuyenMai.getData();
                                int statusCode = responseSingleKhuyenMai.getStatusCode();
                                String message = responseSingleKhuyenMai.getMessage();
                                Log.d("new khuyen mai:", newKhuyenMai.toString());
                                Log.d("status code:", statusCode+"");
                                Log.d("message :", message);


                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("newKhuyenMai", newKhuyenMai);
                                setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                                finish(); // Đóng activity
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseSingleKhuyenMai> call, Throwable throwable) {
                            Log.d("errror response : ", throwable.toString());
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
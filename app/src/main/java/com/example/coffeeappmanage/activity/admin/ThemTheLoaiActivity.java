package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.ResponseSingleTheLoai;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTheLoaiActivity extends AppCompatActivity {

    Toolbar toolbarThemTheLoai;
    EditText edtTenTheLoaiThem;
    TextView btnThemTheLoai;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_the_loai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().get("user");

            Log.d("ChangePasswordActivity user: ", user.toString());

        }

        toolbarThemTheLoai = findViewById(R.id.toolbarThemTheLoai);
        setSupportActionBar(toolbarThemTheLoai);

        edtTenTheLoaiThem = findViewById(R.id.edtTenTheLoaiThem);
        btnThemTheLoai = findViewById(R.id.btnThemTheLoai);

        btnThemTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTenTheLoaiThem.getText().toString().trim().equals("")){
                    Toast.makeText(ThemTheLoaiActivity.this, "Nhập tên thể loại cần thêm!", Toast.LENGTH_SHORT).show();
                } else {
                    String tenTheLoai = edtTenTheLoaiThem.getText().toString().trim();
                    TheLoai theLoai = new TheLoai(0, tenTheLoai);
                    ApiService.apiService.createTheLoai(theLoai).enqueue(new Callback<ResponseSingleTheLoai>() {
                        @Override
                        public void onResponse(Call<ResponseSingleTheLoai> call, Response<ResponseSingleTheLoai> response) {
                            if (response.isSuccessful()) {
                                ResponseSingleTheLoai responseTheLoai = response.body();
                                TheLoai newTheLoai = (TheLoai) responseTheLoai.getData();
                                int statusCode = responseTheLoai.getStatusCode();
                                String message = responseTheLoai.getMessage();
                                Log.d("new the loai:", newTheLoai.toString());
                                Log.d("status code:", statusCode+"");
                                Log.d("message :", message);


                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("newtheloai", newTheLoai);
                                setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                                finish(); // Đóng activity
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseSingleTheLoai> call, Throwable throwable) {
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
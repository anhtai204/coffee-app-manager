package com.example.coffeeappmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.admin.HomeAdminActivity;
import com.example.coffeeappmanage.activity.user.HomeUserActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.ResponseUser;
import com.example.coffeeappmanage.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnDangKy;
    EditText edtEmail, edtMatKhau, edtMatKhauNhapLai;
    RadioButton radAdmin, radUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnDangKy = findViewById(R.id.btnDangKy);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtMatKhauNhapLai = findViewById(R.id.edtMatKhauNhapLai);
        radAdmin = findViewById(R.id.radAdmin);
        radUser = findViewById(R.id.radUser);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.back_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.menuBack);
//        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
//                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

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
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveUser() {
        String email = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        String matKhauNhapLai = edtMatKhauNhapLai.getText().toString().trim();


        if(!matKhau.equals(matKhauNhapLai)){
            Toast.makeText(this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
        } else if (email.length() == 0 || matKhau.length() == 0) {
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!radAdmin.isChecked() && !radUser.isChecked()) {
            Toast.makeText(SignUpActivity.this, "Chọn role", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra email tồn tại
            ApiService.apiService.checkEmailExists(email).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && response.body() != null && response.body()) {
                        // Email đã tồn tại
                        Toast.makeText(SignUpActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu email không tồn tại, tiếp tục tạo tài khoản mới
                        int check = 1;
                        if (radAdmin.isChecked()) {
                            check = 2;
                        }
                        User newUser = new User(0, email, matKhau, check);
                        ApiService.apiService.createUser(newUser).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                    if(radUser.isChecked()){
                                        Intent intent1 = new Intent(SignUpActivity.this, HomeUserActivity.class);
                                        startActivity(intent1);
                                    } else if(radAdmin.isChecked()){
                                        Intent intent2 = new Intent(SignUpActivity.this, HomeAdminActivity.class);
                                        startActivity(intent2);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable throwable) {
                                Toast.makeText(SignUpActivity.this, "Tạo tài khoản thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable throwable) {
                    Toast.makeText(SignUpActivity.this, "API call failed: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("err", throwable.toString());
                }
            });
        }
    }


}
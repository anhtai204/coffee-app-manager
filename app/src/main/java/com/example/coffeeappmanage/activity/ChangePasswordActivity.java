package com.example.coffeeappmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtMatKhauMoi, edtMatKhauMoiNhapLai;
    TextView btnDoiMatKhau;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtMatKhauMoi = findViewById(R.id.edtMatKhauMoi);
        edtMatKhauMoiNhapLai = findViewById(R.id.edtMatKhauMoiNhapLai);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matKhauMoi = edtMatKhauMoi.getText().toString().trim();
                String matKhauMoiNhapLai = edtMatKhauMoiNhapLai.getText().toString().trim();
                if(matKhauMoi.length() == 0){
                    Toast.makeText(ChangePasswordActivity.this, "Nhập mật khẩu mới của bạn!", Toast.LENGTH_SHORT).show();
                } else {
                    if(!matKhauMoi.equals(matKhauMoiNhapLai)){
                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu nhập lại không chính xác!", Toast.LENGTH_SHORT).show();
                        edtMatKhauMoiNhapLai.setText("");
                    } else {
//                        Toast.makeText(ChangePasswordActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        ApiService.apiService.changePassword(user.getId_user(), matKhauMoi).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công, Hãy đăng nhập lại!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
//                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {
                                Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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
//            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
//            startActivity(intent);
//            return true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
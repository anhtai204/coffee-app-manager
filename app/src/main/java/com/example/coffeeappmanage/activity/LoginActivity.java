package com.example.coffeeappmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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


public class LoginActivity extends AppCompatActivity {

    Button btnDangNhap;
    EditText edtEmail, edtMatKhau;
    RadioButton radAdmin, radUser;
    TextView tvDangKy, tvQuenMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        radAdmin = findViewById(R.id.radAdmin);
        radUser = findViewById(R.id.radUser);
        tvDangKy = findViewById(R.id.tvDangKy);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();

        if(email.length() == 0 || matKhau.length() == 0) {
            Toast.makeText(this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
        } else if(!isValidEmail(email)) {
            Toast.makeText(this, "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
        } else {
            int role=1;
            if(radAdmin.isChecked()){
                role = 2;
            }
            Log.e("role", role+"");
            int finalRole = role;
            ApiService.apiService.getAllUser().enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, retrofit2.Response<ResponseUser> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseUser responseUser = response.body();
                        List<User> listUser = responseUser.getData();
                        int statusCode = responseUser.getStatusCode();
                        String message = responseUser.getMessage();

                        boolean check = false;
                        for(User user : listUser){
                            if (user.getEmail().equals(email) && user.getPassword().equals(matKhau) && user.getId_role() == finalRole){
                                Log.e("role", finalRole +"");
                                check = true;
                                if(finalRole == 1 ){
                                    Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                                    Bundle data = new Bundle();
                                    data.putSerializable("user", user);
//                                    Log.d("user", user.toString());
                                    intent.putExtras(data);
                                    startActivity(intent);
                                } else if(finalRole == 2){
                                    Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                        if(check == false){
                            Toast.makeText(LoginActivity.this, "Email, mật khẩu hoặc role không đúng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, "Call api failed" + throwable.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("e", throwable.toString());
                }
            });
        }
    }

}

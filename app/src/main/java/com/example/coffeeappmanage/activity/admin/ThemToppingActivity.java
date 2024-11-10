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
import com.example.coffeeappmanage.model.ResponseSingleTheLoai;
import com.example.coffeeappmanage.model.ResponseSingleTopping;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemToppingActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtTenToppingThem, edtGiaToppingThem;
    TextView btnThemTopping;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_topping);
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

        toolbar = findViewById(R.id.toolbarThemTopping);
        setSupportActionBar(toolbar);

        edtTenToppingThem = findViewById(R.id.edtTenToppingThem);
        edtGiaToppingThem = findViewById(R.id.edtGiaToppingThem);
        btnThemTopping = findViewById(R.id.btnThemTopping);

        btnThemTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTenToppingThem.getText().toString().trim().equals("") ||
                    edtGiaToppingThem.getText().toString().trim().equals("")){
                    Toast.makeText(ThemToppingActivity.this, "Nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
                } else {
                    String tenTopping = edtTenToppingThem.getText().toString().trim();
                    float giaTopping = Float.parseFloat(edtGiaToppingThem.getText().toString().trim());
                    Topping topping = new Topping(0, tenTopping, giaTopping);
                    ApiService.apiService.createTopping(topping).enqueue(new Callback<ResponseSingleTopping>() {
                        @Override
                        public void onResponse(Call<ResponseSingleTopping> call, Response<ResponseSingleTopping> response) {
                            if (response.isSuccessful()) {
                                ResponseSingleTopping responseSingleTopping = response.body();
                                Topping newTopping = (Topping) responseSingleTopping.getData();
                                int statusCode = responseSingleTopping.getStatusCode();
                                String message = responseSingleTopping.getMessage();
                                Log.d("new topping:", newTopping.toString());
                                Log.d("status code:", statusCode+"");
                                Log.d("message :", message);


                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("newtopping", newTopping);
                                setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                                finish(); // Đóng activity
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseSingleTopping> call, Throwable throwable) {
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
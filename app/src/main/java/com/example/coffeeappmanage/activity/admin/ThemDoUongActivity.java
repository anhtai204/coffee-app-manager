package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseProduct_1;
import com.example.coffeeappmanage.model.ResponseSingleTheLoai;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemDoUongActivity extends AppCompatActivity {

    Toolbar toolbarThemDoUong;
    private User user;
    Spinner spnTheLoai;
    TheLoaiAdapter theLoaiAdapter;
    EditText edtTenDoUong, edtGiaDoUong, edtKhuyenMaiDoUong, edtHinhAnhDoUong, edtMoTaDoUong;
    TextView btnThemTheLoai;
    float khuyenmai = 0;
    TheLoai theLoai;
    String path_logo = "C:\\Users\\Admin\\AndroidStudioProjects\\Coffee_App\\CoffeeAppManage\\app\\src\\main\\res\\drawable\\caphe1.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_do_uong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().get("user");

            Log.d("ThemDoUongActivity user: ", user.toString());

        }

        toolbarThemDoUong = findViewById(R.id.toolbarThemDoUong);
        setSupportActionBar(toolbarThemDoUong);

        spnTheLoai = findViewById(R.id.spn_the_loai);
        loadData();
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ThemDoUongActivity.this, theLoaiAdapter.getItem(i).getTen_the_loai(), Toast.LENGTH_SHORT).show();
//                Log.d("The loai: ", theLoaiAdapter.getItem(i).toString());
                theLoai = theLoaiAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtTenDoUong = findViewById(R.id.edtTenDoUong);
        edtGiaDoUong = findViewById(R.id.edtGiaDoUong);
        edtKhuyenMaiDoUong = findViewById(R.id.edtKhuyenMaiDoUong);
        edtHinhAnhDoUong = findViewById(R.id.edtHinhAnhDoUong);
        btnThemTheLoai = findViewById(R.id.btnThemTheLoai);
        edtMoTaDoUong = findViewById(R.id.edtMoTaDoUong);

        btnThemTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTenDoUong.getText().toString().trim().equals("") || 
                    edtGiaDoUong.getText().toString().trim().equals("") ||
                    edtMoTaDoUong.getText().toString().trim().equals("")){
                    Toast.makeText(ThemDoUongActivity.this, "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    if(!edtHinhAnhDoUong.getText().toString().trim().equals("")){
                        path_logo = edtHinhAnhDoUong.getText().toString().trim();
                    }
                    String moTaDoUong = edtMoTaDoUong.getText().toString().trim();
                    String tenDoUong = edtTenDoUong.getText().toString().trim();
                    float giaDoUong = Float.parseFloat(edtGiaDoUong.getText().toString().trim());
                    if(!edtKhuyenMaiDoUong.getText().toString().equals("")){
                        khuyenmai = Float.parseFloat(edtKhuyenMaiDoUong.getText().toString().trim());
                        if(khuyenmai >= giaDoUong){
                            Toast.makeText(ThemDoUongActivity.this, "Khuyến mại không hợp lệ!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    Log.d("ten san pham: ", tenDoUong);
                    Log.d("gia san pham: ", giaDoUong+"");
                    Log.d("khuyen mai gia: ", khuyenmai+"");
                    Log.d("logo: ", path_logo);
                    Log.d("mo ta: ", moTaDoUong);
                    Log.d("the loai: ", theLoai.toString());


                    Product_1 product = new Product_1(0, tenDoUong, giaDoUong, khuyenmai, path_logo, moTaDoUong,
                            theLoai, null);

                    ApiService.apiService.createProduct(product).enqueue(new Callback<ResponseProduct_1>() {
                        @Override
                        public void onResponse(Call<ResponseProduct_1> call, Response<ResponseProduct_1> response) {
                            ResponseProduct_1 responseProduct1 = response.body();
                            Product_1 newProduct = responseProduct1.getData();
                            int statusCode = responseProduct1.getStatusCode();
                            String message = responseProduct1.getMessage();

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("newProduct", newProduct);
                            setResult(Activity.RESULT_OK, resultIntent); // Trả kết quả về Fragment
                            finish(); // Đóng activity
                        }

                        @Override
                        public void onFailure(Call<ResponseProduct_1> call, Throwable throwable) {

                        }
                    });
                }
            }
        });



    }

    private void loadData(){
        ApiService.apiService.getAllTheLoai().enqueue(new Callback<ResponseTheLoai>() {
            @Override
            public void onResponse(Call<ResponseTheLoai> call, Response<ResponseTheLoai> response) {
                ResponseTheLoai responseTheLoai = response.body();
                List<TheLoai> listTheLoaiApi = responseTheLoai.getData();
                int statusCode = responseTheLoai.getStatusCode();
                String message = responseTheLoai.getMessage();

                theLoaiAdapter = new TheLoaiAdapter(ThemDoUongActivity.this, R.layout.item_the_loai_selected, listTheLoaiApi);
                spnTheLoai.setAdapter(theLoaiAdapter);

            }

            @Override
            public void onFailure(Call<ResponseTheLoai> call, Throwable throwable) {

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
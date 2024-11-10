package com.example.coffeeappmanage.activity.admin;

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
    EditText edtTenDoUong, edtGiaDoUong, edtKhuyenMaiDoUong, edtHinhAnhDoUong;
    TextView btnThemTheLoai;


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

            Log.d("ChangePasswordActivity user: ", user.toString());

        }

        toolbarThemDoUong = findViewById(R.id.toolbarThemDoUong);
        setSupportActionBar(toolbarThemDoUong);

        spnTheLoai = findViewById(R.id.spn_the_loai);
        loadData();
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ThemDoUongActivity.this, theLoaiAdapter.getItem(i).getTen_the_loai(), Toast.LENGTH_SHORT).show();
                Log.d("The loai: ", theLoaiAdapter.getItem(i).toString());
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
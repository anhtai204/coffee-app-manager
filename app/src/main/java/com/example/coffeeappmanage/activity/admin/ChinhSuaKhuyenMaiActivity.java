package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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

public class ChinhSuaKhuyenMaiActivity extends AppCompatActivity {

    Toolbar toolbar;
    private User user;
    KhuyenMai khuyenMai;
    EditText edtPhanTramKhuyenMaiSua, edtDieuKienKhuyenMaiSua;
    TextView btnSuaKhuyenMai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chinh_sua_khuyen_mai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().get("user");
            khuyenMai = (KhuyenMai) getIntent().getExtras().get("khuyenmai");
        }

        toolbar = findViewById(R.id.toolbarSuaKhuyenMai);
        setSupportActionBar(toolbar);

        edtPhanTramKhuyenMaiSua = findViewById(R.id.edtPhanTramKhuyenMaiSua);
        edtPhanTramKhuyenMaiSua.setText(khuyenMai.getPhanTramKhuyenMai()+"");
        edtDieuKienKhuyenMaiSua = findViewById(R.id.edtDieuKienKhuyenMaiSua);
        edtDieuKienKhuyenMaiSua.setText(khuyenMai.getDonHangToiThieu()+"");
        btnSuaKhuyenMai = findViewById(R.id.btnSuaKhuyenMai);

        btnSuaKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_khuyen_mai = khuyenMai.getId_khuyen_mai();
                float new_phanTramKhuyenMai = Float.parseFloat(edtPhanTramKhuyenMaiSua.getText().toString().trim());
                float new_dieuKienKhuyenMai = Float.parseFloat(edtDieuKienKhuyenMaiSua.getText().toString().trim());

                KhuyenMai updatedKhuyenMai = new KhuyenMai(0, new_phanTramKhuyenMai, new_dieuKienKhuyenMai);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaKhuyenMaiActivity.this);
                builder.setTitle("Update");
                builder.setMessage("Bạn có chắc muốn thay đổi khuyến mại!");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.updateKhuyenMai(id_khuyen_mai, updatedKhuyenMai).enqueue(new Callback<ResponseSingleKhuyenMai>() {
                            @Override
                            public void onResponse(Call<ResponseSingleKhuyenMai> call, Response<ResponseSingleKhuyenMai> response) {
                                if (response.isSuccessful()) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("updatedKhuyenMai", updatedKhuyenMai);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish(); // Đóng activity và quay lại fragment
                                    Toast.makeText(ChinhSuaKhuyenMaiActivity.this, "Update khuyenMai successed!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseSingleKhuyenMai> call, Throwable throwable) {
                                Toast.makeText(ChinhSuaKhuyenMaiActivity.this, "update khuyenMai failed", Toast.LENGTH_SHORT).show();
                                Log.d("error update: ", throwable.toString());
                            }
                        });
                    }
                });
                builder.create().show();
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
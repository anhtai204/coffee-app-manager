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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaTheLoaiActivity extends AppCompatActivity {

    User user;
    TheLoai theLoai;
    Toolbar toolbar;
    EditText edtTenTheLoai;
    TextView btnCapNhatTheLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chinh_sua_the_loai);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().get("user");
            theLoai = (TheLoai) getIntent().getExtras().get("theloai");
        }

        Log.d("thể loại: ", theLoai.toString());
        Log.d("user: ", user.toString());

        toolbar = findViewById(R.id.toolbarTheLoai);
        setSupportActionBar(toolbar);

        edtTenTheLoai = findViewById(R.id.edtTenTheLoai);
        btnCapNhatTheLoai = findViewById(R.id.btnCapNhatTheLoai);

        edtTenTheLoai.setText(theLoai.getTen_the_loai());

        btnCapNhatTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_theLoai = theLoai.getId_theLoai();
                String new_tenTheLoai = edtTenTheLoai.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaTheLoaiActivity.this);
                builder.setTitle("Update");
                builder.setMessage("Bạn có chắc muốn thay đổi tên thể loại!");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.updateTheLoai(id_theLoai, new_tenTheLoai).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
//                                    TheLoaiAdminFragment fragment = (TheLoaiAdminFragment) getSupportFragmentManager()
//                                            .findFragmentByTag("TheLoaiAdminFragment");
//
//                                    fragment.loadData();
//                                    fragment.rcTheLoaiAdapter.notifyDataSetChanged();
//
//                                    finish();

                                    TheLoai updatedTheLoai = new TheLoai(id_theLoai, new_tenTheLoai);
                                    // Sau khi người dùng chỉnh sửa thể loại thành công
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("updatedTheLoai", updatedTheLoai); // updatedTheLoai là đối tượng TheLoai đã chỉnh sửa
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish(); // Đóng activity và quay lại fragment
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {

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
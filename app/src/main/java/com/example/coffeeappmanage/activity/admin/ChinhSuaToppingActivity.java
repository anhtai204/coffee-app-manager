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
import com.example.coffeeappmanage.model.ResponseSingleTopping;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChinhSuaToppingActivity extends AppCompatActivity {

    Toolbar toolbar;
    private User user;
    Topping topping;
    EditText edtTenToppingSua, edtGiaToppingSua;
    TextView btnCapNhatTopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chinh_sua_topping);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().get("user");
            topping = (Topping) getIntent().getExtras().get("topping");
        }

        Log.d("topping: ", topping.toString());
        Log.d("user: ", user.toString());

        toolbar = findViewById(R.id.toolbarSuaTopping);
        setSupportActionBar(toolbar);

        edtTenToppingSua = findViewById(R.id.edtTenToppingSua);
        edtTenToppingSua.setText(topping.getTopping_name());
        edtGiaToppingSua = findViewById(R.id.edtGiaToppingSua);
        edtGiaToppingSua.setText(topping.getGiaTopping()+"");
        btnCapNhatTopping = findViewById(R.id.btnCapNhatTopping);

        btnCapNhatTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_topping = topping.getId_topping();
                String new_tenTopping = edtTenToppingSua.getText().toString();
                float new_giaTopping = Float.parseFloat(edtGiaToppingSua.getText().toString());

                Topping updatedTopping = new Topping(0, new_tenTopping, new_giaTopping);

                AlertDialog.Builder builder = new AlertDialog.Builder(ChinhSuaToppingActivity.this);
                builder.setTitle("Update");
                builder.setMessage("Bạn có chắc muốn thay đổi topping!");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.updateTopping(id_topping, updatedTopping).enqueue(new Callback<ResponseSingleTopping>() {
                            @Override
                            public void onResponse(Call<ResponseSingleTopping> call, Response<ResponseSingleTopping> response) {
                                if (response.isSuccessful()) {
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("updatedTopping", updatedTopping);
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish(); // Đóng activity và quay lại fragment
                                    Toast.makeText(ChinhSuaToppingActivity.this, "Update topping successed!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseSingleTopping> call, Throwable throwable) {
                                Toast.makeText(ChinhSuaToppingActivity.this, "update topping failed", Toast.LENGTH_SHORT).show();
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
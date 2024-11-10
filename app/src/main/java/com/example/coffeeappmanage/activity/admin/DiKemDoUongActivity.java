package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCDiKemDoUongAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCTheLoaiAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.ResponseTopping;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiKemDoUongActivity extends AppCompatActivity {

    Toolbar toolbar;
    private User user;
    private RecyclerView rcDiKemDoUong;
    FloatingActionButton fabThemTopping;
    List<Topping> toppingList;
    RCDiKemDoUongAdapter rcDiKemDoUongAdapter;
    private ActivityResultLauncher<Intent> suaToppingLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_di_kem_do_uong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Nhận đối tượng User từ Bundle
        if(getIntent().getExtras() != null){
            user = (User) getIntent().getExtras().get("user");

            Log.d("DiKemDoUongActivity user: ", user.toString());

        }
        toolbar = findViewById(R.id.toolbarDiKemDoUong);
        setSupportActionBar(toolbar);

        rcDiKemDoUong = findViewById(R.id.rcDiKemDoUong);
        rcDiKemDoUong.setLayoutManager(new LinearLayoutManager(this));
        rcDiKemDoUong.setHasFixedSize(true);
        toppingList = new ArrayList<>();


        suaToppingLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Topping updatedTopping = (Topping) data.getSerializableExtra("updatedTopping");
                            Log.d("updated topping: ", updatedTopping.toString());
                            if (updatedTopping != null) {
                                loadData();
                                Toast.makeText(DiKemDoUongActivity.this, "Cập nhật thể loại thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );


        rcDiKemDoUongAdapter = new RCDiKemDoUongAdapter(this, toppingList, user, suaToppingLauncher);
        rcDiKemDoUong.setAdapter(rcDiKemDoUongAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Intent intent = result.getData();
                            Topping newtopping = (Topping) intent.getSerializableExtra("newtopping");

                            if (newtopping != null) {
                                toppingList.add(newtopping);
                                Log.d("newtopping: ", newtopping.toString());
                                loadData();
                            }
                        }
                    }
                }
        );

        fabThemTopping = findViewById(R.id.fabThemTopping);
        fabThemTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentThemTheLoai = new Intent(DiKemDoUongActivity.this, ThemToppingActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                intentThemTheLoai.putExtras(data);
                activityResultLauncher.launch(intentThemTheLoai);
            }
        });

    }

    private void loadData(){
        ApiService.apiService.getAllTopping().enqueue(new Callback<ResponseTopping>() {
            @Override
            public void onResponse(Call<ResponseTopping> call, Response<ResponseTopping> response) {
                ResponseTopping responseTopping = response.body();
                List<Topping> listToppingApi = responseTopping.getData();
                int statusCode = responseTopping.getStatusCode();
                String message = responseTopping.getMessage();

                rcDiKemDoUongAdapter.setData(listToppingApi);
                rcDiKemDoUongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseTopping> call, Throwable throwable) {

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
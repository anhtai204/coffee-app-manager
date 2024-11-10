package com.example.coffeeappmanage.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCDoUongAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCTheLoaiAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoUongAdminFragment extends Fragment {

    Toolbar toolbar;
    private RecyclerView rcDoUongAdmin;
    List<Product> listProduct;
    RCDoUongAdapter rcDoUongAdapter;
    private User user;
    FloatingActionButton fabThemDoUong;
    private ActivityResultLauncher<Intent> suaDoUongLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_do_uong_admin, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("DoUongAdminFragment", "User: " + user.toString());
            }
        }

        rcDoUongAdmin = view.findViewById(R.id.rcDoUongAdmin);
        rcDoUongAdmin.setLayoutManager(new LinearLayoutManager(getContext()));
        rcDoUongAdmin.setHasFixedSize(true);
        listProduct = new ArrayList<>();

        suaDoUongLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Product updatedDoUong = (Product) data.getSerializableExtra("updatedProduct");
                            Log.d("updated do uong: ", updatedDoUong.toString());
                            if (updatedDoUong != null) {
                                loadData();
                                Toast.makeText(getContext(), "Cập nhật đồ uống thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        rcDoUongAdapter = new RCDoUongAdapter(getContext(), listProduct, user, suaDoUongLauncher);
        rcDoUongAdmin.setAdapter(rcDoUongAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Intent intent = result.getData();
                            Product_1 newProduct = (Product_1) intent.getSerializableExtra("newProduct");

                            Product new_product = new Product(newProduct.getId_product(), newProduct.getTenSanPham(), newProduct.getGiaSanPham(),
                                    newProduct.getKhuyenmai_gia(), newProduct.getTheLoai(), newProduct.getKhuyenMai(), null, 0, newProduct.getMo_ta(), newProduct.getLogo_product());

                            if (newProduct != null) {
                                listProduct.add(new_product);
                                loadData();
                            }
                        }
                    }
                }
        );

        fabThemDoUong = view.findViewById(R.id.fabThemDoUong);

        fabThemDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentThemTheLoai = new Intent(getContext(), ThemDoUongActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                intentThemTheLoai.putExtras(data);
                activityResultLauncher.launch(intentThemTheLoai);
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng nhấn submit tìm kiếm
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi nội dung tìm kiếm thay đổi
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void loadData() {
        ApiService.apiService.getAllProduct().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseProduct responseProduct = response.body();
                    List<Product> listProductApi = responseProduct.getData();
                    int statusCode = responseProduct.getStatusCode();
                    String message = responseProduct.getMessage();

                    rcDoUongAdapter.setData(listProductApi);
                    rcDoUongAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseProduct> call, Throwable throwable) {
                Toast.makeText(getContext(), "error call api product", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("productData", "Loading product data...");
    }
}
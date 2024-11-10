package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCProductAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KhuyenMaiOthersFragment extends Fragment {
    private RecyclerView recyclerViewOthers;
    List<Product> listProducts;
    RCProductAdapter rcProductAdapter;
    private User user;

    public KhuyenMaiOthersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khuyen_mai_others, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("KhuyenMaiCaPheFragment", "User: " + user.toString());
            }
        }

        recyclerViewOthers = view.findViewById(R.id.recyclerViewOthers);
        recyclerViewOthers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOthers.setHasFixedSize(true);
        listProducts = new ArrayList<>();

        rcProductAdapter = new RCProductAdapter(getContext(), listProducts, user);
        recyclerViewOthers.setAdapter(rcProductAdapter); // Thiết lập adapter ngay lập tức

        ApiService.apiService.getOthersFilterSale().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseProduct responseProduct = response.body();
                    List<Product> productListCallApi = responseProduct.getData();
                    int statusCode = responseProduct.getStatusCode();
                    String message = responseProduct.getMessage();

                    Log.e("productListCallApi", productListCallApi.toString());

                    listProducts.clear(); // Xóa danh sách cũ
                    listProducts.addAll(productListCallApi); // Thêm dữ liệu mới vào list
                    rcProductAdapter.notifyDataSetChanged(); // Cập nhật adapter
                }
            }

            @Override
            public void onFailure(Call<ResponseProduct> call, Throwable throwable) {
                Toast.makeText(getContext(), "Call api failed" + throwable.toString(), Toast.LENGTH_SHORT).show();
                Log.e("failed", throwable.toString());
            }
        });

        return view;
    }
}
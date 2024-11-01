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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TatCaCaPheFragment extends Fragment {

    private RecyclerView recyclerViewCaPhe;
    List<Product> listProducts;
    RCProductAdapter rcProductAdapter;

    public TatCaCaPheFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tat_ca_ca_phe, container, false);

        recyclerViewCaPhe = view.findViewById(R.id.recyclerViewCaPhe);
        recyclerViewCaPhe.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCaPhe.setHasFixedSize(true);
        listProducts = new ArrayList<>();

        rcProductAdapter = new RCProductAdapter(getContext(), listProducts);
        recyclerViewCaPhe.setAdapter(rcProductAdapter); // Thiết lập adapter ngay lập tức

        ApiService.apiService.getAllCoffee().enqueue(new Callback<ResponseProduct>() {
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
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
import com.example.coffeeappmanage.activity.RecyclerProduct.RCRateProductAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.RateProduct;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.ResponseRateProduct;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XepHangTraSuaFragment extends Fragment {

    private RecyclerView recyclerViewTraSua;
//    List<RateProduct> listRateProducts;
    List<Product> listRateProducts;
    RCRateProductAdapter rcRateProductAdapter;
    private User user;

    public XepHangTraSuaFragment() {
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
        View view = inflater.inflate(R.layout.fragment_xep_hang_tra_sua, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("XepHangTraSuaFragment user: ",  user.toString());
            }
        }

        recyclerViewTraSua = view.findViewById(R.id.recyclerViewTraSua);
        recyclerViewTraSua.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTraSua.setHasFixedSize(true);
        listRateProducts = new ArrayList<>();

        rcRateProductAdapter = new RCRateProductAdapter(getContext(), listRateProducts, user);
        recyclerViewTraSua.setAdapter(rcRateProductAdapter);

//        ApiService.apiService.getTraSuaFilterRate().enqueue(new Callback<ResponseRateProduct>() {
//            @Override
//            public void onResponse(Call<ResponseRateProduct> call, Response<ResponseRateProduct> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ResponseRateProduct responseRateProduct = response.body();
//                    List<RateProduct> productListCallApi = responseRateProduct.getData();
//                    int statusCode = responseRateProduct.getStatusCode();
//                    String message = responseRateProduct.getMessage();
//
//                    Log.e("call rate product", productListCallApi.toString());
//
//                    listRateProducts.clear(); // Xóa danh sách cũ
//                    listRateProducts.addAll(productListCallApi); // Thêm dữ liệu mới vào list
//                    rcRateProductAdapter.notifyDataSetChanged(); // Cập nhật adapter
//
//                    Log.e("rate product", listRateProducts.toString());
//                }
//
//                Log.e("tesst", response.body().getData().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseRateProduct> call, Throwable throwable) {
//                Toast.makeText(getContext(), "Call api failed" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                Log.e("failed", throwable.toString());
//            }
//        });

        ApiService.apiService.getTraSuaFilterRate().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseProduct responseRateProduct = response.body();
                    List<Product> productListCallApi = responseRateProduct.getData();
                    int statusCode = responseRateProduct.getStatusCode();
                    String message = responseRateProduct.getMessage();

                    Log.e("call rate product", productListCallApi.toString());

                    listRateProducts.clear(); // Xóa danh sách cũ
                    listRateProducts.addAll(productListCallApi); // Thêm dữ liệu mới vào list
                    rcRateProductAdapter.notifyDataSetChanged(); // Cập nhật adapter

                    Log.e("rate product", listRateProducts.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseProduct> call, Throwable throwable) {

            }
        });

        return view;
    }
}
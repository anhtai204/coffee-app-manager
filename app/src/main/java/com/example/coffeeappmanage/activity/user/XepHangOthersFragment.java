package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCRateProductAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseProduct;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class XepHangOthersFragment extends Fragment {

    private RecyclerView recyclerViewOthers;
    List<Product> listRateProducts;
    RCRateProductAdapter rcRateProductAdapter;
    private User user;


    public XepHangOthersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_xep_hang_others, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("XepHangCaPheFragment user: ",  user.toString());
            }
        }

        recyclerViewOthers = view.findViewById(R.id.recyclerViewOthers);
        recyclerViewOthers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOthers.setHasFixedSize(true);
        listRateProducts = new ArrayList<>();

        rcRateProductAdapter = new RCRateProductAdapter(getContext(), listRateProducts, user);
        recyclerViewOthers.setAdapter(rcRateProductAdapter);

        ApiService.apiService.getOthersFilterRate().enqueue(new Callback<ResponseProduct>() {
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
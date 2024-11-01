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
import com.example.coffeeappmanage.model.RateProduct;
import com.example.coffeeappmanage.model.ResponseRateProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XepHangTraSuaFragment extends Fragment {

    private RecyclerView recyclerViewTraSua;
    List<RateProduct> listRateProducts;
    RCRateProductAdapter rcRateProductAdapter;

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

        recyclerViewTraSua = view.findViewById(R.id.recyclerViewTraSua);
        recyclerViewTraSua.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTraSua.setHasFixedSize(true);
        listRateProducts = new ArrayList<>();

        rcRateProductAdapter = new RCRateProductAdapter(getContext(), listRateProducts);
        recyclerViewTraSua.setAdapter(rcRateProductAdapter);

        ApiService.apiService.getTraSuaFilterRate().enqueue(new Callback<ResponseRateProduct>() {
            @Override
            public void onResponse(Call<ResponseRateProduct> call, Response<ResponseRateProduct> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseRateProduct responseRateProduct = response.body();
                    List<RateProduct> productListCallApi = responseRateProduct.getData();
                    int statusCode = responseRateProduct.getStatusCode();
                    String message = responseRateProduct.getMessage();

                    Log.e("call rate product", productListCallApi.toString());

                    listRateProducts.clear(); // Xóa danh sách cũ
                    listRateProducts.addAll(productListCallApi); // Thêm dữ liệu mới vào list
                    rcRateProductAdapter.notifyDataSetChanged(); // Cập nhật adapter

                    Log.e("rate product", listRateProducts.toString());
                }

                Log.e("tesst", response.body().getData().toString());

            }

            @Override
            public void onFailure(Call<ResponseRateProduct> call, Throwable throwable) {
                Toast.makeText(getContext(), "Call api failed" + throwable.toString(), Toast.LENGTH_SHORT).show();
                Log.e("failed", throwable.toString());
            }
        });

        return view;
    }
}
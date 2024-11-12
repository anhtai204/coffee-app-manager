package com.example.coffeeappmanage.activity.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCDonHangAdminAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCOrderUserAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.ResponseDonHang;
import com.example.coffeeappmanage.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DonHangAdminFragment extends Fragment {

    private RecyclerView rcDonHangAdmin;
    List<DonHang> listDonHang;
    RCDonHangAdminAdapter rcDonHangAdminAdapter;
    private User user;

    public DonHangAdminFragment() {
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
        View view = inflater.inflate(R.layout.fragment_don_hang_admin, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("OrderUserFragment", "User: " + user.toString());
            }
        }

        rcDonHangAdmin = view.findViewById(R.id.rcDonHangAdmin);
        rcDonHangAdmin.setLayoutManager(new LinearLayoutManager(getContext()));
        rcDonHangAdmin.setHasFixedSize(true);
        listDonHang = new ArrayList<>();

        rcDonHangAdminAdapter = new RCDonHangAdminAdapter(getContext(), listDonHang, user);
        rcDonHangAdmin.setAdapter(rcDonHangAdminAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        return view;
    }

    public void loadData() {

        ApiService.apiService.getAllDonHang().enqueue(new Callback<ResponseDonHang>() {
            @Override
            public void onResponse(Call<ResponseDonHang> call, Response<ResponseDonHang> response) {
                if (response.isSuccessful() && response.body()!=null){
                    ResponseDonHang responseDonHang = response.body();
                    List<DonHang> listDonHangApi = responseDonHang.getData();
                    int statusCode = responseDonHang.getStatusCode();
                    String message = responseDonHang.getMessage();

                    rcDonHangAdminAdapter.setData(listDonHangApi);
                    rcDonHangAdminAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDonHang> call, Throwable throwable) {

            }
        });

        Log.d("loadData", "Loading donhang data...");
    }
}
package com.example.coffeeappmanage.activity.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCOrderUserAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCTheLoaiAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.ResponseDonHang;
import com.example.coffeeappmanage.model.ResponseTheLoai;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderUserFragment extends Fragment {

    Toolbar toolbar;
    private RecyclerView rcOrderUser;
    List<DonHang> listDonHang;
    RCOrderUserAdapter rcOrderUserAdapter;
    private User user;

    public OrderUserFragment() {
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
        View view = inflater.inflate(R.layout.fragment_order_user, container, false);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("OrderUserFragment", "User: " + user.toString());
            }
        }

        rcOrderUser = view.findViewById(R.id.rcOrderUser);
        rcOrderUser.setLayoutManager(new LinearLayoutManager(getContext()));
        rcOrderUser.setHasFixedSize(true);
        listDonHang = new ArrayList<>();

        rcOrderUserAdapter = new RCOrderUserAdapter(getContext(), listDonHang, user);
        rcOrderUser.setAdapter(rcOrderUserAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        return view;
    }

    public void loadData() {
        ApiService.apiService.getDonHangByUser(user.getId_user()).enqueue(new Callback<ResponseDonHang>() {
            @Override
            public void onResponse(Call<ResponseDonHang> call, Response<ResponseDonHang> response) {
                if (response.isSuccessful() && response.body()!=null){
                    ResponseDonHang responseDonHang = response.body();
                    List<DonHang> listDonHangApi = responseDonHang.getData();
                    int statusCode = responseDonHang.getStatusCode();
                    String message = responseDonHang.getMessage();

                    List<DonHang> listDonHangDaDat = new ArrayList<>();
                    for(DonHang dh : listDonHangApi){
                        if(!dh.getStatus().equals("dathang")){
                            listDonHangDaDat.add(dh);
                        }
                    }

//                    rcOrderUserAdapter.setData(listDonHangApi);
                    rcOrderUserAdapter.setData(listDonHangDaDat);
                    rcOrderUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseDonHang> call, Throwable throwable) {

            }
        });
        Log.d("loadData", "Loading donhang data...");
    }
}
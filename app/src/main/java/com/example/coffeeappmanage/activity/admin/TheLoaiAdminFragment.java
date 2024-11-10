package com.example.coffeeappmanage.activity.admin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.coffeeappmanage.activity.RecyclerProduct.RCProductAdapter;
import com.example.coffeeappmanage.activity.RecyclerProduct.RCTheLoaiAdapter;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
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


public class TheLoaiAdminFragment extends Fragment {

    Toolbar toolbar;
    private RecyclerView rcTheLoaiAdmin;
    List<TheLoai> listTheLoai;
    RCTheLoaiAdapter rcTheLoaiAdapter;
    private User user;
    FloatingActionButton fabThemTheLoai;
    private ActivityResultLauncher<Intent> suaTheLoaiLauncher;

    public TheLoaiAdminFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_loai_admin, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Nhận đối tượng User từ Bundle
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");

            if (user != null) {
                Log.d("TheLoaiAdminFragment", "User: " + user.toString());
            }
        }

        rcTheLoaiAdmin = view.findViewById(R.id.rcTheLoaiAdmin);
        rcTheLoaiAdmin.setLayoutManager(new LinearLayoutManager(getContext()));
        rcTheLoaiAdmin.setHasFixedSize(true);
        listTheLoai = new ArrayList<>();

        suaTheLoaiLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            TheLoai updatedTheLoai = (TheLoai) data.getSerializableExtra("updatedTheLoai");
                            Log.d("updated the loai: ", updatedTheLoai.toString());
                            if (updatedTheLoai != null) {
                                loadData();
                                Toast.makeText(getContext(), "Cập nhật thể loại thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        rcTheLoaiAdapter = new RCTheLoaiAdapter(getContext(), listTheLoai, user, suaTheLoaiLauncher);
        rcTheLoaiAdmin.setAdapter(rcTheLoaiAdapter); // Thiết lập adapter ngay lập tức

        loadData();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Intent intent = result.getData();
                            TheLoai newTheLoai = (TheLoai) intent.getSerializableExtra("newtheloai");

                            if (newTheLoai != null) {
                                listTheLoai.add(newTheLoai);
                                Log.d("newtheloai: ", newTheLoai.toString());
                                loadData();
                            }
                        }
                    }
                }
        );


        fabThemTheLoai = view.findViewById(R.id.fabThemTheLoai);

//        fabThemTheLoai.setOnTouchListener(new View.OnTouchListener(){
//            float xDelta, yDelta;
//            boolean isDragging = false; // Biến trạng thái để theo dõi việc kéo
//
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                int action = motionEvent.getAction();
//                switch (action){
//                    case MotionEvent.ACTION_DOWN:
//                        xDelta = view.getX() - motionEvent.getRawX();
//                        yDelta = view.getY() - motionEvent.getRawY();
//                        isDragging = false; // Mặc định không phải là kéo
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        // Khi di chuyển, xác định xem có di chuyển một khoảng đáng kể không
//                        if (Math.abs(motionEvent.getRawX() - xDelta) > 10 || Math.abs(motionEvent.getRawY() - yDelta) > 10) {
//                            isDragging = true; // Đánh dấu là đang kéo
//                            view.animate()
//                                    .x(motionEvent.getRawX() + xDelta)
//                                    .y(motionEvent.getRawY() + yDelta)
//                                    .setDuration(0)
//                                    .start();
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        // Khi thả tay, kiểm tra xem có kéo hay không
//                        if (!isDragging) {
//                            // Nếu không phải là kéo, thực hiện hành động click
//                            view.performClick(); // Gọi performClick để kích hoạt onClick
//                        }
//                        break;
//                }
//                // return false đảm bảo sự kiện onClick không bị "ăn"
//                return true;
//            }
//        });

        fabThemTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentThemTheLoai = new Intent(getContext(), ThemTheLoaiActivity.class);
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
        ApiService.apiService.getAllTheLoai().enqueue(new Callback<ResponseTheLoai>() {
            @Override
            public void onResponse(Call<ResponseTheLoai> call, Response<ResponseTheLoai> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseTheLoai responseTheLoai = response.body();
                    List<TheLoai> listTheLoaiApi = responseTheLoai.getData();
                    int statusCode = responseTheLoai.getStatusCode();
                    String message = responseTheLoai.getMessage();

                    rcTheLoaiAdapter.setData(listTheLoaiApi);
                    rcTheLoaiAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseTheLoai> call, Throwable throwable) {
                Toast.makeText(getContext(), "error call api the loai", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("loadData", "Loading categories data...");
    }




}
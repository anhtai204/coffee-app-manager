package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.admin.ChinhSuaTheLoaiActivity;
import com.example.coffeeappmanage.activity.user.DanhGiaActivity;
import com.example.coffeeappmanage.activity.user.OrderProductActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.ResponseBoolean;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RCTheLoaiAdapter extends RecyclerView.Adapter<RCTheLoaiAdapter.TheLoaiViewHolder>{

    private Context context;
    private List<TheLoai> listTheLoai;
    private User user;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    public RCTheLoaiAdapter(Context context, List<TheLoai> listTheLoai, User user, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.listTheLoai = listTheLoai;
        this.user = user;
        this.activityResultLauncher = activityResultLauncher;
    }


//    public RCTheLoaiAdapter(Context context, List<TheLoai> listTheLoai, User user) {
//        this.context = context;
//        this.listTheLoai = listTheLoai;
//        this.user = user;
//    }

    public void setData(List<TheLoai> list){
        this.listTheLoai = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_the_loai, parent, false);
        return new RCTheLoaiAdapter.TheLoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiViewHolder holder, int position) {
        TheLoai theLoai = listTheLoai.get(position);
        if (theLoai == null) {
            return;
        }

        holder.tvTenTheLoai.setText(theLoai.getTen_the_loai());
        holder.imgSuaTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sửa thể loại: " + theLoai.getTen_the_loai(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgXoaTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theLoai.getTen_the_loai().equals("Cà Phê") ||
                    theLoai.getTen_the_loai().equals("Trà Sữa") ||
                    theLoai.getTen_the_loai().equals("Sinh Tố")){
                    Toast.makeText(context, "Thể loại bắt buộc, không thể xóa!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("id_the_loai:", theLoai.getId_theLoai()+"");
                    Log.d("ten_the_loai:", theLoai.getTen_the_loai()+"");

//                    Toast.makeText(context, "Xóa thể loại: " + theLoai.getTen_the_loai(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Xóa");
                    builder.setMessage("Bạn thật sự muốn xóa thể loại: " + theLoai.getTen_the_loai());
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            ApiService.apiService.xoaTheLoai(theLoai.getId_theLoai()).enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call, Response<Void> response) {
//                                    listTheLoai.remove(theLoai);
//                                    notifyDataSetChanged(); // Cập nhật toàn bộ danh sách
//                                    Toast.makeText(context, "Xóa thể loại thành công!", Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call, Throwable throwable) {
//                                    Toast.makeText(context, "Xóa thể loại thất bại!", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                            ApiService.apiService.xoaTheLoai(theLoai.getId_theLoai()).enqueue(new Callback<ResponseBoolean>() {
                                @Override
                                public void onResponse(Call<ResponseBoolean> call, Response<ResponseBoolean> response) {
                                    if(response.isSuccessful()){
                                        ResponseBoolean responseBoolean = response.body();
                                        Boolean check = responseBoolean.getData();
                                        if(!check){
                                            Toast.makeText(context, "Sản phẩm loại này có tồn tại!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            listTheLoai.remove(theLoai);
                                            notifyDataSetChanged(); // Cập nhật toàn bộ danh sách
                                            Toast.makeText(context, "Xóa thể loại thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBoolean> call, Throwable throwable) {
                                    Toast.makeText(context, "Xóa thể loại thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                }
            }
        });

        holder.imgSuaTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theLoai.getTen_the_loai().equals("Cà Phê") ||
                        theLoai.getTen_the_loai().equals("Trà Sữa") ||
                        theLoai.getTen_the_loai().equals("Sinh Tố")){
                    Toast.makeText(context, "Thể loại bắt buộc, không thể sửa!", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intentSuaTheLoai = new Intent(context, ChinhSuaTheLoaiActivity.class);
                    Bundle data = new Bundle();
                    data.putSerializable("user", user);
                    data.putSerializable("theloai", theLoai);
                    intentSuaTheLoai.putExtras(data);

                    // Sử dụng activityResultLauncher để chuyển tới ChinhSuaTheLoaiActivity
                    activityResultLauncher.launch(intentSuaTheLoai);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listTheLoai != null){
            return listTheLoai.size();
        }
        return 0;
    }

    public class TheLoaiViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTenTheLoai;
        private ImageView imgSuaTheLoai, imgXoaTheLoai;

        public TheLoaiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenTheLoai = itemView.findViewById(R.id.tvTenTheLoai);
            imgSuaTheLoai = itemView.findViewById(R.id.imgSuaTheLoai);
            imgXoaTheLoai = itemView.findViewById(R.id.imgXoaTheLoai);

        }
    }
}

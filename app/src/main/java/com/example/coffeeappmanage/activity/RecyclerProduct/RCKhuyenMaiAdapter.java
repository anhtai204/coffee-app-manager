package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.coffeeappmanage.activity.admin.ChinhSuaKhuyenMaiActivity;
import com.example.coffeeappmanage.activity.admin.ChinhSuaToppingActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RCKhuyenMaiAdapter extends RecyclerView.Adapter<RCKhuyenMaiAdapter.KhuyenMaiViewHolder> {

    private Context context;
    private List<KhuyenMai> khuyenMaiList;
    private User user;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public RCKhuyenMaiAdapter(Context context, List<KhuyenMai> khuyenMaiList, User user, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.khuyenMaiList = khuyenMaiList;
        this.user = user;
        this.activityResultLauncher = activityResultLauncher;
    }

//    public RCKhuyenMaiAdapter(Context context, List<KhuyenMai> khuyenMaiList, User user) {
//        this.context = context;
//        this.khuyenMaiList = khuyenMaiList;
//        this.user = user;
//    }

    public void setData(List<KhuyenMai> list){
        this.khuyenMaiList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KhuyenMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_khuyen_mai, parent, false);
        return new RCKhuyenMaiAdapter.KhuyenMaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuyenMaiViewHolder holder, int position) {
        KhuyenMai khuyenMai = khuyenMaiList.get(position);
        if(khuyenMai == null){
            return;
        }

        DecimalFormat df = new DecimalFormat("0.##");
        float phanTramKM = khuyenMai.getPhanTramKhuyenMai();
        String formatted_phantram = df.format(phanTramKM);
        holder.tvPhanTramKhuyenMai.setText("Giảm giá " + formatted_phantram + "%");

        float dieuKien = khuyenMai.getDonHangToiThieu();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        String formatted_dk = decimalFormat.format(dieuKien);
        holder.tvDieuKienKhuyenMai.setText("Áp dụng cho đơn hàng tối thiểu " + formatted_dk + "vnd");


        holder.imgXoaKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Xóa");
                builder.setMessage("Bạn thật sự muốn xóa khuyến mại: " + formatted_phantram + "%");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.deleteKhuyenMai(khuyenMai.getId_khuyen_mai()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                khuyenMaiList.remove(khuyenMai);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa khuyến mại thành công!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {
                                Toast.makeText(context, "Xóa khuyến mại thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create().show();
            }
        });

        holder.imgSuaKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSuaKM = new Intent(context, ChinhSuaKhuyenMaiActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                data.putSerializable("khuyenmai", khuyenMai);
                intentSuaKM.putExtras(data);

                activityResultLauncher.launch(intentSuaKM);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(khuyenMaiList != null){
            return khuyenMaiList.size();
        }
        return 0;
    }

    public class KhuyenMaiViewHolder extends RecyclerView.ViewHolder{

        TextView tvPhanTramKhuyenMai, tvDieuKienKhuyenMai;
        ImageView imgSuaKhuyenMai, imgXoaKhuyenMai;

        public KhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPhanTramKhuyenMai = itemView.findViewById(R.id.tvPhanTramKhuyenMai);
            tvDieuKienKhuyenMai = itemView.findViewById(R.id.tvDieuKienKhuyenMai);
            imgSuaKhuyenMai = itemView.findViewById(R.id.imgSuaKhuyenMai);
            imgXoaKhuyenMai = itemView.findViewById(R.id.imgXoaKhuyenMai);

        }
    }
}

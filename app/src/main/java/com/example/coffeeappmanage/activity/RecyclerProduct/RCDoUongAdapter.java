package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
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
import com.example.coffeeappmanage.activity.admin.ChinhSuaDoUongActivity;
import com.example.coffeeappmanage.activity.admin.ChinhSuaTheLoaiActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RCDoUongAdapter extends RecyclerView.Adapter<RCDoUongAdapter.DoUongViewHolder>{

    private Context context;
    private List<Product> listProduct;
    private User user;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    public RCDoUongAdapter(Context context, List<Product> listProduct, User user, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.listProduct = listProduct;
        this.user = user;
        this.activityResultLauncher = activityResultLauncher;
    }

//    public RCDoUongAdapter(Context context, List<Product> listProduct, User user) {
//        this.context = context;
//        this.listProduct = listProduct;
//        this.user = user;
//    }

    public void setData(List<Product> list){
        this.listProduct = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DoUongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_do_uong, parent, false);
        return new RCDoUongAdapter.DoUongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoUongViewHolder holder, int position) {
        Product product = listProduct.get(position);
        if(product == null){
            return;
        }

        holder.tvTenDoUong.setText(product.getTenSanPham());
        holder.tvTheLoai.setText(product.getTheLoai().getTen_the_loai());

        float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formatted_giasp = decimalFormat.format(gia_sp);
        holder.tvGiaSauGiam.setText(formatted_giasp);

        if(product.getKhuyenmai_gia() != 0){
            String formatted_gia_goc = decimalFormat.format(product.getGiaSanPham());
            holder.tvGiaGoc.setText(formatted_gia_goc);
            holder.tvGiaGoc.setPaintFlags(holder.tvGiaGoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvGiaGoc.setText("");
        }

        holder.imgXoaDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Xóa");
                builder.setMessage("Bạn thật sự muốn xóa sản phẩm: " + product.getTenSanPham());
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.deleteProduct(product.getId_product()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                listProduct.remove(product);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {
                                Toast.makeText(context, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create().show();
            }
        });

        holder.imgSuaDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSuaDoUong = new Intent(context, ChinhSuaDoUongActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                data.putSerializable("product", product);
                intentSuaDoUong.putExtras(data);

                activityResultLauncher.launch(intentSuaDoUong);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listProduct != null){
            return listProduct.size();
        }
        return 0;
    }

    public class DoUongViewHolder extends RecyclerView.ViewHolder{

        TextView tvTenDoUong, tvGiaSauGiam, tvGiaGoc, tvTheLoai;
        ImageView imgSuaDoUong, imgXoaDoUong;

        public DoUongViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenDoUong = itemView.findViewById(R.id.tvTenDoUong);
            tvGiaSauGiam = itemView.findViewById(R.id.tvGiaSauGiam);
            tvGiaGoc = itemView.findViewById(R.id.tvGiaGoc);
            tvTheLoai = itemView.findViewById(R.id.tvTheLoai);
            imgSuaDoUong = itemView.findViewById(R.id.imgSuaDoUong);
            imgXoaDoUong = itemView.findViewById(R.id.imgXoaDoUong);

        }
    }
}

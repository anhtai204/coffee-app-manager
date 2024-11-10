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
import com.example.coffeeappmanage.activity.admin.ChinhSuaDoUongActivity;
import com.example.coffeeappmanage.activity.admin.ChinhSuaToppingActivity;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.TheLoai;
import com.example.coffeeappmanage.model.Topping;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RCDiKemDoUongAdapter extends RecyclerView.Adapter<RCDiKemDoUongAdapter.DiKemDoUongViewHolder> {
    private Context context;
    private List<Topping> toppingList;
    private User user;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    public RCDiKemDoUongAdapter(Context context, List<Topping> listProduct, User user, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.toppingList = toppingList;
        this.user = user;
        this.activityResultLauncher = activityResultLauncher;
    }

//    public RCDiKemDoUongAdapter(Context context, List<Topping> toppingList, User user) {
//        this.context = context;
//        this.toppingList = toppingList;
//        this.user = user;
//    }

    public void setData(List<Topping> list){
        this.toppingList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiKemDoUongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_di_kem_do_uong, parent, false);
        return new RCDiKemDoUongAdapter.DiKemDoUongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiKemDoUongViewHolder holder, int position) {
        Topping topping = toppingList.get(position);
        if(topping == null){
            return;
        }

        holder.tvTenTopping.setText(topping.getTopping_name());

        float gia_topping = topping.getGiaTopping();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        String formatted_gia = decimalFormat.format(gia_topping);
        holder.tvGiaTopping.setText(formatted_gia+"vnd");

        holder.imgXoaTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Xóa");
                builder.setMessage("Bạn thật sự muốn xóa topping: " + topping.getTopping_name());
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.deleteTopping(topping.getId_topping()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                toppingList.remove(topping);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa topping thành công!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {
                                Toast.makeText(context, "Xóa topping thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create().show();
            }
        });

        holder.imgSuaTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSuaTopping = new Intent(context, ChinhSuaToppingActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("user", user);
                data.putSerializable("topping", topping);
                intentSuaTopping.putExtras(data);

                activityResultLauncher.launch(intentSuaTopping);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(toppingList != null){
            return toppingList.size();
        }
        return 0;
    }

    public class DiKemDoUongViewHolder extends RecyclerView.ViewHolder{

        TextView tvTenTopping, tvGiaTopping;
        ImageView imgSuaTopping, imgXoaTopping;

        public DiKemDoUongViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenTopping = itemView.findViewById(R.id.tvTenTopping);
            tvGiaTopping = itemView.findViewById(R.id.tvGiaTopping);
            imgSuaTopping = itemView.findViewById(R.id.imgSuaTopping);
            imgXoaTopping = itemView.findViewById(R.id.imgXoaTopping);

        }
    }
}

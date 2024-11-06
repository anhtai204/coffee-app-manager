package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.user.OrderProductActivity;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.User;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RCProductAdapter extends RecyclerView.Adapter<RCProductAdapter.ProductViewHolder>{

    Context context;
    List<Product> modelArrayList;
    private User user;

    public RCProductAdapter(Context context, List<Product> modelArrayList, User user) {
        this.context = context;
        this.modelArrayList = modelArrayList;
        this.user = user;
    }

    public void setData(List<Product> list){
        this.modelArrayList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = modelArrayList.get(position);
        holder.rc_ten_sp.setText(product.getTenSanPham());
        holder.rc_gioi_thieu.setText("Xin chao");

        DecimalFormat formatStar = new DecimalFormat("0.0"); // Đảm bảo luôn hiển thị 1 chữ số thập phân
        String formatted_star = formatStar.format(product.getAverage_star());
        holder.rc_star.setText(formatted_star);


        float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        String formatted_giasp = decimalFormat.format(gia_sp);
        holder.rc_gia.setText(formatted_giasp);

        if(product.getKhuyenmai_gia() != 0){
            String formatted_gia_goc = decimalFormat.format(product.getGiaSanPham());
            holder.rc_khuyen_mai.setText(formatted_gia_goc);
            holder.rc_khuyen_mai.setPaintFlags(holder.rc_khuyen_mai.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    @Override
    public int getItemCount() {
        if(modelArrayList != null){
            return  modelArrayList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView rc_ten_sp, rc_gioi_thieu, rc_gia, rc_khuyen_mai, rc_star;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            rc_ten_sp = itemView.findViewById(R.id.rc_ten_sp);
            rc_gioi_thieu = itemView.findViewById(R.id.rc_gioi_thieu);
            rc_gia = itemView.findViewById(R.id.rc_gia);
            rc_khuyen_mai = itemView.findViewById(R.id.rc_khuyen_mai);
            rc_star = itemView.findViewById(R.id.rc_star);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi người dùng nhấn vào sản phẩm
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Product clickedProduct = modelArrayList.get(position);
                        Log.e("product clicked", clickedProduct.toString());
                        Toast.makeText(itemView.getContext(), "Product: " + clickedProduct.getTenSanPham().toString(), Toast.LENGTH_SHORT).show();
                        // Thực hiện hành động mong muốn (ví dụ: mở chi tiết sản phẩm)

                        Intent intent = new Intent(context, OrderProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("product", clickedProduct);
                        bundle.putSerializable("user", user);

                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}

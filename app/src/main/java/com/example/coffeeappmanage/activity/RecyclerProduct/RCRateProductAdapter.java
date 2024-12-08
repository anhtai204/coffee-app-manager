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

import com.bumptech.glide.Glide;
import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.user.OrderProductActivity;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.RateProduct;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

public class RCRateProductAdapter extends RecyclerView.Adapter<RCRateProductAdapter.RateProductViewHolder> {

    Context context;
//    List<RateProduct> modelArrayList;
    List<Product> modelArrayList;
    private User user;

//    public RCRateProductAdapter(Context context, List<RateProduct> modelArrayList) {
//        this.context = context;
//        this.modelArrayList = modelArrayList;
//    }
//
//    public void setData(List<RateProduct> list){
//        this.modelArrayList = list;
//        notifyDataSetChanged();
//    }

    public RCRateProductAdapter(Context context, List<Product> modelArrayList, User user) {
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
    public RateProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_rate_product, parent, false);
        return new RateProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateProductViewHolder holder, int position) {
        Product product = modelArrayList.get(position);
        holder.rc_ten_sp_rate.setText(product.getTenSanPham());
        holder.rc_gioi_thieu_rate.setText(product.getMo_ta());

        DecimalFormat formatStar = new DecimalFormat("0.0");
        String formatted_star = formatStar.format(product.getAverage_star());
        holder.rc_star_rate.setText(formatted_star);

        float gia_sp = product.getGiaSanPham() - product.getKhuyenmai_gia();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        String formatted_giasp = decimalFormat.format(gia_sp);
        holder.rc_gia_rate.setText(formatted_giasp);

        if(product.getKhuyenmai_gia() != 0){
            String formatted_gia_goc = decimalFormat.format(product.getGiaSanPham());
            holder.rc_khuyen_mai_rate.setText(formatted_gia_goc);
            holder.rc_khuyen_mai_rate.setPaintFlags(holder.rc_khuyen_mai_rate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        Glide.with(context)
                .load(context.getString(R.string.local_host) + product.getLogo_product())
                .fitCenter()  // Hoặc sử dụng .centerInside() nếu bạn muốn ảnh nhỏ hơn vừa khít với ImageView
                .into(holder.rc_image);

        Log.d("abcas: ", R.string.local_host + product.getLogo_product());
    }

    @Override
    public int getItemCount() {
        if(modelArrayList != null){
            return  modelArrayList.size();
        }
        return 0;
    }

    public class RateProductViewHolder extends RecyclerView.ViewHolder{

        private TextView rc_khuyen_mai_rate, rc_gia_rate, rc_ten_sp_rate, rc_gioi_thieu_rate, rc_star_rate;
        ImageView rc_image;
        public RateProductViewHolder(@NonNull View itemView) {

            super(itemView);

            rc_khuyen_mai_rate = itemView.findViewById(R.id.rc_khuyen_mai_rate);
            rc_gia_rate = itemView.findViewById(R.id.rc_gia_rate);
            rc_ten_sp_rate = itemView.findViewById(R.id.rc_ten_sp_rate);
            rc_gioi_thieu_rate = itemView.findViewById(R.id.rc_gioi_thieu_rate);
            rc_star_rate = itemView.findViewById(R.id.rc_star_rate);
            rc_image = itemView.findViewById(R.id.rc_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi người dùng nhấn vào sản phẩm
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
//                        RateProduct clickedProduct = modelArrayList.get(position);
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

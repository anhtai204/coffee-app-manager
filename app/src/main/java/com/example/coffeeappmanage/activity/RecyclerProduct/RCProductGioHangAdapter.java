package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

public class RCProductGioHangAdapter extends RecyclerView.Adapter<RCProductGioHangAdapter.ProductGioHangViewHolder>{


    Context context;
    List<DonHang> donHangList;
    private User user;

    public RCProductGioHangAdapter(Context context, List<DonHang> donHangList, User user) {
        this.context = context;
        this.donHangList = donHangList;
        this.user = user;
    }

    public void setData(List<DonHang> list){
        this.donHangList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductGioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_giohang_product, parent, false);
        return new RCProductGioHangAdapter.ProductGioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGioHangViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        if(donHang == null){
            return;
        }

        holder.tvGioHangTenSP.setText("tên đơn hàng");
        holder.tvGioHangTuyChinh.setText(donHang.getTuyChinh());

        float gia_don_hang = donHang.getGiaDonHang();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formatted_gia_don_hang = decimalFormat.format(gia_don_hang);
        holder.tvGioHangGia.setText(formatted_gia_don_hang+"vnd");
        holder.tvGioHangCount.setText(donHang.getSoLuong()+"");
        holder.tvGioHangSoLuong.setText(donHang.getSoLuong()+"");

    }

    @Override
    public int getItemCount() {
        if(donHangList != null){
            return  donHangList.size();
        }
        return 0;
    }


    public class ProductGioHangViewHolder extends RecyclerView.ViewHolder{
        TextView tvGioHangTenSP, tvGioHangTuyChinh, tvGioHangGia, tvGioHangSoLuong, tvGioHangCount;
        ImageView imgGioHangChinhSua, imgGioHangXoa, imgGioHangMinus, imgGioHangPlus;

        public ProductGioHangViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGioHangTenSP = itemView.findViewById(R.id.tvGioHangTenSP);
            tvGioHangTuyChinh = itemView.findViewById(R.id.tvGioHangTuyChinh);
            tvGioHangGia = itemView.findViewById(R.id.tvGioHangGia);
            tvGioHangSoLuong = itemView.findViewById(R.id.tvGioHangSoLuong);
            imgGioHangChinhSua = itemView.findViewById(R.id.imgGioHangChinhSua);
            tvGioHangCount = itemView.findViewById(R.id.tvGioHangCount);

            imgGioHangXoa = itemView.findViewById(R.id.imgGioHangXoa);
            imgGioHangMinus = itemView.findViewById(R.id.imgGioHangMinus);
            imgGioHangPlus = itemView.findViewById(R.id.imgGioHangPlus);

        }
    }
}

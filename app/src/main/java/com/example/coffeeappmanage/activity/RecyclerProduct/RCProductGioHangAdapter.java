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

import java.util.List;

public class RCProductGioHangAdapter extends RecyclerView.Adapter<RCProductGioHangAdapter.ProductViewHolder>{


    Context context;
    List<DonHang> donHangList;
    private User user;

    public RCProductGioHangAdapter(Context context, List<DonHang> donHangList, User user) {
        this.context = context;
        this.donHangList = donHangList;
        this.user = user;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_giohang_product, parent, false);
        return new RCProductGioHangAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(donHangList != null){
            return  donHangList.size();
        }
        return 0;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView tvGioHangTenSP, tvGioHangTuyChinh, tvGioHangGia, tvGioHangSoLuong, tvGioHangCount;
        ImageView imgGioHangChinhSua, imgGioHangXoa, imgGioHangMinus, imgGioHangPlus;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGioHangTenSP = itemView.findViewById(R.id.tvGioHangTenSP);
            tvGioHangTuyChinh = itemView.findViewById(R.id.tvGioHangTuyChinh);
            tvGioHangGia = itemView.findViewById(R.id.tvGioHangGia);
            tvGioHangSoLuong = itemView.findViewById(R.id.tvGioHangSoLuong);
            imgGioHangChinhSua = itemView.findViewById(R.id.imgGioHangChinhSua);
            imgGioHangXoa = itemView.findViewById(R.id.imgGioHangXoa);
            imgGioHangMinus = itemView.findViewById(R.id.imgGioHangMinus);
            imgGioHangPlus = itemView.findViewById(R.id.imgGioHangPlus);




        }
    }
}

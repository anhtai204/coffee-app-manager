package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseSingleProduct;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RCOrderUserAdapter extends RecyclerView.Adapter<RCOrderUserAdapter.OrderUserViewHolder> {

    private Context context;
    private List<DonHang> donHangList;
    private User user;


    public RCOrderUserAdapter(Context context, List<DonHang> donHangList, User user) {
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
    public OrderUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_order_user, parent, false);
        return new RCOrderUserAdapter.OrderUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderUserViewHolder holder, int position) {

        DonHang donHang = donHangList.get(position);
        if(donHang == null){
            return;
        }

        ApiService.apiService.getProductById(donHang.getId_product()).enqueue(new Callback<ResponseSingleProduct>() {
            @Override
            public void onResponse(Call<ResponseSingleProduct> call, Response<ResponseSingleProduct> response) {
                if(response.isSuccessful()){
                    ResponseSingleProduct singleProduct = response.body();
                    Product_1 product = singleProduct.getData();
                    int statusCode = singleProduct.getStatusCode();
                    String message = singleProduct.getMessage();

                    holder.tvOrderTen.setText(product.getTenSanPham());
                    holder.tvOrderTuyChinh.setText(donHang.getTuyChinh());
                    holder.tvOrderSoLuong.setText("(" + donHang.getSoLuong() + " đồ uống)");

                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    holder.tvOrderGia.setText(decimalFormat.format(donHang.getGiaDonHang())+"vnd");

                    if(donHang.getStatus().equals("dadat")){
                        holder.tvOrderStatus.setText("Đang chờ duyệt");
                    } else if(donHang.getStatus().equals("daduyet")) {
                        holder.tvOrderStatus.setText("Đang giao");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSingleProduct> call, Throwable throwable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(donHangList != null){
            return donHangList.size();
        }
        return 0;
    }

    public class OrderUserViewHolder extends RecyclerView.ViewHolder{

        ImageView imgOrder;
        TextView tvOrderTen, tvOrderTuyChinh, tvOrderGia, tvOrderSoLuong, tvOrderStatus;

        public OrderUserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOrder = itemView.findViewById(R.id.imgOrder);
            tvOrderTen = itemView.findViewById(R.id.tvOrderTen);
            tvOrderTuyChinh = itemView.findViewById(R.id.tvOrderTuyChinh);
            tvOrderGia = itemView.findViewById(R.id.tvOrderGia);
            tvOrderSoLuong = itemView.findViewById(R.id.tvOrderSoLuong);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);

        }
    }
}

package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.api.ApiService;
import com.example.coffeeappmanage.model.DonHang;
import com.example.coffeeappmanage.model.Product_1;
import com.example.coffeeappmanage.model.ResponseSingleProduct;
import com.example.coffeeappmanage.model.ResponseSingleUser;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RCDonHangAdminAdapter extends RecyclerView.Adapter<RCDonHangAdminAdapter.DonHangAdminViewHolder>{

    private Context context;
    private List<DonHang> donHangList;
    private User user;

    public RCDonHangAdminAdapter(Context context, List<DonHang> donHangList, User user) {
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
    public DonHangAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_don_hang_admin, parent, false);
        return new RCDonHangAdminAdapter.DonHangAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdminViewHolder holder, int position) {
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

                    ApiService.apiService.getUserById(donHang.getId_user()).enqueue(new Callback<ResponseSingleUser>() {
                        @Override
                        public void onResponse(Call<ResponseSingleUser> call, Response<ResponseSingleUser> response) {
                            ResponseSingleUser responseSingleUser = response.body();
                            User customer = responseSingleUser.getData();
                            int statusCode_1 = responseSingleUser.getStatusCode();
                            String message_1 = responseSingleUser.getMessage();

                            holder.tvDonHangEmail.setText(customer.getEmail());
                        }

                        @Override
                        public void onFailure(Call<ResponseSingleUser> call, Throwable throwable) {

                        }
                    });

                    holder.tvDonHangTen.setText(product.getTenSanPham());
                    holder.tvDonHangTuyChinh.setText(donHang.getTuyChinh());
                    holder.tvDonHangSoLuong.setText("(" + donHang.getSoLuong() + " đồ uống)");

                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    holder.tvDonHangGia.setText(decimalFormat.format(donHang.getGiaDonHang())+"vnd");

                    if(donHang.getStatus().equals("dadat")){
                        holder.tvDonHangStatus.setText("Duyệt");
                        holder.tvDonHangStatus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                donHang.setStatus("daduyet");
                                ApiService.apiService.updateDonHang(donHang.getId_donHang(), donHang).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(view.getContext(), "Duyệt đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                                        holder.tvDonHangStatus.setText("Đang giao");
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable throwable) {

                                    }
                                });

                            }
                        });
                    } else if(donHang.getStatus().equals("daduyet")) {
                        holder.tvDonHangStatus.setText("Đang giao");
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


    public class DonHangAdminViewHolder extends RecyclerView.ViewHolder{

        ImageView imgDonHang;
        TextView tvDonHangEmail, tvDonHangTen, tvDonHangTuyChinh, tvDonHangGia,
                tvDonHangSoLuong, tvDonHangStatus;

        public DonHangAdminViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDonHang = itemView.findViewById(R.id.imgDonHang);
            tvDonHangEmail = itemView.findViewById(R.id.tvDonHangEmail);
            tvDonHangTen = itemView.findViewById(R.id.tvDonHangTen);
            tvDonHangTuyChinh = itemView.findViewById(R.id.tvDonHangTuyChinh);
            tvDonHangGia = itemView.findViewById(R.id.tvDonHangGia);
            tvDonHangSoLuong = itemView.findViewById(R.id.tvDonHangSoLuong);
            tvDonHangStatus = itemView.findViewById(R.id.tvDonHangStatus);

        }
    }
}

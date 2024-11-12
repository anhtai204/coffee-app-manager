package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.KhuyenMai;
import com.example.coffeeappmanage.model.User;

import java.text.DecimalFormat;
import java.util.List;

public class RCKhuyenMaiUserAdapter extends RecyclerView.Adapter<RCKhuyenMaiUserAdapter.KhuyenMaiViewHolder> {

    private Context context;
    private List<KhuyenMai> khuyenMaiList;
    private User user;
    private int selectedPosition = -1;  // Lưu vị trí khuyến mãi được chọn

    public RCKhuyenMaiUserAdapter(Context context, List<KhuyenMai> khuyenMaiList, User user) {
        this.context = context;
        this.khuyenMaiList = khuyenMaiList;
        this.user = user;
    }

    public void setData(List<KhuyenMai> list){
        this.khuyenMaiList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KhuyenMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_khuyen_mai_user, parent, false);
        return new RCKhuyenMaiUserAdapter.KhuyenMaiViewHolder(view);
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
        holder.tvPhanTramKhuyenMaiUser.setText("Giảm giá " + formatted_phantram + "%");

        float dieuKien = khuyenMai.getDonHangToiThieu();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        String formatted_dk = decimalFormat.format(dieuKien);
        holder.tvDieuKienKhuyenMaiUser.setText("Áp dụng cho đơn hàng tối thiểu " + formatted_dk + "vnd");

        // Xử lý logic cho RadioButton
        holder.rabKhuyenMai.setChecked(position == selectedPosition);
        holder.rabKhuyenMai.setOnClickListener(v -> {
            // Khi chọn RadioButton, cập nhật vị trí khuyến mãi đã chọn
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();  // Cập nhật lại RecyclerView để hiển thị RadioButton đúng
        });
    }

    @Override
    public int getItemCount() {
        if(khuyenMaiList != null){
            return khuyenMaiList.size();
        }
        return 0;
    }

    public KhuyenMai getSelectedKhuyenMai() {
        if (selectedPosition != -1) {
            return khuyenMaiList.get(selectedPosition);  // Trả về khuyến mãi đã chọn
        }
        return null;  // Không có khuyến mãi nào được chọn
    }

    public class KhuyenMaiViewHolder extends RecyclerView.ViewHolder{

        TextView tvPhanTramKhuyenMaiUser, tvDieuKienKhuyenMaiUser;
        RadioButton rabKhuyenMai;

        public KhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPhanTramKhuyenMaiUser = itemView.findViewById(R.id.tvPhanTramKhuyenMaiUser);
            tvDieuKienKhuyenMaiUser = itemView.findViewById(R.id.tvDieuKienKhuyenMaiUser);
            rabKhuyenMai = itemView.findViewById(R.id.rabKhuyenMai);

        }
    }
}

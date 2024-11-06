package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.activity.user.OrderProductActivity;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.Topping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RCToppingAdapter extends RecyclerView.Adapter<RCToppingAdapter.ToppingViewHolder>{

    private Context context;
    private List<Topping> listTopping;
    private List<Topping> selectedToppings = new ArrayList<>();
    private OnToppingSelectedListener listener;

    public interface OnToppingSelectedListener {
        void onToppingSelected(List<Topping> selectedToppings);
    }

//    public RCToppingAdapter(Context context, List<Topping> listTopping) {
//        this.context = context;
//        this.listTopping = listTopping;
//    }

    public RCToppingAdapter(Context context, List<Topping> toppingList, OnToppingSelectedListener listener) {
        this.context = context;
        this.listTopping = toppingList;
        this.listener = listener;
    }

    public void setData(List<Topping> list){
        this.listTopping = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_topping, parent, false);
        return new RCToppingAdapter.ToppingViewHolder(view);
    }


//    @Override
//    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
//        Topping topping = listTopping.get(position);
//        DecimalFormat decimalFormat = new DecimalFormat("#,###");
//        String gia_topping = decimalFormat.format(topping.getGiaTopping());
//        holder.rc_topping_name.setText(topping.getTopping_name());
//        holder.rc_topping_cost.setText(gia_topping + "vnd");
//
//        // Xử lý click topping
//        holder.itemView.setOnClickListener(v -> {
//            Log.d("RCToppingAdapter", "Item clicked: " + topping.getTopping_name());
//            if (selectedToppings.contains(topping)) {
//                removeFromSelectedToppings(topping); // Xóa topping khỏi danh sách đã chọn
//            } else {
//                addToSelectedToppings(topping); // Thêm topping vào danh sách đã chọn
//            }
//            listener.onToppingSelected(selectedToppings); // Gọi callback
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = listTopping.get(position);
        if (topping == null) {
            return;
        }

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.rc_topping_name.setText(topping.getTopping_name());
        holder.rc_topping_cost.setText(decimalFormat.format(topping.getGiaTopping()) + "vnd");

        holder.rc_topping_cb.setOnCheckedChangeListener(null); // Prevents triggering onCheckedChangeListener during recycling
        holder.rc_topping_cb.setChecked(selectedToppings.contains(topping));

        holder.rc_topping_cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedToppings.add(topping);
            } else {
                selectedToppings.remove(topping);
            }

            // Notify the listener about the topping selection
            listener.onToppingSelected(selectedToppings);
        });
    }


    // Thêm các phương thức để thêm/xóa topping
    private void addToSelectedToppings(Topping topping) {
        selectedToppings.add(topping);
        notifyItemChanged(selectedToppings.indexOf(topping)); // Cập nhật item
    }

    private void removeFromSelectedToppings(Topping topping) {
        selectedToppings.remove(topping);
        notifyItemChanged(selectedToppings.indexOf(topping)); // Cập nhật item
    }

    // Thêm phương thức để lấy danh sách topping đã chọn
    public List<Topping> getSelectedToppings() {
        return selectedToppings;
    }

    @Override
    public int getItemCount() {
        if(listTopping != null){
            return  listTopping.size();
        }
        return 0;
    }

    public class ToppingViewHolder extends RecyclerView.ViewHolder {

        private TextView rc_topping_name, rc_topping_cost;
        private CheckBox rc_topping_cb;

        public ToppingViewHolder(@NonNull View itemView) {
            super(itemView);

            rc_topping_name = itemView.findViewById(R.id.rc_topping_name);
            rc_topping_cost = itemView.findViewById(R.id.rc_topping_cost);
            rc_topping_cb = itemView.findViewById(R.id.rc_topping_cb);
        }
    }
}

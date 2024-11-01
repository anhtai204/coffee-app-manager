package com.example.coffeeappmanage.activity.RecyclerProduct;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.Product;
import com.example.coffeeappmanage.model.Topping;

import java.text.DecimalFormat;
import java.util.List;

public class RCToppingAdapter extends RecyclerView.Adapter<RCToppingAdapter.ToppingViewHolder>{

    private Context context;
    private List<Topping> listTopping;

    public RCToppingAdapter(Context context, List<Topping> listTopping) {
        this.context = context;
        this.listTopping = listTopping;
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

    @Override
    public void onBindViewHolder(@NonNull ToppingViewHolder holder, int position) {
        Topping topping = listTopping.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String gia_topping = decimalFormat.format(topping.getGiaTopping());
        holder.rc_topping_name.setText(topping.getTopping_name());
        holder.rc_topping_cost.setText(gia_topping+"vnd");
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

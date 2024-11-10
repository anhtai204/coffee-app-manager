package com.example.coffeeappmanage.activity.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffeeappmanage.R;
import com.example.coffeeappmanage.model.TheLoai;

import java.util.List;

public class TheLoaiAdapter extends ArrayAdapter<TheLoai> {


    public TheLoaiAdapter(@NonNull Context context, int resource, @NonNull List<TheLoai> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_the_loai_selected, parent, false);
        TextView tvTheLoai = convertView.findViewById(R.id.tv_the_loai_selected);

        TheLoai theLoai = this.getItem(position);
        if(theLoai!=null){
            tvTheLoai.setText(theLoai.getTen_the_loai());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_the_loai, parent, false);
        TextView tvTheLoai = convertView.findViewById(R.id.tv_the_loai);

        TheLoai theLoai = this.getItem(position);
        if(theLoai!=null){
            tvTheLoai.setText(theLoai.getTen_the_loai());
        }
        return convertView;
    }
}

package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.Item;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyviewHolder> {
    Context context;
    List<Item> itemList;

    public DetailAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public DetailAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.mtvTensp.setText(item.getFood_name()+"");
        holder.mtvSoLuong.setText("Số lượng: " + item.getSize() +"");
        holder.mtvGia.setText("Giá: "+item.getPrice()+"");
        Glide.with(context).load(item.getImage_food()).into(holder.mimgOrderDetail);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView mimgOrderDetail;
        TextView mtvTensp, mtvSoLuong, mtvGia;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            mimgOrderDetail = itemView.findViewById(R.id.imgOrderDetail);
            mtvTensp = itemView.findViewById(R.id.tvTensp);
            mtvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            mtvGia = itemView.findViewById(R.id.tvGia);
        }
    }
}

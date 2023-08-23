package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.Order;

import java.util.List;
public class OrderAdapter extends RecyclerView.Adapter <OrderAdapter.MyviewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Order> listorder;

    public OrderAdapter(Context context, List<Order> listorder) {
        this.context = context;
        this.listorder = listorder;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Order order = listorder.get(position);
        holder.mtvOrder.setText("Đơn hàng: "+order.getId_order());
        holder.mtvUser.setText("Người đặt: "+order.getFull_name());
        holder.mtvAddress.setText("Địa chỉ: "+order.getAddress());
        holder.mtvTong.setText("Tổng cộng: "+order.getTotal()+"đ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(order.getDate_created());
        holder.mtvDate.setText("Ngày mua: " + formattedDate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.mrcvOrderDetail.getContext(), LinearLayoutManager.VERTICAL, false
        );
        layoutManager.setInitialPrefetchItemCount(order.getItem().size());

        DetailAdapter detailAdapter = new DetailAdapter(context,order.getItem());
        holder.mrcvOrderDetail.setLayoutManager(layoutManager);
        holder.mrcvOrderDetail.setAdapter(detailAdapter);
        holder.mrcvOrderDetail.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listorder.size();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView mtvOrder,mtvAddress,mtvTong,mtvDate,mtvUser;
        RecyclerView mrcvOrderDetail;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            mtvOrder = itemView.findViewById(R.id.tvOrder);
            mtvAddress = itemView.findViewById(R.id.tvAddress);
            mtvTong = itemView.findViewById(R.id.tvTong);
            mtvDate = itemView.findViewById(R.id.tvDate);
            mtvUser = itemView.findViewById(R.id.tvUser);
            mrcvOrderDetail = itemView.findViewById(R.id.rcvOrderDetail);

        }
    }
}

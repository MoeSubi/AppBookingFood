package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.appbookingfood.Interface.ItemClickListener;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.activity.DetailActivity;
import com.phucthinh.appbookingfood.model.Event.SuaXoaEvent;
import com.phucthinh.appbookingfood.model.FoodDetail;
import com.phucthinh.appbookingfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDetailAdapter.MyViewHolder> {

    Context context;
    List<FoodDetail> foodDetailList;

    public FoodDetailAdapter(Context context, List<FoodDetail> foodDetailList) {
        this.context = context;
        this.foodDetailList = foodDetailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_detail, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FoodDetail foodDetail = foodDetailList.get(position);
        holder.mtvName.setText(foodDetail.getFood_name());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.mtvPrice.setText("Giá: " + decimalFormat.format(Double.parseDouble(foodDetail.getPrice().trim())) + "đ");
        if (foodDetail.getImage_food().contains("http")){
            Glide.with(context).load(foodDetail.getImage_food()).into(holder.mimgImage);
        }else{
            String hinh = Utils.BASE_URL + "images/" + foodDetail.getImage_food();
            Glide.with(context).load(hinh).into(holder.mimgImage);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClik) {
                if (!isLongClik)
                {
                    //click
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("detail", foodDetail);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(foodDetail));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        TextView mtvName, mtvPrice;
        ImageView mimgImage;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvName = itemView.findViewById(R.id.item_tvFoodName);
            mtvPrice = itemView.findViewById(R.id.item_tvPrice);
            mimgImage = itemView.findViewById(R.id.item_imgFoodName);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(),"Sửa");
            menu.add(0,1,getAdapterPosition(),"Xóa");
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }

}

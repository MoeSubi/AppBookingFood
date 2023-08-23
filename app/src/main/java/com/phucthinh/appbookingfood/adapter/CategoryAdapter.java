package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.Interface.ItemClickListener;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.Event.EditRemoveCateEvent;
import com.phucthinh.appbookingfood.model.FoodCategory;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<FoodCategory> cateList;
    public CategoryAdapter(Context context, List<FoodCategory> cateList) {
        this.context = context;
        this.cateList = cateList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        FoodCategory foodCategory = cateList.get(position);
        myViewHolder.mtvNameCate.setText(foodCategory.getCategory_name().trim());
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClik) {
                EventBus.getDefault().postSticky(new EditRemoveCateEvent(foodCategory));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {

        TextView mtvNameCate;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvNameCate = itemView.findViewById(R.id.tvNameCate);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,getAdapterPosition(),"Edit");
            menu.add(0,1,getAdapterPosition(),"Remove");
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}

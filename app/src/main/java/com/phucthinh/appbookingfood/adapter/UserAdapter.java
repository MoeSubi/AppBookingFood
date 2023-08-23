package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.phucthinh.appbookingfood.Interface.ItemClickListener;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.activity.DetailUserActivity;
import com.phucthinh.appbookingfood.model.Event.EditRemoveUserEvent;
import com.phucthinh.appbookingfood.model.User;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<User> userList;
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        User user = userList.get(position);
        myViewHolder.mtvFullName.setText(user.getFull_name().trim());
        myViewHolder.mtvIdRole.setText(String.valueOf(user.getId_role()));
        if(user.getId_role() == 1){
            myViewHolder.mtvIdRole.setText("Admin");
        }else{
            myViewHolder.mtvIdRole.setText("User");
        }
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClik) {
                if (!isLongClik)
                {
                    Intent intent = new Intent(context, DetailUserActivity.class);
                    intent.putExtra("detailUser", user);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    EventBus.getDefault().postSticky(new EditRemoveUserEvent(user));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnCreateContextMenuListener {
        TextView mtvFullName, mtvIdRole;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvFullName = itemView.findViewById(R.id.tvFullName);
            mtvIdRole = itemView.findViewById(R.id.tvIdRole);
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
            menu.add(0,0,getAdapterPosition(),"RemoveU");
        }
        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}

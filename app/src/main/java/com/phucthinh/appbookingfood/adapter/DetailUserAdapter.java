package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.Interface.ItemClickListener;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.User;

import java.util.List;

public class DetailUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<User> userListDetail;

    public DetailUserAdapter(Context context, List<User> userListDetail) {
        this.context = context;
        this.userListDetail = userListDetail;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        User userDetail = userListDetail.get(position);
        myViewHolder.mtvFullNameDet.setText(userDetail.getFull_name().trim());
        myViewHolder.mtvPhoneNumberDet.setText(userDetail.getPhone_number().trim());
        myViewHolder.mtvEmailDet.setText(userDetail.getEmail().trim());
        myViewHolder.mtvAddressDet.setText(userDetail.getAddress().trim());
        myViewHolder.mtvPasswordDet.setText(userDetail.getPassword().trim());
        myViewHolder.mtvRoleDet.setText(String.valueOf(userDetail.getId_role()));

    }

    @Override
    public int getItemCount() {
        return userListDetail.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mtvFullNameDet, mtvPhoneNumberDet, mtvEmailDet, mtvAddressDet, mtvPasswordDet, mtvRoleDet;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvFullNameDet = itemView.findViewById(R.id.tvFullNameDet);
            mtvPhoneNumberDet = itemView.findViewById(R.id.tvPhoneNumberDet);
            mtvEmailDet = itemView.findViewById(R.id.tvEmailDet);
            mtvAddressDet = itemView.findViewById(R.id.tvAddressDet);
            mtvPasswordDet = itemView.findViewById(R.id.tvPasswordDet);
            mtvRoleDet = itemView.findViewById(R.id.tvRoleDet);
        }

    }
}

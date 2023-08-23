package com.phucthinh.appbookingfood.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phucthinh.appbookingfood.Interface.IImageClickListener;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.Cart;
import com.phucthinh.appbookingfood.model.Event.TotalEvent;
import com.phucthinh.appbookingfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.mtvitemCartName.setText(cart.getFoodName());
        holder.mtvitemCartSize.setText(cart.getSize() + " ");
        Glide.with(context).load(cart.getImage()).into(holder.mimgitemCart);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.mtvitemCartPrice.setText(decimalFormat.format(cart.getPrice()) + "đ");
        long price = cart.getSize() * cart.getPrice();
        holder.mtvitemCartTotal.setText(decimalFormat.format(price) + "đ");
        holder.mchkitemCartCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Utils.arrCartPay.add(cart);
                    EventBus.getDefault().postSticky(new TotalEvent());
                }
                else
                {
                    for (int i = 0; i <Utils.arrCartPay.size(); i++)
                    {
                        if (Utils.arrCartPay.get(i).getId_detail() == cart.getId_detail())
                        {
                            Utils.arrCartPay.remove(i);
                            EventBus.getDefault().postSticky(new TotalEvent());
                        }
                    }
                }
            }
        });
        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int position, int giaTri) {
                if (giaTri == 1)
                {
                    if (cartList.get(position).getSize() > 1)
                    {
                        int newSize = cartList.get(position).getSize() - 1;
                        cartList.get(position).setSize(newSize);

                        holder.mtvitemCartSize.setText(cartList.get(position).getSize() + " ");
                        long price = cartList.get(position).getSize() * cartList.get(position).getPrice();
                        holder.mtvitemCartTotal.setText(decimalFormat.format(price) + "đ");
                        EventBus.getDefault().postSticky(new TotalEvent());
                    } else if (cartList.get(position).getSize() == 1)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Xác nhận xóa?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Utils.arrCart.remove(position);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TotalEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
                else if (giaTri == 2)
                {
                    if (cartList.get(position).getSize() < 11)
                    {
                        int newSize = cartList.get(position).getSize() + 1;
                        cartList.get(position).setSize(newSize);
                    }
                    holder.mtvitemCartSize.setText(cartList.get(position).getSize() + " ");
                    long price = cartList.get(position).getSize() * cartList.get(position).getPrice();
                    holder.mtvitemCartTotal.setText(decimalFormat.format(price) + "đ");
                    EventBus.getDefault().postSticky(new TotalEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mimgitemCart,mimgitemCartRemove,mimgitemCartAdd;
        TextView mtvitemCartName,mtvitemCartPrice,mtvitemCartSize,mtvitemCartTotal;
        IImageClickListener listener;
        CheckBox mchkitemCartCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mimgitemCart = itemView.findViewById(R.id.img_itemCart);
            mimgitemCartRemove = itemView.findViewById(R.id.img_itemCartRemove);
            mimgitemCartAdd = itemView.findViewById(R.id.img_itemCartAdd);
            mtvitemCartName = itemView.findViewById(R.id.tv_item_CartName);
            mtvitemCartPrice = itemView.findViewById(R.id.tv_item_CartPrice);
            mtvitemCartSize = itemView.findViewById(R.id.tv_item_CartSize);
            mtvitemCartTotal = itemView.findViewById(R.id.tv_item_CartTotal);
            mchkitemCartCheckBox = itemView.findViewById(R.id.chk_item_Cart);

            //event click
            mimgitemCartAdd.setOnClickListener(this);
            mimgitemCartRemove.setOnClickListener(this);
        }

        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v == mimgitemCartRemove)
            {
                //1 tru
                listener.onImageClick(v, getAdapterPosition(), 1);
            }
            else
            {
                //2 cong
                listener.onImageClick(v, getAdapterPosition(), 2);
            }
        }
    }
}

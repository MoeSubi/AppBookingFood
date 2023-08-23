package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.Cart;
import com.phucthinh.appbookingfood.model.FoodDetail;
import com.phucthinh.appbookingfood.utils.Utils;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    TextView mtvFoodName, mtvPrice, mtvDescription;
    Button mbtnAddCart;
    ImageView mimgDetail;
    Spinner mspnSize;
    Toolbar mtbDetail;

    FoodDetail foodDetail;
    NotificationBadge notificationBadge;

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        actionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        mbtnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
            }
        });
    }

    private void addCart() {
        if(Utils.arrCart.size() > 0)
        {
            boolean flag = false;
            int size = Integer.parseInt(mspnSize.getSelectedItem().toString());
            for(int i = 0; i <Utils.arrCart.size(); i++)
            {
                if (Utils.arrCart.get(i).getId_detail() == foodDetail.getId_detail())
                {
                    Utils.arrCart.get(i).setSize(size + Utils.arrCart.get(i).getSize());
                    flag = true;
                }
            }
            if (flag == false)
            {
                long price = Long.parseLong(foodDetail.getPrice().trim());
                Cart cart = new Cart();
                cart.setPrice(price);
                cart.setSize(size);
                cart.setId_detail(foodDetail.getId_detail());
                cart.setFoodName(foodDetail.getFood_name());
                cart.setImage(foodDetail.getImage_food());
                Utils.arrCart.add(cart);
            }
        }
        else
        {
            int size = Integer.parseInt(mspnSize.getSelectedItem().toString());
            long price = Long.parseLong(foodDetail.getPrice().trim());
            Cart cart = new Cart();
            cart.setPrice(price);
            cart.setSize(size);
            cart.setId_detail(foodDetail.getId_detail());
            cart.setFoodName(foodDetail.getFood_name());
            cart.setImage(foodDetail.getImage_food());
            Utils.arrCart.add(cart);
        }
        int totalItem = 0;
        for (int i = 0; i <Utils.arrCart.size(); i++)
        {
            totalItem = totalItem +Utils.arrCart.get(i).getSize();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        foodDetail = (FoodDetail) getIntent().getSerializableExtra("detail");
        mtvFoodName.setText(foodDetail.getFood_name());
        mtvDescription.setText(foodDetail.getDescription());
        Glide.with(getApplicationContext()).load(foodDetail.getImage_food()).into(mimgDetail);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mtvPrice.setText("Giá: " + decimalFormat.format(Double.parseDouble(foodDetail.getPrice().trim())) + "đ");
        Integer[] size = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, size);
        mspnSize.setAdapter(arrayAdapter);
    }

    private void initView() {
        mtvFoodName = findViewById(R.id.tvFoodName);
        mtvPrice = findViewById(R.id.tvPrice);
        mtvDescription = findViewById(R.id.tvDescription);
        mbtnAddCart = findViewById(R.id.btnAddCart);
        mimgDetail = findViewById(R.id.imgDetail);
        mspnSize = findViewById(R.id.spnSize);
        mtbDetail = findViewById(R.id.tbDetail);
        notificationBadge = findViewById(R.id.badge);
        frameLayout = findViewById(R.id.flCart);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
        if (Utils.arrCart != null)
        {
            int totalItem = 0;
            for (int i = 0; i <Utils.arrCart.size(); i++)
            {
                totalItem = totalItem +Utils.arrCart.get(i).getSize();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }
    private void actionToolBar() {
        setSupportActionBar(mtbDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.arrCart != null)
        {
            int totalItem = 0;
            for (int i = 0; i <Utils.arrCart.size(); i++)
            {
                totalItem = totalItem +Utils.arrCart.get(i).getSize();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
    }
}
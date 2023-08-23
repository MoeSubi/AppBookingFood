package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.CartAdapter;
import com.phucthinh.appbookingfood.model.Event.TotalEvent;
import com.phucthinh.appbookingfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    TextView mtvCartNull, mtvTotal;
    Toolbar mtbCart;
    RecyclerView mrcvCart;
    Button mbtnBuy;
    CartAdapter cartAdapter;
    long total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        intiView();
        initControl();
        if (Utils.arrCartPay != null)
        {
            Utils.arrCartPay.clear();
        }
        total();
    }

    private void total() {
        total = 0;
        if (Utils.arrCartPay != null) {
            for (int i = 0; i < Utils.arrCartPay.size(); i++) {
                total = total + (Utils.arrCartPay.get(i).getPrice() * Utils.arrCartPay.get(i).getSize());
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        mtvTotal.setText(decimalFormat.format(total) + "đ");
    }


    private void initControl() {
        setSupportActionBar(mtbCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mrcvCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvCart.setLayoutManager(layoutManager);
        if (Utils.arrCart.size() == 0)
        {
            mtvCartNull.setVisibility(View.VISIBLE);
        } else
        {
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.arrCart);
            mrcvCart.setAdapter(cartAdapter);
        }
        mbtnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("totalPrice", total);
                Utils.arrCart.clear();
                startActivity(intent);
            }
        });
    }

    private void intiView() {
        mtvCartNull = findViewById(R.id.tvCartNull);
        mtvTotal = findViewById(R.id.tvTotal);
        mtbCart = findViewById(R.id.tbCart);
        mrcvCart = findViewById(R.id.rcvCart);
        mbtnBuy = findViewById(R.id.btnBuy);
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void totalEvent(TotalEvent event)
    {
        if (event != null)
        {
            total();
        }
    }
}
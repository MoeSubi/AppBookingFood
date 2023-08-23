package com.phucthinh.appbookingfood.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.OrderAdapter;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class ViewOrderActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    RecyclerView mrcvDonHang;
    Toolbar mtbDonHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        initView();
        initToolbar();
        getOrder();

    }


    private void getOrder() {
        compositeDisposable.add(apiFood.viewOrder(Utils.user_current.getUser_id(),Utils.user_current.getId_role())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        OrderModel -> {
                            OrderAdapter adapter = new OrderAdapter(getApplicationContext(), OrderModel.getResult());
                            mrcvDonHang.setAdapter(adapter);
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Vui lòng đăng nhập để xem đơn hàng.", Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    private void initToolbar() {
        setSupportActionBar(mtbDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        mrcvDonHang = findViewById(R.id.rcvDonHang);
        mtbDonHang = findViewById(R.id.tbDonHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvDonHang.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PayActivity extends AppCompatActivity {
    Toolbar mtbCartPay;
    TextView mtvTotalPrice, mtvPhoneNumber, mtvEmail;
    EditText medtAddress;
    AppCompatButton mbtnOrder;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    long total;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initView();
        initControl();
        countItem();
    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i <Utils.arrCartPay.size(); i++)
        {
            totalItem = totalItem +Utils.arrCartPay.get(i).getSize();
        }
    }

    private void initControl() {
        setSupportActionBar(mtbCartPay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbCartPay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        total = getIntent().getLongExtra("totalPrice", 0);
        mtvTotalPrice.setText(decimalFormat.format(total));
        mtvEmail.setText(Utils.user_current.getEmail());
        mtvPhoneNumber.setText(Utils.user_current.getPhone_number());

        mbtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = medtAddress.getText().toString().trim();
                if (TextUtils.isEmpty(address))
                {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //check if user is logged in before proceeding with the order
                    if(Utils.user_current.getEmail() == null && Utils.user_current.getPassword() == null ){
                        Toast.makeText(getApplicationContext(), "Please login to order", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //post data
                    String strEmail = Utils.user_current.getEmail();
                    String strPhoneNumber = Utils.user_current.getPhone_number();
                    int userId = Utils.user_current.getUser_id();
                    String currentTime = getCurrentTimeFormatted();
                    Log.d("Đơn hàng", new Gson().toJson(Utils.arrCartPay));
                    compositeDisposable.add(apiFood.createOrder(strEmail,strPhoneNumber,String.valueOf(total),userId,address, totalItem, new Gson().toJson(Utils.arrCartPay), currentTime)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                        Utils.arrCartPay.clear();
                                        Intent intent = new Intent(getApplicationContext(), AfterLoginActivity.class);
                                        startActivity(intent);
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private String getCurrentTimeFormatted() {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();
        // Create a Date object using the current time
        Date currentDate = new Date(currentTimeMillis);
        // Format the Date object into the desired string representation
        return sdf.format(currentDate);
    }

    private void initView() {
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        mtbCartPay = findViewById(R.id.tbCartPay);
        mtvTotalPrice = findViewById(R.id.tvTotalPay);
        mtvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        mtvEmail = findViewById(R.id.tvEmail);
        mbtnOrder = findViewById(R.id.btnOrder);
        medtAddress = findViewById(R.id.edtAddress);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
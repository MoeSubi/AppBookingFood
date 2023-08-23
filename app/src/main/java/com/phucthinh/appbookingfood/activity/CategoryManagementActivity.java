package com.phucthinh.appbookingfood.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.CategoryAdapter;
import com.phucthinh.appbookingfood.model.Event.EditRemoveCateEvent;
import com.phucthinh.appbookingfood.model.FoodCategory;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryManagementActivity extends AppCompatActivity {

    RecyclerView mreViewManage;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    FoodCategory foodCategoryEditRem;
    ImageView mimgAddCate, mimgAdmin;
    //    AlertDialog dialog;
    List<FoodCategory> foodCategoryList;
    CategoryAdapter categoryAdapter;
    Toolbar mtbManageCate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);

        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);

        initView();
        initControl();
        getCateDetail();
        actionBar();
//        buildDialog();
        if (isConnected(this)) {
            getCateDetail();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }

    private void actionBar() {
        setSupportActionBar(mtbManageCate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbManageCate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    private void buildDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view =  getLayoutInflater().inflate(R.layout.dialog_item,null);
//        medtNameCate = findViewById(R.id.edtNameCate);
//        builder.setView(view);
//        builder.setTitle("Input category name")
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        addCate();
//                    }
//                });
//
//        dialog = builder.create();
//    }

    private void initView() {
        mimgAddCate = findViewById(R.id.imgAddCate);
        mreViewManage = findViewById(R.id.reViewManage);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mreViewManage.setHasFixedSize(true);
        mreViewManage.setLayoutManager(layoutManager);
        mtbManageCate = findViewById(R.id.tbManageCate);
    }

    private void initControl() {
        mimgAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AddCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getCateDetail() {
        compositeDisposable.add(apiFood.getFoodCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodCategoryModel -> {
                            if (foodCategoryModel.isSuccess())
                            {
                                foodCategoryList = foodCategoryModel.getResult();
                                categoryAdapter = new CategoryAdapter(getApplicationContext(), foodCategoryList);
                                mreViewManage.setAdapter(categoryAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void removeCate() {
        compositeDisposable.add(apiFood.rmvCate(foodCategoryEditRem.getId_cate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        addFoodModel -> {
                            if(addFoodModel.isSuccess()) {
                                Toast.makeText(getApplicationContext(), addFoodModel.getMessage(), Toast.LENGTH_SHORT).show();
                                getCateDetail();

                            }else {
                                Toast.makeText(getApplicationContext(), addFoodModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Log.d("log",throwable.getMessage());
                        }
                ));
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Edit")){
            editCate();
        }else if(item.getTitle().equals("Remove")) {
            removeCate();
        }
        return super.onContextItemSelected(item);
    }

    private void editCate() {
        Intent intent = new Intent(getApplicationContext(), AddCategoryActivity.class);
        intent.putExtra("edit", foodCategoryEditRem);
        startActivity(intent);
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventEditCateRemove(EditRemoveCateEvent event){
        if(event != null)
            foodCategoryEditRem = event.getFoodCategory();
    }
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
package com.phucthinh.appbookingfood.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.FoodCategoryAdapter;
import com.phucthinh.appbookingfood.adapter.FoodDetailAdapter;
import com.phucthinh.appbookingfood.model.Event.SuaXoaEvent;
import com.phucthinh.appbookingfood.model.FoodCategory;
import com.phucthinh.appbookingfood.model.FoodDetail;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanliSp extends AppCompatActivity {

    ImageButton mbtnThem;
    RecyclerView mrvQL;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    NavigationView mngvMain;
    ListView mlvMain;
    DrawerLayout mdlMain;
    Toolbar mtbQL;
    FoodCategoryAdapter foodCategoryAdapter;
    List<FoodCategory> foodCategoryList;
    List<FoodDetail> list;
    FoodDetailAdapter adapter;
    FoodDetail sanPhamSuaXoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanli_sp);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        initView();
        actionBar();
        initControl();
        getFoodDetail();

        if (isConnected(this)) {
            getFoodCategory();
            getFoodDetail();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }
    private void initControl(){
        mbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThemSP.class);
                startActivity(intent);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(mtbQL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbQL.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        mtbQL.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdlMain.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getEventClick() {
        mlvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent uuDai = new Intent(getApplicationContext(), UuDaiActivity.class);
                        uuDai.putExtra("id_cate", 2);
                        startActivity(uuDai);
                        break;
                    case 1:
                        Intent monMoi = new Intent(getApplicationContext(), UuDaiActivity.class);
                        monMoi.putExtra("id_cate", 3);
                        startActivity(monMoi);
                        break;
                    case 2:
                        Intent combo1Nguoi = new Intent(getApplicationContext(), UuDaiActivity.class);
                        combo1Nguoi.putExtra("id_cate", 4);
                        startActivity(combo1Nguoi);
                        break;
                    case 3:
                        Intent comboNhom = new Intent(getApplicationContext(), UuDaiActivity.class);
                        comboNhom.putExtra("id_cate", 5);
                        startActivity(comboNhom);
                        break;
                    case 4:
                        Intent burgerComMiY = new Intent(getApplicationContext(), UuDaiActivity.class);
                        burgerComMiY.putExtra("id_cate", 6);
                        startActivity(burgerComMiY);
                        break;
                    case 5:
                        Intent thucUongTrangMieng = new Intent(getApplicationContext(), UuDaiActivity.class);
                        thucUongTrangMieng.putExtra("id_cate", 7);
                        startActivity(thucUongTrangMieng);
                        break;
                    case 6:
                        Intent intent = new Intent(getApplicationContext(), ViewOrderActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        Intent tk= new Intent(getApplicationContext(), ThongKeActivity.class);
                        startActivity(tk);
                        break;
                    case 8:
                        Intent exit= new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(exit);
                        break;
                }
            }
        });
    }
    private void getFoodDetail() {
        compositeDisposable.add(apiFood.getFoodDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodDetailModel -> {
                            if (foodDetailModel.isSuccess())
                            {
                                list = foodDetailModel.getResult();
                                adapter = new FoodDetailAdapter(getApplicationContext(), list);
                                mrvQL.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }


    private void getFoodCategory() {
        compositeDisposable.add(apiFood.getFoodCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodCategoryModel -> {
                            if (foodCategoryModel.isSuccess()) {
                                foodCategoryList = foodCategoryModel.getResult();
                                foodCategoryList.add(new FoodCategory("8","Thống kê"));
                                foodCategoryList.add(new FoodCategory("9","Thoát"));
                                //khoi tao adapter
                                foodCategoryAdapter = new FoodCategoryAdapter(getApplicationContext(), foodCategoryList);
                                mlvMain.setAdapter(foodCategoryAdapter);
                            }
                        }
                ));
    }
    private void initView(){
        mbtnThem = (ImageButton) findViewById(R.id.btnThemSp);
        mrvQL = (RecyclerView) findViewById(R.id.rvQL);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mrvQL.setHasFixedSize(true);
        mrvQL.setLayoutManager(layoutManager);
        mngvMain = findViewById(R.id.ngvMain);
        mlvMain = findViewById(R.id.lvMain);
        mdlMain = findViewById(R.id.dlMain);
        mtbQL = findViewById(R.id.tbQL);
        foodCategoryList = new ArrayList<>();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Edit")){
            suaSanPham();
        }else if (item.getTitle().equals("Remove")){
            xoaSanPham();
        }
        return super.onContextItemSelected(item);
    }

    private void xoaSanPham() {
        compositeDisposable.add(apiFood.xoaSanPham(sanPhamSuaXoa.getId_detail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()){
                                Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                                getFoodDetail();
                            }else {
                                Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        },
                        throwable -> {
                            Log.d("log", throwable.getMessage());
                        }
                ));
    }

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(),ThemSP.class);
        intent.putExtra("sua", sanPhamSuaXoa);
        startActivity(intent);
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

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
        if (event != null){
            sanPhamSuaXoa = event.getFoodDetail();
        }
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
package com.phucthinh.appbookingfood.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.FoodCategoryAdapter;
import com.phucthinh.appbookingfood.adapter.FoodDetailAdapter;
import com.phucthinh.appbookingfood.model.FoodCategory;
import com.phucthinh.appbookingfood.model.FoodDetail;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Toolbar mtbMain;
    ViewFlipper mvfMain;
    RecyclerView mrcvMain;
    NavigationView mngvMain;
    ListView mlvMain;
    DrawerLayout mdlMain;
    FoodCategoryAdapter foodCategoryAdapter;
    List<FoodCategory> foodCategoryList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    List<FoodDetail> foodDetailList;
    FoodDetailAdapter foodDetailAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView mivLogin, mivSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);

        initView();
        actionBar();
        actionViewFlipper();
        initControl();

        if (isConnected(this)) {
            actionViewFlipper();
            getFoodCategory();
            getFoodDetail();
            getEventClick();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }
    private void initControl() {

    }
    private void getEventClick() {
        mlvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                switch (i)
                {
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
                        Intent donHang = new Intent(getApplicationContext(),ViewOrderActivity.class);
                        startActivity(donHang);
                        break;
                }
            }
        });
        mivLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
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
                                foodDetailList = foodDetailModel.getResult();
                                foodDetailAdapter = new FoodDetailAdapter(getApplicationContext(), foodDetailList);
                                mrcvMain.setAdapter(foodDetailAdapter);
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
                                //khoi tao adapter
                                foodCategoryAdapter = new FoodCategoryAdapter(getApplicationContext(), foodCategoryList);
                                mlvMain.setAdapter(foodCategoryAdapter);
                            }
                        }
                ));
    }

    private void actionViewFlipper() {
        int[] arrAd = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
        for (int i = 0; i < arrAd.length; i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(arrAd[i]);
            mvfMain.addView(imageView);
        }
        mvfMain.setFlipInterval(3000);
        mvfMain.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_int_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        mvfMain.setInAnimation(slide_in);
        mvfMain.setOutAnimation(slide_out);
    }

    private void actionBar() {
        setSupportActionBar(mtbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        mtbMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdlMain.openDrawer(GravityCompat.START);
            }
        });
        mivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(search);
            }
        });
    }



    private void initView() {
        mtbMain = findViewById(R.id.tbMain);
        mvfMain = findViewById(R.id.vfMain);
        mrcvMain = findViewById(R.id.rcvMain);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this , 2);
        mrcvMain.setLayoutManager(layoutManager);
        mrcvMain.setHasFixedSize(true);
        mngvMain = findViewById(R.id.ngvMain);
        mlvMain = findViewById(R.id.lvMain);
        mdlMain = findViewById(R.id.dlMain);
        badge = findViewById(R.id.badge);
        frameLayout = findViewById(R.id.flCart);
        mivLogin = findViewById(R.id.ivLogin);
        mivSearch = findViewById(R.id.ivSearch);

        //khoi tao list
        foodCategoryList = new ArrayList<>();
        foodDetailList = new ArrayList<>();
        if(Utils.arrCart == null)
        {
            Utils.arrCart = new ArrayList<>();
        }
        else
        {
            int totalItem = 0;
            for (int i = 0; i <Utils.arrCart.size(); i++)
            {
                totalItem = totalItem +Utils.arrCart.get(i).getSize();
            }
            badge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i <Utils.arrCart.size(); i++)
        {
            totalItem = totalItem +Utils.arrCart.get(i).getSize();
        }
        badge.setText(String.valueOf(totalItem));
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

}
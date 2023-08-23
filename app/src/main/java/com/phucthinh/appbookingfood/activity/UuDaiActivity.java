package com.phucthinh.appbookingfood.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.UuDaiAdapter;
import com.phucthinh.appbookingfood.model.FoodDetail;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UuDaiActivity extends AppCompatActivity {

    Toolbar mtbUuDai;
    RecyclerView mrcvUuDai;
    ApiFood apiFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int id_cate;
    UuDaiAdapter uuDaiAdapter;
    List<FoodDetail> foodDetailList;
    LinearLayoutManager layoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uu_dai);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);

        id_cate = getIntent().getIntExtra("id_cate", 2);

        initView();
        actionToolBar();
        getData(page);
        addEventLoad();
    }

    private void addEventLoad() {
        mrcvUuDai.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false)
                {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == foodDetailList.size() - 1)
                    {
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                foodDetailList.add(null);
                uuDaiAdapter.notifyItemInserted(foodDetailList.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remove null
                foodDetailList.remove(foodDetailList.size() - 1);
                uuDaiAdapter.notifyItemRemoved(foodDetailList.size());
                page = page + 1;
                getData(page);
                uuDaiAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiFood.getFood(page, id_cate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodDetailModel -> {
                            if (foodDetailModel.isSuccess())
                            {
                                if (uuDaiAdapter == null)
                                {
                                    foodDetailList = foodDetailModel.getResult();
                                    uuDaiAdapter = new UuDaiAdapter(getApplicationContext(), foodDetailList);
                                    mrcvUuDai.setAdapter(uuDaiAdapter);
                                }
                                else
                                {
                                    int vt = foodDetailList.size() - 1;
                                    int sladd = foodDetailModel.getResult().size();
                                    for (int i = 0; i <sladd; i++)
                                    {
                                        foodDetailList.add(foodDetailModel.getResult().get(i));
                                    }
                                    uuDaiAdapter.notifyItemRangeInserted(vt,sladd);
                                }
                            }
                            else
                            {
                                isLoading = true;
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void actionToolBar() {
        setSupportActionBar(mtbUuDai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (id_cate == 3)
        {
            actionBar.setTitle("Món Mới");
        } else if (id_cate == 4)
        {
            actionBar.setTitle("Combo 1 Người");
        }
        else if (id_cate == 5)
        {
            actionBar.setTitle("Combo Nhóm");
        }
        else if (id_cate == 6)
        {
            actionBar.setTitle("Burger - Cơm - Mì Ý");
        } else
        {
            actionBar.setTitle("Thức Uống & Tráng Miệng");
        }
        mtbUuDai.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mtbUuDai = findViewById(R.id.tbUuDai);
        mrcvUuDai = findViewById(R.id.rcvUuDai);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mrcvUuDai.setLayoutManager(layoutManager);
        mrcvUuDai.setHasFixedSize(true);
        foodDetailList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
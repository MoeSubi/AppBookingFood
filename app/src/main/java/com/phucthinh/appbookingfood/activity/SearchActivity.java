package com.phucthinh.appbookingfood.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class SearchActivity extends AppCompatActivity {

    Toolbar mtbSearch;
    RecyclerView mrcvSearch;
    EditText medtSearch;
    ApiFood apiFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    UuDaiAdapter uuDaiAdapter;
    List<FoodDetail> foodDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        actionToolBar();
    }

    private void initView() {
        foodDetailList = new ArrayList<>();
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);

        mtbSearch = findViewById(R.id.tbSearch);
        mrcvSearch = findViewById(R.id.rcvSearch);
        medtSearch = findViewById(R.id.edtSearch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrcvSearch.setHasFixedSize(true);
        mrcvSearch.setLayoutManager(layoutManager);

        medtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                {
                    foodDetailList.clear();
                    uuDaiAdapter = new UuDaiAdapter(getApplicationContext(), foodDetailList);
                    mrcvSearch.setAdapter(uuDaiAdapter);
                }
                else
                {
                    getDataSearch(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getDataSearch(String s) {
        foodDetailList.clear();
        compositeDisposable.add(apiFood.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        foodDetailModel -> {
                            if (foodDetailModel.isSuccess())
                            {
                                foodDetailList = foodDetailModel.getResult();
                                uuDaiAdapter = new UuDaiAdapter(getApplicationContext(), foodDetailList);
                                mrcvSearch.setAdapter(uuDaiAdapter);
                            } else
                            {
                                Toast.makeText(getApplicationContext(), foodDetailModel.getMessage(), Toast.LENGTH_SHORT).show();
                                foodDetailList.clear();
                                uuDaiAdapter.notifyDataSetChanged();
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void actionToolBar() {
        setSupportActionBar(mtbSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
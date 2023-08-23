package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.FoodCategory;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddCategoryActivity extends AppCompatActivity {
    Button mbtnAddCate;
    EditText mnameCate;
    Toolbar mtbAddCate;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    Toolbar mtbAddCategory;
    FoodCategory editCate;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        setContentView(R.layout.activity_add_category);

        initView();
        initData();
        actionBar();
        Intent intent = getIntent();
        editCate = (FoodCategory) intent.getSerializableExtra("edit");
        if(editCate == null){
            //add
            flag = false;
        }else{
            //edit
            flag = true;
            mtbAddCate.setTitle("Edit category");
            setSupportActionBar(mtbAddCate);
            mbtnAddCate.setText("Edit");
            mnameCate.setText(editCate.getCategory_name());
        }
    }

    private void actionBar() {
        setSupportActionBar(mtbAddCategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbAddCategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        mbtnAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == false){
                    addCate();
                    Intent intent = new Intent(getApplicationContext(),CategoryManagementActivity.class);
                    startActivity(intent);
                }else{
                    editCate();
                    Intent intent = new Intent(getApplicationContext(),CategoryManagementActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    private void editCate() {
        String getCateName = mnameCate.getText().toString().trim();

        if(TextUtils.isEmpty(getCateName)){
            Toast.makeText(getApplicationContext(), "Please fill the full information",Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiFood.editCate(getCateName, editCate.getId_cate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void initView() {
        mtbAddCate = findViewById(R.id.tbAddCate);
        mbtnAddCate = findViewById(R.id.btnAddCate);
        mnameCate = findViewById(R.id.nameCate);
        mtbAddCategory = findViewById(R.id.tbAddCate);
    }
    private void addCate() {
        String getCateName = mnameCate.getText().toString().trim();

        if(TextUtils.isEmpty(getCateName)){
            Toast.makeText(getApplicationContext(), "Please fill the full information",Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiFood.addCate(getCateName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if(messageModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Log.d("log",throwable.getMessage());
                            }
                    ));
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
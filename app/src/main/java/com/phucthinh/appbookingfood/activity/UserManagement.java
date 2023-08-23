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
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.adapter.UserAdapter;
import com.phucthinh.appbookingfood.model.Event.EditRemoveUserEvent;
import com.phucthinh.appbookingfood.model.User;
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

public class UserManagement extends AppCompatActivity  {
    RecyclerView mreViewManage;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    User userRem;
    ImageView mimgAddUser, mimgAdmin;
    List<User> userList;
    UserAdapter userAdapter;
    Toolbar mtbManageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        initView();
        initControl();
        getUserDetail();
        actionBar();
        if (isConnected(this)) {
            getUserDetail();
        } else {
            Toast.makeText(getApplicationContext(), "Không có internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }

    private void actionBar() {
        setSupportActionBar(mtbManageUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbManageUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mimgAddUser = findViewById(R.id.imgAddUser);
        mreViewManage = findViewById(R.id.reViewManage);
        mtbManageUser = findViewById(R.id.tbManageUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mreViewManage.setHasFixedSize(true);
        mreViewManage.setLayoutManager(layoutManager);
    }
    private void initControl() {
        mimgAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUser.class);
                startActivity(intent);
            }
        });
    }


    private void getUserDetail() {
        compositeDisposable.add(apiFood.getUser()
                //request 1 API
                .subscribeOn(Schedulers.io())
                //cho phep subscribe chay tren mainThread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess())
                            {
                                userList = userModel.getResult();
                                userAdapter = new UserAdapter(getApplicationContext(), userList);
                                mreViewManage.setAdapter(userAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    private void removeUser() {
        compositeDisposable.add(apiFood.rmvUser(userRem.getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if(messageModel.isSuccess()) {
                                Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                getUserDetail();

                            }else {
                                Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Log.d("log",throwable.getMessage());
                        }
                ));
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("RemoveU")){
            removeUser();
        }
        return super.onContextItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventEditRemoveUser(EditRemoveUserEvent event){
        if(event != null)
            userRem = event.getUser();
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
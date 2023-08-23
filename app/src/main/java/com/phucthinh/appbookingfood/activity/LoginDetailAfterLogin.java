package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.User;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginDetailAfterLogin extends AppCompatActivity {
    EditText mtxtFullNameAfter, mtxtPhoneNumberAfter, mtxtEmailAfter, mtxtAddressAfter;
    TextView mtxtNameAfter;
    Button mbtnEditU;
    ImageView mivMain, mivLogout;
    User user;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_detail_after_login);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        Paper.init(this);
        //logout
        if(Paper.book().read("user") !=null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        initView();
        initControl();
    }
    private void initView() {
        mtxtFullNameAfter = findViewById(R.id.txtFullNameAfter);
        mtxtFullNameAfter.setText(Utils.user_current.getFull_name());
        mtxtNameAfter = findViewById(R.id.txtNameAfter);
        mtxtNameAfter.setText(Utils.user_current.getFull_name());
        mtxtPhoneNumberAfter = findViewById(R.id.txtPhoneNumberAfter);
        mtxtPhoneNumberAfter.setText(Utils.user_current.getPhone_number());
        mtxtEmailAfter = findViewById(R.id.txtEmailAfter);
        mtxtEmailAfter.setText(Utils.user_current.getEmail());
        mtxtAddressAfter = findViewById(R.id.txtAddressAfter);
        mtxtAddressAfter.setText(Utils.user_current.getAddress());
        mbtnEditU = findViewById(R.id.btnEditU);
        mivMain = findViewById(R.id.ivMain);
        mivLogout = findViewById(R.id.ivLogoutt);
    }
    private void initControl() {
        mbtnEditU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(user);
            }


        });
        mivMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent afterLog = new Intent(getApplicationContext(), AfterLoginActivity.class);
                startActivity(afterLog);
                finish();
            }
        });
        mivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //del key user
                Paper.book().delete("user");
                Utils.user_current = new User();
                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logout);
                finish();

                Toast.makeText(getApplicationContext(),"Log out successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUser(User user) {
        String getFullName = mtxtFullNameAfter.getText().toString().trim();
        String getPhoneNumber = mtxtPhoneNumberAfter.getText().toString().trim();
        String getEmail = mtxtEmailAfter.getText().toString().trim();
        String getAddress = mtxtAddressAfter.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(getFullName) || TextUtils.isEmpty(getPhoneNumber)
                || TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getAddress)){
            Toast.makeText(getApplicationContext(), "Please fill the full information",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getFullName)){
            Toast.makeText(getApplicationContext(),"Full name not include number", Toast.LENGTH_SHORT).show();
        }else if (getPhoneNumber.length() != 10 ) {
            Toast.makeText(getApplicationContext(), "Phone number should be at least 10 characters in length", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailPattern)){
            Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiFood.editUserCustomer(getFullName, getPhoneNumber, getEmail, getAddress, user.getUser_id())
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

}
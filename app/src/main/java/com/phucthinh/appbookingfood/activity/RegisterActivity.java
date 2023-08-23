package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText mfull_name, memail, mphone_number, maddress, mpassword;
    Button mbtnRegister;
    ApiFood apiFood;
    TextView mtxtLogin;

    Toolbar mtbRegister;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(mtbRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initControl() {
        mtxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        String get_full_name = mfull_name.getText().toString().trim();
        String get_phone_number = mphone_number.getText().toString().trim();
        String get_email = memail.getText().toString().trim();
        String get_address = maddress.getText().toString().trim();
        String get_password = mpassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        int id_role = 2;
        if (TextUtils.isEmpty(get_full_name)){
            Toast.makeText(getApplicationContext(),"Please enter your full name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(get_phone_number)){
            Toast.makeText(getApplicationContext(),"Please enter your phone number", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(get_email)){
            Toast.makeText(getApplicationContext(),"Please enter your email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(get_address)){
            Toast.makeText(getApplicationContext(),"Please enter your address", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(get_password)){
            Toast.makeText(getApplicationContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(get_full_name)){
            Toast.makeText(getApplicationContext(),"Full name not include number", Toast.LENGTH_SHORT).show();
        }else if (get_password.length() < 8){
            Toast.makeText(getApplicationContext(),"Password should be at least 8 characters in length", Toast.LENGTH_SHORT).show();
        }else if (get_phone_number.length() !=10){
            Toast.makeText(getApplicationContext(),"Phone number should be at least 10 characters in length", Toast.LENGTH_SHORT).show();
        }else if (!get_email.matches(emailPattern)){
            Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_SHORT).show();
        } else{
            compositeDisposable.add(apiFood.getRegister(get_full_name,get_phone_number,get_email,get_address,get_password, id_role).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){
                                    Utils.user_current.setEmail(get_email);
                                    Utils.user_current.setPassword(get_password);
                                    finish();
                                } else{
                                    Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    private void initView() {
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        mfull_name = findViewById(R.id.fullName);
        memail = findViewById(R.id.email);
        mphone_number = findViewById(R.id.phoneNumber);
        maddress = findViewById(R.id.address);
        mpassword = findViewById(R.id.password);
        mtxtLogin = findViewById(R.id.txtLogin);
        mbtnRegister = findViewById(R.id.btnRegister);
        mtbRegister = findViewById(R.id.tbRegister);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
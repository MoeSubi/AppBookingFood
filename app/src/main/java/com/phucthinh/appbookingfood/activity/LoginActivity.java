package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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

public class LoginActivity extends AppCompatActivity {
    TextView mtxtRegister;
    EditText musername, mpassword;
    Button mbtnLogin;
    ApiFood apiFood;

    Toolbar mtbLogin;
    int id_role;
    boolean isLogin = false;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initControl();
        actionBar();
        id_role = getIntent().getIntExtra("id_role", 2);

    }

    private void actionBar() {
        setSupportActionBar(mtbLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initControl() {
        mtxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void initView() {
        Paper.init(this);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        mtxtRegister = findViewById(R.id.txtRegister);
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
        mbtnLogin = findViewById(R.id.btnLogin);
        mtbLogin = findViewById(R.id.tbLogin);

        //read to save for the next login
//        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
//            musername.setText(Paper.book().read("email"));
//            mpassword.setText(Paper.book().read("password"));

    }

    private void isLogin(String email, String password) {
        compositeDisposable.add(apiFood.getLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
                                isLogin = true;
                                Paper.book().write("isLogin", isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                // save data user
                                Paper.book().write("user", userModel.getResult().get(0));
                                User user = userModel.getResult().get(0);

                                Intent intent = new Intent(getApplicationContext(), user.getId_role() == 2 ? AfterLoginActivity.class : AdminActivity.class);

                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Fail to Login", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void Login(){
        String get_username = musername.getText().toString().trim();
        String get_password = mpassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(get_username)){
            Toast.makeText(getApplicationContext(),"Please enter your email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(get_password)) {
            Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
        }else if (!get_username.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
        }else if (get_password.length() < 8){
            Toast.makeText(getApplicationContext(),"Password should be at least 8 characters in length", Toast.LENGTH_SHORT).show();
        } else{
            //save data
//            Paper.book().write("email", get_username);
//            Paper.book().write("password", get_password);
            isLogin(get_username,get_password);
//            compositeDisposable.add(apiFood.getLogin(get_username, get_password)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            userModel -> {
//                                if(userModel.isSuccess()){
//                                    isLogin = true;
//                                    Paper.book().write("isLogin", isLogin);
//                                    Utils.user_current = userModel.getResult().get(0);
//                                    Intent intent = new Intent(getApplicationContext(), AfterLoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else{
//                                    Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
//                                }
//                            },
//                            throwable -> {
//                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
//                            }
//                    ));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current != null && Utils.user_current.getEmail() != null && Utils.user_current.getPassword() != null) {
            musername.setText(Utils.user_current.getEmail());
            mpassword.setText(Utils.user_current.getPassword());
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }

}
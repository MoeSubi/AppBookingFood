package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.User;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddUser extends AppCompatActivity {
    Spinner mspinnerRole;
    Toolbar mtbAddUser;
    int id_role = 0;
    Button mbtnAddUser;
    EditText mfullName, mphoneNumber, memail, maddress, mpassword;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    User editUser;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        initView();
        initData();
    }

    private void initData() {
        List<String> strList = new ArrayList<>();
        strList.add("Select role: ");
        strList.add("Admin");
        strList.add("Customer");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strList);
        mspinnerRole.setAdapter(adapter);
        mspinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_role = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mbtnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addUser();
                    Intent intent = new Intent(getApplicationContext(),UserManagement.class);
                    startActivity(intent);
            }
        });
    }
    private void addUser(){
        String getFullName = mfullName.getText().toString().trim();
        String getPhoneNumber = mphoneNumber.getText().toString().trim();
        String getEmail = memail.getText().toString().trim();
        String getAddress = maddress.getText().toString().trim();
        String getPassword = mpassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(getFullName) || TextUtils.isEmpty(getPhoneNumber) || TextUtils.isEmpty(getEmail)
                || TextUtils.isEmpty(getAddress)|| TextUtils.isEmpty(getPassword) || id_role == 0){
            Toast.makeText(getApplicationContext(), "Please fill the full information",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getFullName)){
            Toast.makeText(getApplicationContext(),"Full name not include number", Toast.LENGTH_SHORT).show();
        }else if (getPassword.length() < 8){
            Toast.makeText(getApplicationContext(),"Password should be at least 8 characters in length", Toast.LENGTH_SHORT).show();
        }else if (getPhoneNumber.length() !=10){
            Toast.makeText(getApplicationContext(),"Phone number should be at least 10 characters in length", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailPattern)){
            Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiFood.addUser(getFullName, getPhoneNumber,getEmail,getAddress,getPassword,(id_role))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            addFoodModel -> {
                                if(addFoodModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), addFoodModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), addFoodModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Log.d("log",throwable.getMessage());
                            }
                    ));
        }
    }
//    private void editUser(){
//        String getFullName = mfullName.getText().toString().trim();
//        String getPhoneNumber = mphoneNumber.getText().toString().trim();
//        String getEmail = memail.getText().toString().trim();
//        String getAddress = maddress.getText().toString().trim();
//        String getPassword = mpassword.getText().toString().trim();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        if(TextUtils.isEmpty(getFullName) || TextUtils.isEmpty(getPhoneNumber)
//                || TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getAddress)|| TextUtils.isEmpty(getPassword) || id_role == 0){
//            Toast.makeText(getApplicationContext(), "Please fill the full information",Toast.LENGTH_SHORT).show();
//        }else if (TextUtils.isDigitsOnly(getFullName)){
//            Toast.makeText(getApplicationContext(),"Full name not include number", Toast.LENGTH_SHORT).show();
//        }else if (getPassword.length() < 8){
//            Toast.makeText(getApplicationContext(),"Password should be at least 8 characters in length", Toast.LENGTH_SHORT).show();
//        }else if (getPhoneNumber.length() !=10){
//            Toast.makeText(getApplicationContext(),"Phone number should be at least 10 characters in length", Toast.LENGTH_SHORT).show();
//        }else if (!getEmail.matches(emailPattern)){
//            Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_SHORT).show();
//        }else{
//            compositeDisposable.add(apiFood.editUser(getFullName, getPhoneNumber,getEmail,getAddress,getPassword, id_role,editUser.getUser_id())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            messageModel -> {
//                                if(messageModel.isSuccess()) {
//                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            },
//                            throwable -> {
//                                Log.d("log",throwable.getMessage());
//                            }
//                    ));
//        }
//    }
    private void initView() {
        mspinnerRole = findViewById(R.id.spinnerRole);
        mtbAddUser = findViewById(R.id.tbAddUser);
        mbtnAddUser = findViewById(R.id.btnAddUser);
        mfullName = findViewById(R.id.fullNameUser);
        mphoneNumber = findViewById(R.id.phoneNumberUser);
        memail = findViewById(R.id.emailUser);
        maddress = findViewById(R.id.addressUser);
        mpassword = findViewById(R.id.passwordUser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
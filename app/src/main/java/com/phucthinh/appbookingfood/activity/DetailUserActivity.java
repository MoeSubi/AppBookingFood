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

public class DetailUserActivity extends AppCompatActivity {
    Spinner mspinRole;
    int id_role = 0;
    User userDetail;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiFood apiFood;
    Button mbtnEditUser;
    Toolbar mtbEdit;
    EditText medtFullNameAfter, medtPhoneNumberAfter, medtEmailAfter, medtAddressAfter, medtPasswordAfter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);

        initView();
        initData();
        initControl();
        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(mtbEdit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbEdit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mspinRole = findViewById(R.id.spinRole);
        medtFullNameAfter = findViewById(R.id.edtFullNameAfter);
        medtPhoneNumberAfter = findViewById(R.id.edtPhoneNumberAfter);
        medtEmailAfter = findViewById(R.id.edtEmailAfter);
        medtAddressAfter = findViewById(R.id.edtAddressAfter);
        mbtnEditUser = findViewById(R.id.btnEditUser);
        medtPasswordAfter = findViewById(R.id.edtPasswordAfter);
        mtbEdit = findViewById(R.id.tbEdit);
    }

    private void initData() {
        userDetail = (User) getIntent().getSerializableExtra("detailUser");
        List<String> strList = new ArrayList<>();
        strList.add("Admin");
        strList.add("Customer");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strList);
        mspinRole.setAdapter(adapter);
        mspinRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_role = i + 1;
//                String s = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(DetailUserActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(DetailUserActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
        medtFullNameAfter.setText(userDetail.getFull_name());
        medtPhoneNumberAfter.setText(userDetail.getPhone_number());
        medtEmailAfter.setText(userDetail.getEmail());
        medtAddressAfter.setText(userDetail.getAddress());
        medtPasswordAfter.setText(userDetail.getPassword());
    }
    private void initControl() {
        mbtnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(userDetail);
                Intent intent = new Intent(getApplicationContext(),UserManagement.class);
                startActivity(intent);
            }
        });
    }

    private void updateUser(User user){
        String getFullName = medtFullNameAfter.getText().toString().trim();
        String getPhoneNumber = medtPhoneNumberAfter.getText().toString().trim();
        String getEmail = medtEmailAfter.getText().toString().trim();
        String getAddress = medtAddressAfter.getText().toString().trim();
        String getPassword = medtPasswordAfter.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(getFullName) || TextUtils.isEmpty(getPhoneNumber)
                || TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getAddress) || TextUtils.isEmpty(getPassword) || id_role == 0 ){
            Toast.makeText(getApplicationContext(), "Please fill the full information",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isDigitsOnly(getFullName)){
            Toast.makeText(getApplicationContext(),"Full name not include number", Toast.LENGTH_SHORT).show();
        }else if (getPhoneNumber.length() != 10 ) {
            Toast.makeText(getApplicationContext(), "Phone number should be at least 10 characters in length", Toast.LENGTH_SHORT).show();
        }else if (getPassword.length() < 8){
                Toast.makeText(getApplicationContext(),"Password should be at least 8 characters in length", Toast.LENGTH_SHORT).show();
        }else if (!getEmail.matches(emailPattern)){
            Toast.makeText(getApplicationContext(),"Invalid email", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(apiFood.editUser(getFullName, getPhoneNumber, getEmail, getAddress, getPassword, (id_role), user.getUser_id())
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
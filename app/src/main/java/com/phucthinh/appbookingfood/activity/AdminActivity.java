package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.model.User;
import com.phucthinh.appbookingfood.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AdminActivity extends AppCompatActivity {
    Button mbtnAddCate, mbtnAdduser, mbtnAddFood,mbtnAddOrder;
    ImageView mivLogout, mivMain;
    TextView mtxtNameAdmin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText musername, mpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_management);
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
        mbtnAddCate= findViewById(R.id.btnAddCate);
        mbtnAdduser= findViewById(R.id.btnAddUser);
        mbtnAddFood = findViewById(R.id.btnAddFood);
        mbtnAddOrder = findViewById(R.id.btnAddOrder);
        mivMain = findViewById(R.id.ivMain);
        mivLogout = findViewById(R.id.ivLogout);
        mtxtNameAdmin = findViewById(R.id.txtNameAdmin);
        mtxtNameAdmin.setText(Utils.user_current.getFull_name());
        musername = findViewById(R.id.username);
        mpassword = findViewById(R.id.password);
    }

    private void initControl() {
        mbtnAddCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cate = new Intent(getApplicationContext(), CategoryManagementActivity.class);
                startActivity(cate);
            }
        });
        mbtnAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(getApplicationContext(), UserManagement.class);
                startActivity(user);
            }
        });
        mbtnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent food = new Intent(getApplicationContext(), QuanliSp.class);
                startActivity(food);
            }
        });
        mbtnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent order = new Intent(getApplicationContext(), ViewOrderActivity.class);
                startActivity(order);
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
                Toast.makeText(getApplicationContext(),"Log out successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}
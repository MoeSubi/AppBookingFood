package com.phucthinh.appbookingfood.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.phucthinh.appbookingfood.R;

public class UserDetailActivity extends AppCompatActivity {
    Toolbar mtbUserDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        mtbUserDetail = findViewById(R.id.tbUserDetail);

        actionBar();
    }

    private void actionBar() {
        setSupportActionBar(mtbUserDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtbUserDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
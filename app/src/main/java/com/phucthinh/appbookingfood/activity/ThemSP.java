package com.phucthinh.appbookingfood.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.phucthinh.appbookingfood.R;
import com.phucthinh.appbookingfood.databinding.ActivityThemSpBinding;
import com.phucthinh.appbookingfood.model.FoodDetail;
import com.phucthinh.appbookingfood.model.MessageModel;
import com.phucthinh.appbookingfood.retrofit.ApiFood;
import com.phucthinh.appbookingfood.retrofit.RetrofitClient;
import com.phucthinh.appbookingfood.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSP extends AppCompatActivity {

    Spinner mspinnerLoai;
    int loai = 0;
    ActivityThemSpBinding binding;
    ApiFood apiFood;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String mediaPath;
    FoodDetail foodUpdate;
    Button mbtnThemSp;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpBinding.inflate(getLayoutInflater());
        apiFood = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiFood.class);
        setContentView(binding.getRoot());
        initView();
        initData();

        Intent intent = getIntent();
        foodUpdate = (FoodDetail) intent.getSerializableExtra("sua");
        if(foodUpdate==null){
            // them sp
            flag = false;
        }else{
            // sua sp
            flag = true;
            binding.btnThemSp.setText("Sửa sản phẩm");
            // show data
            binding.edtMota.setText(foodUpdate.getDescription());
            binding.edtGia.setText(foodUpdate.getPrice());
            binding.edtTen.setText(foodUpdate.getFood_name());
            binding.edtHinh.setText(foodUpdate.getImage_food());
            binding.spinnerLoai.setSelection(foodUpdate.getId_cate());
        }

    }

    private void initData(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn loại");
        stringList.add("Ưu đãi");
        stringList.add("Món Mới");
        stringList.add("Combo 1 Người");
        stringList.add("Combo Nhóm");
        stringList.add("Buger - Cơm - Mì Ý");
        stringList.add("Thức Uống & Tráng Miệng");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,stringList);
        mspinnerLoai.setAdapter(adapter);
        mspinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnThemSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==false){
                    themsanpham();

                }else {
                    foodUpdate();
                    Intent intent = new Intent(getApplicationContext(),QuanliSp.class);
                    startActivity(intent);
                }
            }
        });

        binding.imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemSP.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();

            }
        });
    }

    private void foodUpdate() {
        String str_ten = binding.edtTen.getText().toString().trim();
        String str_gia = binding.edtGia.getText().toString().trim();
        String str_mota = binding.edtMota.getText().toString().trim();
        String str_hinhanh = binding.edtHinh.getText().toString().trim();

        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_mota) || TextUtils.isEmpty(str_hinhanh) || loai ==0){
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }else {
            compositeDisposable.add(apiFood.getUpdatetsp(str_ten,str_gia,str_hinhanh,str_mota,loai,foodUpdate.getId_detail())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),messageModel.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mediaPath = data.getDataString();
            uploadMultipleFiles();
            Log.d("log","onActivityResult:" + mediaPath);
        } else {
            Toast.makeText(getApplicationContext(), "Không có hình ảnh được chọn", Toast.LENGTH_SHORT).show();
        }
    }


    private void themsanpham() {
        String str_ten = binding.edtTen.getText().toString().trim();
        String str_gia = binding.edtGia.getText().toString().trim();
        String str_mota = binding.edtMota.getText().toString().trim();
        String str_hinhanh = binding.edtHinh.getText().toString().trim();

        if (TextUtils.isEmpty(str_ten) || TextUtils.isEmpty(str_gia) || TextUtils.isEmpty(str_mota) || TextUtils.isEmpty(str_hinhanh) || loai == 0) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
        } else {
            compositeDisposable.add(apiFood.getInsertsp(str_ten, str_gia, str_hinhanh, str_mota, (loai))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    // Nếu thành công, thực hiện chuyển activity ở đây
                                    Intent intent = new Intent(getApplicationContext(), QuanliSp.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor == null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result =cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void uploadMultipleFiles() {


        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<MessageModel> call = apiFood.uploadFile(fileToUpload1);
        call.enqueue(new Callback< MessageModel >() {
            @Override
            public void onResponse(Call < MessageModel > call, Response< MessageModel > response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        binding.edtHinh.setText(serverResponse.getName());
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }

            }
            @Override
            public void onFailure(Call < MessageModel > call, Throwable t) {
                Log.d("Log",t.getMessage());
            }
        });
    }

    private void initView(){
        mspinnerLoai = (Spinner)findViewById(R.id.spinnerLoai);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}